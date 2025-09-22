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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class TelaAdm extends JPanel {

	private static final long serialVersionUID = 1L;
	
	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);
	
	ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
	Image scaledImage3 = barraIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon barraResized = new ImageIcon(scaledImage3);
	
	JLabel lblBarra;
	JLabel lblMenu;

	/**
	 * Create the panel.
	 */
	public TelaAdm() {
		setPreferredSize(new Dimension(707, 500));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][98.00,grow][grow][81.00][-56.00][65.00,grow][][]", "[35px][66.00,grow][grow][][][66]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 204));
		add(panel, "flowx,cell 0 0 20 1,grow");
		panel.setLayout(new MigLayout("fill", "[][][][][][][][][][][][][][][]", "[]"));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "cell 1 0 12 1,grow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		lblMenu = new JLabel(menuResized);
		lblMenu.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblMenu, "cell 1 0 12 1,grow");
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		lblBarra = new JLabel(barraResized);
		lblBarra.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblBarra, "cell 1 0 12 1,grow");
		lblBarra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 17);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 35);
			}
		});
		panel.addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	            Image img = menuIcon.getImage();
	            Image scaled = img.getScaledInstance(panel.getWidth() / 40, panel.getHeight()*2 / 4, Image.SCALE_SMOOTH);
	            ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
	            Image imgbarra = barraIcon.getImage();
	            Image scaledBarra = imgbarra.getScaledInstance(panel.getWidth() / 40, panel.getHeight()*2 / 4, Image.SCALE_SMOOTH);
	            lblMenu.setIcon(new ImageIcon(scaled));
	            lblBarra.setIcon(new ImageIcon(scaledBarra));
	        }
	    });

	}
	
	public void barra(MouseListener actionListener) {
	    this.lblBarra.addMouseListener(actionListener);
	}
	public void menu(MouseListener actionListener) {
	    this.lblMenu.addMouseListener(actionListener);
	}

}
