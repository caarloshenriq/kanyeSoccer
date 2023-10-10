package view;

import dao.DAO;
import model.Players;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayersModal extends JFrame {

    private JPanel contentPane;
    private JTable PrincipalTable;
    private JTable team1Table;
    private JTable team2Table;
    private ArrayList<Players> players;
    private ArrayList<Integer> selectedPlayerIds = new ArrayList<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PlayersModal frame = new PlayersModal(null, null, 0, 0);
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

    public PlayersModal(String name1, String name2, int id1, int id2) throws Exception {
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
                new String[] {
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

                    if (selectedRow >= 0 && selectedRow < players.size()) {
                        Players selectedPlayer = players.get(selectedRow);
                        int playerId = selectedPlayer.getId();
                        String playerName = selectedPlayer.getName();
                        String playerPosition = selectedPlayer.getPosition();

                        DefaultTableModel team1TableModel = (DefaultTableModel) team1Table.getModel();
                        DefaultTableModel team2TableModel = (DefaultTableModel) team2Table.getModel();

                        if (team1TableModel.getRowCount() < 7 && !selectedPlayerIds.contains(playerId)) {
                            team1TableModel.addRow(new Object[]{playerName, playerPosition});
                            selectedPlayerIds.add(playerId); // Adiciona o ID ao rastreador
                        } else if (!selectedPlayerIds.contains(playerId)) {
                            team2TableModel.addRow(new Object[]{playerName, playerPosition});
                            selectedPlayerIds.add(playerId); // Adiciona o ID ao rastreador
                        }

                        // Remove o jogador da tabela principal
                        principalTableModel.removeRow(selectedRow);
                    }
                }
            }
        });
        DefaultTableModel principalModel = (DefaultTableModel) PrincipalTable.getModel();
        for (Players jogador : players) {
            principalModel.addRow(new Object[]{jogador.getName(), jogador.getPosition()});
        }
        PrincipalPane.setViewportView(PrincipalTable);

        JScrollPane team1Pane = new JScrollPane();
        team1Pane.setBounds(10, 83, 260, 287);
        contentPane.add(team1Pane);

        DefaultTableModel team1TableModel = new DefaultTableModel(
                new String[] {
                        "Nome", "Posição"
                },
                0
        );

        team1Table = new JTable(team1TableModel);
        team1Pane.setViewportView(team1Table);

        JScrollPane team2Pane = new JScrollPane();
        team2Pane.setBounds(644, 83, 260, 287);
        contentPane.add(team2Pane);

        DefaultTableModel team2TableModel = new DefaultTableModel(
                new String[] {
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

        JLabel Team1Name = new JLabel("TIME 1");
        Team1Name.setFont(new Font("Tahoma", Font.BOLD, 14));
        Team1Name.setBounds(83, 58, 87, 14);
        contentPane.add(Team1Name);

        JLabel team2Name = new JLabel("TIME 2");
        team2Name.setFont(new Font("Tahoma", Font.BOLD, 14));
        team2Name.setBounds(736, 58, 87, 14);
        contentPane.add(team2Name);

        JCheckBox checkBox = new JCheckBox("Distribuição Automatica");
        checkBox.setBounds(10, 407, 232, 23);
        contentPane.add(checkBox);


        JButton newGame = new JButton("Criar Partida");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(checkBox.isSelected()) {
                    System.out.println("Checkado");
                }else {
                    System.out.println("nao");
                }
                System.out.println("Clicado");


            }
        });
        newGame.setBounds(753, 407, 151, 23);
        contentPane.add(newGame);
    }
}
