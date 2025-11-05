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
	 * Atualiza ações relacionadas ao botão de perfil do `DrawerMenu`.
	 * <p>
	 * Garante que o `DrawerMenu` tenha a referência correta do `Navegador` para
	 * que suas ações (abrir configurações do usuário, logout, etc.) funcionem
	 * corretamente quando o usuário faz login/out ou quando o contexto muda.
	 * </p>
	 */
	public void updateProfileAction() {
		// Garante que listeners do DrawerMenu apontem para o navegador atual
		// Define o navegador na view para que ela possa executar ações de navegação
		view.setNavegador(navegador);
	}

} // Fim da classe PopupMenuController