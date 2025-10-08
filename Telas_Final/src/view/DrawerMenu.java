package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.Navegador;

public class DrawerMenu extends JPanel {
    private static final int MENU_WIDTH = 250;
    private static final int MENU_HIDE_POSITION = -MENU_WIDTH;
    private static final int MENU_SHOW_POSITION = 0;
    private JFrame parentFrame;
    private Navegador navegador; // Add Navegador reference
    private int currentPosition = MENU_HIDE_POSITION;
    private Timer timer;
    private JButton btnLogout; // Store reference to logout button
    private JButton btnSettings; // Store reference to settings button

    public DrawerMenu(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        // Use default height if parentFrame is null
        int height = (parentFrame != null) ? parentFrame.getHeight() : 700;
        setPreferredSize(new Dimension(MENU_WIDTH, height));
        setVisible(false); // Hide menu at startup

        // Adiciona alguns botões ao menu
        add(createMenuButton("Home"));
        add(createMenuButton("Profile"));
        btnSettings = createMenuButton("Settings");
        add(btnSettings);
        btnLogout = createMenuButton("Logout");
        add(btnLogout);

        // Timer para animação
        timer = new Timer(10, e -> {
            if (currentPosition != MENU_SHOW_POSITION && currentPosition != MENU_HIDE_POSITION) {
                setLocation(currentPosition, 0);
            }
        });
    }

    // Set Navegador reference
    public void setNavegador(Navegador navegador) {
        this.navegador = navegador;
        // Attach settings action
        if (btnSettings != null) {
            for (ActionListener al : btnSettings.getActionListeners()) {
                btnSettings.removeActionListener(al);
            }
            btnSettings.addActionListener(e -> {
                if (this.navegador != null) {
                    this.navegador.navegarPara("TEMP");
                    this.setVisible(false); // Hide menu after navigation
                }
            });
        }
        // Attach logout action
        if (btnLogout != null) {
            for (ActionListener al : btnLogout.getActionListeners()) {
                btnLogout.removeActionListener(al);
            }
            btnLogout.addActionListener(e -> {
                if (this.navegador != null) {
                    this.navegador.navegarPara("LOGIN");
                    this.setVisible(false); // Hide menu after logout
                }
            });
        }
    }

    // Método para alternar entre mostrar e ocultar o menu
    public void toggleMenu() {
        System.out.println("toggleMenu called, current visible: " + isVisible()); // debug
        setVisible(!isVisible()); // Just toggle visibility, let BorderLayout handle sizing/position
        if (getParent() != null) {
            getParent().revalidate();
            getParent().repaint();
        }
        revalidate();
        repaint();
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

    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        // Update preferred size when parentFrame is set
        if (parentFrame != null) {
            setPreferredSize(new Dimension(MENU_WIDTH, parentFrame.getHeight()));
            revalidate();
            repaint();
        }
    }
}