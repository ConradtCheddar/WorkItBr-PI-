package controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;

import model.Servico;
import model.ServicoDAO;
import model.Usuario;
import model.UsuarioDAO;
import view.ServicoListCellRenderer;
import view.TelaHistorico;
import view.VisServico;
import view.VisServicoAndamento;
import view.VisServicoCnteFinalizado;
import view.VisServicoFinalizado;

public class HistoricoController extends ComponentAdapter {
	private final TelaHistorico view;
	private final UsuarioDAO model;
	private final Navegador navegador;
	private final ServicoDAO servicoDAO;
	private final TelaFactory telaFactory;
	private Usuario usuarioLogado;
	private boolean isContratado;
	


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
		            	if (navegador.getCurrentUser().isContratado()) {
		            		Servico servicoSelecionado = (Servico) selectedItem;
			                VisServicoFinalizado vs = new VisServicoFinalizado(servicoSelecionado);
			                navegador.adicionarPainel("VIS_FINALIZADO", vs);
			                navegador.navegarPara("VIS_FINALIZADO");
			                return;
		            	}
		            	if (navegador.getCurrentUser().isContratante()) {
		            		Servico servicoSelecionado = (Servico) selectedItem;
			                VisServicoCnteFinalizado vs = new VisServicoCnteFinalizado(servicoSelecionado);
			                new VisServicoCnteFinalizadoController(vs, servicoDAO, navegador, servicoSelecionado, telaFactory);
			                navegador.adicionarPainel("VIS_FINALIZADO_CNTE", vs);
			                navegador.navegarPara("VIS_FINALIZADO_CNTE");
			                return;
		            	}
		               
		            }
		        }
		    }
		});
		
	}
	

	public void atualizarListaHistorico() {
		usuarioLogado = navegador.getCurrentUser();
		System.out.println("atualizarListaHistorico chamado");
		ServicoDAO servicoDAO = new ServicoDAO();
		
		if(usuarioLogado.isContratado()) {
			isContratado = true;
		} else {
			isContratado = false;
			
		}
		
		System.out.println("isContratado: " + isContratado);
		java.util.List<Servico> servicosFinalizados = servicoDAO.listarServicosFinalizados(navegador, isContratado);
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