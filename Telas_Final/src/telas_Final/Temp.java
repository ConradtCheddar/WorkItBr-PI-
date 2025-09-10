package telas_Final;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class Temp extends JPanel {

	private static final long serialVersionUID = 1L;

	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);
	/**
	 * Create the panel.
	 */
	public Temp(Primario prim) {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 204));
		add(panel, "flowx,cell 0 0 41 1,grow");
		panel.setLayout(new MigLayout("fill", "[][][][][][][][][][][][]", "[]"));
		
		JLabel lblMenu = new JLabel(menuResized);
		lblMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				prim.mostrarTela(prim.TEMP_PANEL);
			}
		});
		lblMenu.setHorizontalAlignment(SwingConstants.TRAILING);
		panel.add(lblMenu, "cell 11 0,grow");
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "flowx,cell 0 0 12 1,grow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.LOGIN_PANEL);
			}
		});
		
		JButton btnNewButton_3 = new JButton("Config");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.CONFIG_USER_PANEL);
			}
		});
		add(btnNewButton_3, "cell 11 6,grow");
		add(btnNewButton, "cell 11 9,grow");
		
		JButton btnADM = new JButton("ADM");
		btnADM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.ADM_PANEL);
			}
		});
		add(btnADM, "cell 11 10,grow");
		
		JButton btnNewButton_1 = new JButton("Trabalhos");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.TRABALHOS_PANEL);
			}
		});
		add(btnNewButton_1, "cell 11 11,grow");
		
		JButton btnNewButton_2 = new JButton("Contratante");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prim.mostrarTela(prim.CONTRATANTE_PANEL);
			}
		});
		add(btnNewButton_2, "cell 11 12,grow");
		
		panel.addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	            Image img = menuIcon.getImage();
	            Image scaled = img.getScaledInstance(panel.getWidth() / 40, panel.getHeight()*2 / 4, Image.SCALE_SMOOTH);
	            lblMenu.setIcon(new ImageIcon(scaled));
	        }
	    });
		

	}

}
