package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

	public class DrawerMenu extends JPanel {
    private static final int MENU_WIDTH = 250;
    private static final int MENU_HIDE_POSITION = -MENU_WIDTH;
    private static final int MENU_SHOW_POSITION = 0;
    private JFrame parentFrame;
    private int currentPosition = MENU_HIDE_POSITION;
    private Timer timer;

    public DrawerMenu(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(MENU_WIDTH, parentFrame.getHeight()));
        
        // Adiciona alguns botões ao menu
        add(createMenuButton("Home"));
        add(createMenuButton("Profile"));
        add(createMenuButton("Settings"));
        add(createMenuButton("Logout"));

        // Timer para animação
        timer = new Timer(10, e -> {
            if (currentPosition != MENU_SHOW_POSITION && currentPosition != MENU_HIDE_POSITION) {
                setLocation(currentPosition, 0);
            }
        });
    }

    // Método para alternar entre mostrar e ocultar o menu
    public void toggleMenu() {
        if (currentPosition == MENU_HIDE_POSITION) {
            // Inicia animação para mostrar o menu
            animateMenu(MENU_SHOW_POSITION);
        } else {
            // Inicia animação para ocultar o menu
            animateMenu(MENU_HIDE_POSITION);
        }
    }

    // Método para animar o menu deslizando
    private void animateMenu(int targetPosition) {
        new Thread(() -> {
            while (currentPosition != targetPosition) {
                // A cada passo, move o menu um pouco
                currentPosition += (targetPosition > currentPosition) ? 10 : -10;
                if (Math.abs(currentPosition - targetPosition) < 10) {
                    currentPosition = targetPosition;
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            setLocation(currentPosition, 0);
        }).start();
    }

    // Método para criar um botão do menu
	private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(MENU_WIDTH, 50));
        return button;
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        parentFrame.repaint();
    }
}

