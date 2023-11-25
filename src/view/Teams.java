package view;

import model.ModelTable;
import model.Players;
import view.FirstView;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import dao.DAO;

import model.ModelTableRanking;
import model.Team;

import java.awt.Font;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Teams extends JFrame {

	private JPanel contentPane;
	private JTable game;

	private ArrayList<Team> team;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teams frame = new Teams();
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
	public Teams() throws Exception {
		team = DAO.listarRanking();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 493);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(209, 213, 219));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		JLabel lblNewLabel = new JLabel("YE SOCCER");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(396, 49, 194, 34);
		contentPane.add(lblNewLabel);

		JScrollPane gamePane = new JScrollPane();
		gamePane.setBounds(115, 139, 672, 230);
		contentPane.add(gamePane);
		ModelTableRanking modelTableRanking = new ModelTableRanking(team);
		game = new JTable();
		game.setModel(modelTableRanking);
		gamePane.setViewportView(game);

	}
}