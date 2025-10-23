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
			// Verifica se há um usuário logado
			if (navegador.getCurrentUser() == null) {
				javax.swing.JOptionPane.showMessageDialog(null, "Erro: Nenhum usuário logado. Por favor, faça login novamente.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
				navegador.navegarPara("LOGIN");
				return;
			}
			
			Servico servico = this.model.configID(s.getNome_Servico());
			
			s.setIdServico(servico.getIdServico());
			
			s.setIdContratado(navegador.getCurrentUser().getIdUsuario());
			
			this.model.aceitarServico(s);
			
			navegador.navegarPara("CONTRATADO");
		});
		
	}
}
