package main;

import javax.swing.UIManager;

import controller.CadastroController;
import controller.ContratadoController;
import controller.ContratanteController;
import controller.LoginController;
import controller.Navegador;
import controller.TempController;
import model.UsuarioDAO;
import view.Primario;
import view.TelaAdm;
import view.TelaCadastro;
import view.TelaContratado;
import view.TelaContratante;
import view.TelaLogin;
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
		
		//tela do contratante
		TelaContratante telacontratante = new TelaContratante();
		ContratanteController contratanteController = new ContratanteController();
		
		//tela do contratado
		TelaContratado telacontratado = new TelaContratado();
		ContratadoController contratadocontroller = new ContratadoController();
		
		Temp temp = new Temp();
		TempController tempcontroller = new TempController(temp, usuarioDAO, navegador);
		
		TelaAdm telaadm = new TelaAdm();
		
		
		
		
		
		
		
		
		navegador.adicionarPainel("LOGIN", telalogin);
		navegador.adicionarPainel("CADASTRO", telacadastro);
		navegador.adicionarPainel("CONTRATANTE", telacontratante);
		navegador.adicionarPainel("CONTRATADO", telacontratado);
		navegador.adicionarPainel("TEMP", temp);
		navegador.adicionarPainel("ADM", telaadm);
		
		navegador.navegarPara("TEMP");
		
		prim.setVisible(true);
		
	}
}
