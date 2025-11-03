package view;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;

import model.Servico;

import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class VisServicoCnte extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel Perfil;
	private JPanel PanelInfo;
	private JPanel PanelDesc;
	private JTextArea taTitulo;
	private JTextArea taModalidade;
	private JTextArea taPreco;
	private JButton btnEditar;
	private JTextArea tpDesc;

	public VisServicoCnte(Servico s) {
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow]"));
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Serviço"));
		add(panel, "cell 0 0 4 6,grow");
		panel.setLayout(new CardLayout(0, 0));
		
		Perfil = new JPanel();
		panel.add(Perfil, "name_1709392782600");
		
		PanelInfo = new JPanel();
		PanelInfo.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Serviço"));
		add(PanelInfo, "cell 4 0 6 7,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][][grow][grow][][grow][grow][grow][grow][grow]"));
		
		taTitulo = new JTextArea("Titulo");
		taTitulo.setEditable(false);
		taTitulo.setFocusable(false);
		taTitulo.setLineWrap(true);
		taTitulo.setWrapStyleWord(true);
		taTitulo.setRows(1);
		taTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		taTitulo.setBackground(PanelInfo.getBackground());
		taTitulo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taTitulo, "cell 0 0 9 2,grow");
		
		taModalidade = new JTextArea("Modalidade");
		taModalidade.setEditable(false);
		taModalidade.setFocusable(false);
		taModalidade.setLineWrap(true);
		taModalidade.setWrapStyleWord(true);
		taModalidade.setRows(1);
		taModalidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taModalidade.setBackground(PanelInfo.getBackground());
		taModalidade.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taModalidade, "cell 0 2 9 3,grow");
		
		taPreco = new JTextArea("Preco");
		taPreco.setEditable(false);
		taPreco.setFocusable(false);
		taPreco.setLineWrap(true);
		taPreco.setWrapStyleWord(true);
		taPreco.setRows(1);
		taPreco.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taPreco.setBackground(PanelInfo.getBackground());
		taPreco.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taPreco, "cell 0 5 9 3,grow");
		
	PanelDesc = new JPanel();
	PanelDesc.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Descrição"));
	add(PanelDesc, "cell 0 8 10 8,grow");
	PanelDesc.setLayout(new MigLayout("", "[grow]", "[grow]"));
	
	tpDesc = new JTextArea();
	tpDesc.setEditable(false);
	tpDesc.setFocusable(false);
	tpDesc.setLineWrap(true);
	tpDesc.setWrapStyleWord(true);
	tpDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
	tpDesc.setBackground(PanelDesc.getBackground());
	tpDesc.setText(s.getDescricao());
	
	JScrollPane scrollPane = new JScrollPane(tpDesc);
	scrollPane.setBorder(null);
	PanelDesc.add(scrollPane, "cell 0 0,grow");
		
		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(btnEditar, "cell 0 16 11 1,alignx center");

		taTitulo.setText(s.getNome_Servico());
		taModalidade.setText(s.getModalidade());
		taPreco.setText(String.format("R$ %.2f", s.getValor()));
		tpDesc.setText(s.getDescricao());
		
		// Dynamic font sizing
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSizeTitulo = Math.max(16, panelHeight / 30);
				int fontSizeInfo = Math.max(14, panelHeight / 35);
				int fontSizeDesc = Math.max(12, panelHeight / 40);
				int fontSizeBotao = Math.max(14, panelHeight / 35);
				
				taTitulo.setFont(new Font("Tahoma", Font.PLAIN, fontSizeTitulo));
				taModalidade.setFont(new Font("Tahoma", Font.PLAIN, fontSizeInfo));
				taPreco.setFont(new Font("Tahoma", Font.PLAIN, fontSizeInfo));
				tpDesc.setFont(new Font("Tahoma", Font.PLAIN, fontSizeDesc));
				btnEditar.setFont(new Font("Tahoma", Font.PLAIN, fontSizeBotao));
			}
		});
		

	}
	
	public void Editar(ActionListener actionListener) {
		this.btnEditar.addActionListener(actionListener);
	}

}