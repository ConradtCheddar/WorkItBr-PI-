package controller;

import model.Servico;
import view.VisServicoCnteAceito;
import model.UsuarioDAO;
import model.Usuario;
import view.Mensagem;
import view.VisContratado;
import javax.swing.JOptionPane;

public class VisContratadoController {
	private final VisContratado view;
	private final Navegador navegador;
	private final Usuario u;

	public VisContratadoController(VisContratado view, Navegador navegador, Usuario u){
		this.view = view;
		this.navegador = navegador;
		this.u= u;
		
		Mensagem M = new Mensagem();
		
		this.view.voltar(e ->{
			navegador.navegarPara("SERV");
		});
		
	}
}