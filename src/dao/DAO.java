package dao;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private static String LIST_PLAYER = "SELECT id,name,gols,number,position FROM player ORDER BY gols DESC";
    private static String RANKING_TEAMS = "SELECT name, vitorias, tecnico FROM team ORDER BY vitorias DESC;";
    private static String CREATE_GAME = "INSERT INTO partidas (team1_id, team2_id, dategame) VALUES (?,?,?)";

    private static String CONSULT_PARTIDA = " SELECT p.id, t1.name AS time1, team1_goals AS gol1, team2_goals AS gol2, t2.name AS time2, dategame as data, finalizada FROM partidas p JOIN team t1 ON p.team1_id = t1.id JOIN team t2 ON p.team2_id = t2.id WHERE p.id = ? ";
    private static String getGames = "SELECT p.id, t1.name AS time1, team1_goals AS gol1, team2_goals AS gol2, t2.name AS time2, dategame as data, finalizada FROM partidas p JOIN team t1 ON p.team1_id = t1.id JOIN team t2 ON p.team2_id = t2.id;";

    public DAO() {

    }

    private void dispose() {

    }


    public boolean realizarLogin(Players players) {
        Connection connection = Conexao.getInstancia().abrirConexao();
        String query = LOGIN;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, players.getName());
            preparedStatement.setString(2, players.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Login Bem sucedido.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Nome do usuário e/ou senha inválidos");
                return false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }
        return true;
    }


    public boolean cadastrarJogador(Players player) {
        try (Connection connection = Conexao.getInstancia().abrirConexao()) {
            String queryCheckExistence = CHECK_TEAM_EXISTENCE;
            String query = CREATE_PLAYER;

            try {
                PreparedStatement checkExistenceStatement = connection.prepareStatement(queryCheckExistence);
                checkExistenceStatement.setString(1, player.getName());
                ResultSet resultSet = checkExistenceStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        JOptionPane.showMessageDialog(null, "Nome de jogador já está em uso. Tente outro!!");
                        return false;
                    }
                }
                PreparedStatement createPlayerStatement = connection.prepareStatement(query);
                int i = 1;
                createPlayerStatement.setString(i++, player.getName());
                createPlayerStatement.setString(i++, player.getPassword());
                createPlayerStatement.setString(i++, player.getNumber());
                createPlayerStatement.setString(i, player.getPosition());

                createPlayerStatement.execute(); // Corrigido para usar createPlayerStatement
                connection.commit();
                JOptionPane.showMessageDialog(null, "Jogador cadastrado com sucesso!!!");
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }
        return false;
    }


    public ArrayList<Players> listarPlayers() throws Exception {
        Connection connection = Conexao.getInstancia().abrirConexao();
        ArrayList<Players> playersList = new ArrayList<>();
        String query = LIST_PLAYER;

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Players player = new Players(resultSet.getInt("id"),
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


    public static ArrayList<Team> listarRanking() throws Exception {
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
                team.setTecnico(resultSet.getString("tecnico"));
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
                        resultSet.getInt("gol2"),
                        resultSet.getString("data"),
                        resultSet.getBoolean("finalizada"));


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

    public Match consultarPartida(String id) throws Exception {
        Connection connection = Conexao.getInstancia().abrirConexao();
        Match partida = null;
        String query = CONSULT_PARTIDA;

        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                partida = new Match(resultSet.getInt("id"),
                        resultSet.getString("time1"),
                        resultSet.getInt("gol1"),
                        resultSet.getString("time2"),
                        resultSet.getInt("gol2"),
                        resultSet.getString("data"),
                        resultSet.getBoolean("finalizada"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }

        if (partida == null) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar a partida selecionada.",
                    "", JOptionPane.WARNING_MESSAGE);
            throw new Exception("Não foi possível localizar a partida selecionada.");
        }

        return partida;
    }

    public static void updateMatch(int id, int gols1, int gols2, int team1, int team2) {
        Connection connection = Conexao.getInstancia().abrirConexao();

        String query = "UPDATE partidas set team1_goals = ?, team2_goals = ?, finalizada = 1 WHERE id = ?";
        String updateWinner = "UPDATE team set vitorias = vitorias+ 1 WHERE id = ?";
        int winner;
        try {
            preparedStatement = connection.prepareStatement(query);

            int i = 1;

            preparedStatement.setInt(i++, gols1);
            preparedStatement.setInt(i++, gols2);
            preparedStatement.setInt(i, id);

            preparedStatement.execute();
            connection.commit();

            if (gols1 == gols2) {
                JOptionPane.showMessageDialog(null, "Partida alterada com sucesso");
            } else {

                if (gols1 > gols2) {
                    winner = team1;
                } else {
                    winner = team2;
                }
                preparedStatement = connection.prepareStatement(updateWinner);
                int l = 1;

                preparedStatement.setInt(l, winner);
                preparedStatement.execute();
                connection.commit();
                JOptionPane.showMessageDialog(null, "Partida alterada com sucesso");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }

    }

    public static void PlayersMatch(List<Integer> team1, List<Integer> team2, int team1Id, int team2Id) {
        Connection connection = Conexao.getInstancia().abrirConexao();
        String getMatchid = "SELECT id FROM partidas ORDER BY id DESC LIMIT 1";
        String insertQuery = "INSERT INTO playerPlay (playerId, teamId, matchId, situation) VALUES (?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            int matchId = 0;
            PreparedStatement getMatchIdStatement = connection.prepareStatement(getMatchid);
            ResultSet resultSet = getMatchIdStatement.executeQuery();

            if (resultSet.next()) {
                matchId = resultSet.getInt("id");
            }

            for (Integer playerId : team1) {
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, playerId);
                insertStatement.setInt(2, team1Id);
                insertStatement.setInt(3, matchId);
                insertStatement.setString(4,"Em casa");
                insertStatement.executeUpdate();
            }

            for (Integer playerId : team2) {
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, playerId);
                insertStatement.setInt(2, team2Id);
                insertStatement.setInt(3, matchId);
                insertStatement.setString(4, "visitante");
                insertStatement.executeUpdate();
            }

            connection.commit();
            JOptionPane.showMessageDialog(null, "Partida alterada com sucesso");

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.err.println("Erro ao inserir jogadores: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
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