package view;

import dao.DAO;
import model.Players;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import static dao.DAO.UpdateGoals;

public class SelectBestPlayers extends JFrame {

    private JPanel contentPane;
    private JTable PrincipalTalbe;
    private JTable team1Table;
    private JTable team2Table;

    private ArrayList<Players> Team1Players;
    private ArrayList<Players> Team2Players;

    private ArrayList<Integer> players = new ArrayList<>();
    private static ArrayList<String> jogadores = new ArrayList<>();

    /**
     * Launch the application.
     */
    public static void main(int args, String team1, String team2, int gols1, int gols2) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelectBestPlayers frame = new SelectBestPlayers(args, team1, team2, gols1, gols2);
                    frame.setVisible(true);
                    frame.setResizable(false);
                    frame.setTitle("Ye Soccer - Eleger Jogadores");
                    frame.setLocationRelativeTo(null);
                    ImageIcon image = new ImageIcon("ye-face.png");
                    frame.setIconImage(image.getImage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SelectBestPlayers(int idMatch, String team1, String team2, int gols1, int gols2) throws Exception {
        DAO dao = new DAO();
        Team1Players = dao.listarPlayersEmCasa(idMatch);
        Team2Players = dao.listarPlayersVisitantes(idMatch);
        final int[] selectedPlayerT1 = {0};
        final int[] selectedPlayerT2 = {0};
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

        PrincipalTalbe = new JTable();
        PrincipalTalbe.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Nome", "Posi\u00E7\u00E3o", "time"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        PrincipalPane.setViewportView(PrincipalTalbe);
        DefaultTableModel PrincipalModel = (DefaultTableModel) PrincipalTalbe.getModel();

        JScrollPane team1Pane = new JScrollPane();
        team1Pane.setBounds(10, 83, 242, 287);
        contentPane.add(team1Pane);

        team1Table = new JTable();
        team1Table.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Nome", "Posi\u00E7\u00E3o"
                }

        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Isso torna todas as células da team2Table não editáveis
            }
        });
        team1Pane.setViewportView(team1Table);
        DefaultTableModel team1Model = (DefaultTableModel) team1Table.getModel();
        for (Players jogador : Team1Players) {
            team1Model.addRow(new Object[]{jogador.getName(), jogador.getPosition()});
        }

        JScrollPane team2Pane = new JScrollPane();
        team2Pane.setBounds(677, 83, 227, 287);
        contentPane.add(team2Pane);
        JLabel Team1Name = new JLabel(team1);
        Team1Name.setFont(new Font("Tahoma", Font.BOLD, 14));
        Team1Name.setBounds(83, 58, 87, 14);
        contentPane.add(Team1Name);
        team2Table = new JTable();
        team2Table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nome", "Posi\u00E7\u00E3o"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Isso torna todas as células da team2Table não editáveis
            }
        });
        team2Pane.setViewportView(team2Table);
        DefaultTableModel team2Model = (DefaultTableModel) team2Table.getModel();
        for (Players jogador : Team2Players) {
            team2Model.addRow(new Object[]{jogador.getName(), jogador.getPosition()});
        }

        JLabel PrincipalPlayer = new JLabel("GOLEADORES");
        PrincipalPlayer.setFont(new Font("Tahoma", Font.BOLD, 14));
        PrincipalPlayer.setLabelFor(PrincipalPane);
        PrincipalPlayer.setBounds(410, 58, 137, 14);
        contentPane.add(PrincipalPlayer);

        JLabel team2Name = new JLabel(team2);
        team2Name.setFont(new Font("Tahoma", Font.BOLD, 14));
        team2Name.setBounds(736, 58, 87, 14);
        contentPane.add(team2Name);


        JButton newGame = new JButton("Salvar");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean result = UpdateGoals(players);

                    if (result) {
                        BestPlayer.main(jogadores, idMatch);
                        dispose();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        newGame.setBounds(753, 407, 151, 23);
        newGame.setForeground(new Color(255, 255, 255));
        newGame.setBackground(new Color(59, 130, 246));
        contentPane.add(newGame);


        JButton team1Add = new JButton(">>");
        team1Add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectRow = team1Table.getSelectedRow();

                if (selectRow < 0) {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                } else {
                    if (selectedPlayerT1[0] < gols1) {
                        Players selectedPlayer = Team1Players.get(selectRow);
                        PrincipalModel.addRow(new Object[]{selectedPlayer.getName(), selectedPlayer.getPosition(), selectedPlayer.getSituation()});
                        players.add(selectedPlayer.getId());
                        jogadores.add(selectedPlayer.getName());
                        selectedPlayerT1[0]++;
                    } else {
                        JOptionPane.showMessageDialog(null, "Você já atingiu a soma de gols do seu time nessa partida.");
                    }
                }
            }
        });
        team1Add.setForeground(new Color(255, 255, 255));
        team1Add.setBackground(new Color(59, 130, 246));
        team1Add.setBounds(262, 198, 61, 23);
        contentPane.add(team1Add);


        JButton team2Add = new JButton("<<");
        team2Add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectRow = team2Table.getSelectedRow();
                if (selectRow < 0) {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                } else {
                    if (selectedPlayerT2[0] < gols2) {
                        Players selectedPlayer = Team2Players.get(selectRow);
                        PrincipalModel.addRow(new Object[]{selectedPlayer.getName(), selectedPlayer.getPosition(), selectedPlayer.getSituation()});
                        players.add(selectedPlayer.getId());
                        jogadores.add(selectedPlayer.getName());
                        selectedPlayerT2[0]++;
                    } else {
                        JOptionPane.showMessageDialog(null, "Você já atingiu a soma de gols do seu time nessa partida.");
                    }
                }


            }
        });
        team2Add.setBounds(606, 198, 61, 23);
        contentPane.add(team2Add);
        team2Add.setForeground(new Color(255, 255, 255));
        team2Add.setBackground(new Color(59, 130, 246));

        JButton Remove = new JButton("Remover");
        Remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = PrincipalTalbe.getSelectedRow();
                PrincipalTalbe.setRowSelectionInterval(selectedRow, selectedRow);
                String team = (String) PrincipalTalbe.getValueAt(selectedRow, 2);

                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(null, "Selecione um jogador.");
                } else {
                    PrincipalModel.removeRow(selectedRow);
                    players.remove(selectedRow);
                    jogadores.remove(selectedRow);

                    if (team.equals("casa")) {
                        selectedPlayerT1[0]--;
                    } else {
                        selectedPlayerT2[0]--;
                    }
                }

            }
        });
        Remove.setBackground(Color.RED);
        Remove.setForeground(new Color(255, 255, 255));
        Remove.setBounds(331, 381, 260, 23);
        contentPane.add(Remove);
    }
}