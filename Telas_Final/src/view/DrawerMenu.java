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
// Importa o Navegador responsável por controlar navegação entre telas
import controller.Navegador;
// Importa a fábrica de telas que cria instâncias dinâmicas de visualizações
import controller.TelaFactory;
// Importa o DAO para acesso a dados de usuários no banco de dados
import model.UsuarioDAO;
// Importa a classe de modelo que representa um usuário no sistema
import model.Usuario;
// Importa a tela de configuração de usuário
import view.TelaConfigUser;
// Importa o controller da tela de configuração de usuário
import controller.TelaConfigUserController;

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
	private JButton btnLogout;
	// Botão que navega para a tela de configurações (temporária)
	private JButton btnSettings;
	// Botão que navega para o perfil/configurações do usuário
	private JButton btnProfile;
	// Botão que navega para a lista de trabalhos/serviços
	private JButton btnTrabalhos;
	// Botão que navega para a tela inicial (Home) baseado no tipo de usuário
	private JButton btnHome;
	// Referência ao DAO para operações de banco de dados relacionadas a usuários
	private UsuarioDAO usuarioDAO;
	// Referência ao navegador que controla a navegação entre telas
	private Navegador navegador;
	// Referência à fábrica que cria instâncias dinâmicas de telas
	private TelaFactory telaFactory;
	// Callback executado quando o estado do menu muda (abre ou fecha)
	private Consumer<Boolean> onStateChange;
	// Painel superior que contém os botões principais de navegação
	private JPanel topPanel;
	// Painel inferior que contém o botão de logout
	private JPanel bottomPanel;

	public DrawerMenu(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
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

	public void setTelaFactory(TelaFactory telaFactory) {
		this.telaFactory = telaFactory;
	}

	public void setNavegador(Navegador navegador) {
		this.navegador = navegador;
		// Home button
		if (btnHome != null) {
			for (ActionListener al : btnHome.getActionListeners()) {
				btnHome.removeActionListener(al);
			}
			btnHome.addActionListener(e -> {
				if (this.navegador != null) {
					Usuario u = this.navegador.getCurrentUser();
					if (u != null) {
						// Redireciona para a tela principal baseada no tipo de usuário
						if (u.isAdmin()) {
							this.navegador.navegarPara("ADM");
						} else if (u.isContratante()) {
							this.navegador.navegarPara("SERVICOS");
						} else if (u.isContratado()) {
							this.navegador.navegarPara("CONTRATADO");
						} else {
							// Fallback para login se não tiver tipo definido
							this.navegador.navegarPara("LOGIN");
						}
					}
				}
				if (isOpen)
					toggleMenu();
			});
		}
		// Settings button
		if (btnSettings != null) {
			for (ActionListener al : btnSettings.getActionListeners()) {
				btnSettings.removeActionListener(al);
			}
			btnSettings.addActionListener(e -> {
				if (this.navegador != null) {
					this.navegador.navegarPara("TEMP");
				}
				if (isOpen)
					toggleMenu();
			});
		}
		// Logout button
		if (btnLogout != null) {
			for (ActionListener al : btnLogout.getActionListeners()) {
				btnLogout.removeActionListener(al);
			}
			btnLogout.addActionListener(e -> {
				if (this.navegador != null) {
					this.navegador.clearCurrentUser();
					if (telaFactory != null) {
						telaFactory.limparCache();
					}
					this.navegador.removerPainel("CONFIG_USER");
					this.navegador.clearHistory();
					this.navegador.limparImagensPerfil();
					this.navegador.navegarPara("LOGIN", false);
				}
				if (isOpen)
					toggleMenu();
			});
		}
		// Trabalhos button
		if (btnTrabalhos != null) {
			for (ActionListener al : btnTrabalhos.getActionListeners()) {
				btnTrabalhos.removeActionListener(al);
			}
			btnTrabalhos.addActionListener(e -> {
				if (this.navegador != null) {
					Usuario u = this.navegador.getCurrentUser();
					// SAFETY: check for null user to avoid NPE when no one is logged in
					if (u != null) {
						if (u.isContratante()) {
							this.navegador.navegarPara("SERVICOS");
						} else if (u.isContratado()) {
							this.navegador.navegarPara("CONTRATADO");
						} else {
							navegador.navegarPara("TEMP");
						}
					} else {
						// Sem usuário logado: fallback para tela temporária (ou login)
						navegador.navegarPara("TEMP");
					}
				}
				if (isOpen)
					toggleMenu();
			});
		}
		// Profile button
		if (btnProfile != null) {
			for (ActionListener al : btnProfile.getActionListeners()) {
				btnProfile.removeActionListener(al);
			}
			btnProfile.addActionListener(e -> {
				if (this.navegador != null) {
					Usuario usuario = this.navegador.getCurrentUser();
					if (usuario != null) {
						if (telaFactory != null) {
							String panelName = telaFactory.criarTelaConfigUser(usuario);
							this.navegador.navegarPara(panelName);
						} else {
							Usuario usuarioBanco = usuarioDAO.getUsuarioById(usuario.getIdUsuario());
							if (usuarioBanco != null) {
								this.navegador.setCurrentUser(usuarioBanco);
								usuario = usuarioBanco;
							}
							TelaConfigUser telaConfigUser = new TelaConfigUser();
							new TelaConfigUserController(telaConfigUser, usuarioDAO, navegador, usuario);
							this.navegador.adicionarPainel("CONFIG_USER", telaConfigUser);
							this.navegador.navegarPara("CONFIG_USER");
						}
					} else {
						JOptionPane.showMessageDialog(this, "Nenhum usuário logado.", "Erro",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				if (isOpen)
					toggleMenu();
			});
		}
	}

	public void setOnStateChange(Consumer<Boolean> callback) {
		this.onStateChange = callback;
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
		JButton button = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Desenha o fundo arredondado
				if (getModel().isPressed()) {
					g2.setColor(getBackground().darker());
				} else if (getModel().isRollover()) {
					g2.setColor(getBackground().brighter());
				} else {
					g2.setColor(getBackground());
				}
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

				// Desenha o texto
				g2.setColor(getForeground());
				FontMetrics fm = g2.getFontMetrics();
				int textWidth = fm.stringWidth(getText());
				int textHeight = fm.getAscent();
				int x = (getWidth() - textWidth) / 2;
				int y = (getHeight() + textHeight) / 2 - 2;
				g2.drawString(getText(), x, y);

				g2.dispose();
			}

			@Override
			protected void paintBorder(Graphics g) {
				// Não desenha borda padrão
			}
		}; // Fim da classe anônima JButton

		button.setAlignmentX(Component.CENTER_ALIGNMENT); // Define alinhamento horizontal do botão como centralizado
		button.setBackground(Color.GRAY); // Define cor de fundo cinza para o botão
		button.setForeground(Color.WHITE); // Define cor do texto como branco
		button.setFocusPainted(false); // Remove indicador visual de foco (borda ao selecionar)
		button.setBorderPainted(false); // Remove pintura de borda padrão do Swing
		button.setContentAreaFilled(false); // Remove preenchimento automático da área de conteúdo
		button.setOpaque(false); // Define botão como não opaco (transparente onde não foi pintado)
		int buttonWidth = MENU_WIDTH - (2 * MARGIN); // Calcula largura do botão subtraindo margens dos dois lados (250 - 20 = 230 pixels)
		button.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT)); // Define tamanho máximo do botão (230 x 50 pixels)
		button.setPreferredSize(new Dimension(buttonWidth, BUTTON_HEIGHT)); // Define tamanho preferencial do botão (230 x 50 pixels)
		button.setMinimumSize(new Dimension(buttonWidth, BUTTON_HEIGHT)); // Define tamanho mínimo do botão (230 x 50 pixels)
		return button; // Retorna botão configurado e estilizado
	} // Fim do método createMenuButton

	/**
	 * Retorna se o menu está aberto.
	 * 
	 * @return true se menu está aberto, false caso contrário
	 */
	public boolean isOpen() { // Método getter público que retorna estado de abertura do menu
		return isOpen; // Retorna valor da flag isOpen
	} // Fim do método isOpen

	/**
	 * Retorna se uma animação está em execução.
	 * 
	 * @return true se animação está rodando, false caso contrário
	 */
	public boolean isAnimating() { // Método getter público que retorna se animação está executando
		return isAnimating; // Retorna valor da flag isAnimating
	} // Fim do método isAnimating
} // Fim da classe DrawerMenu