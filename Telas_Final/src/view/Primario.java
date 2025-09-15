package view;

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

		this.contentPane = new JPanel(cardLayout);
		this.contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(this.contentPane);

	}

	public void mostrarTela(String panelName) {
		this.cardLayout.show(this.contentPane, panelName);
		this.pack();
	}
	
	public void adicionarTela(String panelName, JPanel tela) {
		this.contentPane.add(tela, panelName);
	}



}
