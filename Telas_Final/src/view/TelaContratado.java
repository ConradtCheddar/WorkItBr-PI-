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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class TelaContratado extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	
	ImageIcon chatIcon = new ImageIcon(getClass().getResource("/imagens/clickable_icon.png"));
	Image scaledImage = chatIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	ImageIcon chatResized = new ImageIcon(scaledImage);

	/**
	 * Create the panel.
	 */
	public TelaContratado() {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));
		
		JLabel lblNewLabel_1 = new JLabel("Trabalhos em andamento");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1, "cell 2 3 3 1,grow");
		
		scrollPane = new JScrollPane();
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");
		add(scrollPane, "cell 2 4 3 11,grow");
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		add(panel_1, "cell 7 4 11 2,grow");
		panel_1.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		add(panel_2, "cell 7 7 11 2,grow");
		panel_2.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");
		
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		add(panel_3, "cell 7 10 11 2,grow");
		panel_3.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");
		
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.LIGHT_GRAY);
		add(panel_4, "cell 7 13 11 2,grow");
		panel_4.putClientProperty("FlatLaf.style", "arc: 20; background: #BFBFBF;");

		JLabel iconLabel = new JLabel(chatResized);
		add(iconLabel, "cell 18 5");
		iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel_3 = new JLabel(chatResized);
		lblNewLabel_3.setVerticalAlignment(SwingConstants.BOTTOM);
		add(lblNewLabel_3, "cell 18 8,alignx center,aligny center");
		lblNewLabel_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel_4 = new JLabel(chatResized);
		lblNewLabel_4.setVerticalAlignment(SwingConstants.BOTTOM);
		add(lblNewLabel_4, "cell 18 11,alignx center,aligny center");
		lblNewLabel_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel_5 = new JLabel(chatResized);
		lblNewLabel_5.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblNewLabel_5.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblNewLabel_5.setVerticalAlignment(SwingConstants.BOTTOM);
		add(lblNewLabel_5, "cell 18 14,alignx center,aligny center");
		lblNewLabel_5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


	}

}
