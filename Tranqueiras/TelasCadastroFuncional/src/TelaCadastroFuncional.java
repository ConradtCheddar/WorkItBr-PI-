package sql;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class Controlador extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtsenha1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controlador frame = new Controlador();
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

	String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	String Usuario = "root";
	String Senha = "admin";
	private JTextField txtEmail;
	private JTextField txtCPF_CNPJ;
	private JTextField txtTelefone;
	private JPasswordField txtsenha2;

	public Controlador() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 788, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setBackground(Color.BLUE);
		btnLogin.setBounds(514, 388, 136, 33);
		contentPane.add(btnLogin);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(81, 163, 136, 33);
		txtUsuario.setFocusable(true);

		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(81, 5, 623, 59);
		panel.setBackground(Color.BLUE);
		panel.setForeground(Color.BLUE);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("WorkITBr");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(41, 0, 543, 64);
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(Color.BLUE);
		txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtUsuario.setForeground(Color.BLACK);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblUsuario = new JLabel("Nome do Usuario");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(81, 125, 136, 27);
		contentPane.add(lblUsuario);

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(324, 261, 136, 27);
		contentPane.add(lblNewLabel_1);

		txtsenha1 = new JPasswordField();
		txtsenha1.setBounds(324, 299, 136, 33);
		contentPane.add(txtsenha1);

		JLabel Email = new JLabel("Email");
		Email.setHorizontalAlignment(SwingConstants.CENTER);
		Email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Email.setBounds(324, 125, 136, 27);
		contentPane.add(Email);

		txtEmail = new JTextField();
		txtEmail.setForeground(Color.BLACK);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtEmail.setFocusable(true);
		txtEmail.setColumns(10);
		txtEmail.setBounds(324, 163, 136, 33);
		contentPane.add(txtEmail);

		JLabel CPF_CNPJ = new JLabel("CPF/CNPJ");
		CPF_CNPJ.setHorizontalAlignment(SwingConstants.CENTER);
		CPF_CNPJ.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CPF_CNPJ.setBounds(568, 125, 136, 27);
		contentPane.add(CPF_CNPJ);

		txtCPF_CNPJ = new JTextField();
		txtCPF_CNPJ.setForeground(Color.BLACK);
		txtCPF_CNPJ.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtCPF_CNPJ.setFocusable(true);
		txtCPF_CNPJ.setColumns(10);
		txtCPF_CNPJ.setBounds(568, 163, 136, 33);
		contentPane.add(txtCPF_CNPJ);

		JLabel Telefone = new JLabel("N* Telefone");
		Telefone.setHorizontalAlignment(SwingConstants.CENTER);
		Telefone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Telefone.setBounds(81, 261, 136, 27);
		contentPane.add(Telefone);

		txtTelefone = new JTextField();
		txtTelefone.setForeground(Color.BLACK);
		txtTelefone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtTelefone.setFocusable(true);
		txtTelefone.setColumns(10);
		txtTelefone.setBounds(81, 299, 136, 33);
		contentPane.add(txtTelefone);

		JLabel senha_confirm = new JLabel("Confirmar Senha");
		senha_confirm.setHorizontalAlignment(SwingConstants.CENTER);
		senha_confirm.setFont(new Font("Tahoma", Font.PLAIN, 15));
		senha_confirm.setBounds(568, 261, 136, 27);
		contentPane.add(senha_confirm);

		txtsenha2 = new JPasswordField();
		txtsenha2.setBounds(568, 299, 136, 33);
		contentPane.add(txtsenha2);

		JRadioButton btncontratante = new JRadioButton("contratante");
		btncontratante.setBounds(81, 371, 109, 23);
		contentPane.add(btncontratante);

		JRadioButton btncontratado = new JRadioButton("contratado");
		btncontratado.setBounds(287, 371, 109, 23);
		contentPane.add(btncontratado);

		btnLogin.addActionListener(e -> {
			String email = txtEmail.getText();
			String usuario = txtUsuario.getText();
			String cpf_cnpj = txtCPF_CNPJ.getText();
			String telefone = txtTelefone.getText();
			String senha = new String(txtsenha1.getPassword());
			String senha2 = new String(txtsenha2.getPassword());
			boolean contratado = btncontratado.isSelected();
			boolean contratante = btncontratante.isSelected();

			if (contratado == true) {
				if (email.isEmpty() || usuario.isEmpty() || cpf_cnpj.isEmpty() || telefone.isEmpty() || senha.isEmpty()
						|| senha2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os espaços", "Erro", JOptionPane.ERROR_MESSAGE);
				} else {
					if (senha.equals(senha2)) {
						try {
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection conn = DriverManager.getConnection(url, Usuario, Senha);

							String sql = "INSERT INTO Login (Email, Nome, CPF_CNPJ, Telefone, Senha, I) VALUES (?, ?, ?, ?, ?)";
							var stmt = conn.prepareStatement(sql);
							stmt.setString(1, email); // Email
							stmt.setString(2, usuario); // Nome
							stmt.setString(3, cpf_cnpj); // CPF/CNPJ
							stmt.setString(4, telefone); // Telefone
							stmt.setString(5, senha); // Senha
							stmt.setBoolean(6, contratado); // contratado

							stmt.executeUpdate();
							System.out.println("Usuário cadastrado com sucesso!");

							stmt.close();
							conn.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Senha Incoreta", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					;
				}
			} else if (contratante == true) {
				if (email.isEmpty() || usuario.isEmpty() || cpf_cnpj.isEmpty() || telefone.isEmpty() || senha.isEmpty()
						|| senha2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os espaços", "Erro", JOptionPane.ERROR_MESSAGE);
				} else {
					if (senha.equals(senha2)) {
						try {
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection conn = DriverManager.getConnection(url, Usuario, Senha);

							String sql = "INSERT INTO Login (Email, Nome, CPF_CNPJ, Telefone, Senha) VALUES (?, ?, ?, ?, ?)";
							var stmt = conn.prepareStatement(sql);
							stmt.setString(1, email); // Email
							stmt.setString(2, usuario); // Nome
							stmt.setString(3, cpf_cnpj); // CPF/CNPJ
							stmt.setString(4, telefone); // Telefone
							stmt.setString(5, senha); // Senha
							stmt.setBoolean(6, contratante); // contratante

							stmt.executeUpdate();
							System.out.println("Usuário cadastrado com sucesso!");

							stmt.close();
							conn.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Senha Incoreta", "Erro", JOptionPane.ERROR_MESSAGE);
					}
					;
				}
			}

		});

	}
}
