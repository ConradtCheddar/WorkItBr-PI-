package telas_Final;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	private JRadioButton rbtnContatante;
	private JRadioButton rdbtnContratado;
	private JButton btnCadastrar;

	/**
	 * Create the panel.
	 */
	public TelaCadastro2(Primario prim) {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][][][grow][grow][][grow][grow][][][][][][grow][][][grow][][][][grow][grow][][][][][][][][][grow][grow][][][grow][][grow][grow][][][][][grow][][][][grow][][][][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		add(panel, "flowx,cell 0 0 40 1,grow");
		panel.setLayout(new MigLayout("fill", "[center]", "[]"));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		tfEmail = new JTextField();
		add(tfEmail, "cell 4 10 32 1,growx");
		tfEmail.setColumns(10);
		tfEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
		tfEmail.putClientProperty("JComponent.roundRect", true);
		
		tfTelefone = new JTextField();
		add(tfTelefone, "cell 4 13 32 1,growx");
		tfTelefone.setColumns(10);
		tfTelefone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Telefone");
		tfTelefone.putClientProperty("JComponent.roundRect", true);
		
		tfCPF = new JTextField();
		add(tfCPF, "cell 4 16 32 1,growx");
		tfCPF.setColumns(10);
		tfCPF.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "CPF");
		tfCPF.putClientProperty("JComponent.roundRect", true);
		
		tfUsuario = new JTextField();
		add(tfUsuario, "cell 4 19 32 1,growx");
		tfUsuario.setColumns(10);
		tfUsuario.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		tfUsuario.putClientProperty("JComponent.roundRect", true);
		
		tfSenha = new JTextField();
		add(tfSenha, "cell 4 22 32 1,growx");
		tfSenha.setColumns(10);
		tfSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");
		tfSenha.putClientProperty("JComponent.roundRect", true);
		
		tfConfirmarSenha = new JTextField();
		add(tfConfirmarSenha, "cell 4 25 32 1,growx");
		tfConfirmarSenha.setColumns(10);
		tfConfirmarSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirmar Senha");
		tfConfirmarSenha.putClientProperty("JComponent.roundRect", true);
		
		rbtnContatante = new JRadioButton("Contratante");
		rbtnContatante.setHorizontalAlignment(SwingConstants.CENTER);
		rbtnContatante.setHorizontalTextPosition(SwingConstants.LEFT);
		add(rbtnContatante, "cell 10 31,growy, growx"); 
		
		rdbtnContratado = new JRadioButton("Contratado");
		add(rdbtnContratado, "cell 27 31,grow");
		
		ButtonGroup div = new ButtonGroup();
		
		div.add(rbtnContatante);
		div.add(rdbtnContratado);
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBackground(Color.BLUE);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.TEMP_PANEL);
			}
		});
		add(btnCadastrar, "cell 11 34 16 5,grow");
		btnCadastrar.putClientProperty("JComponent.roundRect", true);
		
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
				int fontSize = Math.max(15, panelHeight / 40);
				tfEmail.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfTelefone.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfCPF.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfUsuario.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfSenha.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfConfirmarSenha.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				rdbtnContratado.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				rbtnContatante.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});
		
	}

}
