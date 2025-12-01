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
import util.FontScaler;
import util.FontScaler.FontSize;

public class VisServicoCnteAceito extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JPanel PanelInfo;
	private JPanel PanelDesc;
	private JTextArea taTitulo;
	private JTextArea taModalidade;
	private JTextArea taPreco;
	private JButton btnVoltar, btnContratado;
	private JTextArea tpDesc;

	public VisServicoCnteAceito(Servico s) {
		setLayout(new MigLayout("", "[grow][grow 170]", "[grow][grow 130][grow 10]"));

		panel = new JPanel();
		TitledBorder titledBorder = new TitledBorder(new LineBorder(Color.GRAY, 1), "Visualizar Contratado");
		titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
		panel.setBorder(titledBorder);
		add(panel, "cell 0 0,grow");
		panel.setLayout(new CardLayout(0, 0));

		btnContratado = new JButton("<html>Visualizar<br>Contratado</html>");
		panel.add(btnContratado, "name_8888915899200");

		PanelInfo = new JPanel();
		TitledBorder titledBorder2 = new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Serviço");
		titledBorder2.setTitleFont(new Font("Arial", Font.BOLD, 14));
		PanelInfo.setBorder(titledBorder2);
		add(PanelInfo, "cell 1 0,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));

		taTitulo = new JTextArea("Titulo");
		taTitulo.setEditable(false);
		taTitulo.setFocusable(false);
	
		taTitulo.setWrapStyleWord(true);
		taTitulo.setRows(1);
		taTitulo.setBackground(PanelInfo.getBackground());
		taTitulo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taTitulo, "cell 0 0,grow");

		taModalidade = new JTextArea("Modalidade");
		taModalidade.setEditable(false);
		taModalidade.setFocusable(false);
	
		taModalidade.setWrapStyleWord(true);
		taModalidade.setRows(1);
		taModalidade.setBackground(PanelInfo.getBackground());
		taModalidade.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taModalidade, "cell 0 1,grow");

		taPreco = new JTextArea("Preco");
		taPreco.setEditable(false);
		taPreco.setFocusable(false);
		
		taPreco.setWrapStyleWord(true);
		taPreco.setRows(1);
		taPreco.setBackground(PanelInfo.getBackground());
		taPreco.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taPreco, "cell 0 2,grow");

		PanelDesc = new JPanel();
		TitledBorder titledBorder3 = new TitledBorder(new LineBorder(Color.GRAY, 1), "Descrição");
		titledBorder3.setTitleFont(new Font("Arial", Font.BOLD, 14));
		PanelDesc.setBorder(titledBorder3);
		add(PanelDesc, "cell 0 1 2 1,grow");
		PanelDesc.setLayout(new MigLayout("", "[grow]", "[grow]"));

		tpDesc = new JTextArea();
		tpDesc.setEditable(false);
		tpDesc.setFocusable(false);

		tpDesc.setWrapStyleWord(true);
		tpDesc.setBackground(PanelDesc.getBackground());
		tpDesc.setText(s.getDescricao());

		JScrollPane scrollPane = new JScrollPane(tpDesc);
		scrollPane.setBorder(null);
		PanelDesc.add(scrollPane, "cell 0 0,grow");

		btnVoltar = new JButton("Voltar");
		add(btnVoltar, "cell 0 2 2 1,alignx center");

		taTitulo.setText(s.getNome_Servico());
		taModalidade.setText(s.getModalidade());
		taPreco.setText(String.format("R$ %.2f", s.getValor()));
		tpDesc.setText(s.getDescricao());

		FontScaler.addAutoResize(this, new Object[] { taTitulo, FontSize.TEXTO },
				new Object[] { taModalidade, FontSize.TEXTO }, new Object[] { taPreco, FontSize.TEXTO },
				new Object[] { tpDesc, FontSize.TEXTO }, new Object[] { btnVoltar, FontSize.BOTAO },
				new Object[] { btnContratado, FontSize.BOTAO });
	}

	public void voltar(ActionListener actionListener) {
		this.btnVoltar.addActionListener(actionListener);
	}

	public void contratado(ActionListener actionListener) {
		this.btnContratado.addActionListener(actionListener);
	}
}