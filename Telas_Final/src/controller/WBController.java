package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import model.Usuario;
import model.UsuarioDAO;
import view.TelaContratante;
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

        // initial back button state
        this.view.setBackEnabled(navegador.hasHistory());
        // register listener so UI updates when history changes
        this.navegador.setOnHistoryChange(() -> SwingUtilities.invokeLater(() -> {
            this.view.setBackEnabled(navegador.hasHistory());
        }));
        
        this.view.barra(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popup2.getView().toggleMenu();
            }
        });

        this.view.menu(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Ao clicar no ícone de voltar, delega ao Navegador para voltar à tela anterior
                navegador.voltar();
            }
        });
    }
}