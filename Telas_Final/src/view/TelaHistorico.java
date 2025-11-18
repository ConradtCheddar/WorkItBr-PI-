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
import util.FontScaler;
import util.FontScaler.FontSize;

import javax.swing.JList;

import model.Servico;

public class TelaHistorico extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JList<Servico> listaHistorico;
	private Runnable onShowCallback;
	private JLabel lblVoltar;
	private JLabel lblHistorico;

	public TelaHistorico() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 20 20 20 20, gap 20", "[grow]", "[grow]"));

		lblVoltar = new JLabel("");
		add(lblVoltar, "cell 0 0");

		scrollPane = new JScrollPane();
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setBackground(Color.LIGHT_GRAY);
		add(scrollPane, "cell 0 0,grow");

		listaHistorico = new JList<Servico>();
		scrollPane.setViewportView(listaHistorico);

		lblHistorico = new JLabel("Histórico de Trabalhos");
		scrollPane.setColumnHeaderView(lblHistorico);
		lblHistorico.setHorizontalAlignment(SwingConstants.CENTER);

		FontScaler.addAutoResize(this, new Object[] { lblHistorico, FontSize.SUBTITULO });
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

	public void cliqueDuploNoHistorico(MouseListener actionListener) {
		this.listaHistorico.addMouseListener(actionListener);
	}

	public JList<Servico> getListaHistorico() {
		return listaHistorico;
	}

	public void setListaHistorico(JList<Servico> listHistorico) {
		this.listaHistorico = listHistorico;
	}
}