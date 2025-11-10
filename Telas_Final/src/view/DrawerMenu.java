// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa componentes Swing para construção da interface gráfica
import javax.swing.*;
// Importa classes AWT para gerenciamento de layout, cores e dimensões
import java.awt.*;
// Importa classes para manipulação de eventos (cliques, ações, etc.)
import java.awt.event.*;
// Importa Consumer para callbacks funcionais (notificações de mudança de estado)
import java.util.function.Consumer;

/**
 * Menu lateral deslizante (drawer) que aparece do lado direito da tela.
 * <p>
 * Responsável por: exibir opções de navegação (Home, Profile, Trabalhos,
 * Settings), gerenciar logout do usuário, animar a abertura/fechamento do menu
 * com efeito suave, e integrar-se com o sistema de navegação para trocar entre
 * telas.
 * </p>
 */
public class DrawerMenu extends JPanel {
	// Largura fixa do menu em pixels quando está aberto
	private static final int MENU_WIDTH = 250;
	// Margem interna entre os botões e a borda do menu (espaçamento)
	private static final int MARGIN = 10;
	// Altura padrão dos botões do menu
	private static final int BUTTON_HEIGHT = 50;
	// Duração total da animação de abertura/fechamento em milissegundos
	private static final int ANIMATION_DURATION = 300;
	// Taxa de quadros por segundo da animação (frames por segundo)
	private static final int ANIMATION_FPS = 60;

	// Flag que indica se o menu está atualmente aberto (true) ou fechado (false)
	private boolean isOpen = false;
	// Flag que indica se uma animação está em execução no momento
	private boolean isAnimating = false;
	// Timer responsável por executar os frames da animação periodicamente
	private Timer animationTimer;
	// Timestamp que marca o início da animação (usado para calcular progresso)
	private long animationStartTime;
	// Posição X de destino para onde o menu está se movendo
	private int targetX;
	// Posição X inicial de onde o menu começou a se mover
	private int startX;

	// Botão que realiza o logout do usuário e limpa a sessão
	private final JButton btnLogout;
	// Botão que navega para a tela de configurações (temporária)
	private final JButton btnSettings;
	// Botão que navega para o perfil/configurações do usuário
	private final JButton btnProfile;
	// Botão que navega para a lista de trabalhos/serviços
	private final JButton btnTrabalhos;
	// Botão que navega para a tela inicial (Home) baseado no tipo de usuário
	private final JButton btnHome;
	
	// Callback executado quando o estado do menu muda (abre ou fecha)
	private Consumer<Boolean> onStateChange;
	// Painel superior que contém os botões principais de navegação
	private final JPanel topPanel;
	// Painel inferior que contém o botão de logout
	private final JPanel bottomPanel;

	public DrawerMenu() {
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
		setOpaque(true);
		setPreferredSize(new Dimension(MENU_WIDTH, 0));

		// Painel superior com os botões principais
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.setOpaque(true);
		topPanel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

		// Painel inferior para o botão de logout
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.setOpaque(true);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

		// Adiciona botões ao painel superior
		btnHome = createMenuButton("Home");
		topPanel.add(btnHome);
		topPanel.add(Box.createVerticalStrut(5));
		btnProfile = createMenuButton("Profile");
		topPanel.add(btnProfile);
		topPanel.add(Box.createVerticalStrut(5));
		btnTrabalhos = createMenuButton("Trabalhos");
		topPanel.add(btnTrabalhos);
		topPanel.add(Box.createVerticalStrut(5));
		btnSettings = createMenuButton("Settings");
		topPanel.add(btnSettings);

		// Adiciona botão de logout ao painel inferior
		btnLogout = createMenuButton("Logout");
		bottomPanel.add(btnLogout);

		// Adiciona os painéis ao DrawerMenu
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);

