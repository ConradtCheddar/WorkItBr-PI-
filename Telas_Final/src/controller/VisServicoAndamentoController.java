package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JOptionPane;

import model.Servico;
import model.ServicoDAO;
import model.UsuarioDAO;
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
		
		
		this.view.finalizar(e -> {
		
			model.deletarServico(s.getIdServico());
			JOptionPane.showMessageDialog(null, "Serviço finalizado com sucesso!");
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
					
					JOptionPane.showMessageDialog(null, 
						"Arquivo '" + arquivo.getName() + "' salvo com sucesso!", 
						"Sucesso", 
						JOptionPane.INFORMATION_MESSAGE);
					
					System.out.println("Arquivo salvo no banco: " + caminhoArquivo);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, 
						"Erro ao ler o arquivo selecionado.", 
						"Erro", 
						JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, 
						"Erro ao salvar o arquivo no banco de dados.", 
						"Erro", 
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}
}
