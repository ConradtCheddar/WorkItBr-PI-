package controller;

import model.Servico;
import model.ServicoDAO;
import model.UsuarioDAO;
import view.Mensagem;
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
		
		Mensagem M = new Mensagem();
		
		this.view.aceitar(e ->{
			if (navegador.getCurrentUser() == null) {
				M.Erro("Erro: Nenhum usuário logado. Por favor, faça login novamente.", "Erro");
				navegador.navegarPara("LOGIN");
				return;
			}
			
			Servico servico = null;
			if (s != null && s.getIdServico() > 0) {
				servico = this.model.buscarServicoPorId(s.getIdServico());
			} else if (s != null && s.getNome_Servico() != null) {
				servico = this.model.buscarServicoPorNome(s.getNome_Servico());
			}
			
			if (servico == null) {
                M.Erro("Erro: Serviço não encontrado no banco de dados.", "Erro");
				navegador.navegarPara("CONTRATADO");
				return;
			}
			
			s.setIdServico(servico.getIdServico());
			s.setIdContratado(navegador.getCurrentUser().getIdUsuario());
			
			this.model.aceitarServico(s);
			
			navegador.navegarPara("CONTRATADO");
		});
		
	}
}