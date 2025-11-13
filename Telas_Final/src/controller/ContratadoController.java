package controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import model.Servico;
import model.ServicoDAO;
import model.Usuario;
import model.UsuarioDAO;
import view.ServicoListCellRenderer;
import view.TelaContratado;
import view.VisServico;
import view.VisServicoAndamento;

public class ContratadoController extends ComponentAdapter {
	private final TelaContratado view;
	private final UsuarioDAO model;
	private final Navegador navegador;
	private final ServicoDAO servicoDAO;
	private final TelaFactory telaFactory;

	public ContratadoController(TelaContratado view, UsuarioDAO model, Navegador navegador, ServicoDAO servicoDAO, TelaFactory telaFactory){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.servicoDAO = servicoDAO;
		this.telaFactory = telaFactory;
		
		this.view.cliqueDuploNoJList(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	/**
		    	 * verifica contagem
		    	 */
		    	if (e.getClickCount() == 2) { 
		            int index = view.getListaDisponivel().locationToIndex(e.getPoint());
		            if (index == -1) return;
		            Object selectedItem = view.getListaDisponivel().getModel().getElementAt(index);
		            
		            /**
		             * instancia a tela correta
		             */
		            if (selectedItem instanceof Servico) {
		                Servico servicoSelecionado = (Servico) selectedItem;
		                VisServico vs = new VisServico(servicoSelecionado);
		                new VisServicoController(vs, servicoDAO, navegador, servicoSelecionado);
		                navegador.adicionarPainel("VISUALIZAR_SERVICO", vs);
		                navegador.navegarPara("VISUALIZAR_SERVICO");
		            } 
		        }
		    }
		});
		
		this.view.cliqueDuploNoAndamento(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	/**
		    	 * verifica contagem
		    	 */
		    	if (e.getClickCount() == 2) { 
		            int index = view.getListaAndamento().locationToIndex(e.getPoint());
		            if (index == -1) return;
		            Object selectedItem = view.getListaAndamento().getModel().getElementAt(index);
		            
		            /**
		             * instancia a tela correta
		             */
		            if (selectedItem instanceof Servico) {
		                Servico servicoSelecionado = (Servico) selectedItem;
		                VisServicoAndamento vs = new VisServicoAndamento(servicoSelecionado);
		                new VisServicoAndamentoController(vs, servicoDAO, navegador, servicoSelecionado);
		                navegador.adicionarPainel("VISUALIZAR_SERVICO_ANDAMENTO", vs);
		                navegador.navegarPara("VISUALIZAR_SERVICO_ANDAMENTO");
		            }
		        }
		    }
		});
		
	}
	
	public void atualizarListaDisponivel() {
		ServicoDAO servicoDAO = new ServicoDAO();
		java.util.List<Servico> servicos = servicoDAO.listarServicos();
		DefaultListModel<Servico> listModel = new DefaultListModel<>();
		for (Servico s : servicos) {
			listModel.addElement(s);
		}
		this.view.getListaDisponivel().setModel(listModel);
		this.view.getListaDisponivel().setCellRenderer(new ServicoListCellRenderer());
	}
	
	public void atualizarListaAceitos() {
		ServicoDAO servicoDAO = new ServicoDAO();
		java.util.List<Servico> servicosAceitos = servicoDAO.listarServicosAceitos(navegador);
		DefaultListModel<Servico> listModelAceito = new DefaultListModel<>();
		for (Servico s : servicosAceitos) {
			listModelAceito.addElement(s);
		}
		this.view.getListaAndamento().setModel(listModelAceito);
		this.view.getListaAndamento().setCellRenderer(new ServicoListCellRenderer());
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		atualizarListaDisponivel();
		atualizarListaAceitos();

	}
}