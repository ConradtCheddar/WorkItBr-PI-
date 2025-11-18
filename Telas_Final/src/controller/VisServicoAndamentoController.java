package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JOptionPane;

import model.Servico;
import model.ServicoDAO;
import model.Status;
import model.UsuarioDAO;
import view.Mensagem;
import view.VisServico;
import view.VisServicoAndamento;

public class VisServicoAndamentoController {
	private final VisServicoAndamento view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private final Servico s;

	public VisServicoAndamentoController(VisServicoAndamento view, ServicoDAO model, Navegador navegador, Servico s){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.s= s;
		
		Mensagem M = new Mensagem();
		
		this.view.finalizar(e -> {
			s.setStatus(Status.FINALIZADO);
			boolean sucesso = model.atualizarServicoPorId(s.getIdServico(), s);
			if (sucesso) {
			M.Sucesso("Serviço finalizado com sucesso.", "Sucesso");
			} else {
				M.Erro("Erro ao finalizar o serviço.", "Erro");
			}
			navegador.navegarPara("CONTRATADO");
		});
		
		this.view.Adicionar(e -> {
			String caminhoArquivo = view.selecionarArquivo();
			if (caminhoArquivo != null) {
				try {
					// Converter o arquivo para bytes
					File arquivo = new File(caminhoArquivo);
					byte[] arquivoBytes = Files.readAllBytes(arquivo.toPath());
					
					
					// Salvar no banco de dados
					model.salvarArquivoServico(s.getIdServico(), arquivoBytes);
					
					M. Sucesso("Arquivo salvo com sucesso no banco de dados.", "Sucesso");
					
					System.out.println("Arquivo salvo no banco: " + caminhoArquivo);
				} catch (IOException ex) {
					ex.printStackTrace();
					M.Erro("Erro ao ler o arquivo selecionado.", "Erro");
				} catch (Exception ex) {
					ex.printStackTrace();
					M.Erro("Erro ao salvar o arquivo no banco de dados.", "Erro");
				}
			}
		});
		
	}
}