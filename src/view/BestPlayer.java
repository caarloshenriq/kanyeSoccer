package view;

import dao.DAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BestPlayer extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(ArrayList<String> args, ArrayList<String> playerName) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BestPlayer frame = new BestPlayer(args, playerName);
                    frame.setVisible(true);
                    frame.setResizable(false);
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
    public BestPlayer(ArrayList<String> players, ArrayList<String> playersName) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 294);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel BestPlayer = new JLabel("Selecione os melhores jogador");
        BestPlayer.setFont(new Font("Tahoma", Font.BOLD, 15));
        BestPlayer.setBounds(98, 11, 249, 29);
        contentPane.add(BestPlayer);

        JComboBox cboxPlayers = new JComboBox();
        cboxPlayers.setBounds(113, 77, 203, 22);
        contentPane.add(cboxPlayers);
        for (String player : players) {
            cboxPlayers.addItem(player);
        }
        JLabel lblNewLabel = new JLabel("Quem fez o gol mais bonito:");
        lblNewLabel.setBounds(113, 63, 191, 14);
        contentPane.add(lblNewLabel);

        JLabel lblMelhorJogadorDa = new JLabel("Melhor jogador da partida:");
        lblMelhorJogadorDa.setBounds(113, 110, 191, 14);
        contentPane.add(lblMelhorJogadorDa);

        JComboBox bestPlayer = new JComboBox();
        bestPlayer.setBounds(113, 124, 203, 22);
        contentPane.add(bestPlayer);
        System.out.println(playersName);
        for (String player : playersName) {
            bestPlayer.addItem(player);
        }

        JButton btn = new JButton("Pronto");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DAO.BestGoal((String) cboxPlayers.getSelectedItem(), (String) bestPlayer.getSelectedItem());
                dispose();
                FirstView.main(new String[0]);
            }
        });
        btn.setBounds(168, 200, 89, 23);
        contentPane.add(btn);


    }
}
