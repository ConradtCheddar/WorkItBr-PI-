// Define o pacote onde esta classe está localizada
package controller;

// Importa JOptionPane para exibir diálogos de mensagem ao usuário
import javax.swing.JOptionPane;

// Importa a classe Servico que representa um serviço no sistema
import model.Servico;
// Importa ServicoDAO, responsável por operações de banco de dados relacionadas a serviços
import model.ServicoDAO;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a view de visualização de serviço (não utilizada, mas mantida por compatibilidade)
import view.VisServico;
// Importa a view de visualização de serviço em andamento
import view.VisServicoAndamento;

/**
 * Controller para visualização de serviços em andamento (aceitos).
 * <p>
 * Trata ações como finalizar o serviço: remove o serviço via DAO e
 * navega de volta para a tela do contratado exibindo confirmação.
 * </p>
 */
public class VisServicoAndamentoController {
	// Referência à camada de visualização (interface gráfica) da tela de serviço em andamento
	private final VisServicoAndamento view;
	// Referência ao DAO que gerencia operações de banco de dados para serviços
	private final ServicoDAO model;
	// Referência ao navegador que controla a navegação entre telas
	private final Navegador navegador;
	// Referência ao serviço que está sendo visualizado
	private final Servico s;

	/**
	 * Construtor que inicializa o controller e configura os listeners.
	 * 
	 * @param view referência à tela de visualização de serviço em andamento
	 * @param model referência ao DAO de serviços
	 * @param navegador objeto que controla a navegação
	 * @param s objeto do serviço sendo visualizado
	 */
	public VisServicoAndamentoController(VisServicoAndamento view, ServicoDAO model, Navegador navegador, Servico s){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		// Atribui a referência do serviço recebido ao atributo da classe
		this.s= s;
		
		// Ao finalizar serviço: exclui via DAO e notifica usuário
		// Configura o listener do botão "finalizar" na view
		this.view.finalizar(e -> {
			// Remove o serviço do banco de dados pelo ID
			model.deletarServico(s.getIdServico());
			// Exibe mensagem de confirmação ao usuário
			JOptionPane.showMessageDialog(null, "Serviço finalizado com sucesso!");
			// Navega de volta para a tela do contratado
			navegador.navegarPara("CONTRATADO");
		});
		
	} // Fim do construtor VisServicoAndamentoController
} // Fim da classe VisServicoAndamentoController