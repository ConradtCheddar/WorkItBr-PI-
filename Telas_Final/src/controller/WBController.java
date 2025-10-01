package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.UsuarioDAO;
import view.TelaContratante;
import view.wbBarra;

public class WBController {
    private final wbBarra view;
    private final UsuarioDAO model;
    private final Navegador navegador;
    private final PopupController popup;

    public WBController(wbBarra view, UsuarioDAO model, Navegador navegador, PopupController popup) {
        this.view = view;
        this.model = model;
        this.navegador = navegador;
        this.popup = popup;
        
        this.view.barra(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popup.PopupMenu(e, view.getLblBarra());
            }
        });


        this.view.menu(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
    }
}
