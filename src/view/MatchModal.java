package view;

import model.Match;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import static model.GameTable.match;

public class MatchModal extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField result1;
    private JTextField result2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Match match = null;
                    MatchModal frame = new MatchModal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MatchModal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 479, 350);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(209, 213, 219));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));



        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel firstTeam = new JLabel("TIME 1");
        firstTeam.setFont(new Font("Tahoma", Font.BOLD, 13));
        firstTeam.setBounds(76, 117, 91, 14);
        contentPane.add(firstTeam);

        result1 = new JTextField();
        result1.setBounds(52, 142, 86, 68);
        contentPane.add(result1);
        result1.setColumns(10);

        result2 = new JTextField();
        result2.setColumns(10);
        result2.setBounds(289, 142, 86, 68);
        contentPane.add(result2);

        JLabel secondTeam = new JLabel("TIME 1");
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
                System.out.println("funcionando");
            }
        });
        btnNewButton.setBounds(167, 250, 89, 23);
        contentPane.add(btnNewButton);
    }
}