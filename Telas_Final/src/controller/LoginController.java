// Define o pacote onde esta classe está localizada
package controller;

// Importa MouseAdapter para criar listeners de eventos de mouse personalizados
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;
import java.util.Arrays;

// Importa JOptionPane para exibir diálogos de mensagem ao usuário
import javax.swing.JOptionPane;

// Importa a classe Usuario que representa um usuário no sistema
import model.Usuario;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a view da tela principal (container principal da aplicação)
import view.Primario;
// Importa a view da tela de login
import view.TelaLogin;
// Importa a view da tela de cadastro
import view.TelaCadastro;

/**
 * Controller responsável pela lógica de login e transições relacionadas.
 * <p>
 * Escuta ações da `TelaLogin`, valida credenciais via {@link UsuarioDAO},
 * atualiza o usuário corrente no {@link Navegador} e direciona a aplicação
 * para a tela apropriada (admin / contratado / contratante).
 * </p>
 */
public class LoginController {
	// Referência à camada de visualização (interface gráfica) da tela de login
	private final TelaLogin view;
	// Referência ao DAO que gerencia operações de banco de dados para usuários
	private final UsuarioDAO model;
	// Referência ao objeto que controla a navegação entre telas
	private final Navegador navegador;
	// Referência à tela de cadastro (usada para navegação)
	private final TelaCadastro telaCadastro;
	// Referência ao controller do menu popup (para atualizar perfil após login)
	private final PopupMenuController popupMenuController;
	
	/**
	 * Construtor que inicializa o controller e configura os listeners.
	 * 
	 * @param view referência à tela de login
	 * @param model referência ao DAO de usuários
	 * @param navegador objeto que controla a navegação entre telas
	 * @param telaCadastro referência à tela de cadastro
	 * @param popupMenuController controller do menu popup
	 */
	public LoginController(TelaLogin view, UsuarioDAO model, Navegador navegador, TelaCadastro telaCadastro, PopupMenuController popupMenuController){
		this.view = view;
		this.model = model;
		this.navegador = navegador;
		this.telaCadastro = telaCadastro;
		this.popupMenuController = popupMenuController;
		
		initListeners();
	}

	/**
	 * Inicializa os listeners de eventos da tela de login.
	 */
	private void initListeners() {
		// Registra ação de login
		this.view.login(e -> handleLoginAttempt());

		// Listener para o link de cadastro
		this.view.cadastro(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        telaCadastro.limparCampos();
		        navegador.navegarPara("CADASTRO");
		    }
		});
	}

	/**
	 * Gerencia a tentativa de login, validando credenciais e tratando possíveis erros.
	 */
	private void handleLoginAttempt() {
		String nome = view.getUsuario();
		char[] senha = view.getSenha();

		// Verifica se os campos não estão vazios
		if (nome.trim().isEmpty() || senha.length == 0) {
			JOptionPane.showMessageDialog(view, "Usuário e senha não podem estar em branco.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
			view.limparFormulario();
			Arrays.fill(senha, ' '); // Limpa a senha da memória
			return;
		}

		try {
			Usuario u = model.login(nome, senha);
			
			if (u != null) {
				handleSuccessfulLogin(u);
			} else {
				handleFailedLogin();
			}
		} catch (Exception e) {
			handleLoginError(e);
		} finally {
			// Limpa a senha da memória por segurança
			Arrays.fill(senha, ' ');
			// Limpa os campos do formulário na view
			this.view.limparFormulario();
		}
	}

	/**
	 * Executa as ações necessárias após um login bem-sucedido.
	 * @param user O usuário autenticado.
	 */
	private void handleSuccessfulLogin(Usuario user) {
		model.decode64(user);
		this.navegador.setCurrentUser(user);
		popupMenuController.updateProfileAction();
		this.navegador.notifyHistoryChange();
		navigateToUserScreen(user);
	}

	/**
	 * Navega para a tela apropriada com base no perfil do usuário.
	 * @param user O usuário autenticado.
	 */
	private void navigateToUserScreen(Usuario user) {
		String destination;
		if (user.isAdmin()) {
			destination = "ADM";
		} else if (user.isContratado()) {
			destination = "CONTRATADO";
		} else if (user.isContratante()) {
			destination = "SERVICOS";
		} else {
			destination = "LOGIN"; // Fallback para a tela de login
		}
		navegador.navegarPara(destination, false);
	}

	/**
	 * Exibe uma mensagem de falha de autenticação.
	 */
	private void handleFailedLogin() {
		JOptionPane.showMessageDialog(view, "Usuário ou senha incorretos.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Exibe uma mensagem de erro genérica para falhas de conexão ou outras exceções.
	 * @param e A exceção que ocorreu.
	 */
	private void handleLoginError(Exception e) {
		// O ideal seria logar o erro para análise posterior
		e.printStackTrace(); 
		JOptionPane.showMessageDialog(view, "Ocorreu um erro de comunicação com o sistema. Tente novamente mais tarde.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
	}
} // Fim da classe LoginController