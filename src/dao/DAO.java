package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import controller.Conexao;
import model.Team;
import view.FirstView;
import view.JLogin;
import model.Players;
import view.JPrincipal;

public class DAO {

    private static PreparedStatement preparedStatement = null;
    public static ResultSet resultSet = null;
    private static int idDoTime;

    private static String CHECK_TEAM_EXISTENCE = "SELECT COUNT(*) FROM team WHERE name = ? OR tecnico = ?";
    private static String LOGIN = "SELECT * FROM player WHERE name = ? AND password = ?";
    private static String CREATE_TEAM = "INSERT INTO team (name, password, tecnico) VALUES (?, ?, ?)";
    private static String CREATE_PLAYER = "INSERT INTO player (name, password, number, position) VALUES (?,?,?,?);";
    private static String CONSULT_PLAYER = "SELECT id,name,gols,number,position FROM player WHERE Id = ?";
    private static String LIST_PLAYER = "SELECT id,name,gols,number,position FROM player WHERE 1+1";
    private static String ALTER_TEAM = "UPDATE team SET name = ?, tecnico = ? WHERE id = ?";
    private static String ALTER_PLAYER = "UPDATE player SET name = ?, number = ?, position = ? WHERE id = ?";
    private static String DELETE_TEAM = "DELETE FROM team  WHERE id = ?";
    private static String DELETE_PLAYER = "DELETE FROM player  WHERE id = ?";
    private static String CONSULT_TEAM = "SELECT name, password FROM team WHERE name = ? AND password = ?";
    private static String RANKING_TEAMS = "SELECT name, vitorias FROM team ORDER BY vitorias DESC;";
    private static String CREATE_GAME = "INSERT INTO partidas (team1_id, team2_id) VALUES (?,?)";

    public DAO() {

    }

    private void dispose() {

    }

    public void cadastrarTeam(Team team) {
        Connection connection = Conexao.getInstancia().abrirConexao();

        String queryCheckExistence = CHECK_TEAM_EXISTENCE;
        String queryCreateTeam = CREATE_TEAM;
        try {
            // Check if name or technician already exist
            PreparedStatement checkExistenceStatement = connection.prepareStatement(queryCheckExistence);
            checkExistenceStatement.setString(1, team.getName());
            checkExistenceStatement.setString(2, team.getTecnico());
            ResultSet resultSet = checkExistenceStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    JOptionPane.showMessageDialog(null,
                            "Nome do time ou técnico já estão em uso. Não é possível criar o time.");
                    return;
                }
            }

            // Create team if name and technician are available
            PreparedStatement createTeamStatement = connection.prepareStatement(queryCreateTeam);
            int i = 1;
            createTeamStatement.setString(i++, team.getName());
            createTeamStatement.setString(i++, team.getPassword());
            createTeamStatement.setString(i++, team.getTecnico());

            createTeamStatement.executeUpdate(); // Use executeUpdate() for INSERT statements
            connection.commit();

