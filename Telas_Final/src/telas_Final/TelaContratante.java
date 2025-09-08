package telas_Final;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

public class TelaContratante extends JPanel {

	private static final long serialVersionUID = 1L;
	
	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);
	
	ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
	Image scaledImage3 = barraIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon barraResized = new ImageIcon(scaledImage3);
	
	private JTextField tfPesquisar;

	/**
	 * Create the panel.
	 */
	public TelaContratante(Primario prim) {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]", "[35px][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 204));
		add(panel, "flowx,cell 0 0 41 1,grow");
		panel.setLayout(new MigLayout("fill", "[][][][][][][][][][][][][]", "[]"));
		
		JLabel lblBarra = new JLabel(barraResized);
		lblBarra.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBarra.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				prim.mostrarTela(prim.TEMP_PANEL);
			}
		});
		panel.add(lblBarra, "cell 12 0,grow");
		lblBarra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JLabel lblMenu = new JLabel(menuResized);
		lblMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				prim.mostrarTela(prim.TEMP_PANEL);
			}
		});
		lblMenu.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblMenu, "cell 0 0,grow");
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "flowx,cell 6 0,grow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		

		
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

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 17);
				int fontSize2 = Math.max(15, panelHeight / 40);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfPesquisar.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
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
	
	

}
