package controller;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.UsuarioDAO;
import view.Mensagem;
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

        this.view.setBackEnabled(navegador.hasHistory());
        this.view.setMenuEnabled(navegador.getCurrentUser() != null);
        
        Mensagem M = new Mensagem();
        
        this.navegador.setOnHistoryChange(() -> SwingUtilities.invokeLater(() -> {
            this.view.setBackEnabled(navegador.hasHistory());
            this.view.setMenuEnabled(navegador.getCurrentUser() != null);
        }));
        
        for (MouseListener ml : this.view.getLblBarra().getMouseListeners()) {
            this.view.getLblBarra().removeMouseListener(ml);
        }
        
        this.view.getLblBarra().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (navegador.getCurrentUser() == null) {
                  M.Aviso("Você precisa estar logado para acessar o menu.","Acesso Negado");
                  
                    return;
                }
                popup2.getView().toggleMenu();
            }
        });

        for (MouseListener ml : this.view.getLblVoltar().getMouseListeners()) {
            if (ml.getClass().getName().contains("WBController")) {
                this.view.getLblVoltar().removeMouseListener(ml);
            }
        }
        
        this.view.getLblVoltar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!navegador.hasHistory()) {
                   M.Aviso( "Não há tela anterior para retornar.","Aviso");
                    return;
                }
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