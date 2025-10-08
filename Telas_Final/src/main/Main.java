package main;

import javax.swing.UIManager;

import controller.CadastroController;
import controller.ContratadoController;
import controller.ContratanteController;
import controller.LoginController;
import controller.Navegador;
import controller.PopupController;
import controller.PopupMenuController;
import controller.TempController;
import controller.WBController;
import model.UsuarioDAO;
import view.DrawerMenu;
import view.Primario;
import view.TelaAdm;
import view.TelaCadastro;
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
		// Create DrawerMenu once and pass to Primario and PopupMenuController
		DrawerMenu pm = new DrawerMenu(null); // We'll set Primario after construction
		Primario prim = new Primario(wbb, pm);
		pm.setParentFrame(prim); // Set parentFrame reference after Primario is constructed

		Navegador navegador = new Navegador(prim);
		pm.setNavegador(navegador); // Set Navegador reference for logout
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		PopupController popup = new PopupController();

		// tela de login
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
		
		PopupMenuController popup2 = new PopupMenuController(pm, navegador);
		WBController wbcontroller = new WBController(wbb, usuarioDAO, navegador, popup, popup2);

		navegador.adicionarPainel("LOGIN", telalogin);
		navegador.adicionarPainel("CADASTRO", telacadastro);
		navegador.adicionarPainel("CONTRATANTE", telacontratante);
		navegador.adicionarPainel("CONTRATADO", telacontratado);
		navegador.adicionarPainel("TEMP", temp);
		navegador.adicionarPainel("ADM", telaadm);

		navegador.navegarPara("LOGIN");
		prim.setVisible(true);
	}
}