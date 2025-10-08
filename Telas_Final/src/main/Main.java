package main;

import javax.swing.UIManager;

import controller.CadastroContratanteController;
import controller.CadastroController;
import controller.ContratadoController;
import controller.ContratanteController;
import controller.LoginController;
import controller.Navegador;
import controller.PopupController;
import controller.PopupMenuController;
import controller.TempController;
import controller.WBController;
import model.ServicoDAO;
import model.UsuarioDAO;
import view.DrawerMenu;
import view.Primario;
import view.TelaAdm;
import view.TelaCadastro;
import view.TelaCadastroContratante;
import view.TelaContratado;
import view.TelaContratante;
import view.TelaLogin;
import view.Temp;
import view.wbBarra;

public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
		} catch (Exception ex) {
			System.err.println("Falha ao carregar o tema FlatLaf");
		}
		wbBarra wbb = new wbBarra();
		
		DrawerMenu pm = new DrawerMenu(null);
		Primario prim = new Primario(wbb, pm);
		pm.setParentFrame(prim);

		Navegador navegador = new Navegador(prim);
		pm.setNavegador(navegador);
		UsuarioDAO usuarioDAO = new UsuarioDAO();
<<<<<<< HEAD
		PopupController popup = new PopupController();

		// tela de login
=======
		ServicoDAO servicoDAO = new ServicoDAO();
	    PopupController popup = new PopupController();
	     
		
		//tela de login
>>>>>>> Func_Contratante
		TelaLogin telalogin = new TelaLogin();
		LoginController logincontroller = new LoginController(telalogin, usuarioDAO, navegador);

		// tela de cadastro
		TelaCadastro telacadastro = new TelaCadastro();
		CadastroController cadastrocontroller = new CadastroController(telacadastro, usuarioDAO, navegador);

		// tela do contratante
		TelaContratante telacontratante = new TelaContratante();
		ContratanteController contratanteController = new ContratanteController(telacontratante, usuarioDAO, navegador);

		// tela do contratado
		TelaContratado telacontratado = new TelaContratado();
		ContratadoController contratadocontroller = new ContratadoController(telacontratado, usuarioDAO, navegador);

		Temp temp = new Temp();
		TempController tempcontroller = new TempController(temp, usuarioDAO, navegador);

		TelaAdm telaadm = new TelaAdm();
<<<<<<< HEAD
=======
		wbBarra wbb = new wbBarra();
		WBController wbcontroller = new WBController(wbb, usuarioDAO, navegador, popup);
		
		TelaCadastroContratante telaCadastroContratante = new TelaCadastroContratante();
		CadastroContratanteController cadastroContratanteController = new CadastroContratanteController(telaCadastroContratante, servicoDAO, navegador);
		
		
		
	
	   
		
		
		
>>>>>>> Func_Contratante
		
		PopupMenuController popup2 = new PopupMenuController(pm, navegador);
		WBController wbcontroller = new WBController(wbb, usuarioDAO, navegador, popup, popup2);

		navegador.adicionarPainel("LOGIN", telalogin);
		navegador.adicionarPainel("CADASTRO", telacadastro);
		navegador.adicionarPainel("CONTRATANTE", telacontratante);
		navegador.adicionarPainel("CONTRATADO", telacontratado);
		navegador.adicionarPainel("TEMP", temp);
		navegador.adicionarPainel("ADM", telaadm);
<<<<<<< HEAD

		navegador.navegarPara("LOGIN");
=======
		navegador.adicionarPainel("CADASTROCONTRATANTE", telaCadastroContratante);
	
		
		navegador.navegarPara("CADASTROCONTRATANTE");
		
>>>>>>> Func_Contratante
		prim.setVisible(true);
	}
}