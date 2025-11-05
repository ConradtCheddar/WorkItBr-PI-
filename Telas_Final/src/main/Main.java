package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.ContratadoController;
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
import view.TelaListaServicos;
import view.TelaLogin;
import view.wbBarra;

/**
 * Classe de inicialização da aplicação.
 * <p>
 * Responsável por montar a sequência de inicialização que: mostra a splash,
 * inicializa recursos (tema, DAOs, componentes visuais), registra controladores
 * e telas no navegador, pré-calcula layouts para evitar flicker e, por fim,
 * exibe a janela principal enquanto fecha a splash de forma suave.
 * </p>
 * <p>
 * A inicialização é executada em uma thread separada para manter a splash
 * responsiva. Apenas a preinicialização de layout é feita via
 * {@link SwingUtilities#invokeAndWait} para garantir que todos os cálculos de
 * layout do Swing sejam realizados antes de mostrar a janela principal.
 * </p>
 */
public class Main {
	public static void main(String[] args) {
		// Mostra a splashscreen imediatamente para feedback visual ao usuário.
		SplashScreen splash = new SplashScreen();
		splash.setVisible(true);
		
		// Inicialização pesada em thread separada para não travar a splash.
		new Thread(() -> {
			try {
				// --- Tema / Look & Feel ---
				splash.setProgress(5, "Carregando tema...");
				try {
					UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
				} catch (Exception ex) {
					// Log mínimo: se o tema falhar, seguimos com o L&F padrão
					System.err.println("Falha ao carregar o tema FlatLaf");
				}
				Thread.sleep(200);
				
				// --- Componentes base ---
				splash.setProgress(15, "Inicializando componentes...");
				wbBarra wbb = new wbBarra();
				Thread.sleep(150);
				
				// --- Conexões / DAOs ---
				splash.setProgress(25, "Conectando ao banco de dados...");
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				ServicoDAO servicoDAO = new ServicoDAO();
				Thread.sleep(200);
				
				// --- Interface principal ---
				splash.setProgress(35, "Criando interface principal...");
				DrawerMenu pm = new DrawerMenu(usuarioDAO); // menu lateral com contexto do usuário
				Primario prim = new Primario(wbb, pm); // janela principal que recebe a barra e o menu
				Thread.sleep(150);

				// --- Navegação ---
				splash.setProgress(45, "Configurando navegação...");
				controller.Navegador navegador = new controller.Navegador(prim);
				navegador.setUsuarioDAO(usuarioDAO);
				prim.setNavegador(navegador);
				pm.setNavegador(navegador);
				Thread.sleep(100);

				// --- Fábrica de telas ---
				splash.setProgress(55, "Criando fábrica de telas...");
				controller.TelaFactory telaFactory = new controller.TelaFactory(navegador, servicoDAO, usuarioDAO);
				pm.setTelaFactory(telaFactory);
				Thread.sleep(100);
				
				// --- Controladores auxiliares / Popups ---
				splash.setProgress(60, "Inicializando controladores...");
				PopupController popup = new PopupController();

				// Telas básicas
				TelaLogin telalogin = new TelaLogin();
				TelaCadastro telacadastro = new TelaCadastro();

				// Controller de menu de contexto (popup) que interage com o DrawerMenu
				controller.PopupMenuController popup2 = new controller.PopupMenuController(pm, navegador, telaFactory);
				controller.WBController wbController = new controller.WBController(wbb, usuarioDAO, navegador, popup, popup2);
				Thread.sleep(150);

				// --- Login / Cadastro ---
				splash.setProgress(70, "Configurando tela de login...");
				LoginController logincontroller = new LoginController(telalogin, usuarioDAO, navegador, telacadastro, popup2);
				controller.CadastroController cadastrocontroller = new controller.CadastroController(telacadastro, usuarioDAO, navegador);
				Thread.sleep(100);

				// --- Contratado / Contratante ---
				splash.setProgress(80, "Configurando tela do contratado...");
				TelaContratado telacontratado = new TelaContratado();
				ContratadoController contratadocontroller = new ContratadoController(telacontratado, usuarioDAO, navegador, servicoDAO, telaFactory);
				telacontratado.adicionarOuvinte(contratadocontroller); // registra listeners do contratado
				Thread.sleep(100);
				
				splash.setProgress(85, "Configurando cadastros...");
				TelaCadastroContratante telacadastrocontratante = new TelaCadastroContratante();
				controller.CadastroContratanteController cadastrocontratantecontroller = new controller.CadastroContratanteController(telacadastrocontratante, servicoDAO, navegador);

				TelaAdm telaadm = new TelaAdm();
				Thread.sleep(100);
				
				// --- Lista de serviços ---
				splash.setProgress(90, "Configurando lista de serviços...");
				TelaListaServicos telaservicos = new TelaListaServicos();
				ListaServicosController controller = new ListaServicosController(telaservicos, servicoDAO, navegador, telaFactory);
				// Atualiza a tabela sempre que a tela de serviços é mostrada
				telaservicos.setOnShow(() -> controller.atualizarTabelaServicosDoUsuario());
				Thread.sleep(100);

				// --- Registro das telas no navegador ---
				splash.setProgress(95, "Registrando telas...");
				navegador.adicionarPainel("LOGIN", telalogin);
				navegador.adicionarPainel("CADASTRO", telacadastro);
				navegador.adicionarPainel("CONTRATADO", telacontratado);
				navegador.adicionarPainel("ADM", telaadm);
				navegador.adicionarPainel("CADASTRO_CONTRATANTE", telacadastrocontratante);
				navegador.adicionarPainel("SERVICOS", telaservicos);
				Thread.sleep(100);

				splash.setProgress(98, "Finalizando...");
				navegador.navegarPara("LOGIN");
				Thread.sleep(100);

				splash.setProgress(100, "Preparando interface...");
				
				// PRÉ-INICIALIZAÇÃO: força cálculo de todos os layouts antes de mostrar
				// Isso reduz mudanças de tamanho visíveis ao usuário quando a janela aparece.
				SwingUtilities.invokeAndWait(() -> {
					prim.preinicializar();
				});
				
				Thread.sleep(300); // pequena pausa para garantir renderização
				
				// Exibe a janela principal no Event Dispatch Thread
				SwingUtilities.invokeLater(() -> {
					prim.setVisible(true);
					prim.toFront();
					prim.requestFocus();
					// Habilita listeners de redimensionamento da barra somente após visibilidade
					wbb.habilitarResizeListeners();
				});
				
				// Aguarda janela ficar visível antes de fechar a splash
				Thread.sleep(150);
				
				// Fecha a splash com animação/timeout (250ms)
				splash.closeSplash(250);
				
			} catch (Exception e) {
				// Em caso de erro durante a inicialização, registra a pilha e fecha a splash
				e.printStackTrace();
				splash.closeSplash();
			}
		}).start();
	}
}