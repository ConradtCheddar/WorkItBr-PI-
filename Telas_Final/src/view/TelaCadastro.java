package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import util.FieldValidator;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;

public class TelaCadastro extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfEmail;
	private JTextField tfTelefone;
	private JTextField tfCPF;
	private JTextField tfUsuario;
	private JPasswordField senha;
	private JPasswordField senha2;
	private ScalableRadioButton rdbtnContratante;
	private ScalableRadioButton rdbtnContratado;
	private JButton btnCadastrar;
	private ButtonGroup div;
	private JLabel lblTitulo;

	/**
	 * Create the panel.
	 * 
	 */
	public TelaCadastro() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[grow][grow][grow][grow][grow]",
				"[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]"));

		div = new ButtonGroup();

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBackground(new Color(0, 102, 204));
		btnCadastrar.putClientProperty("JComponent.roundRect", true);

		lblTitulo = new JLabel("Cadastro");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, "cell 2 0,grow");

		tfEmail = new JTextField();
		add(tfEmail, "cell 1 1 3 1,growx");
		tfEmail.setColumns(10);
		tfEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
		tfEmail.putClientProperty("JComponent.roundRect", true);
		tfEmail.setOpaque(false);
		tfEmail.putClientProperty(FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");

		tfTelefone = new JTextField();
		add(tfTelefone, "cell 1 2 3 1,growx");
		tfTelefone.setColumns(10);
		tfTelefone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Telefone");
		tfTelefone.putClientProperty("JComponent.roundRect", true);
		tfTelefone.setOpaque(false);
		tfTelefone.putClientProperty(FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");
		// Aplicar formatação automática de telefone
		((AbstractDocument) tfTelefone.getDocument()).setDocumentFilter(new FieldValidator.TelefoneDocumentFilter());

		tfCPF = new JTextField();
		add(tfCPF, "cell 1 3 3 1,growx");
		tfCPF.setColumns(10);
		tfCPF.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "CPF ou CNPJ");
		tfCPF.putClientProperty("JComponent.roundRect", true);
		tfCPF.setOpaque(false);
		tfCPF.putClientProperty(FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");
		// Aplicar formatação automática de CPF
		((AbstractDocument) tfCPF.getDocument()).setDocumentFilter(new FieldValidator.CPFDocumentFilter());

		tfUsuario = new JTextField();
		add(tfUsuario, "cell 1 4 3 1,growx");
		tfUsuario.setColumns(10);
		tfUsuario.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		tfUsuario.putClientProperty("JComponent.roundRect", true);
		tfUsuario.setOpaque(false);
		tfUsuario.putClientProperty(FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");

		senha = new JPasswordField();
		add(senha, "cell 1 5 3 1,growx");
		senha.setColumns(10);
		senha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");
		senha.putClientProperty("JComponent.roundRect", true);
		senha.setOpaque(false);
		senha.putClientProperty(FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");

		senha2 = new JPasswordField();
		add(senha2, "cell 1 6 3 1,growx");
		senha2.setColumns(10);
		senha2.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirmar Senha");
		senha2.putClientProperty("JComponent.roundRect", true);
		senha2.setOpaque(false);
		senha2.putClientProperty(FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");

		rdbtnContratante = new ScalableRadioButton("Contratante");
		rdbtnContratante.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		rdbtnContratante.setHorizontalAlignment(SwingConstants.RIGHT);
		add(rdbtnContratante, "cell 1 7,alignx center,growy");
		div.add(rdbtnContratante);

		rdbtnContratado = new ScalableRadioButton("Contratado");
		add(rdbtnContratado, "cell 3 7,alignx center,growy");
		div.add(rdbtnContratado);
		add(btnCadastrar, "cell 2 8,grow");

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 40);
				int fontSize2 = Math.max(15, panelHeight / 25);
				int fontSize3 = Math.max(15, panelHeight / 20);
				tfEmail.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				tfTelefone.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				tfCPF.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				tfUsuario.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				senha.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				senha2.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				btnCadastrar.putClientProperty("JComponent.roundRect", true);
				lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, fontSize3));
				rdbtnContratante.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				rdbtnContratado.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				// updateIconSize() é chamado automaticamente quando setFont é chamado
				rdbtnContratante.updateIconSize();
				rdbtnContratado.updateIconSize();
			}
		});
	}

	/**
	 * Metodo para a funcionalidade do botão cadastrar
	 */
	public void cadastrar(ActionListener actionlistener) {
		this.btnCadastrar.addActionListener(actionlistener);
	}

	/**
	 * Metodo para limpar caixas de texto
	 */
	public void limparCampos() {
		tfEmail.setText("");
		tfTelefone.setText("");
		tfCPF.setText("");
		tfUsuario.setText("");
		senha.setText("");
		senha2.setText("");
		div.clearSelection();
	}

	// Getters & Setters

	public JTextField getTfEmail() {
		return tfEmail;
	}

	public void setTfEmail(JTextField tfEmail) {
		this.tfEmail = tfEmail;
	}

	public JTextField getTfTelefone() {
		return tfTelefone;
	}

	public void setTfTelefone(JTextField tfTelefone) {
		this.tfTelefone = tfTelefone;
	}

	public JTextField getTfCPF() {
		return tfCPF;
	}

	public void setTfCPF(JTextField tfCPF) {
		this.tfCPF = tfCPF;
	}

	public JTextField getTfUsuario() {
		return tfUsuario;
	}

	public void setTfUsuario(JTextField tfUsuario) {
		this.tfUsuario = tfUsuario;
	}

	public JPasswordField getSenha() {
		return senha;
	}

	public void setSenha(JPasswordField senha) {
		this.senha = senha;
	}

	public JPasswordField getSenha2() {
		return senha2;
	}

	public void setSenha2(JPasswordField senha2) {
		this.senha2 = senha2;
	}

	public ScalableRadioButton getRdbtnContratante() {
		return rdbtnContratante;
	}

	public void setRdbtnContratante(ScalableRadioButton rdbtnContratante) {
		this.rdbtnContratante = rdbtnContratante;
	}

	public ScalableRadioButton getRdbtnContratado() {
		return rdbtnContratado;
	}

	public void setRdbtnContratado(ScalableRadioButton rdbtnContratado) {
		this.rdbtnContratado = rdbtnContratado;
	}

	public JButton getBtnCadastrar() {
		return btnCadastrar;
	}

	public void setBtnCadastrar(JButton btnCadastrar) {
		this.btnCadastrar = btnCadastrar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}