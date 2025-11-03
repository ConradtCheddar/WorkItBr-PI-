package main;

import javax.swing.SwingUtilities;
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
import view.SplashScreen;
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
		// Mostra a tela de splash imediatamente
		SplashScreen splash = new SplashScreen();
		splash.setVisible(true);
		
		// Executa a inicialização em uma thread separada
		new Thread(() -> {
			try {
				// Inicialização do tema
				splash.setProgress(5, "Carregando tema...");
				try {
					UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
				} catch (Exception ex) {
					System.err.println("Falha ao carregar o tema FlatLaf");
				}
				Thread.sleep(200);
				
				// Componentes base
				splash.setProgress(15, "Inicializando componentes...");
				wbBarra wbb = new wbBarra();
				Thread.sleep(150);
				
				splash.setProgress(25, "Conectando ao banco de dados...");
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				ServicoDAO servicoDAO = new ServicoDAO();
				Thread.sleep(200);
				
				splash.setProgress(35, "Criando interface principal...");
				DrawerMenu pm = new DrawerMenu(usuarioDAO);
				Primario prim = new Primario(wbb, pm);
				Thread.sleep(150);

				splash.setProgress(45, "Configurando navegação...");
				Navegador navegador = new Navegador(prim);
				navegador.setUsuarioDAO(usuarioDAO);
				prim.setNavegador(navegador);
				pm.setNavegador(navegador);
				Thread.sleep(100);

				splash.setProgress(55, "Criando fábrica de telas...");
				TelaFactory telaFactory = new TelaFactory(navegador, servicoDAO, usuarioDAO);
				pm.setTelaFactory(telaFactory);
				Thread.sleep(100);
				
				splash.setProgress(60, "Inicializando controladores...");
				PopupController popup = new PopupController();

				TelaLogin telalogin = new TelaLogin();
				TelaCadastro telacadastro = new TelaCadastro();
				PopupMenuController popup2 = new PopupMenuController(pm, navegador, telaFactory);
				WBController wbController = new WBController(wbb, usuarioDAO, navegador, popup, popup2);
				Thread.sleep(150);

				splash.setProgress(70, "Configurando tela de login...");
				LoginController logincontroller = new LoginController(telalogin, usuarioDAO, navegador, telacadastro, popup2);
				CadastroController cadastrocontroller = new CadastroController(telacadastro, usuarioDAO, navegador);
				Thread.sleep(100);

				splash.setProgress(75, "Configurando tela do contratante...");
				TelaContratante telacontratante = new TelaContratante();
				ContratanteController contratanteController = new ContratanteController(telacontratante, usuarioDAO, navegador);
				Thread.sleep(100);

				splash.setProgress(80, "Configurando tela do contratado...");
				TelaContratado telacontratado = new TelaContratado();
				ContratadoController contratadocontroller = new ContratadoController(telacontratado, usuarioDAO, navegador, servicoDAO, telaFactory);
				telacontratado.adicionarOuvinte(contratadocontroller);
				Thread.sleep(100);
				
				splash.setProgress(85, "Configurando cadastros...");
				TelaCadastroContratante telacadastrocontratante = new TelaCadastroContratante();
				CadastroContratanteController cadastrocontratantecontroller = new CadastroContratanteController(telacadastrocontratante, servicoDAO, navegador);
				
				Temp temp = new Temp();
				TempController tempcontroller = new TempController(temp, usuarioDAO, navegador);

				TelaAdm telaadm = new TelaAdm();
				Thread.sleep(100);
				
				splash.setProgress(90, "Configurando lista de serviços...");
				TelaListaServicos telaservicos = new TelaListaServicos();
				ListaServicosController controller = new ListaServicosController(telaservicos, servicoDAO, navegador, telaFactory);
				telaservicos.setOnShow(() -> controller.atualizarTabelaServicosDoUsuario());
				Thread.sleep(100);

				splash.setProgress(95, "Registrando telas...");
				navegador.adicionarPainel("LOGIN", telalogin);
				navegador.adicionarPainel("CADASTRO", telacadastro);
				navegador.adicionarPainel("CONTRATANTE", telacontratante);
				navegador.adicionarPainel("CONTRATADO", telacontratado);
				navegador.adicionarPainel("TEMP", temp);
				navegador.adicionarPainel("ADM", telaadm);
				navegador.adicionarPainel("CADASTRO_CONTRATANTE", telacadastrocontratante);
				navegador.adicionarPainel("SERVICOS", telaservicos);
				Thread.sleep(100);

				splash.setProgress(98, "Finalizando...");
				navegador.navegarPara("login");
				Thread.sleep(200);
				
				splash.setProgress(100, "Pronto!");
				Thread.sleep(300);
				
				// Mostra a janela principal na EDT
				SwingUtilities.invokeLater(() -> {
					prim.setVisible(true);
					// Força o layout completo antes de fechar o splash
					prim.revalidate();
					prim.repaint();
					
					// Aguarda um pouco para garantir que a janela foi renderizada
					SwingUtilities.invokeLater(() -> {
						splash.closeSplash();
					});
				});
				
			} catch (Exception e) {
				e.printStackTrace();
				splash.closeSplash();
			}
		}).start();
	}
}