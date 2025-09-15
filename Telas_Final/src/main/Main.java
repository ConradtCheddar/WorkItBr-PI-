package main;

import javax.swing.UIManager;

import controller.CadastroController;
import controller.LoginController;
import controller.Navegador;
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
	    
		
		Navegador navegador =new Navegador(prim);
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		//tela de login
		TelaLogin telalogin = new TelaLogin();
		LoginController logincontroller = new LoginController(telalogin, usuarioDAO, navegador);
		
		//tela de cadastro
		TelaCadastro telacadastro = new TelaCadastro();
		CadastroController cadastrocontroller = new CadastroController(telacadastro, usuarioDAO, navegador);
		
		
		
		
		
		
		navegador.adicionarPainel("LOGIN", telalogin);
		navegador.adicionarPainel("CADASTRO", telacadastro);
		
		navegador.navegarPara("LOGIN");
		
		prim.setVisible(true);
		
	}
}
