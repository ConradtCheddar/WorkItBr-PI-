package controller;

import model.Servico;
import model.ServicoDAO;
import view.VisServicoCnte;
import view.VisServicoCnteAceito;

public class VisServicoCnteAceitoController {
	private final VisServicoCnteAceito view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private final Servico s;

	public VisServicoCnteAceitoController(VisServicoCnteAceito view, ServicoDAO model, Navegador navegador, Servico s){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.s= s;
		
		this.view.contratante(e ->{
			
		});
		
		this.view.voltarEditar(e ->{
			navegador.navegarPara("SERVICOS");
		});
		
		
	}
}
