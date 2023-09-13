package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import javax.swing.JTable;

import dao.DAO;
import model.ModelTable;

import model.ModelTableRanking;
import model.Players;
import model.Team;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;

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
	private ArrayList<Team> team;
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
		try {
			//team = dao.listarRanking();
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

		lblNewLabel = new JLabel("YE SOCCER");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblNewLabel.setBounds(378, 27, 207, 39);
		contentPane.add(lblNewLabel);

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


//		JScrollPane teamPane = new JScrollPane();
//		teamPane.setBounds(765, 43, 139, 386);
//		contentPane.add(teamPane);
//		ModelTableRanking modelTableRanking = new ModelTableRanking(team); // Renomeado para evitar conflito de nomes
//		teamTable = new JTable(modelTableRanking); // Configure o modelo para a tabela
//		teamPane.setViewportView(teamTable); // Defina a tabela no painel de rolagem
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