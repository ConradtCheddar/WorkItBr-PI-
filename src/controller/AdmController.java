package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.UsuarioDAO;
import view.TelaAdm;
import view.TelaContratante;

public class AdmController {
	private final TelaAdm view;
	private final UsuarioDAO model;
	private final Navegador navegador;

	public AdmController(TelaAdm view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
	}
}
