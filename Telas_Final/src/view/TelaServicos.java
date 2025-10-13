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

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JTable;

public class TelaServicos extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the panel.
	 * 
	 */
	public TelaServicos() {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][grow][][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));
		
		table = new JTable();
		add(table, "cell 1 1 5 13,grow");

	}


//	/**
//	 * metodo para a funcionalidade do botão cadastrar
//	 */
//	public void cadastrar(ActionListener actionlistener) {
//		this.btnCadastrarTrabalho.addActionListener(actionlistener);
//	}
//
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
}