package main;

import javax.swing.UIManager;

import controller.LoginController;
import controller.TelaController;
import model.UsuarioDAO;
import view.Primario;
import view.TelaAdm;
import view.TelaCadastro;
import view.TelaContratante;
import view.TelaLogin;
import view.TelaTrabalhos;
import view.Temp;

public class Main {
	public static void main(String[] args) {
	       try {
	    	   UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
	        } catch (Exception ex) {
	            System.err.println("Falha ao carregar o tema FlatLaf");
	        }
	    Primario prim = new Primario();
	    
		prim.setVisible(true);
		TelaController navegador =new TelaController(prim);
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		TelaLogin telaLogin = new TelaLogin();
		LoginController loginController = new LoginController(telaLogin,usuarioDAO,navegador);
		TelaCadastro telaCadastro = new TelaCadastro();
		Temp temp = new Temp();
		TelaAdm adm = new TelaAdm();
		TelaTrabalhos trabalhos = new TelaTrabalhos();
		TelaContratante Panelcontratante = new TelaContratante();
		
		navegador.adicionarPainel(telaLogin, "LOGIN_PANEL");
		contentPane.add(telaCadastro, CAD2_PANEL);
		contentPane.add(temp, TEMP_PANEL);
		contentPane.add(adm, ADM_PANEL);
		contentPane.add(trabalhos, TRABALHOS_PANEL);
		contentPane.add(Panelcontratante, CONTRATANTE_PANEL);
		
		
	}
}
