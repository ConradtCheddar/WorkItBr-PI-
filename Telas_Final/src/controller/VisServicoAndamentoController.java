package controller;

import model.Servico;
import model.ServicoDAO;
import model.UsuarioDAO;
import view.VisServico;

public class VisServicoAndamentoController {
	private final VisServico view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private final Servico s;

	public VisServicoAndamentoController(VisServico view, ServicoDAO model, Navegador navegador, Servico s){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.s= s;
		
		
	}
}
