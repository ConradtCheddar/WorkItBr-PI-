package view;

import javax.swing.*;
import util.FontScaler;

import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class DrawerMenu extends JPanel {
	private static final int MENU_WIDTH = 250;
	private static final int MARGIN = 10;
	private static final int ANIMATION_DURATION = 300;
	private static final int ANIMATION_FPS = 60;

	private boolean isOpen = false;
	private boolean isAnimating = false;
	private Timer animationTimer;
	private long animationStartTime;
	private int targetX;
	private int startX;

	private final JButton btnLogout;
	private final JButton btnProfile;
	private final JButton btnTrabalhos;
	private final JButton btnHome;
	
	private Consumer<Boolean> onStateChange;
	private final JPanel topPanel;
	private final JPanel bottomPanel;
	private Component parentComponent;

	public DrawerMenu() {
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);
		setOpaque(true);
		setPreferredSize(new Dimension(MENU_WIDTH, 0));

		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.setBackground(Color.DARK_GRAY);
		topPanel.setOpaque(true);
		topPanel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.setBackground(Color.DARK_GRAY);
		bottomPanel.setOpaque(true);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN));

		btnHome = createMenuButton("Histórico");
		topPanel.add(btnHome);
		topPanel.add(Box.createVerticalStrut(5));
		btnProfile = createMenuButton("Perfil");
		topPanel.add(btnProfile);
		topPanel.add(Box.createVerticalStrut(5));
		btnTrabalhos = createMenuButton("Trabalhos");
		topPanel.add(btnTrabalhos);
		topPanel.add(Box.createVerticalStrut(5));
		btnLogout = createMenuButton("Sair");
		bottomPanel.add(btnLogout);

		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);

		isOpen = false;
		
		// Configurar listener de redimensionamento para escalar tamanhos dos botões
		addComponentListener(new ComponentAdapter() {
			private int lastHeight = -1;
			
			@Override
			public void componentResized(ComponentEvent e) {
				int newHeight = e.getComponent().getHeight();
				// Só atualizar se a altura realmente mudou
				if (newHeight != lastHeight && newHeight > 0) {
					lastHeight = newHeight;
					updateButtonSizes();
				}
			}
		});
	}

	public JButton getBtnLogout() {
		return btnLogout;
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

	public void setOnStateChange(Consumer<Boolean> onStateChange) {
		this.onStateChange = onStateChange;
	}

	public void setParentComponent(Component parent) {
		this.parentComponent = parent;
		updateButtonSizes();
	}

	public void toggleMenu() {
		if (isAnimating)
			return;

		isOpen = !isOpen;
		startAnimation();
	}

	public void closeMenu() {
		if (!isOpen && !isAnimating)
			return;
		if (isAnimating) {
			if (animationTimer != null && animationTimer.isRunning()) {
				animationTimer.stop();
			}
			isAnimating = false;
		}
		if (isOpen) {
			isOpen = false;
			startAnimation();
		} else {
			setVisible(false);
		}
	}

	private void startAnimation() {
		if (animationTimer != null && animationTimer.isRunning()) {
			animationTimer.stop();
		}

		isAnimating = true;
		animationStartTime = System.currentTimeMillis();

		if (onStateChange != null) {
			onStateChange.accept(isOpen);
		}

		Container parent = getParent();
		if (parent != null) {
			if (isOpen) {
				startX = parent.getWidth();
				targetX = parent.getWidth() - MENU_WIDTH;
			} else {
				startX = parent.getWidth() - MENU_WIDTH;
				targetX = parent.getWidth();
			}

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

	private void updateAnimation() {
		long elapsed = System.currentTimeMillis() - animationStartTime;
		float progress = Math.min(1.0f, (float) elapsed / ANIMATION_DURATION);

		float easedProgress = easeOutCubic(progress);

		int currentX = startX + (int) ((targetX - startX) * easedProgress);

		Container parent = getParent();
		if (parent != null) {
			int y = 0;
			int height = parent.getHeight();
			setBounds(currentX, y, MENU_WIDTH, height);
			parent.revalidate();
			parent.repaint();
		}

		if (progress >= 1.0f) {
			if (animationTimer != null) {
				animationTimer.stop();
			}
			isAnimating = false;
		}
	}

	private float easeOutCubic(float t) {
		float f = t - 1.0f;
		return f * f * f + 1.0f;
	}

	private JButton createMenuButton(String text) {
		JButton button = new JButton(text);
		int initialHeight = getButtonHeight();
		button.setMaximumSize(new Dimension(Integer.MAX_VALUE, initialHeight));
		button.setPreferredSize(new Dimension(MENU_WIDTH - 2 * MARGIN, initialHeight));
		button.setMinimumSize(new Dimension(MENU_WIDTH - 2 * MARGIN, initialHeight));
		
		// Aplicar fonte inicial escalada
		int panelHeight = parentComponent != null ? parentComponent.getHeight() : 600;
		int fontSize = FontScaler.calculateFontSize(panelHeight, FontScaler.FontSize.BOTAO);
		Font initialFont = new Font("Tahoma", Font.PLAIN, fontSize);
		button.setFont(initialFont);
		
		button.setFocusPainted(false);
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBackground(new Color(70, 130, 180));
		button.setForeground(Color.WHITE);
		button.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
			BorderFactory.createEmptyBorder(5, 15, 5, 15)
		));
		button.setHorizontalAlignment(SwingConstants.LEFT);

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(new Color(100, 149, 237));
				button.setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(new Color(70, 130, 180));
			}
		});

		return button;
	}
	
	private int getButtonHeight() {
		int panelHeight = parentComponent != null ? parentComponent.getHeight() : getHeight();
		if (panelHeight <= 0) {
			panelHeight = 600; // valor padrão se ainda não foi renderizado
		}
		return FontScaler.calculateFontSize(panelHeight, FontScaler.FontSize.BOTAO) + 15;
	}
	
	public void updateButtonSizes() {
		int buttonHeight = getButtonHeight();
		int panelHeight = parentComponent != null ? parentComponent.getHeight() : 600;
		JButton[] buttons = { btnHome, btnProfile, btnTrabalhos, btnLogout };
		
		for (JButton button : buttons) {
			// Atualizar tamanho do botão
			button.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonHeight));
			button.setPreferredSize(new Dimension(MENU_WIDTH - 2 * MARGIN, buttonHeight));
			button.setMinimumSize(new Dimension(MENU_WIDTH - 2 * MARGIN, buttonHeight));
			
			// Aplicar fonte escalada com estilo bold
			int fontSize = FontScaler.calculateFontSize(panelHeight, FontScaler.FontSize.BOTAO);
			Font scaledFont = new Font("Tahoma", Font.PLAIN, fontSize);
			button.setFont(scaledFont);
		}
		
		revalidate();
		repaint();
	}
}