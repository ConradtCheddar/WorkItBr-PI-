// Define o pacote onde esta classe está localizada
package controller;

// Importa a classe Servico que representa um serviço no sistema
import model.Servico;
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
 * Controller responsável por exibir informações do contratado relacionadas a um serviço.
 * <p>
 * Quando acionado, recupera o usuário contratado a partir do serviço e cria uma
 * tela de visualização do contratado, registrando um listener para o botão
 * Voltar que retorna à tela de serviço aceito (quando aplicável).
 * </p>
 */
public class VisContratadoController {
	// Referência à camada de visualização (interface gráfica) da tela de serviço aceito
	private final VisServicoCnteAceito view;
	// Referência ao navegador que controla a navegação entre telas
	private final Navegador navegador;
	// Referência ao serviço que contém informações do contratado
	private final Servico s;

	/**
	 * Construtor que inicializa o controller e configura os listeners.
	 * 
	 * @param view referência à tela de visualização de serviço aceito
	 * @param navegador objeto que controla a navegação
	 * @param s objeto do serviço que contém o ID do contratado
	 */
	public VisContratadoController(VisServicoCnteAceito view, Navegador navegador, Servico s){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		// Atribui a referência do serviço recebido ao atributo da classe
		this.s= s;
		
		// Ação que abre a visualização do contratado responsável pelo serviço
		// Configura o listener do botão "contratante" (que mostra o contratado)
		this.view.contratante(e ->{
			// Obtém o ID do contratado a partir do serviço
			int idContratado = s.getIdContratado();
			// Verifica se o ID do contratado é válido (maior que zero)
			if (idContratado <= 0) {
				// Exibe aviso se o contratado não está definido
				JOptionPane.showMessageDialog(null, "Contratado não definido para este serviço.", "Aviso", JOptionPane.WARNING_MESSAGE);
				// Interrompe a execução do método
				return;
			}
			// Busca o usuário no banco e cria a tela do contratado
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
			// Gera o nome do painel baseado no ID do contratado
			String panelName = "VIS_CONTRATADO_" + contratado.getIdUsuario();
			// Cria a view de visualização do perfil do contratado
			VisContratado tela = new VisContratado(contratado);
			// Adiciona o painel ao navegador para que possa ser exibido
			navegador.adicionarPainel(panelName, tela);
			// Navega para a tela criada
			navegador.navegarPara(panelName);
			// Configura botão voltar da tela do contratado para retornar à tela do serviço aceito
			// Obtém o ID do serviço para construir o nome do painel de retorno
			int idServicoLocal = s.getIdServico();
			// Define o nome do painel de retorno (tela de serviço aceito ou SERVICOS como fallback)
			String prevPanelLocal = (idServicoLocal > 0) ? ("VIS_Servico_Cnte_Aceito_" + idServicoLocal) : "SERVICOS";
			// Configura o listener do botão voltar da tela do contratado
			tela.voltar(ev -> {
				// Navega para o painel de retorno quando o botão voltar é clicado
				navegador.navegarPara(prevPanelLocal);
			});
		});
		
		// Ação de voltar da view atual: retorna para a lista de serviços
		// Configura o listener do botão "voltar" na view atual
		this.view.voltar(e ->{
			// Navega para a tela de lista de serviços
			navegador.navegarPara("SERVICOS");
		});
		
	} // Fim do construtor VisContratadoController
} // Fim da classe VisContratadoController