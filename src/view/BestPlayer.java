package view;

import model.Players;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BestPlayer extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BestPlayer frame = new BestPlayer();
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
    public BestPlayer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 204);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel BestPlayer = new JLabel("Selecione o melhor jogador");
        BestPlayer.setFont(new Font("Tahoma", Font.BOLD, 15));
        BestPlayer.setBounds(113, 11, 249, 29);
        contentPane.add(BestPlayer);

        Choice c = new Choice();
        c.setBounds(113, 77, 203, 22);


        contentPane.add(c);



        JButton btn = new JButton("Pronto!");
        btn.setBounds(165, 133, 89, 23);
        contentPane.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.getItem(c.getSelectedIndex());
            }
        });
    }
}
