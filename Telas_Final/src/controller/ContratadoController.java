package controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;

import model.Servico;
import model.ServicoDAO;
import model.UsuarioDAO;
import view.TelaContratado;
import view.TelaContratante;
import view.ServicoListCellRenderer;

public class ContratadoController extends ComponentAdapter {
	private final TelaContratado view;
	private final UsuarioDAO model;
	private final Navegador navegador;

	public ContratadoController(TelaContratado view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;

		// Fetch all jobs from the database and set them in listaDisponivel
		ServicoDAO servicoDAO = new ServicoDAO();
		java.util.List<Servico> servicos = servicoDAO.listarServicos();
		DefaultListModel<Servico> listModel = new DefaultListModel<>();
		for (Servico s : servicos) {
			listModel.addElement(s);
		}
		this.view.getListaDisponivel().setModel(listModel);
		this.view.getListaDisponivel().setCellRenderer(new ServicoListCellRenderer());
	}
	
	public void atualizarListaDisponivel() {
		ServicoDAO servicoDAO = new ServicoDAO();
		java.util.List<Servico> servicos = servicoDAO.listarServicos();
		DefaultListModel<Servico> listModel = new DefaultListModel<>();
		for (Servico s : servicos) {
			listModel.addElement(s);
		}
		this.view.getListaDisponivel().setModel(listModel);
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		System.out.println("att");
		atualizarListaDisponivel();

	}
}