package view;

import java.awt.CardLayout;
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
	
	private static JPanel contentPane;
	
	private static CardLayout cardLayout;
	
	JPanel wbPanel;
	JLabel lblBarra;
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
		this.cardLayout = new CardLayout();

		this.contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(900, 100));
		contentPane.setBackground(new Color(0, 102, 204));
		this.contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new MigLayout("fill, insets 0", "[20px]", "[35px]"));
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 102, 204));
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		lblBarra = new JLabel(barraResized);
		lblBarra.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblBarra, "cell 0 0 12 2,grow");
		lblBarra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		lblMenu = new JLabel(menuResized);
		lblMenu.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblMenu, "cell 0 0 12 2,grow");
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel = new JLabel("WorkITBr");
		contentPane.add(lblNewLabel, "cell 0 0 12 2,grow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		contentPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
				Image img = menuIcon.getImage();
				Image scaled = img.getScaledInstance(contentPane.getWidth() / 40, contentPane.getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
				ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
				Image imgbarra = barraIcon.getImage();
				Image scaledBarra = imgbarra.getScaledInstance(contentPane.getWidth() / 40, contentPane.getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
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
