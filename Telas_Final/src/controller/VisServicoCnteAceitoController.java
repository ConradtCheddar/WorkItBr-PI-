package controller;

import model.Servico;
import model.ServicoDAO;
import view.VisServicoCnte;
import view.VisServicoCnteAceito;
import model.UsuarioDAO;
import model.Usuario;
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
		
		this.view.contratante(e ->{
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
			int idServico = s.getIdServico();
			String prevPanel = (idServico > 0) ? ("VIS_SERVICO_CNTE_ACEITO_" + idServico) : "SERVICOS";
			
			String panelName = telaFactory.criarVisContratado(contratado, prevPanel);
			navegador.navegarPara(panelName);
		});
		
		this.view.voltar(e ->{
			navegador.navegarPara("SERVICOS");
		});
		
	}
}