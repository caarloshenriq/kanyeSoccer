package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Menu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
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
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 493);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(209, 213, 219));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnPlayer = new JButton("Jogadores");
		btnPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JPrincipal jPrincipal = new JPrincipal();
				jPrincipal.setLocationRelativeTo(jPrincipal);
				jPrincipal.setVisible(true);
				jPrincipal.setTitle("Ye Soccer - jogador");
				ImageIcon image = new ImageIcon("ye-face.png");
				jPrincipal.setIconImage(image.getImage());
			}
		});
		btnPlayer.setBounds(40, 159, 126, 38);
		btnPlayer.setForeground(new Color(255, 255, 255));
		btnPlayer.setBackground(new Color(59, 130, 246));
		contentPane.add(btnPlayer);

		JButton btnPartidas = new JButton("Partidas");
		btnPartidas.setForeground(Color.WHITE);
		btnPartidas.setBackground(new Color(59, 130, 246));
		btnPartidas.setBounds(40, 237, 126, 38);
		contentPane.add(btnPartidas);

		//criação menu

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 914, 22);
		contentPane.add(menuBar);

		JMenu fileMenu = new JMenu("Arquivo");
		menuBar.add(fileMenu);

		JMenuItem exitMenu = new JMenuItem("Sair");
		exitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
			}
		});
		fileMenu.add(exitMenu);

		JMenu PlayersMenu = new JMenu("Jogadores");
		menuBar.add(PlayersMenu);

		JMenuItem playerRanking = new JMenuItem("Ranking");
		playerRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JPrincipal.main(new String[0]);
			}
		});
		PlayersMenu.add(playerRanking);


	}
}