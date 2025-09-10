package telas_Final;

import java.awt.CardLayout;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static CardLayout cardLayout;

	private TelaLogin telaLogin;
	private TelaConfigUser telaconfiguser;
	private TelaCadastro2 telaCadastro2;
	private Temp temp;
	private TelaAdm adm;
	private TelaTrabalhos trabalhos;
	private TelaContratante Panelcontratante;
	private Usuario usuario;

	public static final String LOGIN_PANEL = "Login";
	public static final String CAD2_PANEL = "Cadastro2";
	public static final String TEMP_PANEL = "Temp";
	public static final String ADM_PANEL = "Adm";
	public static final String TRABALHOS_PANEL = "Trabalhos";
	public static final String CONTRATANTE_PANEL = "Contratante";
	public static final String CONFIG_USER_PANEL = "ConfigUser";

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
		Panelcontratante = new TelaContratante(this);
		telaconfiguser = new TelaConfigUser(this );
		

		contentPane.add(telaLogin, LOGIN_PANEL);
		contentPane.add(telaCadastro2, CAD2_PANEL);
		contentPane.add(temp, TEMP_PANEL);
		contentPane.add(adm, ADM_PANEL);
		contentPane.add(trabalhos, TRABALHOS_PANEL);
		contentPane.add(Panelcontratante, CONTRATANTE_PANEL);
		contentPane.add(telaconfiguser, CONFIG_USER_PANEL);

		setContentPane(contentPane);

		mostrarTela(LOGIN_PANEL);
	}


	public static void mostrarTela(String panelName) {
		cardLayout.show(contentPane, panelName);
	}
	




	public void setUsuario(Usuario u) {
		this.usuario = u;
		telaconfiguser.mostrarDados(u);
		
	}



}
