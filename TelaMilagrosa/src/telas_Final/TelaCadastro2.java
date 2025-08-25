package telas_Final;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastro2 extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtUsuario;
	private JTextField txtSenha;
	private JTextField txtConfirmarSenha;
	private JButton btnContratado;
	private JButton btnContratante;

	/**
	 * Create the panel.
	 */
	public TelaCadastro2(Primario prim) {
		setPreferredSize(new Dimension(700, 500));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][][][grow][][grow][][][grow][grow][][][][][grow][grow][grow][grow][grow][][][grow][][][][grow][][][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		add(panel, "flowx,cell 0 0 27 1,grow");
		panel.setLayout(new MigLayout("fill", "[center]", "[]"));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		txtUsuario = new JTextField();
		add(txtUsuario, "cell 4 14 19 1,growx");
		txtUsuario.setColumns(10);
		PromptSupport.setPrompt("Usuario", txtUsuario);
		PromptSupport.setForeground(new Color(100, 100, 100), txtUsuario);
		
		txtSenha = new JTextField();
		add(txtSenha, "cell 4 17 19 1,growx");
		txtSenha.setColumns(10);
		PromptSupport.setPrompt("Senha", txtSenha);
		PromptSupport.setForeground(new Color(100, 100, 100), txtSenha);
		
		txtConfirmarSenha = new JTextField();
		add(txtConfirmarSenha, "cell 4 20 19 1,growx");
		txtConfirmarSenha.setColumns(10);
		PromptSupport.setPrompt("Confirmar Senha", txtConfirmarSenha);
		PromptSupport.setForeground(new Color(100, 100, 100), txtConfirmarSenha);
		
		btnContratante = new JButton("Cadastrar Contratante");
		btnContratante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.LOGIN_PANEL);
			}
		});
		add(btnContratante, "cell 10 33 7 1,grow");
		btnContratado = new JButton("Cadastrar Contratado");
		btnContratado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.LOGIN_PANEL);
			}
		});
		add(btnContratado, "cell 10 33 7 3,grow");
		
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
				int fontSize = Math.max(15, panelHeight / 25);
				txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				txtSenha.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				txtConfirmarSenha.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				btnContratado.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				btnContratante.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});
		
	}

}
