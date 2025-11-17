package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.ContratadoController;
import controller.ListaServicosController;
import controller.LoginController;
import controller.PopupController;
import controller.TempController;
import model.ServicoDAO;
import model.UsuarioDAO;
import view.DrawerMenu;
import view.Mensagem;
import view.Primario;
import view.SplashScreen;
import view.TelaVisArquivos;
import view.Temp;
import view.TelaCadastro;
import view.TelaCadastroContratante;
import view.TelaContratado;
import view.TelaListaServicos;
import view.TelaLogin;
import view.wbBarra;

public class Main {
	public static void main(String[] args) {
		SplashScreen splash = new SplashScreen();
		splash.setVisible(true);
		
		new Thread(() -> {
			try {
				splash.setProgress(5, "Carregando tema...");
				try {
					UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
				} catch (Exception ex) {
					System.err.println("Falha ao carregar o tema FlatLaf");
				}
				Thread.sleep(200);
				
				splash.setProgress(15, "Inicializando componentes...");
				wbBarra wbb = new wbBarra();
				Mensagem M = new Mensagem();
				Thread.sleep(150);
				
				splash.setProgress(25, "Conectando ao banco de dados...");
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				ServicoDAO servicoDAO = new ServicoDAO();
				Thread.sleep(200);
				
				splash.setProgress(35, "Criando interface principal...");
				DrawerMenu pm = new DrawerMenu();
				Primario prim = new Primario(wbb, pm);
				Thread.sleep(150);

				splash.setProgress(45, "Configurando navegação...");
				controller.Navegador navegador = new controller.Navegador(prim);
				navegador.setUsuarioDAO(usuarioDAO);
				prim.setNavegador(navegador);
				Thread.sleep(100);

				splash.setProgress(55, "Criando fábrica de telas...");
				controller.TelaFactory telaFactory = new controller.TelaFactory(navegador, servicoDAO, usuarioDAO);
				Thread.sleep(100);
				
				splash.setProgress(60, "Inicializando controladores...");
				PopupController popup = new PopupController();

				TelaLogin telalogin = new TelaLogin();
				TelaCadastro telacadastro = new TelaCadastro();

				controller.PopupMenuController popup2 = new controller.PopupMenuController(pm, navegador, telaFactory);
				controller.WBController wbController = new controller.WBController(wbb, usuarioDAO, navegador, popup, popup2);
				Thread.sleep(150);

				splash.setProgress(70, "Configurando tela de login...");
				LoginController logincontroller = new LoginController(telalogin, usuarioDAO, navegador, telacadastro, popup2);
				controller.CadastroController cadastrocontroller = new controller.CadastroController(telacadastro, usuarioDAO, navegador);
				Thread.sleep(100);

				splash.setProgress(80, "Configurando tela do contratado...");
				TelaContratado telacontratado = new TelaContratado();
				ContratadoController contratadocontroller = new ContratadoController(telacontratado, usuarioDAO, navegador, servicoDAO, telaFactory);
				telacontratado.adicionarOuvinte(contratadocontroller);
				Temp temp = new Temp();
				TempController tempController = new TempController(temp, usuarioDAO, navegador);
				Thread.sleep(100);
				
				splash.setProgress(85, "Configurando cadastros...");
				TelaCadastroContratante telacadastrocontratante = new TelaCadastroContratante();
				controller.CadastroContratanteController cadastrocontratantecontroller = new controller.CadastroContratanteController(telacadastrocontratante, servicoDAO, navegador);

				Thread.sleep(100);
				
				splash.setProgress(90, "Configurando lista de serviços...");
				TelaListaServicos telaservicos = new TelaListaServicos();
				ListaServicosController controller = new ListaServicosController(telaservicos, servicoDAO, navegador, telaFactory);
				telaservicos.setOnShow(() -> controller.atualizarTabelaServicosDoUsuario());
				Thread.sleep(100);

				splash.setProgress(95, "Registrando telas...");
				navegador.adicionarPainel("LOGIN", telalogin);
				navegador.adicionarPainel("CADASTRO", telacadastro);
				navegador.adicionarPainel("CONTRATADO", telacontratado);
				navegador.adicionarPainel("CADASTRO_CONTRATANTE", telacadastrocontratante);
				navegador.adicionarPainel("SERVICOS", telaservicos);
				navegador.adicionarPainel("TEMP", temp);
				Thread.sleep(100);

				splash.setProgress(98, "Finalizando...");
				navegador.navegarPara("LOGIN");
				Thread.sleep(100);

				splash.setProgress(100, "Preparando interface...");
				
				SwingUtilities.invokeAndWait(() -> {
					prim.preinicializar();
				});
				
				Thread.sleep(300);
				
				SwingUtilities.invokeLater(() -> {
					prim.setVisible(true);
					prim.toFront();
					prim.requestFocus();
					wbb.habilitarResizeListeners();
				});
				
				Thread.sleep(150);
				
				splash.closeSplash(250);
				
			} catch (Exception e) {
				e.printStackTrace();
				splash.closeSplash();
			}
		}).start();
	}
}