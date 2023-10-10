package view;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Criptografia;
import dao.DAO;
import model.Players;
import model.Team;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel labelSenha;
	private JTextField TeamName;
	private JLabel labelEmail;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JLogin frame = new JLogin();
					frame.setVisible(true);
					frame.setTitle("Ye Soccer - login");
					ImageIcon image = new ImageIcon("ye-face.png");
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
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
	public JLogin() {
		DAO dao = new DAO();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(209, 213, 219));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(72, 22, 312, 216);
		contentPane.add(panel);
		panel.setLayout(null);
		labelSenha = new JLabel("Senha:");
		labelSenha.setBounds(46, 79, 121, 14);
		panel.add(labelSenha);

		TeamName = new JTextField();
		TeamName.setColumns(10);
		TeamName.setBounds(46, 48, 199, 20);
		panel.add(TeamName);

		labelEmail = new JLabel("Nome:");
		labelEmail.setBounds(46, 34, 121, 14);
		panel.add(labelEmail);

		JButton btnLogin = new JButton("Entrar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Criptografia criptografia = new Criptografia(password, Criptografia.MD5);
				Criptografia criptografia = new Criptografia(passwordField.getText(), Criptografia.MD5);

				Players player = new Players(0,TeamName.getText(), criptografia.criptografar(), null,null,null,null);

				dao.realizarLogin(player);
				dispose();
			}
		});

		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(new Color(59, 130, 246));
		btnLogin.setBounds(46, 137, 199, 23);
		panel.add(btnLogin);

		JLabel lblNewLabel_2 = new JLabel("YE SOCCER");
		lblNewLabel_2.setFont(new Font("Yu Gothic", Font.BOLD, 13));
		lblNewLabel_2.setBounds(107, 11, 106, 26);
		panel.add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(46, 93, 199, 20);
		panel.add(passwordField);

		lblNewLabel = new JLabel("Crie uma conta");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
					CreateTeam.main(new String[0]);
			}
		});
		lblNewLabel.setBounds(107, 171, 124, 14);
		panel.add(lblNewLabel);
	}
}
