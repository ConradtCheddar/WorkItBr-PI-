package controller;

import model.Servico;
import model.ServicoDAO;
import view.VisServico;
import view.VisServicoCnte;

public class VisServicoCnteController {
	private final VisServicoCnte view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private final Servico s;

	public VisServicoCnteController(VisServicoCnte view, ServicoDAO model, Navegador navegador, Servico s){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.s= s;
		
		this.view.voltarEditar(e ->{
			navegador.navegarPara("SERVICOS");
		});
		
		
	}
}
