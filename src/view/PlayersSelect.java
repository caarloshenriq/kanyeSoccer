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
import java.util.HashSet;
import java.util.Set;

public class PlayersSelect extends JFrame {

    private JPanel contentPane;
    private JTable PrincipalTable;
    private JTable team1Table;
    private JTable team2Table;
    private ArrayList<Players> player;
    private ArrayList<Integer> selectedPlayerT1 = new ArrayList<>();
    private ArrayList<Integer> selectedPlayerT2 = new ArrayList<>();
    boolean autoSelect = false;

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
        player = dao.listarPlayers();
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
                new String[]{"Nome", "Posição"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Retorne false para todas as colunas que você não quer que sejam editáveis
                return false; // Nenhuma célula é editável
            }
        };

        PrincipalTable = new JTable(principalTableModel);

        PrincipalTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int selectedRow = PrincipalTable.getSelectedRow();
                    Players selectedPlayer = player.get(selectedRow);

                }
            }
        });


        DefaultTableModel principalModel = (DefaultTableModel) PrincipalTable.getModel();
        for (Players jogador : player) {
            principalModel.addRow(new Object[]{jogador.getName(), jogador.getPosition()});
        }

        PrincipalPane.setViewportView(PrincipalTable);

        PrincipalPane.setViewportView(PrincipalTable);

        JScrollPane team1Pane = new JScrollPane();
        team1Pane.setBounds(10, 83, 242, 287);
        contentPane.add(team1Pane);

        DefaultTableModel team1TableModel = new DefaultTableModel(
                new String[]{"Nome", "Posição"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Nenhuma célula é editável
            }
        };

        team1Table = new JTable(team1TableModel);
        team1Pane.setViewportView(team1Table);

        JScrollPane team2Pane = new JScrollPane();
        team2Pane.setBounds(677, 83, 227, 287);
        contentPane.add(team2Pane);

        DefaultTableModel team2TableModel = new DefaultTableModel(
                new String[]{"Nome", "Posição"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Nenhuma célula é editável
            }
        };

        team2Table = new JTable(team2TableModel);
        team2Pane.setViewportView(team2Table);


        JLabel Team1Name = new JLabel(name1);
        Team1Name.setFont(new Font("Tahoma", Font.BOLD, 14));
        Team1Name.setBounds(83, 58, 87, 14);
        contentPane.add(Team1Name);

        JLabel team2Name = new JLabel(name2);
        team2Name.setFont(new Font("Tahoma", Font.BOLD, 14));
        team2Name.setBounds(736, 58, 87, 14);
        contentPane.add(team2Name);

        JButton newGame = new JButton("Salvar");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayerT2.size() < 7 || selectedPlayerT1.size() < 7) {
                    JOptionPane.showMessageDialog(null, "Insrira pelo menos 7 jogadores em cada time.");
                } else {
                    DAO.PlayersMatch(selectedPlayerT1, selectedPlayerT2, id1, id2);
                    dispose();
                    FirstView.main(new String[0]);
                }


            }
        });
        newGame.setForeground(new Color(255, 255, 255));
        newGame.setBackground(new Color(59, 130, 246));
        JButton team1Add = new JButton("<<");
        team1Add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = PrincipalTable.getSelectedRow();
                PrincipalTable.setRowSelectionInterval(selectedRow, selectedRow);
                String name = (String) PrincipalTable.getValueAt(selectedRow, 0);
                String position = (String) PrincipalTable.getValueAt(selectedRow, 1);
                int playerId = -1;

                for (Players player : player) {
                    if (player.getName().equals(name)) {
                        playerId = player.getId();
                        break;
                    }
                }
                if (selectedPlayerT1.size() > 9) {
                    JOptionPane.showMessageDialog(null, "Quantidade maxima de jogadores (10) excedida.");
                } else if (selectedRow >= 0 && selectedRow < player.size()) {
                    principalTableModel.removeRow(selectedRow);
                    DefaultTableModel model = (DefaultTableModel) team1Table.getModel();
                    model.addRow(new Object[]{name, position});
                    selectedPlayerT1.add(playerId);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                }
            }
        });
        team1Add.setForeground(new Color(255, 255, 255));
        team1Add.setBackground(new Color(59, 130, 246));
        team1Add.setBounds(262, 153, 61, 23);
        contentPane.add(team1Add);

        JButton team1Remove = new JButton(">>");
        team1Remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = team1Table.getSelectedRow();

                if (selectedRow >= 0 && selectedRow < team1Table.getRowCount()) {
                    String name = (String) team1Table.getValueAt(selectedRow, 0);
                    String position = (String) team1Table.getValueAt(selectedRow, 1);
                    int playerId = -1;

                    for (Players player : player) {
                        if (player.getName().equals(name)) {
                            playerId = player.getId();
                            break;
                        }
                    }

                    if (playerId != -1 && selectedPlayerT1.contains(playerId)) {
                        team1TableModel.removeRow(selectedRow);
                        DefaultTableModel model = (DefaultTableModel) PrincipalTable.getModel();
                        model.addRow(new Object[]{name, position});
                        selectedPlayerT1.remove(Integer.valueOf(playerId)); // Use Integer.valueOf para remover pelo valor e não pelo índice
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                }
            }
        });
        team1Remove.setForeground(new Color(255, 255, 255));
        team1Remove.setBackground(new Color(255, 0, 0));
        team1Remove.setBounds(260, 237, 61, 23);
        contentPane.add(team1Remove);

        JButton team2Remove = new JButton("<<");
        team2Remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int selectedRow = team2Table.getSelectedRow();

                if (selectedRow >= 0 && selectedRow < team2Table.getRowCount()) {
                    String name = (String) team2Table.getValueAt(selectedRow, 0);
                    String position = (String) team2Table.getValueAt(selectedRow, 1);
                    int playerId = -1;

                    for (Players player : player) {
                        if (player.getName().equals(name)) {
                            playerId = player.getId();
                            break;
                        }
                    }

                    if (playerId != -1 && selectedPlayerT2.contains(playerId)) {
                        team2TableModel.removeRow(selectedRow);
                        DefaultTableModel model = (DefaultTableModel) PrincipalTable.getModel();
                        model.addRow(new Object[]{name, position});
                        selectedPlayerT2.remove(Integer.valueOf(playerId)); // Use Integer.valueOf para remover pelo valor e não pelo índice
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                }
            }
        });
        team2Remove.setForeground(new Color(255, 255, 255));
        team2Remove.setBackground(new Color(255, 0, 0));
        team2Remove.setBounds(604, 237, 61, 23);
        contentPane.add(team2Remove);

        JButton Team2Add = new JButton(">>");
        Team2Add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = PrincipalTable.getSelectedRow();
                PrincipalTable.setRowSelectionInterval(selectedRow, selectedRow);
                String name = (String) PrincipalTable.getValueAt(selectedRow, 0);
                String position = (String) PrincipalTable.getValueAt(selectedRow, 1);
                int playerId = -1;

                for (Players player : player) {
                    if (player.getName().equals(name)) {
                        playerId = player.getId();
                        break;
                    }
                }
                if (selectedPlayerT2.size() > 9) {
                    JOptionPane.showMessageDialog(null, "Quantidade maxima de jogadores (10) excedida.");
                } else if (selectedRow >= 0 && selectedRow < player.size()) {
                    principalTableModel.removeRow(selectedRow);
                    DefaultTableModel model = (DefaultTableModel) team2Table.getModel();
                    model.addRow(new Object[]{name, position});
                    selectedPlayerT2.add(playerId);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                }
            }
        });
        Team2Add.setForeground(new Color(255, 255, 255));
        Team2Add.setBackground(new Color(59, 130, 246));
        JButton AuthAdd = new JButton("Add. Automaticamente");
        AuthAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (autoSelect) {
                    JOptionPane.showMessageDialog(null, "A distribuição automática só pode ser utilizada uma vez.");

                } else {

                    Set<Integer> selectedPlayerIDs = new HashSet<>();
                    selectedPlayerIDs.addAll(selectedPlayerT1);
                    selectedPlayerIDs.addAll(selectedPlayerT2);

                    int team1Size = selectedPlayerT1.size();
                    int team2Size = selectedPlayerT2.size();

                    for (Players selectedPlayer : player) {
                        if (team1Size < 7 && !selectedPlayerIDs.contains(selectedPlayer.getId())) {
                            team1TableModel.addRow(new Object[]{selectedPlayer.getName(), selectedPlayer.getPosition()});
                            selectedPlayerT1.add(selectedPlayer.getId());
                            selectedPlayerIDs.add(selectedPlayer.getId());
                            team1Size++;
                        }

                        if (team1Size >= 7) {
                            break;
                        }
                    }

                    for (Players selectedPlayer : player) {
                        if (team2Size < 7 && !selectedPlayerIDs.contains(selectedPlayer.getId())) {
                            team2TableModel.addRow(new Object[]{selectedPlayer.getName(), selectedPlayer.getPosition()});
                            selectedPlayerT2.add(selectedPlayer.getId());
                            selectedPlayerIDs.add(selectedPlayer.getId());
                            team2Size++;
                        }

                        if (team2Size >= 7) {
                            break;
                        }
                    }

                    DefaultTableModel principalModel = (DefaultTableModel) PrincipalTable.getModel();
                    principalModel.setRowCount(0);

                    for (Players selectedPlayer : player) {
                        if (!selectedPlayerIDs.contains(selectedPlayer.getId())) {
                            principalModel.addRow(new Object[]{selectedPlayer.getName(), selectedPlayer.getPosition()});
                        }
                    }

                    autoSelect = !autoSelect;
                }

            }

        });
        AuthAdd.setForeground(new Color(255, 255, 255));
        AuthAdd.setBackground(new Color(59, 130, 246));
        AuthAdd.setBounds(10, 407, 175, 23);
        contentPane.add(AuthAdd);

        Team2Add.setBounds(606, 153, 61, 23);
        contentPane.add(Team2Add);

        newGame.setBounds(753, 407, 151, 23);
        contentPane.add(newGame);
    }
}