// Define o pacote onde esta classe está localizada
package controller;

// Importa ComponentAdapter para criar listeners de eventos de componente
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componente
import java.awt.event.ComponentEvent;
// Importa MouseAdapter para criar listeners de eventos de mouse personalizados
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre cliques do mouse
import java.awt.event.MouseEvent;

// Importa DefaultListModel, o modelo de dados padrão para JList
import javax.swing.DefaultListModel;
// Importa JOptionPane para exibir diálogos ao usuário
import javax.swing.JOptionPane;

// Importa a classe Servico que representa um serviço no sistema
import model.Servico;
// Importa ServicoDAO, responsável por operações de banco de dados relacionadas a serviços
import model.ServicoDAO;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa o renderizador customizado que define como os serviços são exibidos na lista
import view.ServicoListCellRenderer;
// Importa a view da tela do contratado
import view.TelaContratado;
// Importa a view de visualização de serviço disponível
import view.VisServico;
// Importa a view de visualização de serviço em andamento
import view.VisServicoAndamento;

/**
 * Controller que gerencia as interações da tela do contratado.
 * <p>
 * Adiciona listeners para duplo-clique nas listas de serviços disponíveis e
 * em andamento, criando e exibindo as visualizações detalhadas correspondentes
 * quando o usuário abre um item. Também atualiza as listas quando a tela
 * é mostrada (método componentShown).
 * </p>
 */
public class ContratadoController extends ComponentAdapter {
	// Referência à camada de visualização (interface gráfica) da tela do contratado
	private final TelaContratado view;
	// Referência ao DAO que gerencia operações de banco de dados para usuários
	private final UsuarioDAO model;
	// Referência ao objeto que controla a navegação entre telas
	private final Navegador navegador;
	// Referência ao DAO que gerencia operações de banco de dados para serviços
	private final ServicoDAO servicoDAO;
	// Referência à fábrica de telas que cria instâncias de visualizações dinamicamente
	private final TelaFactory telaFactory;

