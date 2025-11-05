// Define o pacote onde esta classe está localizada
package controller;

// Importa a classe Servico que representa um serviço no sistema
import model.Servico;
// Importa ServicoDAO, responsável por operações de banco de dados relacionadas a serviços
import model.ServicoDAO;
// Importa a view de visualização de serviço (não utilizada, mas mantida por compatibilidade)
import view.VisServico;
// Importa a view de visualização de serviço para o contratante
import view.VisServicoCnte;

/**
 * Controller para a visualização de um serviço na perspectiva do contratante
 * (serviço ainda não aceito).
 * <p>
 * Fornece ações básicas da view, por exemplo navegar de volta para a lista
 * de serviços ao editar/voltar.
 * </p>
 */
public class VisServicoCnteController {
	// Referência à camada de visualização (interface gráfica) da tela de visualização de serviço do contratante
	private final VisServicoCnte view;
	// Referência ao DAO que gerencia operações de banco de dados para serviços
	private final ServicoDAO model;
	// Referência ao navegador que controla a navegação entre telas
	private final Navegador navegador;
	// Referência ao serviço que está sendo visualizado
	private final Servico s;

	/**
	 * Construtor que inicializa o controller e configura os listeners.
	 * 
	 * @param view referência à tela de visualização de serviço do contratante
	 * @param model referência ao DAO de serviços
	 * @param navegador objeto que controla a navegação
	 * @param s objeto do serviço sendo visualizado
	 */
	public VisServicoCnteController(VisServicoCnte view, ServicoDAO model, Navegador navegador, Servico s){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		// Atribui a referência do serviço recebido ao atributo da classe
		this.s= s;
		
		// Ao clicar em editar/voltar na view, retorna para a lista de serviços
		// Configura o listener do botão "Editar" (ou voltar) na view
		this.view.Editar(e ->{
			// Navega para a tela de lista de serviços
			navegador.navegarPara("SERVICOS");
		});
		
	} // Fim do construtor VisServicoCnteController
} // Fim da classe VisServicoCnteController