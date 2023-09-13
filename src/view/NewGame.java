package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Choice;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import dao.*;

public class NewGame extends JFrame {

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
                    NewGame frame = new NewGame();
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
    public NewGame() {
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

        Choice team1 = new Choice();
        team1.setBounds(79, 201, 220, 20);
        contentPane.add(team1);

        team1.add("tabajara fc");
        team1.add("charo fc");

        Choice team2 = new Choice();
        team2.setBounds(599, 201, 220, 20);
        contentPane.add(team2);
        team2.add("tabajara fc");
        team2.add("charo fc");

        JLabel lblNewLabel_1 = new JLabel("VS.");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(432, 201, 68, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("TIME 1");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_2.setBounds(164, 181, 56, 14);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("TIME 2");
        lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_2_1.setBounds(690, 182, 56, 14);
        contentPane.add(lblNewLabel_2_1);

        JButton NewGame = new JButton("Criar Partida");
        NewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int t1;
                int t2;
                String time1 = (team1.getItem(team1.getSelectedIndex()));
                String time2 = (team2.getItem(team2.getSelectedIndex()));

                if(time1.equals("tabajara fc")){
                    t1 = 1;
                } else {
                    t1 = 2;
                }

                if(time2.equals("tabajara fc")){
                    t2 = 1;
                } else {
                    t2 = 2;
                }

                if(t1 == t2){
                    JOptionPane.showMessageDialog(null,
                            "Os times não podem ser iguais.");
                } else{
                    DAO.createGame(t1,t2);
                    dispose();
                    FirstView.main(new String[0]);
                }
            }
        });
        NewGame.setBounds(372, 376, 163, 34);
        NewGame.setForeground(new Color(255, 255, 255));
        NewGame.setBackground(new Color(59, 130, 246));
        contentPane.add(NewGame);

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                FirstView.main(new String[0]);
            }
        });
        backButton.setBounds(10, 11, 89, 23);
        backButton.setForeground(new Color(255, 255, 255));
        backButton.setBackground(new Color(59, 130, 246));
        contentPane.add(backButton);
    }
}