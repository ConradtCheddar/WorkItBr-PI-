package controller;

import java.util.ArrayList;

import model.Servico;
import model.ServicoDAO;
import model.Usuario;
import model.UsuarioDAO;
import view.TelaCadastro;
import view.TelaCadastroContratante;
import view.TelaServicos;

public class TelaServicoController {
	private final TelaServicos view;
	private final ServicoDAO model;
	private final Navegador navegador;

	public TelaServicoController(TelaServicos view, ServicoDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
		ServicoDAO dao = new ServicoDAO();
		ArrayList<Servico> lista = dao.buscarTodosServicosPorUsuario(navegador.getCurrentUser());
		this.view.atualizarTable(lista);
		
				
	}
}