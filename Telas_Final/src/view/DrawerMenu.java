package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.function.Consumer;
import controller.Navegador;
import controller.TelaFactory;
import model.UsuarioDAO;
import model.Usuario;
import view.TelaConfigUser;
import controller.TelaConfigUserController;

public class DrawerMenu extends JPanel {
    private static final int MENU_WIDTH = 250;
    private static final int ANIMATION_STEP = 20; // Mais rápido
    private static final int ANIMATION_DELAY = 8; // Mantém fluidez
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
    private TelaFactory telaFactory;
    private Consumer<Boolean> onStateChange; // retorno de chamada para Primario

    public DrawerMenu(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
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
    
    public void setTelaFactory(TelaFactory telaFactory) {
        this.telaFactory = telaFactory;
    }

    public void setNavegador(Navegador navegador) {
        this.navegador = navegador;
        // Home button: fecha o menu após ação
        JButton btnHome = (JButton) getComponent(0);
        for (ActionListener al : btnHome.getActionListeners()) {
            btnHome.removeActionListener(al);
        }
        btnHome.addActionListener(e -> {
            if (this.navegador != null) {
                this.navegador.navegarPara("HOME");
            }
            if (isOpen) toggleMenu();
        });
        if (btnSettings != null) {
            for (ActionListener al : btnSettings.getActionListeners()) {
                btnSettings.removeActionListener(al);
            }
            btnSettings.addActionListener(e -> {
                if (this.navegador != null) {
                    this.navegador.navegarPara("TEMP");
                }
                if (isOpen) toggleMenu();
            });
        }
        if (btnLogout != null) {
            for (ActionListener al : btnLogout.getActionListeners()) {
                btnLogout.removeActionListener(al);
            }
            btnLogout.addActionListener(e -> {
                if (this.navegador != null) {
                    // Limpa o usuário atual e remove telas dinâmicas
                    this.navegador.clearCurrentUser();
                    if (telaFactory != null) {
                        telaFactory.limparCache();
                    }
                    // Remove telas de configuração que podem existir
                    this.navegador.removerPainel("CONFIG_USER");
                    // Limpa o histórico de navegação para desabilitar o botão voltar
                    this.navegador.clearHistory();
                    // Navega para LOGIN sem empilhar
                    this.navegador.navegarPara("LOGIN", false);
                }
                if (isOpen) toggleMenu();
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
                if (isOpen) toggleMenu();
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
                        // Usa TelaFactory se disponível, senão cria manualmente
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
                        JOptionPane.showMessageDialog(this, "Nenhum usuário logado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                }
                if (isOpen) toggleMenu();
            });
        }
    }

    public void setOnStateChange(Consumer<Boolean> callback) {
        this.onStateChange = callback;
    }

    // Permite ajuste de largura para animação
    public void setMenuWidth(int width) {
        if (width < 0) width = 0;
        currentWidth = width;
        setPreferredSize(new Dimension(currentWidth, getParent() != null ? getParent().getHeight() : getPreferredSize().height));
        setMaximumSize(new Dimension(MENU_WIDTH, Integer.MAX_VALUE));
        setSize(currentWidth, getHeight()); // Atualiza tamanho imediatamente
        revalidate(); // Garante que o layout dos botões seja recalculado
        repaint();    // Redesenha o DrawerMenu
        // Força repaint do menuLayer (GlassPane) se existir
        java.awt.Container top = getTopLevelAncestor();
        if (top instanceof JFrame) {
            java.awt.Component glass = ((JFrame)top).getGlassPane();
            if (glass != null) glass.repaint();
        }
        // Atualiza posição para ancorar à direita, se possível
        if (getParent() != null) {
            int parentWidth = getParent().getWidth();
            setLocation(parentWidth - currentWidth, 0);
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