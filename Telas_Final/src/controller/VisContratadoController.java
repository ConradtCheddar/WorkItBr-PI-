package controller;

import model.Servico;
import view.VisServicoCnteAceito;
import model.UsuarioDAO;
import model.Usuario;
import view.Mensagem;
import view.VisContratado;
import javax.swing.JOptionPane;

public class VisContratadoController {
	private final VisServicoCnteAceito view;
	private final Navegador navegador;
	private final Servico s;

	public VisContratadoController(VisServicoCnteAceito view, Navegador navegador, Servico s){
		this.view = view;
		this.navegador = navegador;
		this.s= s;
		
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
			String panelName = "VIS_CONTRATADO_" + contratado.getIdUsuario();
			VisContratado tela = new VisContratado(contratado);
			navegador.adicionarPainel(panelName, tela);
			navegador.navegarPara(panelName);
			int idServicoLocal = s.getIdServico();
			// Usa o nome com sufixo de id para voltar corretamente
			String prevPanelLocal = (idServicoLocal > 0) ? ("VIS_SERVICO_CNTE_ACEITO_" + idServicoLocal) : "SERVICOS";
			tela.voltar(ev -> {
				navegador.navegarPara(prevPanelLocal);
			});
		});
		
		this.view.voltar(e ->{
			navegador.navegarPara("SERVICOS");
		});
		
	}
}