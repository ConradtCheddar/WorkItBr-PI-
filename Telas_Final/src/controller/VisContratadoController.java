package controller;

import model.Servico;
import view.VisServicoCnteAceito;
import model.UsuarioDAO;
import model.Usuario;
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
			// Cria a tela de visualização do contratado e navega até ela
			String panelName = "VIS_CONTRATADO_" + contratado.getIdUsuario();
			VisContratado tela = new VisContratado(contratado);
			navegador.adicionarPainel(panelName, tela);
			navegador.navegarPara(panelName);
			// registra ouvinte do botão Voltar para retornar à tela de serviço aceito (se houver)
			int idServicoLocal = s.getIdServico();
			String prevPanelLocal = (idServicoLocal > 0) ? ("VIS_Servico_Cnte_Aceito_" + idServicoLocal) : "SERVICOS";
			tela.voltar(ev -> {
				navegador.navegarPara(prevPanelLocal);
			});
		});
		
		this.view.voltar(e ->{
			navegador.navegarPara("SERVICOS");
		});
		
		
	}
}