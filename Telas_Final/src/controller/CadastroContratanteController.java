// Define o pacote onde esta classe está localizada
package controller;

// Importa a classe Servico que representa um serviço no sistema
import model.Servico;
// Importa ServicoDAO, responsável por operações de banco de dados relacionadas a serviços
import model.ServicoDAO;
// Importa a classe Usuario que representa um usuário no sistema
import model.Usuario;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a view da tela de cadastro genérica
import view.TelaCadastro;
// Importa a view da tela de cadastro de serviço para contratante
import view.TelaCadastroContratante;

/**
 * Controller responsável pelo cadastro de serviços pelo contratante.
 * <p>
 * Gerencia a lógica de criação de novos serviços, validando se há um usuário
 * logado, coletando os dados do formulário e persistindo no banco de dados
 * através do ServicoDAO.
 * </p>
 */
public class CadastroContratanteController {
	// Referência à camada de visualização (interface gráfica) da tela de cadastro de serviço
	private final TelaCadastroContratante view;
	// Referência ao DAO que gerencia operações de banco de dados para serviços
	private final ServicoDAO model;
	// Referência ao objeto que controla a navegação entre telas
	private final Navegador navegador;

	/**
	 * Construtor que inicializa o controller e configura os listeners.
	 * 
	 * @param view referência à tela de cadastro de contratante
	 * @param model referência ao DAO de serviços
	 * @param navegador objeto que controla a navegação entre telas
	 */
	public CadastroContratanteController(TelaCadastroContratante view, ServicoDAO model, Navegador navegador){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		
		// Configura o listener do botão "cadastrar" na view
		this.view.cadastrar(e ->{
			// Verifica se há um usuário logado
			// Valida se existe um usuário autenticado no sistema
			if (navegador.getCurrentUser() == null) {
				// Exibe mensagem de erro se não houver usuário logado
				javax.swing.JOptionPane.showMessageDialog(view, "Erro: Nenhum usuário logado. Por favor, faça login novamente.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
				// Redireciona para a tela de login
				navegador.navegarPara("LOGIN");
				// Interrompe a execução do método
				return;
			}
			
			// Obtém o texto do campo nome do serviço
			String nome_Servico = this.view.getTfNome().getText();
			// Obtém o texto do campo modalidade (presencial, remoto, etc.)
		    String modalidade = this.view.getTfModalidade().getText();
		    // Obtém o texto do campo valor e converte para Double (número decimal)
		    Double valor;
		    try {
		    	valor = Double.parseDouble(this.view.getTfValor().getText());
		    } catch (NumberFormatException ex) {
		    	javax.swing.JOptionPane.showMessageDialog(view, "Valor inválido. Por favor, insira um número.", "Erro de Formato", javax.swing.JOptionPane.ERROR_MESSAGE);
		    	return;
		    }
		    // Obtém o texto do campo descrição do serviço
		    String descricao = this.view.getTfDescricao().getText();
		    
			// Cria um novo objeto Servico com os dados coletados
			// O parâmetro false indica que o serviço ainda não foi aceito
			// O usuário logado é definido como proprietário do serviço
			Servico s = new Servico(nome_Servico,valor,modalidade,descricao, false, navegador.getCurrentUser());

			// Tenta cadastrar o serviço no banco de dados
			boolean sucesso = model.cadastrarS(s);
			// Verifica se o cadastro foi bem-sucedido
			if (sucesso) {
				// Limpa todos os campos do formulário
				this.view.limparCampos();
				// Navigate to SERVICOS but do not push the cadastro_contratante screen onto history
				// Navega para a tela de serviços sem adicionar esta tela ao histórico de navegação
				// (false = não empilha no histórico, evita que o botão voltar retorne aqui)
				navegador.navegarPara("SERVICOS", false);
				
			}
		});
		
	} // Fim do construtor CadastroContratanteController
} // Fim da classe CadastroContratanteController