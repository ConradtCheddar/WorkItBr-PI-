package controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import model.Servico;
import model.ServicoDAO;
import model.UsuarioDAO;
import view.TelaContratado;
import view.TelaContratante;
import view.VisServico;
import view.VisServicoAndamento;
import view.ServicoListCellRenderer;

public class ContratadoController extends ComponentAdapter {
	private final TelaContratado view;
	private final UsuarioDAO model;
	private final Navegador navegador;

	public ContratadoController(TelaContratado view, UsuarioDAO model, Navegador navegador){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		
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
                        int valorAceito = servicoSelecionado.getIdServico();
                        
                        System.out.println(valorAceito);
                        final ServicoDAO sd = new ServicoDAO();
                        		
                        VisServico vs = new VisServico(servicoSelecionado);
                        VisServicoController vsc = new VisServicoController(vs, sd, navegador, servicoSelecionado);
                        navegador.adicionarPainel("VISUALIZAR_SERVICO", vs);
                        navegador.navegarPara("VISUALIZAR_SERVICO");
                        
                    } else {

                    }   
                    
                }


		    }
		});
		
		this.view.cliqueDuploNoAndamento(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	if (e.getClickCount() == 2) { 
                    int index = view.getListaAndamento().locationToIndex(e.getPoint());
                    Object selectedItem = view.getListaAndamento().getModel().getElementAt(index);
                    
                    if (selectedItem instanceof Servico) {
                        Servico servicoSelecionado = (Servico) selectedItem;

                        String nomeServico = servicoSelecionado.getNome_Servico();
                        String descricaoServico = servicoSelecionado.getDescricao();
                        double valorServico = servicoSelecionado.getValor();
                        int valorAceito = servicoSelecionado.getIdServico();
                        
                        System.out.println(valorAceito);
                        final ServicoDAO sd = new ServicoDAO();
                        		
                        VisServicoAndamento vs = new VisServicoAndamento(servicoSelecionado);
                        navegador.adicionarPainel("VISUALIZAR_SERVICO", vs);
                        navegador.navegarPara("VISUALIZAR_SERVICO");
                        
                    } else {
                    	JOptionPane.showMessageDialog(null, "Nenhum servico selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
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
		java.util.List<Servico> servicosAceitos = servicoDAO.listarServicosAceitos();
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