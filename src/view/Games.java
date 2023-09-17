package view;

import dao.DAO;
import model.GameTable;
import model.Match;
import model.ModelTable;
import model.Players;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Games extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable game;

    private ArrayList<Match> match;
    TableRowSorter<GameTable> rowSorter;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Games frame = new Games();
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
    public Games() throws Exception {
        DAO dao = new DAO();
        try {
            match = dao.listGame();
        } catch (Exception e) {

            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 930, 493);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(209, 213, 219));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("YE SOCCER");
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
        lblNewLabel.setBounds(378, 27, 207, 39);
        contentPane.add(lblNewLabel);

        JScrollPane gamePanel = new JScrollPane();
        gamePanel.setBounds(97, 77, 657, 343);
        contentPane.add(gamePanel);
        GameTable GameTable = new GameTable(match);
        game = new JTable();
        game.setModel(GameTable);
        gamePanel.setViewportView(game);


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