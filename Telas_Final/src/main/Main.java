package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.ContratadoController;
import controller.ContratanteController;
import controller.ListaServicosController;
import controller.LoginController;
import controller.PopupController;
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
				controller.Navegador navegador = new controller.Navegador(prim);
				navegador.setUsuarioDAO(usuarioDAO);
				prim.setNavegador(navegador);
				pm.setNavegador(navegador);
				Thread.sleep(100);

				splash.setProgress(55, "Criando fábrica de telas...");
				controller.TelaFactory telaFactory = new controller.TelaFactory(navegador, servicoDAO, usuarioDAO);
				pm.setTelaFactory(telaFactory);
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
				controller.CadastroContratanteController cadastrocontratantecontroller = new controller.CadastroContratanteController(telacadastrocontratante, servicoDAO, navegador);

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
				navegador.adicionarPainel("ADM", telaadm);
				navegador.adicionarPainel("CADASTRO_CONTRATANTE", telacadastrocontratante);
				navegador.adicionarPainel("SERVICOS", telaservicos);
				Thread.sleep(100);

				splash.setProgress(98, "Finalizando...");
				navegador.navegarPara("login");
				Thread.sleep(100);

				splash.setProgress(100, "Preparando interface...");
				
				// PRÉ-INICIALIZAÇÃO: Constrói tudo ANTES de mostrar
				// Isso evita redimensionamentos visíveis
				SwingUtilities.invokeAndWait(() -> {
					// Força todos os layouts e cálculos de tamanho
					prim.preinicializar();
				});
				
				Thread.sleep(300); // Aumentado para garantir renderização completa
				
				// Mostra a janela principal já completamente renderizada
				SwingUtilities.invokeLater(() -> {
					prim.setVisible(true);
					prim.toFront();
					prim.requestFocus();
					
					// Habilita os listeners de resize SOMENTE após a janela estar visível
					wbb.habilitarResizeListeners();
				});
				
				// Aguarda a janela estar completamente visível
				Thread.sleep(150);
				
				// Fecha a splash DEPOIS que a janela principal está pronta
				splash.closeSplash(250);
				
			} catch (Exception e) {
				e.printStackTrace();
				splash.closeSplash();
			}
		}).start();
	}
}