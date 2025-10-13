package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.Navegador;
import model.UsuarioDAO;
import model.Usuario;
import view.TelaConfigUser;
import controller.TelaConfigUserController;

public class DrawerMenu extends JPanel {
    private static final int MENU_WIDTH = 250;
    private JFrame parentFrame;
    private Navegador navegador;
    private int currentPosition; // x position
    private boolean isOpen = false;
    private Thread animationThread = null;
    private JButton btnLogout;
    private JButton btnSettings;
    private JButton btnProfile;
    private JButton btnTrabalhos;
    private UsuarioDAO usuarioDAO;

    public DrawerMenu(JFrame parentFrame, UsuarioDAO usuarioDAO) {
        this.parentFrame = parentFrame;
        this.usuarioDAO = usuarioDAO;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        int height = (parentFrame != null) ? parentFrame.getHeight() : 700;
        setPreferredSize(new Dimension(MENU_WIDTH, height));
        setVisible(false);
        // Start off-screen right
        currentPosition = (parentFrame != null ? parentFrame.getWidth() : 900);
        setBounds(currentPosition, 0, MENU_WIDTH, height);

        add(createMenuButton("Home"));
        btnProfile = createMenuButton("Profile");
        add(btnProfile);
        btnTrabalhos = createMenuButton("Trabalhos");
        add(btnTrabalhos);
        btnSettings = createMenuButton("Settings");
        add(btnSettings);
        btnLogout = createMenuButton("Logout");
        add(btnLogout);
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
                    this.setVisible(false);
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
                    this.setVisible(false);
                }
            });
        }
        if (btnTrabalhos != null) {
            for (ActionListener al : btnTrabalhos.getActionListeners()) {
                btnTrabalhos.removeActionListener(al);
            }
            btnTrabalhos.addActionListener(e -> {
                if (this.navegador != null) {
                    this.navegador.navegarPara("CADASTRO_CONTRATANTE");
                    this.setVisible(false);
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
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhum usuário logado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    }

    public void toggleMenu() {
        if (parentFrame == null) return;
        int frameWidth = parentFrame.getWidth();
        int targetOpen = frameWidth - MENU_WIDTH;
        int targetClose = frameWidth;
        setVisible(true);
        if (animationThread != null && animationThread.isAlive()) {
            animationThread.interrupt();
        }
        animationThread = new Thread(() -> {
            try {
                if (!isOpen) {
                    // Animate open
                    for (int x = frameWidth; x >= targetOpen; x -= 20) {
                        setLocation(x, 0);
                        currentPosition = x;
                        Thread.sleep(5);
                    }
                    setLocation(targetOpen, 0);
                    currentPosition = targetOpen;
                    isOpen = true;
                } else {
                    // Animate close
                    for (int x = currentPosition; x <= targetClose; x += 20) {
                        setLocation(x, 0);
                        currentPosition = x;
                        Thread.sleep(5);
                    }
                    setLocation(targetClose, 0);
                    currentPosition = targetClose;
                    isOpen = false;
                    SwingUtilities.invokeLater(() -> setVisible(false));
                }
            } catch (InterruptedException e) {
                // Animation interrupted
            }
        });
        animationThread.start();
    }

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
        if (parentFrame != null) {
            setPreferredSize(new Dimension(MENU_WIDTH, parentFrame.getHeight()));
            int frameWidth = parentFrame.getWidth();
            int y = 0;
            int x = isOpen ? (frameWidth - MENU_WIDTH) : frameWidth;
            setBounds(x, y, MENU_WIDTH, parentFrame.getHeight());
            revalidate();
            repaint();
        }
    }
}