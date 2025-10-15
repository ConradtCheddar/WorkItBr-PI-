package controller;

import model.Servico;
import model.ServicoDAO;
import model.UsuarioDAO;
import view.VisServico;

public class VisServicoController {
	private final VisServico view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private final Servico s;

	public VisServicoController(VisServico view, ServicoDAO model, Navegador navegador, Servico s){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.s= s;
		
		this.view.aceitar(e ->{
			
			Servico servico = this.model.configID(s.getNome_Servico());
			
			s.setIdServico(servico.getIdServico());
			
			s.setIdContratado(navegador.getCurrentUser().getIdUsuario());
			
			this.model.aceitarServico(s);
			
			navegador.navegarPara("CONTRATADO");
		});
		
	}
}
