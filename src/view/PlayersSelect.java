package view;

import dao.DAO;
import model.Players;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayersSelect extends JFrame {

    private JPanel contentPane;
    private JTable PrincipalTable;
    private JTable team1Table;
    private JTable team2Table;
    private ArrayList<Players> players;
    private ArrayList<Integer> selectedPlayerT1 = new ArrayList<>();
    private ArrayList<Integer> selectedPlayerT2 = new ArrayList<>();
    List<Integer> team1 = new ArrayList<Integer>();
    List<Integer> team2 = new ArrayList<Integer>();
    private boolean autoSelect;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlayersSelect frame = new PlayersSelect(null, null, 0, 0);
                    frame.setVisible(true);
                    frame.setTitle("Ye Soccer");
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PlayersSelect(String name1, String name2, int id1, int id2) throws Exception {
        DAO dao = new DAO();
        players = dao.listarPlayers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 930, 493);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(209, 213, 219));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane PrincipalPane = new JScrollPane();
        PrincipalPane.setBounds(331, 83, 260, 287);
        contentPane.add(PrincipalPane);

        DefaultTableModel principalTableModel = new DefaultTableModel(
                new String[]{
                        "Nome", "Posição"
                },
                0
        );

        PrincipalTable = new JTable(principalTableModel);

        PrincipalTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int selectedRow = PrincipalTable.getSelectedRow();
                    Players selectedPlayer = players.get(selectedRow);

                }
            }
        });

        DefaultTableModel principalModel = (DefaultTableModel) PrincipalTable.getModel();
        for (Players jogador : players) {
            principalModel.addRow(new Object[]{jogador.getName(), jogador.getPosition()});
        }

        PrincipalPane.setViewportView(PrincipalTable);

        PrincipalPane.setViewportView(PrincipalTable);

        JScrollPane team1Pane = new JScrollPane();
        team1Pane.setBounds(10, 83, 242, 287);
        contentPane.add(team1Pane);

        DefaultTableModel team1TableModel = new DefaultTableModel(
                new String[]{
                        "Nome", "Posição"
                },
                0
        );

        team1Table = new JTable(team1TableModel);
        team1Pane.setViewportView(team1Table);

        JScrollPane team2Pane = new JScrollPane();
        team2Pane.setBounds(677, 83, 227, 287);
        contentPane.add(team2Pane);

        DefaultTableModel team2TableModel = new DefaultTableModel(
                new String[]{
                        "Nome", "Posição"
                },
                0
        );

        team2Table = new JTable(team2TableModel);
        team2Pane.setViewportView(team2Table);

        JLabel PrincipalPlayer = new JLabel("JOGADORES");
        PrincipalPlayer.setFont(new Font("Tahoma", Font.BOLD, 14));
        PrincipalPlayer.setLabelFor(PrincipalPane);
        PrincipalPlayer.setBounds(424, 59, 87, 14);
        contentPane.add(PrincipalPlayer);

        JLabel Team1Name = new JLabel(name1);
        Team1Name.setFont(new Font("Tahoma", Font.BOLD, 14));
        Team1Name.setBounds(83, 58, 87, 14);
        contentPane.add(Team1Name);

        JLabel team2Name = new JLabel(name2);
        team2Name.setFont(new Font("Tahoma", Font.BOLD, 14));
        team2Name.setBounds(736, 58, 87, 14);
        contentPane.add(team2Name);

