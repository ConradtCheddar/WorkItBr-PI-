package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	
	JPanel cPanel;
	JPanel wbPanel;
	
	private static CardLayout cardLayout;
	
	private static CardLayout cardLayout2;

	/**
	 * Create the frame.
	 */
	public Primario() {
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
		
		this.cardLayout = new CardLayout();
		this.cardLayout2 = new CardLayout();

		this.contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 102, 204));
		this.contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(this.contentPane);
		contentPane.setLayout(new MigLayout("fill, insets 0", "[20px][grow][20px]", "[35px][grow][35px]"));
		
		wbPanel = new JPanel(cardLayout2);
		wbPanel.setBackground(new Color(0, 102, 204));
		contentPane.add(wbPanel, "cell 0 0 3 1,grow");
		
		this.cPanel = new JPanel(cardLayout);
		this.cPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(cPanel,"cell 0 1 3 2,grow");

	}

	public void mostrarTela(String panelName) {
		this.cardLayout.show(this.cPanel, panelName);
		this.pack();
	}
	
	public void adicionarTela(String panelName, JPanel tela) {
		this.cPanel.add(tela, panelName);
	}
	
	public void mostrarTelaWB(String panelName) {
		this.cardLayout2.show(this.wbPanel, panelName);
		this.pack();
	}
	
	public void adicionarTelaWB(String panelName, JPanel tela) {
		this.wbPanel.add(tela, panelName);
	}
}
