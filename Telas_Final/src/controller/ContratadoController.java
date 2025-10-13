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
		
		this.view.getListaDisponivel().setCellRenderer(new ServicoListCellRenderer());
		
		this.view.cliqueDuploNoJList(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	if (e.getClickCount() == 2) { 
                    int index = view.getListaDisponivel().locationToIndex(e.getPoint());
                    Object selectedItem = view.getListaDisponivel().getModel().getElementAt(index);
                    
                    if (selectedItem instanceof Servico) {
                        Servico servicoSelecionado = (Servico) selectedItem;

                        
                        String nomeServico = servicoSelecionado.getNome_Servico();
                        String descricaoServico = servicoSelecionado.getDescricao();
                        double valorServico = servicoSelecionado.getValor();
                        
                        
                        // Por exemplo, imprimir os valores
                        System.out.println("Nome do Serviço: " + nomeServico);
                        System.out.println("Descrição do Serviço: " + descricaoServico);
                        System.out.println("Valor do Serviço: " + valorServico);
                    } else {
                        System.out.println("O item selecionado não é do tipo Servico");
                    }
                    
                    System.out.println("Clique duplo em: " + selectedItem);
                    
                    
                }

//		        navegador.navegarPara("CADASTRO");
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
	
	@Override
	public void componentShown(ComponentEvent e) {
		System.out.println("att");
		atualizarListaDisponivel();

	}
}