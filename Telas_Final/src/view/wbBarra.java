package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class wbBarra extends JPanel {

	private static final long serialVersionUID = 1L;
	
	
	//private static CardLayout cardLayout;
	
	JPanel wbPanel;
	private JLabel lblBarra;
	JLabel lblMenu;
	
	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);

	ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
	Image scaledImage3 = barraIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon barraResized = new ImageIcon(scaledImage3);

	/**
	 * Create the panel.
	 */
	public wbBarra() {
	//	this.cardLayout = new CardLayout();

		setPreferredSize(new Dimension(900, 100));
		setBackground(new Color(0, 102, 204));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, debug ", "[grow][grow][grow]", "[grow]"));
		
		lblMenu = new JLabel(menuResized);
		lblMenu.setHorizontalAlignment(SwingConstants.LEFT);
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(lblMenu, "cell 0 0,alignx left,growy");
		
		JLabel lblNewLabel = new JLabel("WorkITBr");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		add(lblNewLabel, "cell 1 0,grow");

		setLblBarra(new JLabel(barraResized));
		getLblBarra().setHorizontalAlignment(SwingConstants.RIGHT);
		getLblBarra().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(getLblBarra(), "cell 2 0,alignx right,growy");
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
				Image img = menuIcon.getImage();
				Image scaled = img.getScaledInstance(getWidth() / 40, getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
				ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
				Image imgbarra = barraIcon.getImage();
				Image scaledBarra = imgbarra.getScaledInstance(getWidth() / 40, getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
				lblMenu.setIcon(new ImageIcon(scaled));
				getLblBarra().setIcon(new ImageIcon(scaledBarra));
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 17);
				int fontSize2 = Math.max(15, panelHeight / 40);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});
	}
	
	public void barra(MouseListener actionListener) {
		this.getLblBarra().addMouseListener(actionListener);
	}

	public void menu(MouseListener actionListener) {
		this.lblMenu.addMouseListener(actionListener);
	}

	public JLabel getLblBarra() {
		return lblBarra;
	}

	public void setLblBarra(JLabel lblBarra) {
		this.lblBarra = lblBarra;
	}

}
