package controller;

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
		
	}
}