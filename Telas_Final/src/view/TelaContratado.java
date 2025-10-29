package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import net.miginfocom.swing.MigLayout;
import javax.swing.JList;

public class TelaContratado extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	
	private JList listaDisponivel;
	private JList listaAndamento;
	private Runnable onShowCallback;
	private JLabel lblDisponiveis;
	private JLabel lblVoltar;

	/**
	 * Create the panel.
	 */
	public TelaContratado() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 20 20 20 20, gap 20", "[grow][grow]", "[grow 10][grow]"));
		
		lblVoltar = new JLabel("");
		add(lblVoltar, "cell 0 0");
		
		scrollPane = new JScrollPane();
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setBackground(Color.LIGHT_GRAY);
		add(scrollPane, "cell 0 1,grow");
		
		listaAndamento = new JList();
		scrollPane.setViewportView(listaAndamento);
		
		JLabel lblAndamento = new JLabel("Trabalhos em andamento");
		scrollPane.setColumnHeaderView(lblAndamento);
		lblAndamento.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "cell 1 1,grow");
		
		listaDisponivel = new JList();
		scrollPane_1.setViewportView(listaDisponivel);
		
		lblDisponiveis = new JLabel("Trabalhos disponiveis");
		lblDisponiveis.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblDisponiveis);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 37);
				int fontSize2 = Math.max(15, panelHeight / 27);
				Font italicPlaceholderFont = new Font("Tahoma", Font.PLAIN, fontSize);
				lblAndamento.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				lblDisponiveis.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
			}
		});


	}

	public JList getListaDisponivel() {
		return listaDisponivel;
	}

	public void setOnShow(Runnable r) {
		this.onShowCallback = r;
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (onShowCallback != null) {
			onShowCallback.run();
		}
	}
	
	public void adicionarOuvinte(ComponentListener listener) {
		this.addComponentListener(listener);
	}
	
	public void cliqueDuploNoJList(MouseListener actionListener) {
	    this.listaDisponivel.addMouseListener(actionListener);
	}
	
	public void cliqueDuploNoAndamento(MouseListener actionListener) {
	    this.listaAndamento.addMouseListener(actionListener);
	}

	public void setListaDisponivel(JList listaDisponivel) {
		this.listaDisponivel = listaDisponivel;
	}

	public JList getListaAndamento() {
		return listaAndamento;
	}

	public void setListaAndamento(JList listAndamento) {
		this.listaAndamento = listAndamento;
	}
	
	
}