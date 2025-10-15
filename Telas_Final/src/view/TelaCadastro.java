package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.BadLocationException;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

public class TelaCadastro extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFormattedTextField tfEmail;
	private JFormattedTextField tfTelefone;
	private JFormattedTextField tfCPF;
	private JTextField tfUsuario;
	private JPasswordField senha;
	private JPasswordField senha2;
	private JRadioButton rdbtnContratante;
	private JRadioButton rdbtnContratado;
	private JButton btnCadastrar;
	private ButtonGroup div;

	/**
	 * Create the panel.
	 * 
	 */
	public TelaCadastro() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0",
				"[20px][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]",
				"[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));

		tfEmail = new JFormattedTextField();
		add(tfEmail, "cell 4 3 13 1,growx");
		tfEmail.setColumns(10);
		tfEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
		tfEmail.putClientProperty("JComponent.roundRect", true);
		aplicarMascaraEmail(tfEmail);

		tfTelefone = new JFormattedTextField();
		add(tfTelefone, "cell 4 4 13 1,growx");
		tfTelefone.setColumns(10);
		tfTelefone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Telefone");
		tfTelefone.putClientProperty("JComponent.roundRect", true);
		aplicarMascaraTelefone(tfTelefone);

		tfCPF = new JFormattedTextField();
		add(tfCPF, "cell 4 5 13 1,growx");
		tfCPF.setColumns(10);
		tfCPF.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "CPF-CNPJ");
		tfCPF.putClientProperty("JComponent.roundRect", true);
		aplicarMascaraCPF(tfCPF);

		tfUsuario = new JTextField();
		add(tfUsuario, "cell 4 7 13 1,growx");
		tfUsuario.setColumns(10);
		tfUsuario.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		tfUsuario.putClientProperty("JComponent.roundRect", true);

		senha = new JPasswordField();
		add(senha, "cell 4 8 13 1,growx");
		senha.setColumns(10);
		senha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");
		senha.putClientProperty("JComponent.roundRect", true);

		senha2 = new JPasswordField();
		add(senha2, "cell 4 9 13 1,growx");
		senha2.setColumns(10);
		senha2.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirmar Senha");
		senha2.putClientProperty("JComponent.roundRect", true);

		rdbtnContratante = new JRadioButton("Contratante");
		add(rdbtnContratante, "cell 7 12");

		rdbtnContratado = new JRadioButton("Contratado");
		add(rdbtnContratado, "cell 13 12");

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBackground(new Color(0, 102, 204));
		btnCadastrar.putClientProperty("JComponent.roundRect", true);
		add(btnCadastrar, "cell 7 14 7 2,grow");

		div = new ButtonGroup();
		div.add(rdbtnContratante);
		div.add(rdbtnContratado);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize2 = Math.max(15, panelHeight / 40);
				tfEmail.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				tfTelefone.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				tfCPF.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				tfUsuario.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				senha.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				senha2.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				btnCadastrar.putClientProperty("JComponent.roundRect", true);
			}
		});
		tfTelefone.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Aqui você move o cursor para o primeiro caractere dentro do parêntese
				tfTelefone.setCaretPosition(1); // Mover para o primeiro caractere dentro do parêntese
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Você pode adicionar lógica aqui, caso precise de algo ao perder o foco
			}
		});
		tfCPF.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// Aqui você move o cursor para o primeiro caractere dentro do parêntese
				tfCPF.setCaretPosition(0); // Mover para o primeiro caractere dentro do parêntese
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Você pode adicionar lógica aqui, caso precise de algo ao perder o foco
			}
		});
	}

	// Método para aplicar a máscara de CPF
	private void aplicarMascaraCPF(JFormattedTextField field) {
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.setPlaceholderCharacter('_');
			mask.install(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para aplicar a máscara de Telefone
	private void aplicarMascaraTelefone(JFormattedTextField field) {
		try {
			MaskFormatter mask = new MaskFormatter("(##) #####-####");
			mask.setPlaceholderCharacter('_');
			mask.install(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Método para aplicar a máscara de Email (apenas validações simples)
	private void aplicarMascaraEmail(JFormattedTextField field) {
		// Não há uma máscara direta simples para email com MaskFormatter,
		// então implementa-se uma validação personalizada para impedir caracteres
		// inválidos
		field.setDocument(new javax.swing.text.PlainDocument() {
			@Override
			public void insertString(int offset, String str, javax.swing.text.AttributeSet a)
					throws javax.swing.BadLocationException {
				if (str.matches("[a-zA-Z0-9@._-]*")) {
					super.insertString(offset, str, a);
				}
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

	public JFormattedTextField getTfEmail() {
		return tfEmail;
	}

	public void setTfEmail(JFormattedTextField tfEmail) {
		this.tfEmail = tfEmail;
	}

	public JFormattedTextField getTfTelefone() {
		return tfTelefone;
	}

	public void setTfTelefone(JFormattedTextField tfTelefone) {
		this.tfTelefone = tfTelefone;
	}

	public JFormattedTextField getTfCPF() {
		return tfCPF;
	}

	public void setTfCPF(JFormattedTextField tfCPF) {
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

	public JRadioButton getRdbtnContratante() {
		return rdbtnContratante;
	}

	public void setRdbtnContratante(JRadioButton rdbtnContratante) {
		this.rdbtnContratante = rdbtnContratante;
	}

	public JRadioButton getRdbtnContratado() {
		return rdbtnContratado;
	}

	public void setRdbtnContratado(JRadioButton rdbtnContratado) {
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
