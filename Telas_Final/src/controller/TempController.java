// Define o pacote onde esta classe está localizada
package controller;

// Importa MouseAdapter para criar listeners de eventos de mouse personalizados
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;

// Importa a classe Usuario que representa um usuário no sistema
import model.Usuario;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a view da tela de cadastro
import view.TelaCadastro;
// Importa a view da tela temporária (para testes/debug)
import view.Temp;

/**
 * Controller auxiliar usado em telas temporárias de navegação (debug/atalhos).
 * <p>
 * Fornece ações rápidas para navegar entre telas (login, adm, cadastro contratado,
 * contratado) e limpar imagens de perfil quando necessário.
 * </p>
 */
public class TempController {
	// Referência à camada de visualização (interface gráfica) da tela temporária
	private final Temp view;
	// Referência ao DAO que gerencia operações de banco de dados para usuários
	private final UsuarioDAO model;
	// Referência ao navegador que controla a navegação entre telas
	private final Navegador navegador;

	/**
	 * Construtor que inicializa o controller e configura os atalhos de navegação.
	 * 
	 * @param view referência à tela temporária
	 * @param model referência ao DAO de usuários
	 * @param navegador objeto que controla a navegação
	 */
	public TempController(Temp view, UsuarioDAO model, Navegador navegador){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		
		// Link para voltar ao login (limpa imagens temporárias de perfil)
		// Configura o listener do botão/link "login"
		this.view.login(e ->{
			// Limpa todos os arquivos temporários de imagens de perfil
			navegador.limparImagensPerfil();
			// Navega para a tela de login
			navegador.navegarPara("LOGIN");
		});
		// Atalhos para outras telas usadas em desenvolvimento
		// Configura o listener do botão/link "adm"
		this.view.adm(e ->{
			// Navega para a tela de administrador
			navegador.navegarPara("ADM");
		});
		// Configura o listener do botão/link "contratante"
		this.view.contratante(e ->{
			// Navega para a tela de cadastro de serviço (contratante)
			navegador.navegarPara("CADASTRO_CONTRATANTE");
		});
		// Configura o listener do botão/link "contratado"
		this.view.contratado(e ->{
			// Navega para a tela do contratado
			navegador.navegarPara("CONTRATADO");
		});
		
	} // Fim do construtor TempController
} // Fim da classe TempController