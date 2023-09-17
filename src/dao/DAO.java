package dao;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.Conexao;
import model.Match;
import model.Team;
import view.FirstView;
import view.JLogin;
import model.Players;
import view.JPrincipal;

import static dao.BD.connection;

public class DAO {

    private static PreparedStatement preparedStatement = null;
    public static ResultSet resultSet = null;
    private static int idDoTime;

    private static String CHECK_TEAM_EXISTENCE = "SELECT COUNT(*) FROM player WHERE name = ?";
    private static String LOGIN = "SELECT * FROM player WHERE name = ? AND password = ?";

    private static String CREATE_PLAYER = "INSERT INTO player (name, password, number, position) VALUES (?,?,?,?);";

    private static String LIST_PLAYER = "SELECT id,name,gols,number,position FROM player WHERE 1+1";
    private static String RANKING_TEAMS = "SELECT name, vitorias FROM team ORDER BY vitorias DESC;";
    private static String CREATE_GAME = "INSERT INTO partidas (team1_id, team2_id, dategame) VALUES (?,?,?)";

    private static String getGames = "SELECT p.id, t1.name AS time1, team1_goals AS gol1, team2_goals AS gol2, t2.name AS time2 FROM partidas p JOIN team t1 ON p.team1_id = t1.id JOIN team t2 ON p.team2_id = t2.id;";

    public DAO() {

    }

    private void dispose() {

    }


    public void realizarLogin(Players players) {
        Connection connection = Conexao.getInstancia().abrirConexao();
        String query = LOGIN;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, players.getName());
            preparedStatement.setString(2, players.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Se o resultado contiver uma linha, isso significa que o login foi
                // bem-sucedido
                JOptionPane.showMessageDialog(null, "Login Bem sucedido.");
                dispose();
                FirstView.main(new String[0]);
            } else {
                // Caso contrário, o login falhou
                JOptionPane.showMessageDialog(null, "Nome do time e/ou senha inválidos");
                dispose();
                JLogin.main(new String[0]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }
    }

    public void cadastrarJogador(Players player) {
        try (Connection connection = Conexao.getInstancia().abrirConexao()) {
            String queryCheckExistence = CHECK_TEAM_EXISTENCE;
            String query = CREATE_PLAYER;

            System.out.println("criacão do time id: " + idDoTime);
            try {
                PreparedStatement checkExistenceStatement = connection.prepareStatement(queryCheckExistence);
                checkExistenceStatement.setString(1, player.getName());
                ResultSet resultSet = checkExistenceStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        JOptionPane.showMessageDialog(null,
                                "Nome de jogador já esta em uso, Tente outro!!");
                        return;
                    }
                }
                PreparedStatement createPlayerStatement = connection.prepareStatement(query);
                int i = 1;
                createPlayerStatement.setString(i++, player.getName());
                createPlayerStatement.setString(i++, player.getPassword());
                createPlayerStatement.setString(i++, player.getNumber());
                createPlayerStatement.setString(i++, player.getPosition());


                preparedStatement.execute();
                connection.commit();

                JOptionPane.showMessageDialog(null, "Jogador cadastrado com sucesso!!!");
                dispose();
                FirstView.main(new String[0]);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }
    }


    public ArrayList<Players> listarPlayers() throws Exception {
        Connection connection = Conexao.getInstancia().abrirConexao();
        ArrayList<Players> playersList = new ArrayList<>();
        String query = LIST_PLAYER;

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Players player = new Players(resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("gols"),
                        resultSet.getString("number"),
                        resultSet.getString("position"));

                playersList.add(player); // Adicione o jogador à lista
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }

        if (playersList.size() < 0) {
            JOptionPane.showMessageDialog(null, "Não há jogadores cadastrados.",
                    "", JOptionPane.WARNING_MESSAGE);
            throw new Exception("Não há jogadores cadastrados!");
        }

        return playersList;
    }


    public ArrayList<Team> listarRanking() throws Exception {
        Connection connection = Conexao.getInstancia().abrirConexao();
        ArrayList<Team> teamsList = new ArrayList<>();
        String query = RANKING_TEAMS;

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Team team = new Team();
                team.setName(resultSet.getString("name"));
                team.setVitorias(resultSet.getInt("vitorias"));

                teamsList.add(team);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }

        if (teamsList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há equipes cadastradas.",
                    "", JOptionPane.WARNING_MESSAGE);
            throw new Exception("Não há equipes cadastradas!");
        }

        return teamsList;
    }

    public static void createGame(int t1, int t2, String date) {
        try (Connection connection = Conexao.getInstancia().abrirConexao()) {
            String query = CREATE_GAME;

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                int i = 1;
                preparedStatement.setInt(i++, t1);
                preparedStatement.setInt(i++, t2);
                preparedStatement.setString(i, date);

                preparedStatement.execute();
                connection.commit();

                JOptionPane.showMessageDialog(null, "Partida criada com sucesso!!!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Match> listGame() throws Exception {
        Connection connection = Conexao.getInstancia().abrirConexao();
        ArrayList<Match> matchList = new ArrayList<>();
        String query = getGames;

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Match match = new Match(resultSet.getInt("id"),
                        resultSet.getString("time1"),
                        resultSet.getInt("gol1"),
                        resultSet.getString("time2"),
                        resultSet.getInt("gol2"));

                matchList.add(match); // Adicione o jogador à lista
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }

        if (matchList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há partidas cadastradas.",
                    "", JOptionPane.WARNING_MESSAGE);
            throw new Exception("Não há partidas cadastradas!");
        }

        return matchList;
    }

    public static void fecharConexao(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
