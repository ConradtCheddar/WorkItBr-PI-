// Define o pacote onde esta classe está localizada
package controller;

// Importa MouseAdapter para criar listeners de eventos de mouse personalizados
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;

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
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		// Atribui a referência da tela de cadastro recebida ao atributo da classe
		this.telaCadastro = telaCadastro;
		// Atribui a referência do controller do menu popup recebido ao atributo da classe
		this.popupMenuController = popupMenuController;
		
		// Registra ação de login: obtém usuário/senha, autentica e navega conforme o perfil
		// Configura o listener do botão "login" na view
		this.view.login(e ->{
			// Obtém o nome de usuário digitado no campo de texto
			String nome = view.getUsuario();
			// Obtém a senha digitada no campo de senha
			String senha = view.getSenha();

			// Cria uma nova instância do DAO de usuários
			UsuarioDAO dao = new UsuarioDAO();
			// Tenta autenticar o usuário no banco de dados
			Usuario u = dao.login(nome, senha);
			
			// Verifica se a autenticação foi bem-sucedida (usuário encontrado)
			if (u != null) {
				// Se autenticação for bem-sucedida: decodifica e salva imagem de perfil localmente
				// Decodifica a imagem de perfil que está em Base64 e salva como arquivo
				dao.decode64(u);
				// Define o usuário autenticado como usuário atual no navegador
				this.navegador.setCurrentUser(u);
				// Atualiza a ação de perfil no menu popup com os dados do usuário logado
				popupMenuController.updateProfileAction();
				// Atualiza botões/menus dependentes do estado de autenticação
				// Notifica que o estado de navegação mudou (para atualizar botões voltar/avançar)
				this.navegador.notifyHistoryChange();
				// Direciona conforme tipo de usuário
				// Verifica se o usuário é administrador
				if (u.isAdmin()) {
					// Navega para a tela de administrador sem adicionar ao histórico
					navegador.navegarPara("ADM", false);
				} else if (u.isContratado()) {
					// Verifica se o usuário é contratado
					// Navega para a tela de contratado sem adicionar ao histórico
					navegador.navegarPara("CONTRATADO", false);
				} else if (u.isContratante()) {
					// Verifica se o usuário é contratante
					// Navega para a tela de serviços (lista de serviços) sem adicionar ao histórico
					navegador.navegarPara("SERVICOS", false);
				} else {
					// Se o usuário não tem nenhum perfil definido, retorna para o login
					navegador.navegarPara("LOGIN", false);
				}
			}else {
				// Mensagem simples para falha de autenticação
				// Se a autenticação falhou, exibe mensagem de erro
				JOptionPane.showMessageDialog(null, "Usuario ou senha incorretos", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			// Limpa os campos de usuário e senha do formulário
			this.view.limparFormulario();
	
		});

		// Listener para o link de cadastro na tela de login
		// Configura um listener de mouse para o link/botão de cadastro
		this.view.cadastro(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Limpa todos os campos da tela de cadastro para começar um novo cadastro limpo
		        telaCadastro.limparCampos();
		        // Navega para a tela de cadastro
		        navegador.navegarPara("CADASTRO");
		    }
		});
	} // Fim do construtor LoginController
	

} // Fim da classe LoginController