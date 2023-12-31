package view;

import dao.DAO;
import model.Match;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;

public class MatchModal extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField result1;
    private JTextField result2;
    public boolean finalizada = false;

    /**
     * Launch the application.
     * eturn
     */
    public static boolean main(String args, String team2, int gol1, int gol2, int id, String date) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MatchModal frame = new MatchModal(args, team2, gol1, gol2, id, date);
                    frame.setVisible(true);
                    frame.setTitle(args + " x " + team2);
                    ImageIcon image = new ImageIcon("ye-face.png");
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setIconImage(image.getImage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return false;
    }

    /**
     * Create the frame.
     */
    public MatchModal(String team1, String team2, int gol1, int gol2, int id, String date) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 479, 350);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(209, 213, 219));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel firstTeam = new JLabel(team1);
        firstTeam.setFont(new Font("Tahoma", Font.BOLD, 13));
        firstTeam.setBounds(76, 117, 91, 14);
        contentPane.add(firstTeam);

        result1 = new JTextField();
        result1.setBounds(52, 142, 86, 68);
        contentPane.add(result1);
        result1.setColumns(10);
        result1.setText(String.valueOf(gol1));

        result2 = new JTextField();
        result2.setColumns(10);
        result2.setBounds(289, 142, 86, 68);
        contentPane.add(result2);
        result2.setText(String.valueOf(gol2));

        JLabel secondTeam = new JLabel(team2);
        secondTeam.setFont(new Font("Tahoma", Font.BOLD, 13));
        secondTeam.setBounds(313, 117, 91, 14);
        contentPane.add(secondTeam);

        JLabel lblNewLabel_2 = new JLabel("X");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_2.setBounds(202, 142, 46, 82);
        contentPane.add(lblNewLabel_2);

        JButton btnNewButton = new JButton("SALVAR");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int team1id;
                int team2id;

                if (team1.equals("tabajara fc")) {
                    team1id = 1;
                } else {
                    team1id = 2;
                }

                if (team2.equals("tabajara fc")) {
                    team2id = 1;
                } else {
                    team2id = 2;
                }
                int gols1 = Integer.parseInt(result1.getText());
                int gols2 = Integer.parseInt(result2.getText());


                if (gols1 < 0 || gols2 < 0) {
                    JOptionPane.showMessageDialog(null, "Por favor, informe um resultado valido, o resultado não pode ser menor que 0");
                } else {
                    DAO.updateMatch(id, gols1, gols2, team1id, team2id);

                    if (gols1 == 0 && gols2 == 0) {
                        //JOptionPane.showMessageDialog(null, "Selecione o melhor jogador da partida");
                        BestPlayer.main(null, id);

                    } else {
                        JOptionPane.showMessageDialog(null, "Selecione os Jogadores que marcaram gol nessa partida");
                        SelectBestPlayers.main(id, team1, team2, gols1, gols2);
                    }
                    dispose();
                }
            }

        });

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Games.main(new String[0]);
            }
        });
        backButton.setBounds(10, 11, 89, 23);
        contentPane.add(backButton);
        backButton.setForeground(new Color(255, 255, 255));
        backButton.setBackground(new Color(59, 130, 246));

        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setBackground(new Color(59, 130, 246));
        btnNewButton.setBounds(167, 250, 89, 23);
        contentPane.add(btnNewButton);
    }
}
