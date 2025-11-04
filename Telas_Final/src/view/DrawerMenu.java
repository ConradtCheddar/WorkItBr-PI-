package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;
import controller.Navegador;
import controller.TelaFactory;
import model.UsuarioDAO;
import model.Usuario;
import view.TelaConfigUser;
import controller.TelaConfigUserController;

public class DrawerMenu extends JPanel {
	private static final int MENU_WIDTH = 250;
	private static final int MARGIN = 10; // Margem entre botões e borda
	private static final int BUTTON_HEIGHT = 50;
	private static final int ANIMATION_DURATION = 300; // Duração da animação em ms
	private static final int ANIMATION_FPS = 60; // Frames por segundo

	private boolean isOpen = false;
	private boolean isAnimating = false;
	private Timer animationTimer;
	private long animationStartTime;
	private int targetX;
	private int startX;

	private JButton btnLogout;
	private JButton btnSettings;
	private JButton btnProfile;
	private JButton btnTrabalhos;
	private JButton btnHome;
	private UsuarioDAO usuarioDAO;
	private Navegador navegador;
	private TelaFactory telaFactory;
	private Consumer<Boolean> onStateChange;
	private JPanel topPanel;
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
		};

		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setBackground(Color.GRAY);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		int buttonWidth = MENU_WIDTH - (2 * MARGIN);
		button.setMaximumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
		button.setPreferredSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
		button.setMinimumSize(new Dimension(buttonWidth, BUTTON_HEIGHT));
		return button;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public boolean isAnimating() {
		return isAnimating;
	}
}