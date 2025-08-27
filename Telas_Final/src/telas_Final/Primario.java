package telas_Final;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.awt.CardLayout;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cardLayout;

	private TelaLogin telaLogin;

	private TelaCadastro2 telaCadastro2;
	private Temp temp;
	private TelaAdm adm;
	private TelaTrabalhos trabalhos;

	public static final String LOGIN_PANEL = "Login";
	public static final String CAD2_PANEL = "Cadastro2";
	public static final String TEMP_PANEL = "Temp";
	public static final String ADM_PANEL = "Adm";
	public static final String TRABALHOS_PANEL = "Trabalhos";
	/**
	 * Create the frame.
	 */
	public Primario() {
		setFocusTraversalPolicyProvider(true);
		setTitle("WorkITBr");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);
		List<Image> icons = new ArrayList<>();
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());


		setIconImages(icons);
		UIManager.put("Button.arc", 999); 


		cardLayout = new CardLayout();

		contentPane = new JPanel(cardLayout);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		telaLogin = new TelaLogin(this);
		telaCadastro2 = new TelaCadastro2(this);
		temp = new Temp(this);
		adm = new TelaAdm(this);
		trabalhos = new TelaTrabalhos(this);

		contentPane.add(telaLogin, LOGIN_PANEL);
		contentPane.add(telaCadastro2, CAD2_PANEL);
		contentPane.add(temp, TEMP_PANEL);
		contentPane.add(adm, ADM_PANEL);
		contentPane.add(trabalhos, TRABALHOS_PANEL);

		setContentPane(contentPane);

		mostrarTela(LOGIN_PANEL);
	}

	public void mostrarTela(String panelName) {
		cardLayout.show(contentPane, panelName);
	}

}
