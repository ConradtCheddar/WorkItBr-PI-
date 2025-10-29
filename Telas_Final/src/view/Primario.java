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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel cPanel;
	private DrawerMenu dm;
	private static CardLayout cardLayout;
	private JTextField textField;
	private wbBarra wbb;
	private JPanel menuLayer;
	private java.util.Map<String, JPanel> painelMap = new java.util.HashMap<>();
	private String currentPanelName;

	/**
	 * Cria o frame.
	 */
	public Primario(wbBarra wbb, DrawerMenu dm) {
		this.wbb = wbb;
		this.dm = dm;
		setFocusTraversalPolicyProvider(true);
		setMinimumSize(new Dimension(1200, 900));
		setTitle("WorkITBr");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
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

		setLayout(new BorderLayout());

		contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBackground(new Color(0, 102, 204));
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		add(contentPanel, BorderLayout.CENTER);

		contentPanel.add(wbb, BorderLayout.NORTH);
		this.cardLayout = new CardLayout();
		this.cPanel = new JPanel(cardLayout);
		this.cPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPanel.add(cPanel, BorderLayout.CENTER);

		menuLayer = new JPanel(null);
		menuLayer.setOpaque(false);
		setGlassPane(menuLayer);
		menuLayer.setVisible(false);

		dm.setOpaque(true);
		dm.setVisible(true);
		dm.setBounds(getWidth(), 0, 0, getHeight());
		menuLayer.add(dm);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				int menuWidth = dm.getPreferredSize().width;
				if (menuWidth > 0) {
					dm.setBounds(getWidth() - menuWidth, 0, menuWidth, getHeight());
				} else {
					dm.setBounds(getWidth(), 0, 0, getHeight());
				}
			}
		});

		dm.setOnStateChange(isOpen -> {
			int menuWidth = dm.getPreferredSize().width;
			if (isOpen && menuWidth > 0) {
				dm.setBounds(getWidth() - menuWidth, 0, menuWidth, getHeight());
				menuLayer.setVisible(true);
			} else {
				dm.setBounds(getWidth(), 0, 0, getHeight());
				menuLayer.setVisible(false);
			}
			menuLayer.repaint();
		});

		wbb.setMenuClickListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				dm.toggleMenu();
			}
		});
	}

	public void mostrarTela(String panelName) {
		this.cardLayout.show(this.cPanel, panelName);
		this.cPanel.revalidate();
		this.cPanel.repaint();
		this.currentPanelName = panelName;
	}

	/**
	 * Remove uma tela do CardLayout se existir
	 * @param panelName Nome do painel a ser removido
	 */
	public void removerTela(String panelName) {
		if (painelMap.containsKey(panelName)) {
			JPanel painel = painelMap.get(panelName);
			this.cPanel.remove(painel);
			painelMap.remove(panelName);
			this.cPanel.revalidate();
			this.cPanel.repaint();
		}
	}

	public void adicionarTelaWB(JPanel tela) {
		this.wbb.add(tela);
	}

	public void adicionarTela(String panelName, JPanel tela) {
		if (panelName == null || tela == null) return;
		if (painelMap.containsKey(panelName)) {
			JPanel antigo = painelMap.get(panelName);
			this.cPanel.remove(antigo);
		}
		painelMap.put(panelName, tela);
		this.cPanel.add(tela, panelName);
		this.cPanel.revalidate();
		this.cPanel.repaint();
	}

	public String getCurrentPanelName() {
		return this.currentPanelName;
	}

	private boolean getDrawerMenuOpenState(DrawerMenu dm) {
		try {
			java.lang.reflect.Field field = DrawerMenu.class.getDeclaredField("isOpen");
			field.setAccessible(true);
			return field.getBoolean(dm);
		} catch (Exception e) {
			return false;
		}
	}

	public void fecharDrawerMenuSeAberto() {
		if (getDrawerMenuOpenState(dm)) {
			if (dm != null) {
				try {
					java.lang.reflect.Method closeMethod = DrawerMenu.class.getDeclaredMethod("closeMenu");
					closeMethod.setAccessible(true);
					closeMethod.invoke(dm);
				} catch (Exception e) {
					dm.toggleMenu();
				}
			}
		}
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
	}
}