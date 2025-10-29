package view;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Servico;

import javax.swing.JTextPane;

public class VisServicoCnte extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel Perfil;
	private JPanel PanelInfo;
	private JPanel PanelDesc;
	private JLabel lblTitulo;
	private JLabel lblModalidade;
	private JLabel lblPreco;
	private JButton btnEditar;
	private JLabel tpDesc;

	public VisServicoCnte(Servico s) {
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow]"));
		
		panel = new JPanel();
		add(panel, "cell 0 0 4 6,grow");
		panel.setLayout(new CardLayout(0, 0));
		
		Perfil = new JPanel();
		panel.add(Perfil, "name_1709392782600");
		
		PanelInfo = new JPanel();
		add(PanelInfo, "cell 4 0 6 7,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][][grow][grow][][grow][grow][grow][grow][grow]"));
		
		lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblTitulo, "cell 0 0 9 2,grow");
		
		lblModalidade = new JLabel("Modalidade");
		lblModalidade.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblModalidade, "cell 0 2 9 3,grow");
		
		lblPreco = new JLabel("Preco");
		lblPreco.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblPreco, "cell 0 5 9 3,grow");
		
		PanelDesc = new JPanel();
		add(PanelDesc, "cell 0 8 10 8,grow");
		PanelDesc.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		tpDesc = new JLabel("");
		PanelDesc.add(tpDesc, "cell 0 0");
		
		btnEditar = new JButton("Editar");
		add(btnEditar, "cell 0 16 11 1,alignx center");

		lblTitulo.setText(s.getNome_Servico());
		lblModalidade.setText(s.getModalidade());
		lblPreco.setText(Double.toString(s.getValor()));
		tpDesc.setText(s.getDescricao());
		

	}
	
	public void Editar(ActionListener actionListener) {
		this.btnEditar.addActionListener(actionListener);
	}

}