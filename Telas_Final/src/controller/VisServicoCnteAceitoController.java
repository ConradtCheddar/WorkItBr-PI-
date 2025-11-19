package controller;

import javax.swing.JOptionPane;

import model.Servico;
import model.ServicoDAO;
import model.Usuario;
import model.UsuarioDAO;
import view.VisServicoCnteAceito;
import view.Mensagem;
import view.VisContratado;
import javax.swing.JOptionPane;

public class VisServicoCnteAceitoController {
	private final VisServicoCnteAceito view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private final Servico s;
	private final TelaFactory telaFactory;

	public VisServicoCnteAceitoController(VisServicoCnteAceito view, ServicoDAO model, Navegador navegador, Servico s, TelaFactory telaFactory){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.s = s;
		this.telaFactory = telaFactory;
		
		Mensagem M = new Mensagem();
		
		this.view.contratado(e ->{
			int idContratado = s.getIdContratado();
			if (idContratado <= 0) {
				M.Aviso("Contratado não definido para este serviço.", "Aviso");
				return;
			}
			UsuarioDAO udao = new UsuarioDAO();
			Usuario contratado = udao.getUsuarioById(idContratado);
			if (contratado == null) {
				M.Erro("Usuário contratado não encontrado.", "Erro");
				return;
			}
			
			String panelName = telaFactory.criarVisContratado(contratado);
			navegador.navegarPara(panelName);
		});
		
		this.view.voltar(e ->{
			navegador.navegarPara("SERV");
		});
		
	}
}