//        JCheckBox checkBox = new JCheckBox("Distribuição Automática");
//        checkBox.setBounds(10, 407, 232, 23);
//        contentPane.add(checkBox);

        JButton newGame = new JButton("Criar Partida");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayerT2.size() < 7 || selectedPlayerT1.size() < 7) {
                    JOptionPane.showMessageDialog(null, "Insrira pelo menos 7 jogadores em cada time.");
                } else {
                    DAO.PlayersMatch(selectedPlayerT1, selectedPlayerT2, id1, id2);
                }
            }
        });

        JButton team1Add = new JButton("<<");
        team1Add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = PrincipalTable.getSelectedRow();
                System.out.println("Selected player: " + selectedRow);
                if (selectedPlayerT1.size() > 9) {
                    JOptionPane.showMessageDialog(null, "Quantidade maxima de jogadores (10) excedida.");
                } else if (selectedRow >= 0 && selectedRow < players.size()) {
                    Players selectedPlayer = players.get(selectedRow);
                    addPlayerToTable((DefaultTableModel) team1Table.getModel(), selectedPlayer, selectedPlayerT1);
                    principalTableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                }
            }
        });
        team1Add.setBounds(262, 153, 61, 23);
        contentPane.add(team1Add);

        JButton team1Remove = new JButton(">>");
        team1Remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = team1Table.getSelectedRow();
                System.out.println(selectedRow);
                if (selectedRow >= 0 && selectedRow < team1Table.getRowCount()) {
                    Players removedPlayer = getPlayerFromTable((DefaultTableModel) team1Table.getModel(), selectedRow);
                    principalModel.addRow(new Object[]{removedPlayer.getName(), removedPlayer.getPosition()});
                    removePlayerFromTable((DefaultTableModel) team1Table.getModel(), selectedRow, team1);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                }
            }
        });
        team1Remove.setBounds(260, 237, 61, 23);
        contentPane.add(team1Remove);

        JButton team2Remove = new JButton("<<");
        team2Remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = team2Table.getSelectedRow();
                //System.out.println(selectedRow);
                if (selectedRow >= 0 && selectedRow < team2Table.getRowCount()) {
                    Players removedPlayer = getPlayerFromTable((DefaultTableModel) team2Table.getModel(), selectedRow);
                    assert removedPlayer != null;
                    principalModel.addRow(new Object[]{removedPlayer.getName(), removedPlayer.getPosition()});
                    removePlayerFromTable((DefaultTableModel) team2Table.getModel(), selectedRow, selectedPlayerT2);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                }
            }
        });
        team2Remove.setBounds(604, 237, 61, 23);
        contentPane.add(team2Remove);

        JButton Team2Add = new JButton(">>");
        Team2Add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = PrincipalTable.getSelectedRow();
                System.out.println(selectedRow);
                if (selectedPlayerT2.size() > 9) {
                    JOptionPane.showMessageDialog(null, "Quantidade maxima de jogadores (10) excedida.");
                    System.out.println(selectedPlayerT2.size());
                } else if (selectedRow >= 0 && selectedRow < players.size()) {
                    Players selectedPlayer = players.get(selectedRow);
                    addPlayerToTable((DefaultTableModel) team2Table.getModel(), selectedPlayer, selectedPlayerT2);
                    principalTableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                }
            }
        });

        JButton AuthAdd = new JButton("Add. Automaticamente");
        AuthAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Ao escolher a distribibuição automatica você não poderá alterar as escalações dos times, Deseja continuar?", "Distruibuição automatica", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    System.out.println("yes");
                }
                distributePlayersAutomatically(id1, id2);
            }
        });
        AuthAdd.setBounds(10, 407, 175, 23);
        contentPane.add(AuthAdd);

        Team2Add.setBounds(606, 153, 61, 23);
        contentPane.add(Team2Add);

        newGame.setBounds(753, 407, 151, 23);
        contentPane.add(newGame);


    }

    // Método para adicionar um jogador a uma tabela específica
    private void addPlayerToTable(DefaultTableModel tableModel, Players player, List team) {
        tableModel.addRow(new Object[]{player.getName(), player.getPosition()});
        team.add(player.getId());
        team.clear();
    }


    // Método para remover um jogador de uma tabela específica
    private void removePlayerFromTable(DefaultTableModel tableModel, int selectedRow, List team) {
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            Players removedPlayer = getPlayerFromTable(tableModel, selectedRow);
            if (removedPlayer != null) {
                tableModel.removeRow(selectedRow);
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    int playerId = getPlayerIdByName((String) tableModel.getValueAt(i, 0));
                    team.add(playerId);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um jogador.");
        }
    }

    // Método para obter o ID de um jogador pelo nome
    private int getPlayerIdByName(String playerName) {
        for (Players player : players) {
            if (player.getName().equals(playerName)) {
                return player.getId();
            }
        }
        return -1; // Se o jogador não for encontrado
    }

    // Método para obter um jogador de uma tabela específica
    private Players getPlayerFromTable(DefaultTableModel tableModel, int selectedRow) {
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            String playerName = (String) tableModel.getValueAt(selectedRow, 0);
            for (Players player : players) {
                if (player.getName().equals(playerName)) {
                    return player;
                }
            }
        }
        return null;
    }

    private void distributePlayersAutomatically(int id1, int id2) {

        if (players.size() < 14) {
            JOptionPane.showMessageDialog(null, "Número insuficiente de jogadores para distribuição automática.");
            return;
        }

        Collections.shuffle(players); // Embaralha a lista de jogadores

        // Limpa as tabelas dos times
        DefaultTableModel team1Model = (DefaultTableModel) team1Table.getModel();
        team1Model.setRowCount(0);

        DefaultTableModel team2Model = (DefaultTableModel) team2Table.getModel();
        team2Model.setRowCount(0);

//        selectedPlayerT1.clear();
//        selectedPlayerT2.clear();

        // Distribui 7 jogadores para cada time
        for (int i = 0; i < 7; i++) {
            Players selectedPlayer = players.get(i);
            addPlayerToTable(team1Model, selectedPlayer, selectedPlayerT1);
            team1.add(selectedPlayer.getId());
            selectedPlayerT1.add(selectedPlayer.getId());
        }

        for (int i = 7; i < 14; i++) {
            Players selectedPlayer = players.get(i);
            addPlayerToTable(team2Model, selectedPlayer, selectedPlayerT2);
            team2.add(selectedPlayer.getId());
        }

        players.subList(0, 14).clear();
        //DAO.PlayersMatch(team1, team2, id1, id2);
        // Atualize a tabela de jogadores principais
        DefaultTableModel principalModel = (DefaultTableModel) PrincipalTable.getModel();
        principalModel.setRowCount(0);

        for (Players jogador : players) {
            principalModel.addRow(new Object[]{jogador.getName(), jogador.getPosition()});
        }
    }
}