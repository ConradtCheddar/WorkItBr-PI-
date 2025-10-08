package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLayeredPane layeredPane;
	private JPanel contentPanel;
	private JPanel cPanel;
	private DrawerMenu dm;
	private static CardLayout cardLayout;
<<<<<<< HEAD

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
=======
	private JTextField textField;
	private wbBarra wbb;
>>>>>>> Popup

	/**
	 * Create the frame.
	 */
	public Primario(wbBarra wbb, DrawerMenu dm) {
		this.wbb = wbb;
		this.dm = dm;
		setFocusTraversalPolicyProvider(true);
		setTitle("WorkITBr");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
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

<<<<<<< HEAD
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

		mostrarTela(CONTRATANTE_PANEL);
	}


	public static void mostrarTela(String panelName) {
		cardLayout.show(contentPane, panelName);
=======
		// Use BorderLayout for layeredPane to fill the frame
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(null); // We'll manage layout manually for overlay
		setContentPane(layeredPane);

		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBackground(new Color(0, 102, 204));
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		// Remove setBounds and setPreferredSize
		layeredPane.add(contentPanel, JLayeredPane.DEFAULT_LAYER);

		contentPanel.add(wbb, BorderLayout.NORTH);
		this.cardLayout = new CardLayout();
		this.cPanel = new JPanel(cardLayout);
		this.cPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPanel.add(cPanel, BorderLayout.CENTER);

		// Remove setBounds for DrawerMenu, set size dynamically
		layeredPane.add(dm, JLayeredPane.PALETTE_LAYER);
		dm.setVisible(false);

		// Add resize listener to update contentPanel and DrawerMenu size
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int w = getWidth();
				int h = getHeight();
				contentPanel.setBounds(0, 0, w, h);
				// Position DrawerMenu at the right edge
				int menuWidth = dm.getPreferredSize().width;
				int x = dm.isVisible() && getDrawerMenuOpenState(dm) ? (w - menuWidth) : w;
				dm.setBounds(x, 0, menuWidth, h);
				layeredPane.revalidate();
				layeredPane.repaint();
			}
		});
		// Initial sizing
		int w = getWidth();
		int h = getHeight();
		int menuWidth = dm.getPreferredSize().width;
		int x = dm.isVisible() && getDrawerMenuOpenState(dm) ? (w - menuWidth) : w;
		contentPanel.setBounds(0, 0, w, h);
		dm.setBounds(x, 0, menuWidth, h);
	}

	public void mostrarTela(String panelName) {
		this.cardLayout.show(this.cPanel, panelName);
		this.cPanel.revalidate();
		this.cPanel.repaint();
>>>>>>> Popup
	}
	




	public void setUsuario(Usuario u) {
		this.usuario = u;
		telaconfiguser.mostrarDados(u);
		
	}

	public void adicionarTela(String panelName, JPanel tela) {
		this.cPanel.add(tela, panelName);
	}

	public void adicionarTelaWB(JPanel tela) {
		this.wbb.add(tela);
	}

	// Helper method to check DrawerMenu open state
	private boolean getDrawerMenuOpenState(DrawerMenu dm) {
		try {
			java.lang.reflect.Field field = DrawerMenu.class.getDeclaredField("isOpen");
			field.setAccessible(true);
			return field.getBoolean(dm);
		} catch (Exception e) {
			return false;
		}
	}
}