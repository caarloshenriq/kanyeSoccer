package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Choice;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import controller.Conexao;
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
    private JTextField username;
    private JPasswordField ConfirmPassword;

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
                    frame.setTitle("Ye Soccer - Cadastrar");
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
        setBounds(100, 100, 505, 437);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(209, 213, 219));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        setContentPane(contentPane);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(null);
        panel_1.setBounds(26, 22, 425, 350);
        contentPane.add(panel_1);


        JLabel softwareName = new JLabel("YE SOCCER");
        softwareName.setFont(new Font("Tahoma", Font.BOLD, 16));
        softwareName.setBounds(168, 32, 145, 14);
        panel_1.add(softwareName);

        JLabel nameLabel = new JLabel("Seu nome:");
        nameLabel.setBounds(24, 112, 70, 14);
        panel_1.add(nameLabel);

        name = new JTextField();
        name.setBounds(24, 125, 360, 20);
        panel_1.add(name);
        name.setColumns(10);

        number = new JTextField();
        number.setColumns(10);
        number.setBounds(24, 167, 155, 20);
        panel_1.add(number);

        JLabel lblNmero = new JLabel("Número:");
        lblNmero.setBounds(24, 154, 70, 14);
        panel_1.add(lblNmero);

        JLabel lblPosio = new JLabel("Posição:");
        lblPosio.setBounds(229, 154, 70, 14);
        panel_1.add(lblPosio);

        Choice c = new Choice();
        c.setBounds(229, 167, 155, 20);
        c.add("Lateral");
        c.add("Zagueiro");
        c.add("Goleiro");
        c.add("Meio-campo");
        c.add("Atacante");
        panel_1.add(c);

        JLabel lblSuaSenha = new JLabel("Sua senha:");
        lblSuaSenha.setBounds(24, 198, 70, 14);
        panel_1.add(lblSuaSenha);

        password = new JPasswordField();
        password.setBounds(23, 211, 361, 20);
        panel_1.add(password);

        username = new JTextField();
        username.setColumns(10);
        username.setBounds(24, 81, 360, 20);
        panel_1.add(username);

        JLabel Usernamelbl = new JLabel("Digite seu username:");
        Usernamelbl.setBounds(24, 68, 136, 14);
        panel_1.add(Usernamelbl);

        ConfirmPassword = new JPasswordField();
        ConfirmPassword.setBounds(24, 257, 361, 20);
        panel_1.add(ConfirmPassword);

        JLabel lblSuaSenha_1 = new JLabel("Confirme sua senha:");
        lblSuaSenha_1.setBounds(25, 244, 155, 14);
        panel_1.add(lblSuaSenha_1);

        Button button = new Button("Criar conta");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Criptografia criptografia = new Criptografia(password.getText(), Criptografia.MD5);

                String position = (c.getItem(c.getSelectedIndex()));
                String nome = name.getText();
                if (position.isBlank() || nome.isBlank() || password.getText().isBlank() || number.getText().isBlank() || ConfirmPassword.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else if(!password.getText().equals(ConfirmPassword.getText())){
                    JOptionPane.showMessageDialog(null, "As senhas não correspondem.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    Players player = new Players(0, nome, criptografia.criptografar(), null, number.getText(), position, null, username.getText());
                    boolean createTeam = dao.cadastrarJogador(player);

                    if (createTeam) {
                        dispose();
                        FirstView.main(new String[0]);
                    }
                }


            }
        });
        button.setForeground(new Color(255, 255, 255));
        button.setBounds(115, 295, 198, 22);
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
        lblNewLabel.setBounds(154, 325, 177, 14);
        panel_1.add(lblNewLabel);


    }
}
