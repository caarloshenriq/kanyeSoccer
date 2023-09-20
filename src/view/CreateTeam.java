package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Choice;
import javax.swing.JPasswordField;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import controller.Criptografia;
import model.Players;
import model.Team;

import dao.DAO;


public class CreateTeam extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JTextField name;
	private final JTextField number;
	public JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateTeam frame = new CreateTeam();
					frame.setVisible(true);
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
	public CreateTeam() {
		DAO dao = new DAO();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 387);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(209, 213, 219));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		setContentPane(contentPane);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(26, 11, 425, 314);
		contentPane.add(panel_1);


		JLabel softwareName = new JLabel("YE SOCCER");
		softwareName.setFont(new Font("Tahoma", Font.BOLD, 16));
		softwareName.setBounds(168, 32, 145, 14);
		panel_1.add(softwareName);

		JLabel nameLabel = new JLabel("Seu nome:");
		nameLabel.setBounds(24, 70, 70, 14);
		panel_1.add(nameLabel);

		name = new JTextField();
		name.setBounds(24, 83, 360, 20);
		panel_1.add(name);
		name.setColumns(10);

		number = new JTextField();
		number.setColumns(10);
		number.setBounds(24, 125, 155, 20);
		panel_1.add(number);

		JLabel lblNmero = new JLabel("Número:");
		lblNmero.setBounds(24, 112, 70, 14);
		panel_1.add(lblNmero);

		JLabel lblPosio = new JLabel("Posição:");
		lblPosio.setBounds(229, 112, 70, 14);
		panel_1.add(lblPosio);

		Choice c = new Choice();
		c.setBounds(229, 125, 155, 20);
		c.add("Lateral");
		c.add("Zagueiro");
		c.add("Goleiro");
		c.add("Meio-campo");
		c.add("Atacante");
		panel_1.add(c);

		JLabel lblSuaSenha = new JLabel("Sua senha:");
		lblSuaSenha.setBounds(24, 156, 70, 14);
		panel_1.add(lblSuaSenha);

		password = new JPasswordField();
		password.setBounds(23, 169, 361, 20);
		panel_1.add(password);

		Button button = new Button("Criar conta");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Criptografia criptografia = new Criptografia(password.getText(), Criptografia.MD5);

				String position = (c.getItem(c.getSelectedIndex()));
				String nome = name.getText();
				Players player = new Players("",nome, criptografia.criptografar(), null,number.getText(),position,null);
				System.out.println(player.getName());
				dao.cadastrarJogador(player);
			}
		});
		button.setForeground(new Color(255, 255, 255));
		button.setBounds(115, 223, 198, 22);
		button.setBackground(new Color(59, 130, 246));
		panel_1.add(button);

		JLabel lblNewLabel = new JLabel("Já possuo uma conta");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				JLogin.main(new String[0]);
			}
		});
		lblNewLabel.setBounds(154, 253, 177, 14);
		panel_1.add(lblNewLabel);
	}
}
