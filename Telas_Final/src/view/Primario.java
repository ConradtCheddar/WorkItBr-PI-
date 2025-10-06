package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	
	JPanel cPanel;
	JPanel wbPanel;
	
	private static CardLayout cardLayout;
	private JTextField textField;
	
	private wbBarra wbb;

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
		
		wbb = new wbBarra();
		
		this.cardLayout = new CardLayout();

		this.contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 102, 204));
		this.contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(this.contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		
		contentPane.add(wbb, BorderLayout.NORTH);
		
		this.cPanel = new JPanel(cardLayout);
		this.cPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(cPanel, BorderLayout.CENTER);
		
		
		
	}

	public void mostrarTela(String panelName) {
		this.cardLayout.show(this.cPanel, panelName);
		this.pack();
	}
	
	public void adicionarTela(String panelName, JPanel tela) {
		this.cPanel.add(tela, panelName);
	}
	
	
	public void adicionarTelaWB(JPanel tela) {
		this.wbb.add(tela);
	}
}
