package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import model.Usuario;
import model.UsuarioDAO;
import net.miginfocom.swing.MigLayout;

public class TelaCadastro extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfEmail;
	private JTextField tfTelefone;
	private JTextField tfCPF;
	private JTextField tfUsuario;
	private JPasswordField senha;
	private JPasswordField senha2;
	private JRadioButton rdbtnContratante;
	private JRadioButton rdbtnContratado;
	private JButton btnCadastrar;

	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);

	/**
	 * Create the panel.
	 * 
	 */
	public TelaCadastro() {
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

		tfEmail = new JTextField();
		add(tfEmail, "cell 4 3 13 1,growx");
		tfEmail.setColumns(10);
		tfEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
		tfEmail.putClientProperty("JComponent.roundRect", true);

		tfTelefone = new JTextField();
		add(tfTelefone, "cell 4 4 13 1,growx");
		tfTelefone.setColumns(10);
		tfTelefone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Telefone");
		tfTelefone.putClientProperty("JComponent.roundRect", true);

		tfCPF = new JTextField();
		add(tfCPF, "cell 4 5 13 1,growx");
		tfCPF.setColumns(10);
		tfCPF.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "CPF-CNPJ");
		tfCPF.putClientProperty("JComponent.roundRect", true);

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

		ButtonGroup div = new ButtonGroup();

		div.add(rdbtnContratante);
		div.add(rdbtnContratado);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 17);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 33);
				int fontSize2 = Math.max(15, panelHeight / 40);
				tfEmail.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfTelefone.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfCPF.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfUsuario.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				senha.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				senha2.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				rdbtnContratado.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				rdbtnContratante.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});

	}
	
	/**
	 *metodo para a funcionalidade do botão cadastrar 
	 */
	public void cadastrar(ActionListener actionlistener) {
		this.btnCadastrar.addActionListener(actionlistener);
	}
	
	/**
	 * metodo para limpar caixas de texto
	 */
	public void limparCampos() {
		tfEmail.setText("");
		tfTelefone.setText("");
		tfCPF.setText("");
		tfUsuario.setText("");
		senha.setText("");
		senha2.setText("");
		rdbtnContratado.setSelected(false);
		rdbtnContratante.setSelected(false);
	}
	
	/**
	 * getters & setters
	 * @return
	 */

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

	public ImageIcon getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(ImageIcon menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Image getScaledImage2() {
		return scaledImage2;
	}

	public void setScaledImage2(Image scaledImage2) {
		this.scaledImage2 = scaledImage2;
	}

	public ImageIcon getMenuResized() {
		return menuResized;
	}

	public void setMenuResized(ImageIcon menuResized) {
		this.menuResized = menuResized;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
