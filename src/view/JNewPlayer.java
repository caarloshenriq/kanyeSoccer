package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.DAO;
import model.Players;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JNewPlayer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private JTextField name;
	private final JTextField number;
	private JTextField position;
	private JButton CancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JNewPlayer frame = new JNewPlayer(null);
					frame.setVisible(true);
					frame.setTitle("Ye Soccer - Jogador");
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
	public JNewPlayer(Players playerSelecionado) {
		 DAO dao = new DAO();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel("Nome:");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nameLabel.setBounds(36, 11, 46, 14);
		contentPane.add(nameLabel);
		
		name = new JTextField();
		name.setBounds(36, 30, 488, 20);
		contentPane.add(name);
		name.setColumns(10);
		
		number = new JTextField();
		number.setColumns(10);
		number.setBounds(36, 74, 488, 20);
		contentPane.add(number);
		
		JLabel lblCamisaNumero = new JLabel("Camisa numero:");
		lblCamisaNumero.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCamisaNumero.setHorizontalAlignment(SwingConstants.LEFT);
		lblCamisaNumero.setBounds(36, 55, 153, 14);
		contentPane.add(lblCamisaNumero);
		
		position = new JTextField();
		position.setColumns(10);
		position.setBounds(36, 118, 488, 20);
		contentPane.add(position);
		
		JLabel lblPosio = new JLabel("Posição:");
		lblPosio.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPosio.setHorizontalAlignment(SwingConstants.LEFT);
		lblPosio.setBounds(36, 99, 328, 14);
		contentPane.add(lblPosio);
		
		JButton CreateButton = new JButton(playerSelecionado==null?"CRIAR":"SALVAR");
		CreateButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		       
		        Players player = new Players(null, name.getText(),null, null, number.getText(), position.getText(), null);

		        if (playerSelecionado == null) {
		        	if (!"".equalsIgnoreCase(name.getText()) && !"".equalsIgnoreCase(number.getText()) && !"".equalsIgnoreCase(position.getText())) {
		        	    dao.cadastrarJogador(player);
		        	} else {
		        	    JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos!!!", "Erro", JOptionPane.ERROR_MESSAGE);
		        	    dispose();
		                JPrincipal jPrincipal = new JPrincipal();
		                jPrincipal.setLocationRelativeTo(jPrincipal);
		                jPrincipal.setVisible(true);
		                jPrincipal.setTitle("Ye Soccer");
		                ImageIcon image = new ImageIcon("ye-face.png");
		                jPrincipal.setIconImage(image.getImage());
		        	}
		        } else {
		            dao.alterarPlayer(playerSelecionado.getId(), player);
		        }

		        dispose(); // Fechar a tela após operações
		    }
		});
		CreateButton.setBounds(435, 220, 89, 23);
		CreateButton.setBackground(new Color(59, 130, 246));
		CreateButton.setForeground(new Color(255, 255, 255));
		contentPane.add(CreateButton);

		CancelButton = new JButton("VOLTAR");
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				JPrincipal jPrincipal = new JPrincipal();
				jPrincipal.setLocationRelativeTo(jPrincipal);
				jPrincipal.setVisible(true);
				jPrincipal.setTitle("Ye Soccer");
				ImageIcon image = new ImageIcon("ye-face.png");
				jPrincipal.setIconImage(image.getImage());
			}
		});
		CancelButton.setForeground(Color.WHITE);
		CancelButton.setBackground(new Color(59, 130, 246));
		CancelButton.setBounds(36, 220, 89, 23);
		contentPane.add(CancelButton);
		
		JButton btnExcluir = new JButton("EXCLUIR");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 dao.excluirPlayer(playerSelecionado.getId());
			}
		});
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setBackground(new Color(255, 0, 0));
		btnExcluir.setBounds(228, 220, 89, 23);
		btnExcluir.setVisible(false);
		contentPane.add(btnExcluir);
		
		if(playerSelecionado!=null) {
		preencherCampos(playerSelecionado);
		btnExcluir.setVisible(true);
		}
	}
	
	private void preencherCampos(Players playerSelecionado) {
		name.setText(playerSelecionado.getName());
		number.setText(playerSelecionado.getNumber());
		position.setText(playerSelecionado.getPosition());
	}
}
