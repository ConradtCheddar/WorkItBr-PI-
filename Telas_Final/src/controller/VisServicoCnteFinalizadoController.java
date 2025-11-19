package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
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

		Mensagem M = new Mensagem();
		
		this.view.contratado(e -> {
			int idContratado = s.getIdContratado();
			if (idContratado <= 0) {
				M.Aviso("Contratado não definido para este serviço.", "Aviso");
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
			// Corrige prevPanel para voltar para a tela finalizado correta
			String prevPanel = (idServico > 0) ? ("VIS_FINALIZADO" + idServico) : "SERVICOS";

			String panelName = telaFactory.criarVisContratado(contratado, prevPanel);
			navegador.navegarPara(panelName);
		});

		this.view.voltar(e -> {
			navegador.navegarPara("SERVICOS");
		});
		
		// When user clicks to download/baixar the file, ask where to save and write bytes to disk
		this.view.visualizar(e ->{
			byte[] arquivoBytes = s.getArquivo();
			if (arquivoBytes == null || arquivoBytes.length == 0) {
				// try to recover from DB
				arquivoBytes = this.model.recuperarArquivo(s.getIdServico());
				if (arquivoBytes == null || arquivoBytes.length == 0) {
					M.Erro("Nenhum arquivo foi descarregado. Confirme com contratado.", "Arquivo não salvo");
					return;
				}
				// set on object for future use
				s.setArquivo(arquivoBytes);
			}

			// Open a Save dialog to ask where to save the file
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Salvar arquivo");
			// suggest a filename
			String sugestao = "sub_servico_N" + s.getIdServico();
			chooser.setSelectedFile(new File(sugestao));
			int resp = chooser.showSaveDialog(null);
			if (resp != JFileChooser.APPROVE_OPTION) {
				// user cancelled
				return;
			}
			File destino = chooser.getSelectedFile();
			Path caminho = destino.toPath();
			try {
				// ensure parent exists
				if (caminho.getParent() != null && !Files.exists(caminho.getParent())) {
					Files.createDirectories(caminho.getParent());
				}
				Files.write(caminho, arquivoBytes);
				M.Sucesso("Arquivo salvo em: " + caminho.toString(), "Arquivo salvo");
				// update service caminho
				s.setCaminho(caminho);
				// navigate to arquivos panel as before
				navegador.navegarPara("ARQUIVOS");
			} catch (IOException ex) {
				ex.printStackTrace();
				M.Erro("Erro ao salvar arquivo: " + ex.getMessage(), "Erro");
			}
		});

		this.view.Reabrir(e -> {
			this.model.ReabrirServico(s);
			navegador.navegarPara("SERVICOS");
		});
	}
}