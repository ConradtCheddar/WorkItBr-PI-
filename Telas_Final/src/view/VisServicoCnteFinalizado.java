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

public class VisServicoCnteFinalizado extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JPanel PanelInfo;
	private JPanel PanelDesc;
	private JPanel Panel1;
	private JTextArea taTitulo;
	private JTextArea taModalidade;
	private JTextArea taPreco;
	private JButton btnVoltar, btnContratado;
	private JTextArea tpDesc;
	private JButton btnVisualizar;
	private JButton btnReabrir;
	private JButton btnBaixar;
	private JPanel panel_1;

	public VisServicoCnteFinalizado(Servico s) {
		setLayout(new MigLayout("", "[grow][grow 170]", "[grow][grow 130][grow 10]"));

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Visualizar Contratado"));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new CardLayout(0, 0));

		btnContratado = new JButton("<html>Visualizar<br>Contratado</html>");
		panel.add(btnContratado, "name_8888915899200");

		PanelInfo = new JPanel();
		PanelInfo.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Serviço"));
		add(PanelInfo, "cell 1 0,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));

		taTitulo = new JTextArea("Titulo");
		taTitulo.setEditable(false);
		taTitulo.setFocusable(false);
		taTitulo.setLineWrap(true);
		taTitulo.setWrapStyleWord(true);
		taTitulo.setRows(1);
		taTitulo.setBackground(PanelInfo.getBackground());
		taTitulo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taTitulo, "cell 0 0,grow");

		taModalidade = new JTextArea("Modalidade");
		taModalidade.setEditable(false);
		taModalidade.setFocusable(false);
		taModalidade.setLineWrap(true);
		taModalidade.setWrapStyleWord(true);
		taModalidade.setRows(1);
		taModalidade.setBackground(PanelInfo.getBackground());
		taModalidade.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taModalidade, "cell 0 1,grow");

		taPreco = new JTextArea("Preco");
		taPreco.setEditable(false);
		taPreco.setFocusable(false);
		taPreco.setLineWrap(true);
		taPreco.setWrapStyleWord(true);
		taPreco.setRows(1);
		taPreco.setBackground(PanelInfo.getBackground());
		taPreco.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taPreco, "cell 0 2,grow");

		PanelDesc = new JPanel();
		PanelDesc.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Descrição"));
		add(PanelDesc, "cell 0 1 2 1,grow");
		PanelDesc.setLayout(new MigLayout("", "[grow]", "[grow]"));

		tpDesc = new JTextArea();
		tpDesc.setEditable(false);
		tpDesc.setFocusable(false);
		tpDesc.setLineWrap(true);
		tpDesc.setWrapStyleWord(true);
		tpDesc.setBackground(PanelDesc.getBackground());
		tpDesc.setText(s.getDescricao());

		JScrollPane scrollPane = new JScrollPane(tpDesc);
		scrollPane.setBorder(null);
		PanelDesc.add(scrollPane, "cell 0 0,grow");

		taTitulo.setText(s.getNome_Servico());
		taModalidade.setText(s.getModalidade());
		taPreco.setText(String.format("R$ %.2f", s.getValor()));
		tpDesc.setText(s.getDescricao());

		Panel1 = new JPanel();
		add(Panel1, "cell 0 2 2 1,grow");
		Panel1.setLayout(new MigLayout("", "[grow][grow][grow]", "[grow]"));

		btnVoltar = new JButton("Voltar");
		Panel1.add(btnVoltar, "cell 0 0,alignx center");

		btnReabrir = new JButton("Reabrir Serviço");
		Panel1.add(btnReabrir, "flowx,cell 1 0,alignx center");

		btnBaixar = new JButton("Baixar arquivo");
		Panel1.add(btnBaixar, "cell 2 0,alignx center");

		btnVisualizar = new JButton("Visualizar Arquivo");
		add(btnVisualizar, "flowx,cell 1 2,alignx center");

		FontScaler.addAutoResize(this, new Object[] { taTitulo, FontSize.SUBTITULO },
				new Object[] { taModalidade, FontSize.TEXTO }, new Object[] { taPreco, FontSize.TEXTO },
				new Object[] { tpDesc, FontSize.TEXTO }, new Object[] { btnVoltar, FontSize.BOTAO },
				new Object[] { btnBaixar, FontSize.BOTAO }, new Object[] { btnContratado, FontSize.BOTAO },
				new Object[] { btnReabrir, FontSize.BOTAO });

	}

	public void voltar(ActionListener actionListener) {
		this.btnVoltar.addActionListener(actionListener);
	}

	public void contratado(ActionListener actionListener) {
		this.btnContratado.addActionListener(actionListener);
	}

	public void visualizar(ActionListener actionListener) {
		this.btnBaixar.addActionListener(actionListener);
	}

	public void Reabrir(ActionListener actionListener) {
		this.btnReabrir.addActionListener(actionListener);
	}

}