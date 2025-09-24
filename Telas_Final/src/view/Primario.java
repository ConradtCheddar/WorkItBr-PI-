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
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;

public class Primario extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	
	JPanel cPanel;
	JLabel lblBarra;
	JLabel lblMenu;
	
	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);

	ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
	Image scaledImage3 = barraIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon barraResized = new ImageIcon(scaledImage3);
	
	private static CardLayout cardLayout;

	/**
	 * Create the frame.
	 */
	public Primario() {
		setFocusTraversalPolicyProvider(true);
		setTitle("WorkITBr");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		setLocationRelativeTo(null);
		List<Image> icons = new ArrayList<>();
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());

		setIconImages(icons);
		UIManager.put("Button.arc", 999);
		
		this.cardLayout = new CardLayout();

		this.contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 102, 204));
		this.contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(this.contentPane);
		contentPane.setLayout(new MigLayout("fill, insets 0", "[20px][grow][20px]", "[35px][grow][35px]"));
		
		JPanel wbPanel = new JPanel();
		wbPanel.setBackground(new Color(0, 102, 204));
		contentPane.add(wbPanel, "cell 0 0 3 1,grow");
		wbPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		this.cPanel = new JPanel(cardLayout);
		this.cPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(cPanel,"cell 0 1 3 2,grow");
		
		lblBarra = new JLabel(barraResized);
		lblBarra.setHorizontalAlignment(SwingConstants.RIGHT);
		cPanel.add(lblBarra, "cell 0 0 12 2,grow");
		lblBarra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		lblMenu = new JLabel(menuResized);
		lblMenu.setHorizontalAlignment(SwingConstants.LEFT);
		cPanel.add(lblMenu, "cell 0 0 12 2,grow");
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblNewLabel = new JLabel("WorkITBr");
		wbPanel.add(lblNewLabel, "cell 0 0 12 2,grow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 17);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});
		
		wbPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
				Image img = menuIcon.getImage();
				Image scaled = img.getScaledInstance(wbPanel.getWidth() / 40, wbPanel.getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
				ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
				Image imgbarra = barraIcon.getImage();
				Image scaledBarra = imgbarra.getScaledInstance(wbPanel.getWidth() / 40, wbPanel.getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
				lblMenu.setIcon(new ImageIcon(scaled));
				lblBarra.setIcon(new ImageIcon(scaledBarra));
			}
		});


	}

	public void mostrarTela(String panelName) {
		this.cardLayout.show(this.cPanel, panelName);
		this.pack();
	}
	
	public void adicionarTela(String panelName, JPanel tela) {
		this.cPanel.add(tela, panelName);
	}
	
	public void barra(MouseListener actionListener) {
	    this.lblBarra.addMouseListener(actionListener);
	}
	public void menu(MouseListener actionListener) {
	    this.lblMenu.addMouseListener(actionListener);
	}



}
