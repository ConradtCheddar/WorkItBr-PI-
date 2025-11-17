package controller;

import model.Servico;
import model.ServicoDAO;
import view.VisServicoCnte;
import view.VisServicoCnteAceito;
import view.VisServicoCnteFinalizado;
import model.UsuarioDAO;
import model.Usuario;
import view.TelaVisArquivos;
import view.VisContratado;
import javax.swing.JOptionPane;

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
			System.out.println(s + " print");
			telaFactory.criarTelaVisArquivo(s);
			navegador.navegarPara("ARQUIVOS");
			
		});

		this.view.Reabrir(e -> {
			this.model.ReabrirServico(s);
			navegador.navegarPara("SERVICOS");
		});
	}
}