package controller;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.UsuarioDAO;
import view.wbBarra;

public class WBController {
    private final wbBarra view;
    private final UsuarioDAO model;
    private final Navegador navegador;
    private final PopupController popup;
    private final PopupMenuController popup2;

    public WBController(wbBarra view, UsuarioDAO model, Navegador navegador, PopupController popup, PopupMenuController popup2) {
        this.view = view;
        this.model = model;
        this.navegador = navegador;
        this.popup = popup;
        this.popup2 = popup2;

        // Estado inicial do botão voltar
        this.view.setBackEnabled(navegador.hasHistory());
        // Estado inicial do menu (desabilitado se não houver usuário logado)
        this.view.setMenuEnabled(navegador.getCurrentUser() != null);
        
        // registra ouvinte para atualizar a UI quando o histórico mudar
        this.navegador.setOnHistoryChange(() -> SwingUtilities.invokeLater(() -> {
            this.view.setBackEnabled(navegador.hasHistory());
            // Atualiza também o estado do menu baseado no usuário logado
            this.view.setMenuEnabled(navegador.getCurrentUser() != null);
        }));
        
        // Remove TODOS os listeners antigos antes de adicionar o novo (para evitar duplicação)
        for (MouseListener ml : this.view.getLblBarra().getMouseListeners()) {
            this.view.getLblBarra().removeMouseListener(ml);
        }
        
        // Configuração do menu (barra de 3 linhas)
        this.view.getLblBarra().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Verifica se o usuário está logado antes de abrir o menu
                if (navegador.getCurrentUser() == null) {
                    JOptionPane.showMessageDialog(
                        view,
                        "Você precisa estar logado para acessar o menu.",
                        "Acesso Negado",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return; // PARA AQUI - não abre o menu
                }
                // Só abre o menu se o usuário estiver logado
                popup2.getView().toggleMenu();
            }
        });

        // Remove TODOS os listeners antigos da seta antes de adicionar o novo
        for (MouseListener ml : this.view.getLblVoltar().getMouseListeners()) {
            // Mantém apenas os listeners internos da própria view (os primeiros adicionados)
            if (ml.getClass().getName().contains("WBController")) {
                this.view.getLblVoltar().removeMouseListener(ml);
            }
        }
        
        // Configuração da seta de voltar
        this.view.getLblVoltar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("[WBController] Seta de voltar clicada!");
                // Verifica se há histórico antes de navegar
                if (!navegador.hasHistory()) {
                    System.out.println("[WBController] Não há histórico para voltar");
                    JOptionPane.showMessageDialog(
                        view,
                        "Não há tela anterior para retornar.",
                        "Aviso",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    return; // PARA AQUI - não navega
                }
                // Só navega se houver histórico
                System.out.println("[WBController] Chamando navegador.voltar()");
                navegador.voltar();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                if (navegador.hasHistory()) {
                    view.getLblVoltar().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                view.getLblVoltar().setCursor(Cursor.getDefaultCursor());
            }
        });
    }
}