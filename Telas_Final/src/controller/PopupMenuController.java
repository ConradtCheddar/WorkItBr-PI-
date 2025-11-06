// Define o pacote onde esta classe está localizada
package controller;

// Importa a view do menu lateral (drawer)
import view.DrawerMenu;
// Importa a view da tela de configuração de usuário
import view.TelaConfigUser;
// Importa o controller da tela de configuração de usuário
import controller.TelaConfigUserController;
// Importa a classe Usuario que representa um usuário no sistema
import model.Usuario;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
import javax.swing.JOptionPane;

/**
 * Controller responsável por ações relacionadas ao menu de perfil exibido no DrawerMenu.
 * <p>
 * Mantém referência ao `DrawerMenu` (view), ao `Navegador` e à `TelaFactory` para
 * permitir que a UI de perfil seja aberta ou atualizada a partir do menu lateral.
 * </p>
 */
public class PopupMenuController {
	// Referência à view do menu lateral (drawer)
	private final DrawerMenu view;
	// Referência ao navegador que controla a navegação entre telas
	private final Navegador navegador;
	// Referência à fábrica de telas que cria instâncias de visualizações
	private final TelaFactory telaFactory;
	private boolean listenersInitialized = false;
	
	/**
	 * Construtor que inicializa o controller com suas dependências.
	 * 
	 * @param view referência ao DrawerMenu (menu lateral)
	 * @param navegador objeto que controla a navegação
	 * @param telaFactory fábrica de telas
	 */
	public PopupMenuController(DrawerMenu view, Navegador navegador, TelaFactory telaFactory){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		// Atribui a referência da fábrica de telas recebida ao atributo da classe
		this.telaFactory = telaFactory;
		// O view receberá o navegador quando necessário via updateProfileAction()
		updateMenuState(); // Define o estado inicial dos botões
	}
	
	/**
	 * Obtém a referência da view do DrawerMenu.
	 * 
	 * @return instância do DrawerMenu
	 */
	public DrawerMenu getView(){
		// Retorna a referência da view
		return view;
	}

	/**
	 * Inicializa os listeners para os botões do menu lateral uma única vez.
	 */
	private void initializeListeners() {
		if (listenersInitialized) {
			return;
		}

		// Ação do botão Home
		view.getBtnHome().addActionListener(e -> {
			navigateToHome();
			view.toggleMenu();
		});

		// Ação do botão de Perfil
		view.getBtnProfile().addActionListener(e -> {
			navigateToProfile();
			view.toggleMenu();
		});

		// Ação do botão Trabalhos
		view.getBtnTrabalhos().addActionListener(e -> {
			navigateToTrabalhos();
			view.toggleMenu();
		});
		
		// Ação do botão de Configurações (Settings)
		view.getBtnSettings().addActionListener(e -> {
			navegador.navegarPara("TEMP");
			view.toggleMenu();
		});

		// Ação do botão de Logout
		view.getBtnLogout().addActionListener(e -> {
			performLogout();
			view.toggleMenu();
		});

		listenersInitialized = true;
	}

	/**
	 * Atualiza o estado dos botões do menu (ativado/desativado) com base no
	 * status de login do usuário.
	 */
	public void updateMenuState() {
		Usuario currentUser = navegador.getCurrentUser();
		boolean isLoggedIn = currentUser != null;

		view.getBtnProfile().setEnabled(isLoggedIn);
		view.getBtnLogout().setEnabled(isLoggedIn);
		view.getBtnTrabalhos().setEnabled(isLoggedIn);
		view.getBtnHome().setEnabled(isLoggedIn);
	}
	
	/**
	 * Navega para a tela inicial apropriada com base no perfil do usuário.
	 */
	private void navigateToHome() {
		Usuario u = navegador.getCurrentUser();
		if (u != null) {
			if (u.isAdmin()) {
				navegador.navegarPara("ADM");
			} else if (u.isContratante()) {
				navegador.navegarPara("SERVICOS");
			} else if (u.isContratado()) {
				navegador.navegarPara("CONTRATADO");
			} else {
				navegador.navegarPara("LOGIN"); // Fallback
			}
		}
	}
	
	/**
	 * Navega para a tela de configuração de perfil do usuário.
	 */
	private void navigateToProfile() {
		Usuario usuario = navegador.getCurrentUser();
		if (usuario != null) {
			String panelName = telaFactory.criarTelaConfigUser(usuario);
			navegador.navegarPara(panelName);
		} else {
			JOptionPane.showMessageDialog(view, "Nenhum usuário logado.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Navega para a tela de trabalhos apropriada (Serviços ou Contratado).
	 */
	private void navigateToTrabalhos() {
		Usuario u = navegador.getCurrentUser();
		if (u != null) {
			if (u.isContratante()) {
				navegador.navegarPara("SERVICOS");
			} else if (u.isContratado()) {
				navegador.navegarPara("CONTRATADO");
			} else {
				// Para admin ou outros tipos, pode ir para uma tela padrão ou TEMP
				navegador.navegarPara("TEMP");
			}
		}
	}
	
	/**
	 * Executa o processo de logout do usuário.
	 */
	private void performLogout() {
		navegador.clearCurrentUser();
		telaFactory.limparCache();
		navegador.removerPainel("CONFIG_USER");
		navegador.clearHistory();
		navegador.limparImagensPerfil();
		navegador.navegarPara("LOGIN", false);
		updateMenuState(); // Atualiza os botões para o estado "deslogado"
	}
	
	/**
	 * Atualiza o menu após uma mudança de estado de login (login/logout).
	 * Garante que os listeners estejam inicializados e o estado dos botões
	 * seja o correto.
	 */
	public void updateProfileAction() {
		initializeListeners();
		updateMenuState();
	}

} // Fim da classe PopupMenuController