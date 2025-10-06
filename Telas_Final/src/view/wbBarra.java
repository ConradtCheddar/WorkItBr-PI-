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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.PopupController;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class wbBarra extends JPanel {

	private static final long serialVersionUID = 1L;

	JPanel wbPanel;
	private JLabel lblBarra;
	JLabel lblMenu;
	JLabel lblNewLabel;
	private JButton btnNewButton;



	/**
	 * Create the panel.
	 */
	public wbBarra() {

		setPreferredSize(new Dimension(900, 85));
		setBackground(new Color(0, 102, 204));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill", "[grow][grow][grow]", "[grow]"));

		lblMenu = new JLabel();
		lblMenu.setHorizontalAlignment(SwingConstants.LEFT);
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(lblMenu, "flowx,cell 0 0,alignx left,growy");

		lblBarra = new JLabel("gyj");
		lblBarra.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBarra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(lblBarra, "cell 2 0,alignx right,growy");
		
		btnNewButton = new JButton("New button");
		add(btnNewButton, "cell 0 0");
		
		lblNewLabel = new JLabel("WorkITBr");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		add(lblNewLabel, "cell 1 0,grow");


		setLblBarra(new JLabel());
		getLblBarra().setHorizontalAlignment(SwingConstants.RIGHT);
		getLblBarra().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(getLblBarra(), "cell 2 0,alignx right,growy");
		
		
		getLblBarra().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Aqui chamamos o PopupController
				PopupController popup = new PopupController();
				popup.PopupMenu(e, getLblBarra()); // Passando o evento e o componente
			}
		});

		
		lblMenu.setBorder(BorderFactory.createLineBorder(Color.RED));
		lblBarra.setBorder(BorderFactory.createLineBorder(Color.RED));
		lblNewLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		



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

				ajustarFonte();
			//	ajustarIcones();

			}
		});

	}

	
	
	public void barra(MouseListener actionListener) {
		this.getLblBarra().addMouseListener(actionListener);
	}

//	public void barra(MouseListener actionListener) {
//		System.out.println("barra");
//		this.lblBarra.addMouseListener(actionListener);
//	}

//	public void menu(MouseListener actionListener) {
//		this.lblMenu.addMouseListener(actionListener);
//	}
	
	public void menu(MouseAdapter mouseAdapter) { 
	    this.lblMenu.addMouseListener(mouseAdapter);
	}
	
	public void btn(ActionListener actionListener) { 
	    this.btnNewButton.addActionListener(actionListener);
	}
	
	

	public void ajustarFonte() {
		int w = getWidth();
		if (w <= 0)
			return;

		float novoTamanho = Math.max(12f, w / 40f);

		lblNewLabel.setFont(lblNewLabel.getFont().deriveFont(novoTamanho));
		revalidate();
		repaint();
	}

	public void ajustarIcones() {
		int largura = Math.max(10, getWidth() / 25);
		int altura = Math.max(10, getHeight() * 2 / 5);
		
		int larguraCasa = Math.max(10, getWidth() / 25);
		int alturaCasa = Math.max(10, getHeight() * 2 / 4);

		ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
		Image img = menuIcon.getImage();
		Image scaled = img.getScaledInstance(larguraCasa, alturaCasa, Image.SCALE_SMOOTH);

		ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
		Image imgBarra = barraIcon.getImage();
		Image scaledBarra = imgBarra.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

		lblMenu.setIcon(new ImageIcon(scaled));
		lblBarra.setIcon(new ImageIcon(scaledBarra));
	}

	public JLabel getLblBarra() {
		return lblBarra;
	}

	public void setLblBarra(JLabel lblBarra) {
		this.lblBarra = lblBarra;
	}

}
