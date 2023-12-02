package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import dao.DAO;
import model.ModelTable;

import model.ModelTableRanking;
import model.Players;
import model.Team;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class JPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField searchPlayer;
	private JScrollPane playersPane;
	private JTable playersTable;
	private ArrayList<Players> players;
	private JTable teamTable;
	private TableRowSorter<ModelTable> rowSorter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JPrincipal frame = new JPrincipal();
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
	public JPrincipal() {
		DAO dao = new DAO();
		try {
			players = dao.listarPlayers();
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

		lblNewLabel = new JLabel("JOGADORES");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblNewLabel.setBounds(378, 27, 207, 39);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Pesquise por um jogador:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(123, 78, 205, 14);
		contentPane.add(lblNewLabel_1);

		searchPlayer = new JTextField();
		searchPlayer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				filtrar();
			}

		});
		searchPlayer.setBounds(121, 94, 597, 20);
		contentPane.add(searchPlayer);
		searchPlayer.setColumns(10);


		playersPane = new JScrollPane();
		playersPane.setBounds(121, 154, 597, 275);
		contentPane.add(playersPane);
		ModelTable ModelTable = new ModelTable(players);
		playersTable = new JTable();
		playersTable.setModel(ModelTable);
		rowSorter = new TableRowSorter<>(ModelTable);
		playersTable.setRowSorter(rowSorter);
		playersPane.setViewportView(playersTable);

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



	private void filtrar() {
		String busca = searchPlayer.getText().trim();

		if (busca.length() == 0) {
			rowSorter.setRowFilter(null);
		} else {
			rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + busca));
		}

	}
}