	/**
	 * Construtor que inicializa o controller e configura os listeners de duplo clique.
	 * 
	 * @param view referência à tela do contratado
	 * @param model referência ao DAO de usuários
	 * @param navegador objeto que controla a navegação entre telas
	 * @param servicoDAO referência ao DAO de serviços
	 * @param telaFactory fábrica que cria instâncias dinâmicas de telas
	 */
	public ContratadoController(TelaContratado view, UsuarioDAO model, Navegador navegador, ServicoDAO servicoDAO, TelaFactory telaFactory){
		// Atribui a referência da view recebida ao atributo da classe
		this.view = view;
		// Atribui a referência do model (DAO de usuários) recebido ao atributo da classe
		this.model = model;
		// Atribui a referência do navegador recebido ao atributo da classe
		this.navegador = navegador;
		// Atribui a referência do DAO de serviços recebido ao atributo da classe
		this.servicoDAO = servicoDAO;
		// Atribui a referência da fábrica de telas recebida ao atributo da classe
		this.telaFactory = telaFactory;
		
		// Duplo clique na lista de serviços disponíveis -> abre visualização do serviço
		// Configura um listener de mouse para a lista de serviços disponíveis
		this.view.cliqueDuploNoJList(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	// Responsável apenas por tratar duplo clique
		    	// Verifica se foi um duplo clique (2 cliques)
		    	if (e.getClickCount() == 2) { 
		            // Obtém o índice do item na lista onde o clique ocorreu
		            int index = view.getListaDisponivel().locationToIndex(e.getPoint());
		            // Obtém o objeto do modelo da lista no índice clicado
		            Object selectedItem = view.getListaDisponivel().getModel().getElementAt(index);
		            
		            // Verifica se o item selecionado é uma instância de Servico
		            if (selectedItem instanceof Servico) {
		                // Faz o cast do objeto para Servico
		                Servico servicoSelecionado = (Servico) selectedItem;

		                // Cria uma visualização específica para o serviço selecionado e navega até ela
		                // Cria uma nova instância do DAO de serviços
		                final ServicoDAO sd = new ServicoDAO();
		                // Cria a view de visualização do serviço disponível
		                VisServico vs = new VisServico(servicoSelecionado);
		                // Cria o controller para a visualização, passando todas as dependências
		                VisServicoController vsc = new VisServicoController(vs, sd, navegador, servicoSelecionado);
		                // Adiciona o painel de visualização ao navegador com a chave "VISUALIZAR_SERVICO"
		                navegador.adicionarPainel("VISUALIZAR_SERVICO", vs);
		                // Navega para o painel de visualização
		                navegador.navegarPara("VISUALIZAR_SERVICO");
		                
		            } else {
		                // item não é do tipo esperado; não faz nada
		            }   
		        }
		    }
		});
		
		// Duplo clique na lista de serviços em andamento -> abre visualização específica
		// Configura um listener de mouse para a lista de serviços em andamento
		this.view.cliqueDuploNoAndamento(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	// Verifica se foi um duplo clique (2 cliques)
		    	if (e.getClickCount() == 2) { 
		            // Obtém o índice do item na lista onde o clique ocorreu
		            int index = view.getListaAndamento().locationToIndex(e.getPoint());
		            // Obtém o objeto do modelo da lista no índice clicado
		            Object selectedItem = view.getListaAndamento().getModel().getElementAt(index);
		            
		            // Verifica se o item selecionado é uma instância de Servico
		            if (selectedItem instanceof Servico) {
		                // Faz o cast do objeto para Servico
		                Servico servicoSelecionado = (Servico) selectedItem;

		                    // Cria uma nova instância do DAO de serviços
		                    final ServicoDAO sd = new ServicoDAO();
		                    // Cria a view de visualização do serviço em andamento
		                    VisServicoAndamento vs = new VisServicoAndamento(servicoSelecionado);
		                    // Cria o controller para a visualização de serviço em andamento
		                    VisServicoAndamentoController vsc = new VisServicoAndamentoController(vs, sd, navegador, servicoSelecionado);
		                    // Adiciona o painel de visualização ao navegador
		                    navegador.adicionarPainel("VISUALIZAR_SERVICO", vs);
		                    // Navega para o painel de visualização
		                    navegador.navegarPara("VISUALIZAR_SERVICO");
		                    
		            } else {
		            	// Se o item não for um Servico, exibe mensagem de erro
		            	JOptionPane.showMessageDialog(null, "Nenhum servico selecionado", "Erro", JOptionPane.ERROR_MESSAGE);
		            }   
		        }
		    }
		});
		
	} // Fim do construtor ContratadoController
	
	/**
	 * Atualiza a lista de serviços disponíveis consultando o DAO e populando o JList.
	 */
	public void atualizarListaDisponivel() {
		// Cria uma nova instância do DAO de serviços
		ServicoDAO servicoDAO = new ServicoDAO();
		// Busca todos os serviços disponíveis (não aceitos) do banco de dados
		java.util.List<Servico> servicos = servicoDAO.listarServicos();
		// Cria um novo modelo de lista que armazenará os serviços
		DefaultListModel<Servico> listModel = new DefaultListModel<>();
		// Percorre todos os serviços retornados do banco
		for (Servico s : servicos) {
			// Adiciona cada serviço ao modelo da lista
			listModel.addElement(s);
		}
		// Define o modelo de dados da JList de serviços disponíveis
		this.view.getListaDisponivel().setModel(listModel);
		// Define o renderizador customizado que controla como os serviços são exibidos visualmente
		this.view.getListaDisponivel().setCellRenderer(new ServicoListCellRenderer());
	}
	
	/**
	 * Atualiza a lista de serviços em andamento (aceitos) consultando o DAO.
	 */
	public void atualizarListaAceitos() {
		// Cria uma nova instância do DAO de serviços
		ServicoDAO servicoDAO = new ServicoDAO();
		// Busca todos os serviços aceitos (em andamento) do banco de dados
		java.util.List<Servico> servicosAceitos = servicoDAO.listarServicosAceitos();
		// Cria um novo modelo de lista que armazenará os serviços aceitos
		DefaultListModel<Servico> listModelAceito = new DefaultListModel<>();
		// Percorre todos os serviços aceitos retornados do banco
		for (Servico s : servicosAceitos) {
			// Adiciona cada serviço aceito ao modelo da lista
			listModelAceito.addElement(s);
		}
		// Define o modelo de dados da JList de serviços em andamento
		this.view.getListaAndamento().setModel(listModelAceito);
		// Define o renderizador customizado para exibição visual dos serviços
		this.view.getListaAndamento().setCellRenderer(new ServicoListCellRenderer());
	}
	
	/**
	 * Sobrescreve o método componentShown do ComponentAdapter.
	 * Este método é chamado automaticamente sempre que a tela do contratado é exibida.
	 */
	@Override
	public void componentShown(ComponentEvent e) {
		// Quando a tela do contratado é mostrada, atualiza as listas para refletir o estado atual
		// Atualiza a lista de serviços disponíveis
		atualizarListaDisponivel();
		// Atualiza a lista de serviços em andamento
		atualizarListaAceitos();

	}
} // Fim da classe ContratadoController