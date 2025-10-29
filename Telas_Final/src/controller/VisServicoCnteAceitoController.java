package controller;

import model.Servico;
import model.ServicoDAO;
import view.VisServicoCnte;
import view.VisServicoCnteAceito;
import model.UsuarioDAO;
import model.Usuario;
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
		
		this.view.contratante(e ->{
			// obtém o id do contratado a partir do serviço
			int idContratado = s.getIdContratado();
			if (idContratado <= 0) {
				JOptionPane.showMessageDialog(null, "Contratado não definido para este serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// Busca o usuário no banco
			UsuarioDAO udao = new UsuarioDAO();
			Usuario contratado = udao.getUsuarioById(idContratado);
			if (contratado == null) {
				JOptionPane.showMessageDialog(null, "Usuário contratado não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Define a tela de retorno
			int idServico = s.getIdServico();
			String prevPanel = (idServico > 0) ? ("VIS_SERVICO_CNTE_ACEITO_" + idServico) : "SERVICOS";
			
			// Usa TelaFactory para criar tela de visualização do contratado
			String panelName = telaFactory.criarVisContratado(contratado, prevPanel);
			navegador.navegarPara(panelName);
		});
		
		this.view.voltar(e ->{
			navegador.navegarPara("SERVICOS");
		});
		
		
	}
}