package view;

import dao.DAO;
import model.Players;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BestPlayer extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     *
     */
    private ArrayList<Players> player;
    public static void main(ArrayList<String> args, int idMatch) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BestPlayer frame = new BestPlayer(args, idMatch);
                    frame.setVisible(true);
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setTitle("Ye Soccer - Eleger jogadores");
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
    public BestPlayer(ArrayList<String> players, int idMatch) throws Exception {
        DAO dao = new DAO();
        player = dao.GetPlayersByMatch(idMatch);
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


        if (players != null && !players.isEmpty()) {
            Set<String> uniquePlayers = new HashSet<>(players);
            for (String playerName : uniquePlayers) {
                cboxPlayers.addItem(playerName);
            }
        } else {
            cboxPlayers.setEnabled(false);
        }

        JLabel lblNewLabel = new JLabel("Gol mais bonito:");
        lblNewLabel.setBounds(113, 63, 191, 14);
        contentPane.add(lblNewLabel);

        JLabel lblMelhorJogadorDa = new JLabel("Melhor jogador da partida:");
        lblMelhorJogadorDa.setBounds(113, 110, 191, 14);
        contentPane.add(lblMelhorJogadorDa);

        JComboBox bestPlayer = new JComboBox();
        bestPlayer.setBounds(113, 124, 203, 22);
        contentPane.add(bestPlayer);

        Set<String> uniquePlayerNames = new HashSet<>();
        for (Players p : player) {
            uniquePlayerNames.add(p.getName());
        }
        for (Players player : player) {
            bestPlayer.addItem(player.getName());
        }

        JButton btn = new JButton("Salvar");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DAO.BestGoal((String) cboxPlayers.getSelectedItem(), (String) bestPlayer.getSelectedItem());
                dispose();
                FirstView.main(new String[0]);

            }
        });
        btn.setForeground(new Color(255, 255, 255));
        btn.setBackground(new Color(59, 130, 246));
        btn.setBounds(168, 200, 89, 23);
        contentPane.add(btn);
    }
}