		isOpen = false;
	}

	//<editor-fold defaultstate="collapsed" desc="Getters for Controller">
	public JButton getBtnLogout() {
		return btnLogout;
	}

	public JButton getBtnSettings() {
		return btnSettings;
	}

	public JButton getBtnProfile() {
		return btnProfile;
	}

	public JButton getBtnTrabalhos() {
		return btnTrabalhos;
	}

	public JButton getBtnHome() {
		return btnHome;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public boolean isAnimating() {
		return isAnimating;
	}
	//</editor-fold>

	public void setOnStateChange(Consumer<Boolean> onStateChange) {
		this.onStateChange = onStateChange;
	}

	public void toggleMenu() {
		if (isAnimating)
			return; // Previne múltiplas animações simultâneas

		isOpen = !isOpen;
		startAnimation();
	}

	/**
	 * Fecha o menu de forma segura (iniciando animação de fechamento se
	 * necessário).
	 */
	public void closeMenu() {
		// Se já estiver fechado e não estiver animando, nada a fazer
		if (!isOpen && !isAnimating)
			return;
		// Se estiver animando, pare a animação e force estado fechado
		if (isAnimating) {
			if (animationTimer != null && animationTimer.isRunning()) {
				animationTimer.stop();
			}
			isAnimating = false;
		}
		// Se estiver aberto, inicie a animação de fechamento
		if (isOpen) {
			isOpen = false;
			startAnimation();
		} else {
			// garantir que esteja invisível
			setVisible(false);
		}
	}

	/**
	 * Inicia a animação de abertura/fechamento do menu
	 */
	private void startAnimation() {
		if (animationTimer != null && animationTimer.isRunning()) {
			animationTimer.stop();
		}

		isAnimating = true;
		animationStartTime = System.currentTimeMillis();

		// Notifica que o menu está mudando de estado
		if (onStateChange != null) {
			onStateChange.accept(isOpen);
		}

		// Calcula posições inicial e final
		Container parent = getParent();
		if (parent != null) {
			if (isOpen) {
				// Abrindo: começa fora da tela (à direita) e vai para posição visível
				startX = parent.getWidth();
				targetX = parent.getWidth() - MENU_WIDTH;
			} else {
				// Fechando: começa na posição visível e vai para fora da tela
				startX = parent.getWidth() - MENU_WIDTH;
				targetX = parent.getWidth();
			}

			// Configura o timer de animação
			int delay = 1000 / ANIMATION_FPS;
			animationTimer = new Timer(delay, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateAnimation();
				}
			});
			animationTimer.start();
		} else {
			isAnimating = false;
		}
	}

	/**
	 * Atualiza a animação a cada frame
	 */
	private void updateAnimation() {
		long elapsed = System.currentTimeMillis() - animationStartTime;
		float progress = Math.min(1.0f, (float) elapsed / ANIMATION_DURATION);

		// Usa função de easing para suavizar a animação (ease-out)
		float easedProgress = easeOutCubic(progress);

		// Calcula a posição atual
		int currentX = startX + (int) ((targetX - startX) * easedProgress);

		// Atualiza a posição do menu
		Container parent = getParent();
		if (parent != null) {
			int y = 0;
			int height = parent.getHeight();
			setBounds(currentX, y, MENU_WIDTH, height);
			parent.revalidate();
			parent.repaint();
		}

		// Verifica se a animação terminou
		if (progress >= 1.0f) {
			if (animationTimer != null) {
				animationTimer.stop();
			}
			isAnimating = false;
		}
	}

	/**
	 * Função de easing cubic para suavizar a animação
	 * 
	 * @param t progresso de 0 a 1
	 * @return valor suavizado
	 */
	private float easeOutCubic(float t) {
		float f = t - 1.0f;
		return f * f * f + 1.0f;
	}

	private JButton createMenuButton(String text) {
		JButton button = new JButton(text);
		button.setMaximumSize(new Dimension(Integer.MAX_VALUE, BUTTON_HEIGHT));
		button.setPreferredSize(new Dimension(MENU_WIDTH - 2 * MARGIN, BUTTON_HEIGHT));
		button.setMinimumSize(new Dimension(MENU_WIDTH - 2 * MARGIN, BUTTON_HEIGHT));
		button.setFocusPainted(false);
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBackground(new Color(70, 130, 180)); // Azul aço claro
		button.setForeground(Color.WHITE);
		button.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(100, 149, 237), 1), // Borda azul clara
			BorderFactory.createEmptyBorder(5, 15, 5, 15)
		));
		button.setHorizontalAlignment(SwingConstants.LEFT);

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(100, 149, 237)); // Azul cornflower ao passar o mouse
				button.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(new Color(70, 130, 180)); // Volta à cor azul aço original
			}
		});

		return button;
	}
}