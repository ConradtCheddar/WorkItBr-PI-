package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import model.Servico;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TelaListaServicos extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;
	private Runnable onShowCallback;
	private JTable tableServicos;
	private JButton btnVisualizar, btnEditar, btnDeletar, btnCadastrar;
	private Object[][] tableData;

	/**
	 * Create the panel.
	 * 
	 */
	public TelaListaServicos() {
		setPreferredSize(new Dimension(597, 364));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 20 20 20 40, gap 40", "[grow][200]", "[grow][grow][grow][grow]"));
		
		String colunas[]= {
			"ID", "Nome", "Valor", "Modalidade", "Aceito?", "Descrição"
		};
		Object dados[][]= new Object[0][6];
		this.tableModel = new DefaultTableModel(dados,colunas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0;
			}
		};
		tableServicos = new JTable(tableModel);
		this.tableData = this.getItems();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "cell 0 0 1 4,grow");
		
		scrollPane_1.setViewportView(tableServicos);
		tableServicos.setForeground(new Color(255, 255, 255));
		tableServicos.setModel(tableModel);
		// Oculta a coluna ID do usuário
		tableServicos.getColumnModel().getColumn(0).setMinWidth(0);
		tableServicos.getColumnModel().getColumn(0).setMaxWidth(0);
		tableServicos.getColumnModel().getColumn(0).setWidth(0);
		
		btnVisualizar = new JButton("Visualizar");
		add(btnVisualizar, "cell 1 0,growx,height 20:40:60");
		
		btnDeletar = new JButton("Deletar");
		add(btnDeletar, "cell 1 1,growx,height 20:40:60");
		
		btnCadastrar = new JButton("Cadastrar");
		add(btnCadastrar, "cell 1 2,growx,height 20:40:60");
		
		btnEditar = new JButton("Editar");
		add(btnEditar, "cell 1 3,growx,height 20:40:60");
	}

	public void atualizarTable(ArrayList<Servico> lista) {
		this.tableModel.setRowCount(0); // Clear table before adding new rows
		for(int i =0; i<lista.size(); i++) {
			Servico s = lista.get(i);
			String aceitoTexto = Boolean.TRUE.equals(s.getAceito()) ? "Sim" : "Não";
			Object[] newRowData = {s.getId(), s.getNome_Servico(), s.getValor(), s.getModalidade(), aceitoTexto, s.getDescricao()};
			this.tableModel.addRow(newRowData);
		}
			
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		if (onShowCallback != null) {
			onShowCallback.run();
		}
	}

	public void setOnShow(Runnable r) {
		this.onShowCallback = r;
	}
	
	public Object[][] getItems() {
		 int rowCount = tableModel.getRowCount();
		    int colCount = tableModel.getColumnCount();

		    Object[][] allValues = new Object[rowCount][colCount];

		    for (int row = 0; row < rowCount; row++) {
		        for (int col = 0; col < colCount; col++) {
		            allValues[row][col] = tableModel.getValueAt(row, col);
		        }
		    }
		    return allValues; 
	}

	/**
	 * metodo para a funcionalidade do botão editar
	 */
	public void editar(ActionListener actionlistener) {
		
		this.btnEditar.addActionListener(actionlistener);
	}
	/**
	 * metodo para a funcionalidade do botão deletar
	 */
	public void deletar(ActionListener actionlistener) {
		
		this.btnDeletar.addActionListener(actionlistener);
	}
	/**
	 * metodo para a funcionalidade do botão Visualizar
	 */
	public void visualizar(ActionListener actionlistener) {
		
		this.btnVisualizar.addActionListener(actionlistener);
	}
	/**
	 * metodo para a funcionalidade do botão Cadastrar
	 */
	public void cadastrar(ActionListener actionlistener) {
		
		this.btnCadastrar.addActionListener(actionlistener);
	}
	
	
	/**
	 * getters & setters
	 * 
	 * @return
	 */
	
	public javax.swing.JTable getTableServicos() {
	    return tableServicos;
	}

	public void setTable(JTable table) {
		this.tableServicos = table;
	}

	public Object[][] getTableData() {
		return tableData;
	}

	public void setTableData(Object[][] tableData) {
		this.tableData = tableData;
	}

	public javax.swing.table.DefaultTableModel getTableModelServicos() {
	    return (javax.swing.table.DefaultTableModel) this.tableServicos.getModel();
	}

	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
}