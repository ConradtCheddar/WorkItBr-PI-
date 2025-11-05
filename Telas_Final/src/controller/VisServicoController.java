// Define o pacote onde esta classe está localizada
package controller;

// Importa a classe Servico que representa um serviço no sistema
import model.Servico;
// Importa ServicoDAO, responsável por operações de banco de dados relacionadas a serviços
import model.ServicoDAO;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a view de visualização de serviço
import view.VisServico;

/**
 * Controller da visualização de um serviço para o contratado.
 * <p>
 * Fornece ação para aceitar um serviço: valida usuário logado, recupera a
 * entidade atualizada do banco (preferencialmente por id) e marca o serviço
 * como aceito atribuindo o contratado atual antes de persistir a mudança.
 * </p>
 */
public class VisServicoController {
	// Referência à camada de visualização (interface gráfica) da tela de visualização de serviço
	private final VisServico view;
	// Referência ao DAO que gerencia operações de banco de dados para serviços
	private final ServicoDAO model;
	// Referência ao navegador que controla a navegação entre telas
	private final Navegador navegador;
	// Referência ao serviço que está sendo visualizado
	private final Servico s;

	/**
	 * Construtor que inicializa o controller e configura os listeners.
	 * 
	 * @param view referência à tela de visualização de serviço
	 * @param model referência ao DAO de serviços
	 * @param navegador objeto que controla a navegação
	 * @param s objeto do serviço sendo visualizado
	 */
	public VisServicoController(VisServico view, ServicoDAO model, Navegador navegador, Servico s){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		// Atribui a referência do serviço recebido ao atributo da classe
		this.s= s;
		
		// Ação do botão "aceitar" na view: validações e atualização do serviço no DAO
		// Configura o listener do botão "aceitar" na view
		this.view.aceitar(e ->{
			// Verifica se há um usuário logado
			// Valida se existe um usuário autenticado no sistema
			if (navegador.getCurrentUser() == null) {
				// Exibe mensagem de erro se não houver usuário logado
				javax.swing.JOptionPane.showMessageDialog(null, "Erro: Nenhum usuário logado. Por favor, faça login novamente.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
				// Redireciona para a tela de login
				navegador.navegarPara("LOGIN");
				// Interrompe a execução do método
				return;
			}
			
			// Variável que armazenará o serviço buscado no banco
			Servico servico = null;
			// Prefere buscar por id (mais seguro) quando disponível
			// Verifica se o serviço tem um ID válido (maior que zero)
			if (s != null && s.getIdServico() > 0) {
				// Busca o serviço no banco de dados pelo ID
				servico = this.model.buscarServicoPorId(s.getIdServico());
			} else if (s != null && s.getNome_Servico() != null) {
				// Se não tem ID, tenta buscar pelo nome do serviço
				servico = this.model.buscarServicoPorNome(s.getNome_Servico());
			}
			
			// Validação: serviço deve existir no banco
			// Verifica se o serviço foi encontrado no banco de dados
			if (servico == null) {
				// Exibe mensagem de erro se o serviço não foi encontrado
				javax.swing.JOptionPane.showMessageDialog(null, "Erro: Serviço não encontrado no banco de dados.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
				// Retorna para a tela do contratado
				navegador.navegarPara("CONTRATADO");
				// Interrompe a execução do método
				return;
			}
			
			// Atualiza referência local e atribui o contratante atual como responsável
			// Define o ID do serviço na referência local
			s.setIdServico(servico.getIdServico());
			// Define o ID do usuário logado como o contratado responsável pelo serviço
			s.setIdContratado(navegador.getCurrentUser().getIdUsuario());
			
			// Marca o serviço como aceito no banco de dados
			this.model.aceitarServico(s);
			
			// Navega de volta para a tela do contratado
			navegador.navegarPara("CONTRATADO");
		});
		
	} // Fim do construtor VisServicoController
} // Fim da classe VisServicoController