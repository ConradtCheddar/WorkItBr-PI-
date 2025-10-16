package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.function.Consumer;
import controller.Navegador;
import model.UsuarioDAO;
import model.Usuario;
import view.TelaConfigUser;
import controller.TelaConfigUserController;

public class DrawerMenu extends JPanel {
    private static final int MENU_WIDTH = 250;
    private static final int ANIMATION_STEP = 20;
    private static final int ANIMATION_DELAY = 5;
    private boolean isOpen = false;
    private boolean animating = false;
    private Timer animationTimer;
    private int currentWidth = 0;
    private JButton btnLogout;
    private JButton btnSettings;
    private JButton btnProfile;
    private JButton btnTrabalhos;
    private UsuarioDAO usuarioDAO;
    private Navegador navegador;
    private Consumer<Boolean> onStateChange; // callback para Primario

    public DrawerMenu(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.RED); // cor visível para depuração
        setOpaque(true);
        // Começa fechado
        currentWidth = 0;
        setPreferredSize(new Dimension(currentWidth, 0));
        setMaximumSize(new Dimension(MENU_WIDTH, Integer.MAX_VALUE));
        setMinimumSize(new Dimension(0, 0));
        setVisible(true);
        isOpen = false;
        add(createMenuButton("Home"));
        btnProfile = createMenuButton("Profile");
        add(btnProfile);
        btnTrabalhos = createMenuButton("Trabalhos");
        add(btnTrabalhos);
        btnSettings = createMenuButton("Settings");
        add(btnSettings);
        btnLogout = createMenuButton("Logout");
        add(btnLogout);
        revalidate();
        repaint();
    }

    public void setNavegador(Navegador navegador) {
        this.navegador = navegador;
        if (btnSettings != null) {
            for (ActionListener al : btnSettings.getActionListeners()) {
                btnSettings.removeActionListener(al);
            }
            btnSettings.addActionListener(e -> {
                if (this.navegador != null) {
                    this.navegador.navegarPara("TEMP");
                }
            });
        }
        if (btnLogout != null) {
            for (ActionListener al : btnLogout.getActionListeners()) {
                btnLogout.removeActionListener(al);
            }
            btnLogout.addActionListener(e -> {
                if (this.navegador != null) {
                    this.navegador.navegarPara("LOGIN");
                }
            });
        }
        if (btnTrabalhos != null) {
            for (ActionListener al : btnTrabalhos.getActionListeners()) {
                btnTrabalhos.removeActionListener(al);
            }
            btnTrabalhos.addActionListener(e -> {
                if (this.navegador != null) {
                    this.navegador.navegarPara("SERVICOS");
                }
            });
        }
        if (btnProfile != null) {
            for (ActionListener al : btnProfile.getActionListeners()) {
                btnProfile.removeActionListener(al);
            }
            btnProfile.addActionListener(e -> {
                if (this.navegador != null) {
                    Usuario usuario = this.navegador.getCurrentUser();
                    if (usuario != null) {
                        try {
                            java.lang.reflect.Field cPanelField = navegador.getClass().getDeclaredField("cPanel");
                            cPanelField.setAccessible(true);
                            javax.swing.JPanel cPanel = (javax.swing.JPanel) cPanelField.get(navegador);
                            java.awt.Component toRemove = null;
                            for (java.awt.Component comp : cPanel.getComponents()) {
                                if ("CONFIG_USER".equals(cPanel.getClientProperty(comp))) {
                                    toRemove = comp;
                                    break;
                                }
                            }
                            if (toRemove != null) {
                                cPanel.remove(toRemove);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        TelaConfigUser telaConfigUser = new TelaConfigUser();
                        new TelaConfigUserController(telaConfigUser, usuarioDAO, navegador, usuario);
                        this.navegador.adicionarPainel("CONFIG_USER", telaConfigUser);
                        this.navegador.navegarPara("CONFIG_USER");
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhum usuário logado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    public void setOnStateChange(Consumer<Boolean> callback) {
        this.onStateChange = callback;
    }

    // Permite ajuste de largura para animação
    public void setMenuWidth(int width) {
        if (width < 0) width = 0;
        System.out.println("[DEBUG] setMenuWidth: " + width);
        currentWidth = width;
        setPreferredSize(new Dimension(currentWidth, getParent() != null ? getParent().getHeight() : getPreferredSize().height));
        setMaximumSize(new Dimension(MENU_WIDTH, Integer.MAX_VALUE));
        revalidate();
        repaint();
        if (getParent() != null) {
            getParent().revalidate();
            getParent().repaint();
            // Força revalidação do JFrame principal
            java.awt.Container top = getTopLevelAncestor();
            if (top != null) {
                top.revalidate();
                top.repaint();
            }
        }
    }
    // Permite ajuste de altura ao redimensionar
    public void setMenuHeight(int height) {
        setPreferredSize(new Dimension(getPreferredSize().width, height));
        setMaximumSize(new Dimension(getMaximumSize().width, height));
        revalidate();
        repaint();
        if (getParent() != null) {
            getParent().revalidate();
            getParent().repaint();
        }
    }
    // Permite ajuste de posição (não faz nada, pois OverlayLayout cuida disso)
    public void setMenuLocation(int x, int y) {
        // Não faz nada
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(0, 0);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(MENU_WIDTH, Integer.MAX_VALUE);
    }

    public void toggleMenu() {
        if (animating) return;
        System.out.println("[DEBUG] toggleMenu chamado. isOpen=" + isOpen + ", currentWidth=" + currentWidth);
        animating = true;
        final int start = currentWidth;
        final int end = isOpen ? 0 : MENU_WIDTH;
        final int direction = (end > start) ? 1 : -1;
        animationTimer = new Timer(ANIMATION_DELAY, null);
        animationTimer.addActionListener(e -> {
            int next = currentWidth + direction * ANIMATION_STEP;
            if ((direction > 0 && next >= end) || (direction < 0 && next <= end)) {
                next = end;
            }
            setMenuWidth(next);
            if (next == end) {
                animationTimer.stop();
                isOpen = !isOpen;
                animating = false;
                System.out.println("[DEBUG] toggleMenu terminou. isOpen=" + isOpen + ", currentWidth=" + currentWidth);
                if (onStateChange != null) onStateChange.accept(isOpen);
            }
        });
        animationTimer.start();
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(MENU_WIDTH, 50));
        button.setPreferredSize(new Dimension(MENU_WIDTH, 50));
        return button;
    }

    public boolean isOpen() {
        return isOpen;
    }
    public boolean isAnimating() {
        return animating;
    }
}