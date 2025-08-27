package telas_Final;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class TelaCadastro2 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfEmail;
	private JTextField tfTelefone;
	private JTextField tfCPF;
	private JTextField tfUsuario;
	private JTextField tfSenha;
	private JTextField tfConfirmarSenha;
	private JRadioButton rdbtnContratante;
	private JRadioButton rdbtnContratado;
	private JButton btnCadastrar;

	
	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);

	/**
	 * Create the panel.

	 */
	public TelaCadastro2(Primario prim) {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));

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
		tfCPF.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "CPF");
		tfCPF.putClientProperty("JComponent.roundRect", true);
		
		tfUsuario = new JTextField();
		add(tfUsuario, "cell 4 7 13 1,growx");
		tfUsuario.setColumns(10);
		tfUsuario.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		tfUsuario.putClientProperty("JComponent.roundRect", true);
		
		tfSenha = new JTextField();
		add(tfSenha, "cell 4 8 13 1,growx");
		tfSenha.setColumns(10);
		tfSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");
		tfSenha.putClientProperty("JComponent.roundRect", true);
		
		tfConfirmarSenha = new JTextField();
		add(tfConfirmarSenha, "cell 4 9 13 1,growx");
		tfConfirmarSenha.setColumns(10);
		tfConfirmarSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirmar Senha");
		tfConfirmarSenha.putClientProperty("JComponent.roundRect", true);
		
		rdbtnContratante = new JRadioButton("Contratante");
		add(rdbtnContratante, "cell 7 12");
		
		rdbtnContratado = new JRadioButton("Contratado");
		add(rdbtnContratado, "cell 13 12");
		

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBackground(new Color(0, 102, 204));
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.TRABALHOS_PANEL);
			}
		});
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
				tfSenha.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfConfirmarSenha.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				rdbtnContratado.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				rdbtnContratante.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});
		
	}

}
