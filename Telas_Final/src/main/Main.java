package main;

import javax.swing.UIManager;

import controller.CadastroContratanteController;
import controller.CadastroController;
import controller.ContratadoController;
import controller.ContratanteController;
import controller.ListaServicosController;
import controller.LoginController;
import controller.Navegador;
import controller.PopupController;
import controller.PopupMenuController;
import controller.TelaFactory;
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
import view.TelaListaServicos;
import view.TelaLogin;
import view.Temp;
import view.VisServicoCnte;
import view.wbBarra;

public class Main {
	public static void main(String[] args) {
		// Inicialização
		try {
			UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
		} catch (Exception ex) {
			System.err.println("Falha ao carregar o tema FlatLaf");
		}
		wbBarra wbb = new wbBarra();
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ServicoDAO servicoDAO = new ServicoDAO();
		
		DrawerMenu pm = new DrawerMenu(usuarioDAO);
		Primario prim = new Primario(wbb, pm);

		Navegador navegador = new Navegador(prim);
		navegador.setUsuarioDAO(usuarioDAO); // Injeta o UsuarioDAO no Navegador
		prim.setNavegador(navegador); // Configura o Navegador no Primario para limpeza ao fechar
		pm.setNavegador(navegador);

		TelaFactory telaFactory = new TelaFactory(navegador, servicoDAO, usuarioDAO);

		pm.setTelaFactory(telaFactory);
		
		PopupController popup = new PopupController();

		TelaLogin telalogin = new TelaLogin();
		TelaCadastro telacadastro = new TelaCadastro();
		PopupMenuController popup2 = new PopupMenuController(pm, navegador, telaFactory);
        WBController wbController = new WBController(wbb, usuarioDAO, navegador, popup, popup2);
		LoginController logincontroller = new LoginController(telalogin, usuarioDAO, navegador, telacadastro, popup2);
		CadastroController cadastrocontroller = new CadastroController(telacadastro, usuarioDAO, navegador);

		// tela do contratante
		TelaContratante telacontratante = new TelaContratante();
		ContratanteController contratanteController = new ContratanteController(telacontratante, usuarioDAO, navegador);

		TelaContratado telacontratado = new TelaContratado();
		ContratadoController contratadocontroller = new ContratadoController(telacontratado, usuarioDAO, navegador, servicoDAO, telaFactory);
        telacontratado.adicionarOuvinte(contratadocontroller);
		
		TelaCadastroContratante telacadastrocontratante = new TelaCadastroContratante();
		CadastroContratanteController cadastrocontratantecontroller = new CadastroContratanteController(telacadastrocontratante, servicoDAO, navegador);
		
		Temp temp = new Temp();
		TempController tempcontroller = new TempController(temp, usuarioDAO, navegador);

		TelaAdm telaadm = new TelaAdm();
		
		TelaListaServicos telaservicos = new TelaListaServicos();
		ListaServicosController controller = new ListaServicosController(telaservicos, servicoDAO, navegador, telaFactory);
		telaservicos.setOnShow(() -> controller.atualizarTabelaServicosDoUsuario());


		navegador.adicionarPainel("LOGIN", telalogin);
		navegador.adicionarPainel("CADASTRO", telacadastro);
		navegador.adicionarPainel("CONTRATANTE", telacontratante);
		navegador.adicionarPainel("CONTRATADO", telacontratado);
		navegador.adicionarPainel("TEMP", temp);
		navegador.adicionarPainel("ADM", telaadm);
		navegador.adicionarPainel("CADASTRO_CONTRATANTE", telacadastrocontratante);
		navegador.adicionarPainel("SERVICOS", telaservicos);
		

		navegador.navegarPara("login");
		prim.setVisible(true);
		pm.setNavegador(navegador);

		
	}
}