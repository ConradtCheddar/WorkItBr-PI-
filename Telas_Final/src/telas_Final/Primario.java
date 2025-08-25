package telas_Final;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.CardLayout;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cardLayout;

	private TelaLogin telaLogin;
	private TelaCadastro1 telaCadastro1;
	private TelaCadastro2 telaCadastro2;
	private Temp temp;
	private TelaAdm adm;

	public static final String LOGIN_PANEL = "Login";
	public static final String CAD1_PANEL = "Cadastro1";
	public static final String CAD2_PANEL = "Cadastro2";
	public static final String TEMP_PANEL = "Temp";
	public static final String ADM_PANEL = "Adm";

	/**
	 * Create the frame.
	 */
	public Primario() {
		setFocusTraversalPolicyProvider(true);
		setTitle("WorkITBr");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);

		cardLayout = new CardLayout();

		contentPane = new JPanel(cardLayout);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		telaLogin = new TelaLogin(this);
		telaCadastro1 = new TelaCadastro1(this);
		telaCadastro2 = new TelaCadastro2(this);
		temp = new Temp(this);
		adm = new TelaAdm(this);

		contentPane.add(telaLogin, LOGIN_PANEL);
		contentPane.add(telaCadastro1, CAD1_PANEL);
		contentPane.add(telaCadastro2, CAD2_PANEL);
		contentPane.add(temp, TEMP_PANEL);
		contentPane.add(adm, ADM_PANEL);

		setContentPane(contentPane);

		mostrarTela(LOGIN_PANEL);
	}

	public void mostrarTela(String panelName) {
		cardLayout.show(contentPane, panelName);
	}

}
