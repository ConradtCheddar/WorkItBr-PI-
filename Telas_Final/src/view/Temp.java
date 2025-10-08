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
	
	Primario prim;
	
	Navegador navegador = new Navegador(prim);
	
	JButton btnLogin;
	JButton btnADM;
	JButton btnContratado;
	JButton btnContratante;
	JButton btnCadastroContratante;
	
	/**
	 * Create the panel.
	 */
	public Temp() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));
		
		btnLogin = new JButton("Login");
		add(btnLogin, "cell 11 9,grow");
		
<<<<<<< HEAD

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "flowx,cell 6 0,grow");
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
=======
		btnADM = new JButton("ADM");
>>>>>>> Popup
		add(btnADM, "cell 11 10,grow");
		
		btnContratado = new JButton("Contratado");
		add(btnContratado, "cell 11 11,grow");
		
		btnContratante = new JButton("Contratante");
		add(btnContratante, "cell 11 12,grow");
		
		btnCadastroContratante = new JButton("Cadastro Contratante");
		add(btnCadastroContratante, "cell 11 13,grow");
		
	

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
	public void cadastroContratante(ActionListener actionListener) {
		this.btnCadastroContratante.addActionListener(actionListener);
	}
	
	
	

}