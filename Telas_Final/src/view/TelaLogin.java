package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.formdev.flatlaf.FlatClientProperties;

import model.Usuario;
import model.UsuarioDAO;
import net.miginfocom.swing.MigLayout;
import util.FontScaler;
import util.FontScaler.FontSize;

public class TelaLogin extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField txtUsuario;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JLabel lblCadastrese;
	private JLabel lblTitulo;
	private JLabel lblntlg;

	/**
	 * Declaração dos elementos da tela
	 */

	public TelaLogin() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]",
				"[20px,grow][20px,grow 40][grow][grow 40][grow][grow 20][grow][grow]"));
		// Removida chamada direta que poderia forçar foco em um dos campos:
		// requestFocusInWindow();
		// Garante que o painel possa receber foco e, quando for exibido, limpa o foco
		// global para evitar que o primeiro campo seja focado automaticamente.
		setFocusable(true);
		addHierarchyListener(e -> {
			if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
				// Executa no próximo ciclo do EDT para garantir que a janela esteja
				// totalmente exibida antes de manipular o foco.
				SwingUtilities.invokeLater(() -> {
					// Limpa o foco global: nenhum componente terá foco inicial.
					KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
					// Requisita foco no painel (opcional) para que o foco não caia em nenhum JTextField
					requestFocusInWindow();
				});
			}
		});
		
		lblTitulo = new JLabel("Login");
		add(lblTitulo, "cell 2 0,alignx center,growy");

		txtUsuario = new JTextField();
		txtUsuario.setForeground(Color.BLACK);
		txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		add(txtUsuario, "cell 1 1 3 1,grow");
		txtUsuario.setColumns(10);
		txtUsuario.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		txtUsuario.putClientProperty("JComponent.roundRect", true);
		txtUsuario.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, false);
		txtUsuario.setOpaque(false);
		// Usa @background para referenciar a cor definida em setBackground
		txtUsuario.putClientProperty(FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setLayout(new MigLayout("fill, insets 0", "[grow]", "[grow]"));
		passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");
		passwordField.putClientProperty("JComponent.roundRect", true);
		passwordField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, false);
		passwordField.setOpaque(false);
		// Usa @background para referenciar a cor definida em setBackground
		passwordField.putClientProperty(FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");
		add(passwordField, "cell 1 3 3 1,grow");

		// Define os prompts imediatamente para garantir que os placeholders apareçam
		PromptSupport.setPrompt("Senha", passwordField);
		PromptSupport.setPrompt("Usuario", txtUsuario);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setFocusTraversalPolicyProvider(true);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(0, 102, 204));
		btnLogin.putClientProperty(FlatClientProperties.STYLE, "arc:999;");
		add(btnLogin, "cell 2 5,grow");

		lblntlg = new JLabel("Ainda não tem um Login?");
		lblntlg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblntlg.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblntlg, "flowx,cell 2 6,alignx center,aligny center");

		lblCadastrese = new JLabel("Cadastre-se");
		lblCadastrese.setCursor(new Cursor(Cursor.HAND_CURSOR));

		lblCadastrese.setForeground(new Color(0, 102, 204));
		lblCadastrese.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastrese.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

		FontScaler.addAutoResize(this,
			new Object[] {lblTitulo, FontSize.TITULO},
			new Object[] {txtUsuario, FontSize.TEXTO},
			new Object[] {passwordField, FontSize.TEXTO},
			new Object[] {btnLogin, FontSize.BOTAO},
			new Object[] {lblCadastrese, FontSize.TEXTO},
			new Object[] {lblntlg, FontSize.TEXTO}
		);
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