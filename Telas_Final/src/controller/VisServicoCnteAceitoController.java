// Define o pacote onde esta classe está localizada
package controller;

// Importa a classe Servico que representa um serviço no sistema
import model.Servico;
// Importa ServicoDAO, responsável por operações de banco de dados relacionadas a serviços
import model.ServicoDAO;
// Importa a view de visualização de serviço do contratante (não utilizada, mas mantida)
import view.VisServicoCnte;
// Importa a view de visualização de serviço aceito do contratante
import view.VisServicoCnteAceito;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a classe Usuario que representa um usuário no sistema
import model.Usuario;
// Importa a view de visualização de perfil do contratado
import view.VisContratado;
// Importa JOptionPane para exibir diálogos de mensagem ao usuário
import javax.swing.JOptionPane;

/**
 * Controller para a visualização de um serviço aceito na perspectiva do contratante.
 * <p>
 * Permite que o contratante veja o contratado responsável pelo serviço e
 * navegue para a tela do contratado via {@link TelaFactory}. Também fornece
 * a ação de voltar para a lista de serviços.
 * </p>
 */
public class VisServicoCnteAceitoController {
	// Referência à camada de visualização (interface gráfica) da tela de serviço aceito
	private final VisServicoCnteAceito view;
	// Referência ao DAO que gerencia operações de banco de dados para serviços
	private final ServicoDAO model;
	// Referência ao navegador que controla a navegação entre telas
	private final Navegador navegador;
	// Referência ao serviço que está sendo visualizado
	private final Servico s;
	// Referência à fábrica de telas que cria visualizações dinâmicas
	private final TelaFactory telaFactory;

	/**
	 * Construtor que inicializa o controller e configura os listeners.
	 * 
	 * @param view referência à tela de visualização de serviço aceito
	 * @param model referência ao DAO de serviços
	 * @param navegador objeto que controla a navegação
	 * @param s objeto do serviço sendo visualizado
	 * @param telaFactory fábrica de telas para criar visualizações dinâmicas
	 */
	public VisServicoCnteAceitoController(VisServicoCnteAceito view, ServicoDAO model, Navegador navegador, Servico s, TelaFactory telaFactory){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		// Atribui a referência do serviço recebido ao atributo da classe
		this.s = s;
		// Atribui a referência da fábrica de telas recebida ao atributo da classe
		this.telaFactory = telaFactory;
		
		// Ao clicar no botão que mostra o contratado, busca o usuário pelo id e navega
		// Configura o listener do botão "contratante" (que na verdade mostra o contratado)
		this.view.contratante(e ->{
			// obtém o id do contratado a partir do serviço
			// Obtém o ID do contratado responsável pelo serviço
			int idContratado = s.getIdContratado();
			// Verifica se o ID do contratado é válido (maior que zero)
			if (idContratado <= 0) {
				// Exibe aviso se o contratado não está definido
				JOptionPane.showMessageDialog(null, "Contratado não definido para este serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);
				// Interrompe a execução do método
				return;
			}
			// Busca o usuário no banco
			// Cria uma instância do DAO de usuários
			UsuarioDAO udao = new UsuarioDAO();
			// Busca o usuário contratado no banco de dados pelo ID
			Usuario contratado = udao.getUsuarioById(idContratado);
			// Verifica se o usuário foi encontrado no banco
			if (contratado == null) {
				// Exibe mensagem de erro se o usuário não foi encontrado
				JOptionPane.showMessageDialog(null, "Usuário contratado não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
				// Interrompe a execução do método
				return;
			}
			// Define a tela de retorno (painel atual ou fallback para SERVICOS)
			// Obtém o ID do serviço
			int idServico = s.getIdServico();
			// Define o nome do painel de retorno (tela atual ou SERVICOS como fallback)
			String prevPanel = (idServico > 0) ? ("VIS_SERVICO_CNTE_ACEITO_" + idServico) : "SERVICOS";
			
			// Usa TelaFactory para criar e navegar até a tela do contratado
			// Cria a tela de visualização do contratado usando a factory
			String panelName = telaFactory.criarVisContratado(contratado, prevPanel);
			// Navega para a tela criada
			navegador.navegarPara(panelName);
		});
		
		// Ação de voltar: retorna para a lista de serviços
		// Configura o listener do botão "voltar" na view
		this.view.voltar(e ->{
			// Navega para a tela de lista de serviços
			navegador.navegarPara("SERVICOS");
		});
		
	} // Fim do construtor VisServicoCnteAceitoController
} // Fim da classe VisServicoCnteAceitoController