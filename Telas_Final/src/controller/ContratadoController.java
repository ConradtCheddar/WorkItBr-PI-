package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.UsuarioDAO;
import view.TelaContratado;
import view.TelaContratante;

public class ContratadoController {
	private final TelaContratado view;
	private final UsuarioDAO model;
	private final Navegador navegador;

	public ContratadoController(TelaContratado view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
		this.view.barra(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        navegador.navegarPara("TEMP");
		    }
		});
		this.view.menu(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        navegador.navegarPara("TEMP");
		    }
		});
		
	}
}
