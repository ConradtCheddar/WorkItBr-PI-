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

public class Primario extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLayeredPane layeredPane;
    private JPanel contentPanel;
    private JPanel cPanel;
    private DrawerMenu dm;
    private static CardLayout cardLayout;
    private JTextField textField;
    private wbBarra wbb;

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

		// Use JLayeredPane for overlay
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(900, 700));
		setContentPane(layeredPane);

		// Main content panel (holds wbBarra and cPanel)
		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBounds(0, 0, 900, 700);
		contentPanel.setBackground(new Color(0, 102, 204));
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		layeredPane.add(contentPanel, JLayeredPane.DEFAULT_LAYER);

		contentPanel.add(wbb, BorderLayout.NORTH);
		this.cardLayout = new CardLayout();
		this.cPanel = new JPanel(cardLayout);
		this.cPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPanel.add(cPanel, BorderLayout.CENTER);

		// DrawerMenu overlay
		dm.setBounds(0, 0, dm.getPreferredSize().width, dm.getPreferredSize().height);
		layeredPane.add(dm, JLayeredPane.PALETTE_LAYER);
		dm.setVisible(false); // Start hidden
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