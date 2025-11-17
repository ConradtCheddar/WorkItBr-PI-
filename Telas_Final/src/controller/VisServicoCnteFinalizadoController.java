package controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import model.Servico;
import model.ServicoDAO;
import model.Usuario;
import model.UsuarioDAO;
import view.Mensagem;
import view.VisServicoCnteFinalizado;

public class VisServicoCnteFinalizadoController {
	private final VisServicoCnteFinalizado view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private final Servico s;
	private final TelaFactory telaFactory;

	public VisServicoCnteFinalizadoController(VisServicoCnteFinalizado view, ServicoDAO model, Navegador navegador,
			Servico s, TelaFactory telaFactory) {
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.s = s;
		this.telaFactory = telaFactory;

		this.view.contratado(e -> {
			int idContratado = s.getIdContratado();
			if (idContratado <= 0) {
				JOptionPane.showMessageDialog(null, "Contratado não definido para este serviço.", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			UsuarioDAO udao = new UsuarioDAO();
			Usuario contratado = udao.getUsuarioById(idContratado);
			if (contratado == null) {
				JOptionPane.showMessageDialog(null, "Usuário contratado não encontrado.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int idServico = s.getIdServico();
			String prevPanel = (idServico > 0) ? ("VIS_SERVICO_CNTE_ACEITO_" + idServico) : "SERVICOS";

			String panelName = telaFactory.criarVisContratado(contratado, prevPanel);
			navegador.navegarPara(panelName);
		});

		this.view.voltar(e -> {
			navegador.navegarPara("SERVICOS");
		});
		
		this.view.visualizar(e ->{
			Mensagem M = new Mensagem();
			if(s.getArquivo() == null) {
				M.Erro("nenhum arquivo foi descarregado\n"
						+ " confirme com contratado", "arquivo não salvo");
			}else {
				try {
					this.model.salvarArquivoLocal(s);
				} catch (IOException e1) {
					M.Erro("erro ao abrir arquivo", "erro inesperado");
					e1.printStackTrace();
				}
				telaFactory.criarTelaVisArquivo(s);
				navegador.navegarPara("ARQUIVOS");
			}
		});

	}
}