            JOptionPane.showMessageDialog(null, "Time criado com sucesso!!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }
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
                idDoTime = resultSet.getInt("id");
                System.out.println("id do time é: " + idDoTime);
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
            String query = CREATE_PLAYER;

            System.out.println("criacão do time id: " + idDoTime);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                int i = 1;
                preparedStatement.setString(i++, player.getName());
                preparedStatement.setString(i++, player.getPassword());
                preparedStatement.setString(i++, player.getNumber());
                preparedStatement.setString(i++, player.getPosition());


                preparedStatement.execute();
                connection.commit();

                JOptionPane.showMessageDialog(null, "Jogador cadastrado com sucesso!!!");
                dispose();
                JPrincipal jPrincipal = new JPrincipal();
                jPrincipal.setLocationRelativeTo(jPrincipal);
                jPrincipal.setVisible(true);
                jPrincipal.setTitle("Ye Soccer");
                ImageIcon image = new ImageIcon("ye-face.png");
                jPrincipal.setIconImage(image.getImage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Players consultarPlayer(String id) throws Exception {
        Connection connection = Conexao.getInstancia().abrirConexao();
        Players players = null;
        String query = CONSULT_PLAYER;
        try {

            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, id);

            resultSet = preparedStatement.executeQuery();
            // String id, String name, String gols, String number, String position, String
            // teamId
            while (resultSet.next()) {
                players = new Players(resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("gols"),
                        resultSet.getString("number"),
                        resultSet.getString("position"), query);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }
        if (players == null) {
            JOptionPane.showMessageDialog(null, "Não foi possivel encontrar o jogador selecionado", "",
                    JOptionPane.WARNING_MESSAGE);
            throw new Exception("Não foi possivel localizar o Jogador selecionado");
        }
        return players;
    }

    public void alterarTeam(String id, Team team) {
        Connection connection = Conexao.getInstancia().abrirConexao();

        String query = ALTER_TEAM;
        try {

            preparedStatement = connection.prepareStatement(query);

            int i = 1;
            preparedStatement.setString(i++, team.getName());
            preparedStatement.setString(i++, team.getTecnico());
            preparedStatement.setString(i++, id);

            preparedStatement.execute();
            connection.commit();

            JOptionPane.showMessageDialog(null, "Time atualizado com sucesso!!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }
    }

    public void alterarPlayer(String id, Players player) {
        Connection connection = Conexao.getInstancia().abrirConexao();

        String query = ALTER_PLAYER;
        try {
            preparedStatement = connection.prepareStatement(query);

            int i = 1;
            preparedStatement.setString(i++, player.getName());
            preparedStatement.setString(i++, player.getNumber());
            preparedStatement.setString(i++, player.getPosition());
            preparedStatement.setString(i++, id);

            preparedStatement.execute();
            connection.commit();

            JOptionPane.showMessageDialog(null, "Jogador atualizado com sucesso!!!");
            dispose();
            JPrincipal jPrincipal = new JPrincipal();
            jPrincipal.setLocationRelativeTo(jPrincipal);
            jPrincipal.setVisible(true);
            jPrincipal.setTitle("Ye Soccer");
            ImageIcon image = new ImageIcon("ye-face.png");
            jPrincipal.setIconImage(image.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }
    }

    public void excluirTeam(String id) {
        Connection connection = Conexao.getInstancia().abrirConexao();

        String query = DELETE_TEAM;
        try {

            preparedStatement = connection.prepareStatement(query);
            // name, password, tecnico
            int i = 1;
            preparedStatement.setString(i++, id);

            preparedStatement.execute();
            connection.commit();

            JOptionPane.showMessageDialog(null, "Time excluido com sucesso!!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }
    }

    public void excluirPlayer(String id) {
        Connection connection = Conexao.getInstancia().abrirConexao();

        String query = DELETE_PLAYER;
        try {

            preparedStatement = connection.prepareStatement(query);
            // name, password, tecnico
            int i = 1;
            preparedStatement.setString(i++, id);

            preparedStatement.execute();
            connection.commit();

            JOptionPane.showMessageDialog(null, "Jogador excluido com sucesso!!!");
            JPrincipal jPrincipal = new JPrincipal();
            jPrincipal.setLocationRelativeTo(jPrincipal);
            jPrincipal.setVisible(true);
            jPrincipal.setTitle("Ye Soccer");
            ImageIcon image = new ImageIcon("ye-face.png");
            jPrincipal.setIconImage(image.getImage());
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
            preparedStatement = connection.prepareStatement(query + idDoTime);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Players player = new Players(resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("gols"),
                        resultSet.getString("number"),
                        resultSet.getString("position"), query);

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

    public Team consultarTeam(String teamName, String senhaCriptografada) throws Exception {
        Connection connection = Conexao.getInstancia().abrirConexao();
        Team team = null;
        String query = CONSULT_TEAM;

        try {
            preparedStatement = connection.prepareStatement(query);
            int i = 1;
            preparedStatement.setString(i++, teamName);
            preparedStatement.setString(i++, senhaCriptografada);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                team = new Team(resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("tecnico"), i); // Coloque aqui o nome da coluna correta
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao(connection);
        }

        if (team == null) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o time selecionado", "",
                    JOptionPane.WARNING_MESSAGE);
            throw new Exception("Não foi possível localizar o time selecionado");
        }

        return team;
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

    public static void createGame(int t1, int t2){
        try (Connection connection = Conexao.getInstancia().abrirConexao()) {
            String query = CREATE_GAME;

            System.out.println("criacão do time id: " + idDoTime);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                int i = 1;
                preparedStatement.setInt(i++, t1);
                preparedStatement.setInt(i++, t2);

                preparedStatement.execute();
                connection.commit();

                JOptionPane.showMessageDialog(null, "Partida criada com sucesso!!!");

            }
        } catch (Exception e) {
            e.printStackTrace();
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
