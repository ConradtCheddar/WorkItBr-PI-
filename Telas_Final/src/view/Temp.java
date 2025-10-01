package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Navegador;
import net.miginfocom.swing.MigLayout;

public class Temp extends JPanel {

	private static final long serialVersionUID = 1L;

//	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
//	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
//	ImageIcon menuResized = new ImageIcon(scaledImage2);
//	
//	ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
//	Image scaledImage3 = barraIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
//	ImageIcon barraResized = new ImageIcon(scaledImage3);
	
	Primario prim;
	
	Navegador navegador = new Navegador(prim);
	
	JButton btnLogin;
	JButton btnADM;
	JButton btnContratado;
	JButton btnContratante;
	
	/**
	 * Create the panel.
	 */
	public Temp() {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));
		
		btnLogin = new JButton("Login");
		add(btnLogin, "cell 11 9,grow");
		
		btnADM = new JButton("ADM");
		add(btnADM, "cell 11 10,grow");
		
		btnContratado = new JButton("Contratado");
		add(btnContratado, "cell 11 11,grow");
		
		btnContratante = new JButton("Contratante");
		add(btnContratante, "cell 11 12,grow");
		
	

	}
	
	public void login(ActionListener actionListener) {
		this.btnLogin.addActionListener(actionListener);
	}
	public void adm(ActionListener actionListener) {
		this.btnADM.addActionListener(actionListener);
	}
	public void contratado(ActionListener actionListener) {
		this.btnContratado.addActionListener(actionListener);
	}
	public void contratante(ActionListener actionListener) {
		this.btnContratante.addActionListener(actionListener);
	}
	
	

}
