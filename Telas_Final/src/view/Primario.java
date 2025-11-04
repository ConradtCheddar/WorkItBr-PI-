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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel cPanel;
	private DrawerMenu dm;
	private CardLayout cardLayout;
	private JTextField textField;
	private wbBarra wbb;
	private JPanel menuLayer;
	private controller.Navegador navegador; // Referência ao navegador para limpeza ao fechar
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

		// Mudança: usar BorderLayout em vez de layout nulo
		menuLayer = new JPanel(null); // Usando null layout para posicionar manualmente
		menuLayer.setOpaque(false);
		setGlassPane(menuLayer);
		menuLayer.setVisible(false);

		dm.setOpaque(true);
		dm.setVisible(false); // Começa invisível
		menuLayer.add(dm);

		// Adiciona listener para fechar o menu ao clicar fora dele
		menuLayer.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// Verifica se o clique foi fora do DrawerMenu
				if (dm.isOpen() && !dm.getBounds().contains(e.getPoint())) {
					dm.toggleMenu();
				}
			}
		});

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				// Atualiza a posição do menu ao redimensionar a janela
				if (dm.isOpen() && !dm.isAnimating()) {
					int menuWidth = 250;
					int x = getWidth() - menuWidth;
					int y = 0;
					int height = getHeight();
					dm.setBounds(x, y, menuWidth, height);
				}
				menuLayer.revalidate();
				menuLayer.repaint();
			}
		});

		dm.setOnStateChange(isOpen -> {
			if (isOpen) {
				dm.setVisible(true);
				menuLayer.setVisible(true);
			} else {
				// Aguarda o fim da animação para esconder
				Timer hideTimer = new Timer(320, e -> {
					if (!dm.isOpen()) {
						dm.setVisible(false);
						menuLayer.setVisible(false);
					}
				});
				hideTimer.setRepeats(false);
				hideTimer.start();
			}
			menuLayer.revalidate();
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

	public java.util.Set<String> getPainelNames() {
		return new java.util.HashSet<>(painelMap.keySet());
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
	
	/**
	 * Configura o Navegador e adiciona WindowListener para limpar imagens ao fechar
	 */
	public void setNavegador(controller.Navegador navegador) {
		this.navegador = navegador;
		
		// Adiciona listener para limpar imagens quando a janela for fechada
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				if (Primario.this.navegador != null) {
					Primario.this.navegador.limparImagensPerfil();
				}
			}
		});
	}
	
	/**
	 * Pré-inicializa a janela forçando todos os layouts e renderizações
	 * antes de torná-la visível. Isso evita redimensionamentos visíveis.
	 */
	public void preinicializar() {
		// Força o cálculo de todos os tamanhos e layouts
		pack();
		
		// Restaura o tamanho desejado
		setSize(1200, 800);
		setLocationRelativeTo(null);
		
		// Força validação completa de todos os componentes
		validate();
		
		// Força a renderização de todos os componentes
		doLayout();
		
		// Força repaint completo
		revalidate();
		repaint();
		
		// Força a pintura imediata de todos os componentes
		// Nota: getGraphics() pode ser null se não houver peer ainda
		java.awt.Graphics g = getGraphics();
		if (g != null) {
			paintAll(g);
			g.dispose();
		}
	}
}