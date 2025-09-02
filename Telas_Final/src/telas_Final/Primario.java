package telas_Final;

import java.awt.CardLayout;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cardLayout;

	private TelaLogin telaLogin;

	private TelaCadastro2 telaCadastro2;
	private Temp temp;
	private TelaAdm adm;
	private TelaTrabalhos trabalhos;
	private TelaContratante Panelcontratante;

	public static final String LOGIN_PANEL = "Login";
	public static final String CAD2_PANEL = "Cadastro2";
	public static final String TEMP_PANEL = "Temp";
	public static final String ADM_PANEL = "Adm";
	public static final String TRABALHOS_PANEL = "Trabalhos";
	public static final String CONTRATANTE_PANEL = "Contratante";
	
	String email;
	String usuario;
	String cpf_cnpj;
	String telefone;
	String senha;
	String senha2;
	boolean contratante;
	boolean contratado;

	static String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	static String Usuario = "root";
	static String Senha = "admin";

	/**
	 * Create the frame.
	 */
	public Primario() {
		setFocusTraversalPolicyProvider(true);
		setTitle("WorkITBr");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);
		List<Image> icons = new ArrayList<>();
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());

		setIconImages(icons);
		UIManager.put("Button.arc", 999);

		cardLayout = new CardLayout();

		contentPane = new JPanel(cardLayout);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		telaLogin = new TelaLogin(this);
		telaCadastro2 = new TelaCadastro2(this);
		temp = new Temp(this);
		adm = new TelaAdm(this);
		trabalhos = new TelaTrabalhos(this);
		Panelcontratante = new TelaContratante(this);

		contentPane.add(telaLogin, LOGIN_PANEL);
		contentPane.add(telaCadastro2, CAD2_PANEL);
		contentPane.add(temp, TEMP_PANEL);
		contentPane.add(adm, ADM_PANEL);
		contentPane.add(trabalhos, TRABALHOS_PANEL);
		contentPane.add(Panelcontratante, CONTRATANTE_PANEL);

		setContentPane(contentPane);

		mostrarTela(LOGIN_PANEL);
	}

	public void mostrarTela(String panelName) {
		cardLayout.show(contentPane, panelName);
	}

	public void cadastrar(JTextField email, JTextField usuario, JTextField cpf_cnpj, JTextField telefone, JPasswordField senha,
			JPasswordField senha2, boolean contratado, boolean contratante) {
		this.email = email.getText();
		this.usuario = usuario.getText();
		this.cpf_cnpj = cpf_cnpj.getText();
		this.telefone = telefone.getText();
		this.senha = new String(senha.getPassword());
		this.senha2 = new String(senha2.getPassword());

		if (contratado == true) {
			if (this.email.isEmpty() || this.usuario.isEmpty() || this.cpf_cnpj.isEmpty() || this.telefone.isEmpty() || this.senha.isEmpty()
					|| this.senha2.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os espaços", "Erro", JOptionPane.ERROR_MESSAGE);
			} else {
				if (this.senha.equals(this.senha2)) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, Usuario, Senha);

						String sql = "INSERT INTO Login (Email, Nome, CPF_CNPJ, Telefone, Senha, idContratado) VALUES (?, ?, ?, ?, ?, ?)";
						var stmt = conn.prepareStatement(sql);
						stmt.setString(1, this.email); // Email
						stmt.setString(2, this.usuario); // Nome
						stmt.setString(3, this.cpf_cnpj); // CPF/CNPJ
						stmt.setString(4, this.telefone); // Telefone
						stmt.setString(5, this.senha); // Senha
						stmt.setBoolean(6, contratado); // contratado

						stmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "Sucesso!", JOptionPane.PLAIN_MESSAGE);
						mostrarTela(LOGIN_PANEL);

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
			if (this.email.isEmpty() || this.usuario.isEmpty() || this.cpf_cnpj.isEmpty() || this.telefone.isEmpty() || this.senha.isEmpty()
					|| this.senha2.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os espaços", "Erro", JOptionPane.ERROR_MESSAGE);
			} else {
				if (this.senha.equals(this.senha2)) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, Usuario, Senha);

						String sql = "INSERT INTO Login (Email, Nome, CPF_CNPJ, Telefone, Senha, idContratante) VALUES (?, ?, ?, ?, ?, ?)";
						var stmt = conn.prepareStatement(sql);
						stmt.setString(1, this.email); // Email
						stmt.setString(2, this.usuario); // Nome
						stmt.setString(3, this.cpf_cnpj); // CPF/CNPJ
						stmt.setString(4, this.telefone); // Telefone
						stmt.setString(5, this.senha); // Senha
						stmt.setBoolean(6, contratante); // contratante

						stmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "Sucesso!", JOptionPane.PLAIN_MESSAGE);
						mostrarTela(LOGIN_PANEL);

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

	}

}
