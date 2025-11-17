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
import view.TelaHistorico;
import view.VisServico;
import view.VisServicoAndamento;

public class HistoricoController extends ComponentAdapter {
	private final TelaHistorico view;
	private final UsuarioDAO model;
	private final Navegador navegador;
	private final ServicoDAO servicoDAO;
	private final TelaFactory telaFactory;

	public HistoricoController(TelaHistorico view, UsuarioDAO model, Navegador navegador, ServicoDAO servicoDAO, TelaFactory telaFactory){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.servicoDAO = servicoDAO;
		this.telaFactory = telaFactory;
		
		
		this.view.cliqueDuploNoHistorico(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	/**
		    	 * verifica contagem
		    	 */
		    	if (e.getClickCount() == 2) { 
		            int index = view.getListaHistorico().locationToIndex(e.getPoint());
		            if (index == -1) return;
		            Object selectedItem = view.getListaHistorico().getModel().getElementAt(index);
		            
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
	
	
	public void atualizarListaHistorico() {
		ServicoDAO servicoDAO = new ServicoDAO();
		java.util.List<Servico> servicosFinalizados = servicoDAO.listarServicosFinalizados(navegador);
		DefaultListModel<Servico> listModelFinalizado = new DefaultListModel<>();
		for (Servico s : servicosFinalizados) {
			listModelFinalizado.addElement(s);
		}
		this.view.getListaHistorico().setModel(listModelFinalizado);
		this.view.getListaHistorico().setCellRenderer(new ServicoListCellRenderer());
	}

	
	@Override
	public void componentShown(ComponentEvent e) {
		atualizarListaHistorico();

	}
}