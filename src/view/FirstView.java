package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FirstView extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FirstView frame = new FirstView();
                    frame.setVisible(true);
                    frame.setTitle("Ye Soccer");
                    ImageIcon image = new ImageIcon("ye-face.png");
                    frame.setResizable(false);
                    frame.setIconImage(image.getImage());
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public FirstView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 930, 493);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(209, 213, 219));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("YE SOCCER");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(406, 41, 194, 34);
        contentPane.add(lblNewLabel);

        JButton players = new JButton("JOGADORES");
        players.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                JPrincipal.main(new String[0]);
            }
        });
        players.setBounds(85, 212, 121, 34);
        players.setForeground(new Color(255, 255, 255));
        players.setBackground(new Color(59, 130, 246));
        contentPane.add(players);

        JButton team = new JButton("TIMES");
        team.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        team.setBounds(258, 212, 121, 34);
        team.setForeground(new Color(255, 255, 255));
        team.setBackground(new Color(59, 130, 246));
        contentPane.add(team);

        JButton newgame = new JButton("NOVA PARTIDA");
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                NewGame.main(new String[0]);
            }
        });
        newgame.setBounds(450, 212, 121, 34);
        newgame.setForeground(new Color(255, 255, 255));
        newgame.setBackground(new Color(59, 130, 246));
        contentPane.add(newgame);

        JButton games = new JButton("PARTIDAS");
        games.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Games.main(new String[0]);
            }
        });
        games.setBounds(635, 212, 121, 34);
        games.setForeground(new Color(255, 255, 255));
        games.setBackground(new Color(59, 130, 246));
        contentPane.add(games);
    }
}
