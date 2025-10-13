package controller;

import java.util.ArrayList;

import model.Servico;
import model.ServicoDAO;
import view.TelaListaServicos;

public class ListaServicosController {
	private final TelaListaServicos view;
	private final ServicoDAO model;
	private final Navegador navegador;
	private static ListaServicosController instanciaAtual;

	public ListaServicosController(TelaListaServicos view, ServicoDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		instanciaAtual = this;
	}

	public static void atualizarTabelaSeExistir() {
		if (instanciaAtual != null) {
			instanciaAtual.atualizarTabelaServicosDoUsuario();
		}
	}

	public void atualizarTabelaServicosDoUsuario() {
		if (navegador.getCurrentUser() != null) {
			ServicoDAO dao = new ServicoDAO();
			ArrayList<Servico> lista = dao.buscarTodosServicosPorUsuario(navegador.getCurrentUser());
			this.view.atualizarTable(lista);
		}
	}
}