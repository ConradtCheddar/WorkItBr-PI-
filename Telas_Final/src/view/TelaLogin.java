
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

	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);

	/**
	 * Declaração dos elementos da tela
	 */

	public TelaLogin() {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0",
				"[20px][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]",
				"[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 204));
		add(panel, "flowx,cell 0 0 41 1,grow");
		panel.setLayout(new MigLayout("fill", "[][][][][][][][][][][][]", "[]"));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "flowx,cell 0 0 12 1,grow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);

		txtUsuario = new JTextField();
		txtUsuario.setForeground(Color.WHITE);
		txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		add(txtUsuario, "cell 4 5 13 1,growx");
		txtUsuario.setColumns(10);
		txtUsuario.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		txtUsuario.putClientProperty("JComponent.roundRect", true);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setLayout(new MigLayout("fill, insets 0", "[grow]", "[grow]"));
		passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		passwordField.putClientProperty("JComponent.roundRect", true);
		add(passwordField, "flowy,cell 4 8 13 1,growx");

		btnLogin = new JButton("Login");
		btnLogin.setFocusTraversalPolicyProvider(true);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(0, 102, 204));
		btnLogin.putClientProperty(FlatClientProperties.STYLE, "arc:999;");
		add(btnLogin, "cell 5 13 11 1,grow");
		requestFocusInWindow();

		JLabel lblntlg = new JLabel("Ainda não tem um Login?");
		lblntlg.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblntlg, "cell 9 14,grow");

		lblCadastrese = new JLabel("Cadastre-se");
		lblCadastrese.setCursor(new Cursor(Cursor.HAND_CURSOR));

		lblCadastrese.setForeground(new Color(0, 102, 204));
		lblCadastrese.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastrese.setFont(new Font("Tahoma", Font.PLAIN, 8));
		add(lblCadastrese, "cell 10 14,grow");
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
				int fontSize = Math.max(15, panelHeight / 17);
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
				passwordField.setFont(italicPlaceholderFont);
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
