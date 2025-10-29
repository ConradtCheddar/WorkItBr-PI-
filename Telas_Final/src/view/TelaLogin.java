package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.formdev.flatlaf.FlatClientProperties;

import model.Usuario;
import model.UsuarioDAO;
import net.miginfocom.swing.MigLayout;

public class TelaLogin extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField txtUsuario;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JLabel lblCadastrese;
	private JLabel lblTitulo;

	/**
	 * Declaração dos elementos da tela
	 */

	public TelaLogin() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]",
				"[20px,grow][20px,grow 40][grow][grow 40][grow][grow 20][grow][grow]"));
		requestFocusInWindow();
		
		lblTitulo = new JLabel("Login");
		add(lblTitulo, "cell 2 0,alignx center,growy");

		txtUsuario = new JTextField();
		txtUsuario.setForeground(Color.BLACK);
		txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		add(txtUsuario, "cell 1 1 3 1,grow");
		txtUsuario.setColumns(10);
		txtUsuario.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		txtUsuario.putClientProperty("JComponent.roundRect", true);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 12));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setLayout(new MigLayout("fill, insets 0", "[grow]", "[grow]"));
		passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		passwordField.putClientProperty("JComponent.roundRect", true);
		add(passwordField, "cell 1 3 3 1,grow");

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogin.setFont(new Font("Dialog", Font.BOLD, 20));
		btnLogin.setFocusTraversalPolicyProvider(true);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(0, 102, 204));
		btnLogin.putClientProperty(FlatClientProperties.STYLE, "arc:999;");
		add(btnLogin, "cell 2 5,grow");

		JLabel lblntlg = new JLabel("Ainda não tem um Login?");
		lblntlg.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblntlg.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblntlg, "flowx,cell 2 6,alignx center,aligny center");

		lblCadastrese = new JLabel("Cadastre-se");
		lblCadastrese.setCursor(new Cursor(Cursor.HAND_CURSOR));

		lblCadastrese.setForeground(new Color(0, 102, 204));
		lblCadastrese.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastrese.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(lblCadastrese, "cell 2 6,growy");
		lblCadastrese.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblCadastrese.setText(
						"<html><a style='text-decoration: underline; color: #0066cc;' href=''>Cadastre-se</a></html>");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblCadastrese.setText(
						"<html><a style='text-decoration: none; color: #0066cc;' href=''>Cadastre-se</a></html>");
			}

		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize1 = Math.max(15, panelHeight / 17);
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 37);
				int fontSize2 = Math.max(15, panelHeight / 23);
				Font italicPlaceholderFont = new Font("Tahoma", Font.PLAIN, fontSize);
				btnLogin.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				lblCadastrese.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				PromptSupport.setPrompt("Senha", passwordField);
				PromptSupport.setPrompt("Usuario", txtUsuario);
				txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				lblntlg.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				passwordField.setFont(italicPlaceholderFont);
				lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, fontSize2 + 5));
			}
		});
	}

	/**
	 * Metodo responsavel por salvar o dado do campo "usuario"
	 */
	public String getUsuario() {
		return this.txtUsuario.getText();
	}

	/**
	 * Metodo responsavel por salvar o dado do campo "senha"
	 */
	public String getSenha() {
		return this.passwordField.getText();
	}

	/**
	 * Metodo responsavel pelo funcionamento do botão "Login"
	 */
	public void login(ActionListener actionListener) {
		this.btnLogin.addActionListener(actionListener);
	}

	/**
	 * Metodo responsavel pelo funcionamento do botão "Cadastre-se"
	 */

	public void cadastro(MouseListener actionListener) {
		this.lblCadastrese.addMouseListener(actionListener);
	}

	/**
	 * Metodo responsavel pelo funcionamento do redimencionamento do painel
	 */

	/**
	 * metodo responsavel pela limpeza do formulario
	 */
	public void limparFormulario() {
		txtUsuario.setText("");
		passwordField.setText("");
	}

}