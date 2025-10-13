package controller;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import model.Servico;
import model.ServicoDAO;
import model.UsuarioDAO;
import view.TelaListaServicos;

public class ListaServicosController {
	private final TelaListaServicos view;
	private final ServicoDAO model;
	private final Navegador navegador;

	public ListaServicosController(TelaListaServicos view, ServicoDAO model, Navegador navegador){

			this.view = view;
			this.model = model;
			this.navegador = navegador;
			
			ServicoDAO dao = new ServicoDAO();
			ArrayList<Servico> lista = dao.buscarTodosServicosPorUsuario(navegador.getCurrentUser());
			this.view.atualizarTable(lista);

			DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
			tableModel.setRowCount(0); // limpa a tabela
			for (Servico s : lista) {
			    tableModel.addRow(new Object[]{
			        s.getNome_Servico(),
			        s.getValor(),
			        s.getDescricao(),
			        s.getModalidade()
			    });
			}



	}

}
