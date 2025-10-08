package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import controller.Navegador;
import net.miginfocom.swing.MigLayout;

public class TelaContratante extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField tfPesquisar;

	/**
	 * Create the panel.
	 */
	public TelaContratante() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0",
				"[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]",
				"[35px][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));

		tfPesquisar = new JTextField();
		add(tfPesquisar, "cell 4 4 14 1,grow");
		tfPesquisar.setColumns(10);
		tfPesquisar.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Pesquisar...");
		tfPesquisar.putClientProperty("JComponent.roundRect", true);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setForeground(Color.LIGHT_GRAY);
		add(panel_1, "cell 3 6 4 3,grow");

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		add(panel_2, "cell 9 6 4 3,grow");

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		add(panel_3, "cell 15 6 4 3,grow");

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.LIGHT_GRAY);
		add(panel_4, "cell 3 12 4 3,grow");

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.LIGHT_GRAY);
		add(panel_5, "cell 9 12 4 3,grow");

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.LIGHT_GRAY);
		add(panel_6, "cell 15 12 4 3,grow");

	}


}