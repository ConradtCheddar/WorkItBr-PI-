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
				dm.setBounds(0, 0, dm.getPreferredSize().width, h);
				layeredPane.revalidate();
				layeredPane.repaint();
			}
		});
		// Initial sizing
		int w = getWidth();
		int h = getHeight();
		contentPanel.setBounds(0, 0, w, h);
		dm.setBounds(0, 0, dm.getPreferredSize().width, h);
	}

	public void mostrarTela(String panelName) {
		this.cardLayout.show(this.cPanel, panelName);
		this.cPanel.revalidate();
		this.cPanel.repaint();
	}

	public void adicionarTela(String panelName, JPanel tela) {
		this.cPanel.add(tela, panelName);
	}

	public void adicionarTelaWB(JPanel tela) {
		this.wbb.add(tela);
	}
}