package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import model.Usuario;
import model.UsuarioDAO;
import view.TelaCadastro;
import view.TelaContratante;

public class ContratanteController {
	private final TelaContratante view;
	private final UsuarioDAO model;
	private final Navegador navegador;

	public ContratanteController(TelaContratante view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		

	}
}
