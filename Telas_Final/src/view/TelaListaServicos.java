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
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
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
	private JTable table;
	private DefaultTableModel model;
	private Runnable onShowCallback;
	private JButton btnVisualizar, btnEditar, btnDeletar;

	/**
	 * Create the panel.
	 * 
	 */
	public TelaListaServicos() {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][][grow][grow][grow][][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]", "[35px][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));
		
		Object dados[][]= {
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},
			{null, null, null, null},};
		
		String colunas[]= {
			"Nome", "valor", "modalidade", "foi aceito?"
		};
		this.model = new DefaultTableModel(dados,colunas);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "cell 2 2 11 13,grow");
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setForeground(new Color(255, 255, 255));
		table.setModel(model);
		
		btnVisualizar = new JButton("Visualizar");
		add(btnVisualizar, "cell 19 4,grow");
		
		btnEditar = new JButton("Editar");
		add(btnEditar, "cell 19 6,grow");
		
		btnDeletar = new JButton("Deletar");
		add(btnDeletar, "cell 19 8,grow");
		
		JButton btnNewButton_2_1 = new JButton("New button");
		add(btnNewButton_2_1, "cell 19 10,grow");
	}

	public void atualizarTable(ArrayList<Servico> lista) {
		this.model.setRowCount(0); // Clear table before adding new rows
		for(int i =0; i<lista.size(); i++) {
			Object[] newRowData = {lista.get(i).getNome_Servico(),lista.get(i).getValor(),lista.get(i).getModalidade(),lista.get(i).getAceito()};
			this.model.addRow(newRowData);
		}
			
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
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

	/**
	 * metodo para a funcionalidade do botão cadastrar
	 */
	public void editar(ActionListener actionlistener) {
		this.btnEditar.addActionListener(actionlistener);
	}

//	/**
//	 * metodo para limpar caixas de texto
//	 */
//	public void limparCampos() {
//		tfNome.setText("");
//	}
//
//	
//	
//	
//	/**
//	 * getters & setters
//	 * 
//	 * @return
//	 */
//	public JTextField getTfNome() {
//		return tfNome;
//	}
//
//	public void setTfNome(JTextField tfNome) {
//		this.tfNome = tfNome;
//	}
//
//	public JButton getBtnCadastrarTrabalho() {
//		return btnCadastrarTrabalho;
//	}
//
//	public void setBtnCadastrarTrabalho(JButton btnCadastrarTrabalho) {
//		this.btnCadastrarTrabalho = btnCadastrarTrabalho;
//	}
//
//	public JTextField getTfModalidade() {
//		return tfModalidade;
//	}
//
//	public void setTfModalidade(JTextField tfModalidade) {
//		this.tfModalidade = tfModalidade;
//	}
//
//	public JTextField getTfValor() {
//		return tfValor;
//	}
//
//	public void setTfValor(JTextField tfValor) {
//		this.tfValor = tfValor;
//	}
//
//	public JTextArea getTfDescricao() {
//		return tfDescricao;
//	}
//
//	public void setTfDescricao(JTextArea tfDescricao) {
//		this.tfDescricao = tfDescricao;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//
//
}
