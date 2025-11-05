// Define o pacote onde esta classe está localizada
package controller;

// Importa CardLayout, gerenciador de layout que permite trocar entre múltiplos painéis
import java.awt.CardLayout;
// Importa ArrayDeque, implementação eficiente de fila/pilha de dupla extremidade
import java.util.ArrayDeque;
// Importa Deque, interface para estrutura de dados de fila/pilha
import java.util.Deque;
// Importa Consumer, interface funcional para operações que consomem um argumento
import java.util.function.Consumer;

// Importa JPanel, componente Swing que representa um painel
import javax.swing.JPanel;

// Importa a classe Usuario que representa um usuário no sistema
import model.Usuario;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa a view da janela principal da aplicação
import view.Primario;
// Importa a view da barra WorkIt (barra superior)
import view.wbBarra;

/**
 * Controlador responsável pela navegação entre as telas da aplicação.
 * <p>
 * Mantém uma referência à janela primária (`Primario`), gerencia o usuário
 * atualmente autenticado e mantém uma pilha de histórico de navegação para
 * permitir voltar para telas anteriores. Também coordena operações relacionadas
 * a imagens de perfil e ao registro/remoção de painéis dinâmicos.
 * </p>
 */
public class Navegador {
	// Referência à janela principal da aplicação que contém todos os painéis
	private Primario prim;
	// Referência ao usuário atualmente autenticado no sistema (null se não logado)
	private Usuario currentUser;
	// Referência ao DAO de usuários para operações de banco de dados
	private UsuarioDAO usuarioDAO;
	
	// Pilha LIFO que armazena os nomes dos painéis visitados
	// Permite implementar funcionalidade de "voltar" para telas anteriores
	private final Deque<String> history = new ArrayDeque<>();
	// Listener opcional acionado quando o histórico muda
	// Usado para atualizar estado de botões voltar/avançar na interface
	private Runnable historyListener;
	
	/**
	 * Construtor que inicializa o navegador com referência à janela principal.
	 * 
	 * @param prim referência à janela principal (Primario) que gerencia os painéis
	 */
	public Navegador(Primario prim) {
		// Atribui a referência da janela principal ao atributo da classe
		this.prim = prim;

	}
	
	/**
	 * Define o DAO de usuários que será usado para operações de banco de dados.
	 * 
	 * @param usuarioDAO instância do DAO de usuários
	 */
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		// Atribui a referência do DAO recebida ao atributo da classe
		this.usuarioDAO = usuarioDAO;
	}
	
	/**
	 * Define um listener que será chamado sempre que o histórico de navegação
	 * for modificado (push/pop/clear).
	 * @param listener Runnable executado na mudança de histórico
	 */
	public void setOnHistoryChange(Runnable listener) {
		// Atribui o listener que será executado sempre que o histórico mudar
		this.historyListener = listener;
	}
	
	/**
	 * @return true se existe ao menos uma entrada no histórico de navegação
	 */
	public boolean hasHistory() {
		// Retorna verdadeiro se a pilha de histórico não está vazia
		return !history.isEmpty();
	}
	
	/**
	 * Adiciona um painel à janela primária (CardLayout).
	 * @param nome identificador do painel
	 * @param tela instância do JPanel que representa a tela
	 */
	public void adicionarPainel(String nome, JPanel tela) {
		// Delega à janela principal a adição do painel ao CardLayout
		this.prim.adicionarTela(nome, tela);

	}
	
	/**
	 * Remove um painel previamente registrado na janela primária.
	 * @param nome nome do painel a remover
	 */
	public void removerPainel(String nome) {
		// Delega à janela principal a remoção do painel do CardLayout
		this.prim.removerTela(nome);
	}

	/**
	 * Navega para a tela informada empilhando a tela atual no histórico
	 * por padrão.
	 * @param nome nome da tela destino
	 */
	public void navegarPara(String nome) {
		// Chama a versão completa do método com pushCurrent=true (adiciona ao histórico)
		navegarPara(nome, true);
	}
	
	/**
	 * Navega para uma tela com opção de controlar se a tela atual deve ser
	 * empilhada no histórico.
	 * <p>
	 * Regras aplicadas:
	 * - Não empilha se a tela atual for igual à tela destino.
	 * - Empilha LOGIN quando navegando para telas de Cadastro (para permitir retornar).
	 * - Nunca empilha telas de cadastro quando saindo delas.
	 * - Caso contrário, empilha se pushCurrent for true e existir tela atual.
	 * </p>
	 * @param nome nome da tela destino
	 * @param pushCurrent se true, empilha a tela atual no histórico
	 */
	public void navegarPara(String nome, boolean pushCurrent) {
		// Se houver um usuário logado, atualiza seus dados e imagem de perfil
		// Verifica se existe um usuário logado E se o DAO está disponível
		if (currentUser != null && usuarioDAO != null) {
			try {
				// Busca os dados mais recentes do usuário no banco de dados
				Usuario usuarioAtualizado = usuarioDAO.getUsuarioById(currentUser.getIdUsuario());
				// Verifica se o usuário foi encontrado no banco
				if (usuarioAtualizado != null) {
					// decodifica imagem64 para arquivo local
					// Decodifica a imagem em Base64 e salva como arquivo temporário
					usuarioDAO.decode64(usuarioAtualizado);
					// atualiza referência do usuário
					// Atualiza a referência local com os dados mais recentes
					currentUser = usuarioAtualizado;
				}
			} catch (Exception e) {
				// Imprime o stack trace em caso de erro ao atualizar usuário
				e.printStackTrace();
			}
		}
		
		// Fecha o menu lateral se estiver aberto antes de trocar de tela
		// Garante que o drawer menu (menu lateral) seja fechado antes da navegação
		this.prim.fecharDrawerMenuSeAberto();
		
		// Lógica de empilhamento no histórico de navegação
		try {
			// Obtém o nome do painel atualmente exibido
			String current = this.prim.getCurrentPanelName();
			// Converte o nome atual para maiúsculas (null-safe)
			String currentUpper = (current != null) ? current.toUpperCase() : null;
			// Converte o nome destino para maiúsculas (null-safe)
			String nomeUpper = (nome != null) ? nome.toUpperCase() : null;
			
			// Log de debug: mostra de qual tela está navegando para qual
			System.out.println("[Navegador] Navegando de '" + current + "' para '" + nome + "'");
			// Log de debug: mostra se deve empilhar a tela atual
			System.out.println("[Navegador] pushCurrent: " + pushCurrent);
			
			// Evita empilhar se for a mesma tela
			// Verifica se a tela atual e a tela destino são a mesma
			if (currentUpper != null && currentUpper.equals(nomeUpper)) {
				// Log de debug: indica que não vai empilhar por ser a mesma tela
				System.out.println("[Navegador] Mesma tela, não empilha");
				// Apenas mostra a tela (não empilha) e retorna
				this.prim.mostrarTela(nome);
				return;
			}
			
			// Empilha LOGIN quando navegando para telas de CADASTRO
			// Regra especial: se está no LOGIN e vai para CADASTRO, empilha o LOGIN
			// Isso permite que o usuário volte para LOGIN após cancelar o cadastro
			if (currentUpper != null && currentUpper.equals("LOGIN") && nomeUpper != null &&
			    (nomeUpper.startsWith("CADASTRO") || nomeUpper.equals("CADASTRO_CONTRATANTE"))) {
				// Adiciona a tela atual (LOGIN) ao topo da pilha de histórico
				history.push(current);
				// Log de debug: confirma que empilhou LOGIN
				System.out.println("[Navegador] De LOGIN para CADASTRO, empilhado. Histórico: " + history.size());
				// Notifica o listener que o histórico mudou
				if (historyListener != null) historyListener.run();
			}
			// Não empilha quando saindo de telas de cadastro
			// Regra especial: quando sai de uma tela de CADASTRO, não empilha
			// Isso evita que telas de cadastro fiquem no histórico após confirmação
			else if (currentUpper != null && 
			         (currentUpper.startsWith("CADASTRO") || currentUpper.equals("CADASTRO_CONTRATANTE"))) {
				// Log de debug: indica que não vai empilhar por estar saindo de CADASTRO
				System.out.println("[Navegador] Saindo de CADASTRO, não empilha");
			}
			// Empilha normalmente se solicitado
			// Regra padrão: se pushCurrent é true E existe tela atual, empilha
			else if (pushCurrent && current != null) {
				// Adiciona a tela atual ao topo da pilha de histórico
				history.push(current);
				// Log de debug: confirma empilhamento com tamanho da pilha
				System.out.println("[Navegador] Empilhado '" + current + "'. Histórico: " + history.size());
				// Notifica o listener que o histórico mudou
				if (historyListener != null) historyListener.run();
			} else {
				// Log de debug: indica que não empilhou
				System.out.println("[Navegador] Não empilhou. pushCurrent=" + pushCurrent);
			}
		} catch (Exception ex) {
			// Em caso de qualquer exceção durante o empilhamento:
			// Log de erro: mostra a mensagem de erro
			System.err.println("[Navegador] Erro ao empilhar histórico: " + ex.getMessage());
			// Imprime o stack trace completo
			ex.printStackTrace();
		}
		
		// Mostra a tela solicitada
		// Delega à janela principal a exibição da tela usando CardLayout
		this.prim.mostrarTela(nome);
		// Caso especial: se navegou para SERVICOS, atualiza a tabela de serviços
		if ("SERVICOS".equals(nome)) {
			// Chama método estático que atualiza a tabela se o controller existir
			controller.ListaServicosController.atualizarTabelaSeExistir();
		}
	}

	/**
	 * Volta para a tela anterior presente no histórico. Se o histórico estiver
	 * vazio, não realiza nenhuma ação.
	 */
	public void voltar() {
		// Log de debug: mostra que o método voltar foi chamado e o tamanho do histórico
		System.out.println("[Navegador] Método voltar() chamado. Tamanho do histórico: " + history.size());
		// Verifica se a pilha de histórico está vazia
		if (history.isEmpty()) {
			// Log de debug: indica que não há para onde voltar
			System.out.println("[Navegador] Histórico vazio, não há para onde voltar");
			// Retorna sem fazer nada
			return;
		}
		// Remove e obtém o nome da tela do topo da pilha
		String anterior = history.pop();
		// Log de debug: mostra para qual tela está voltando e o novo tamanho do histórico
		System.out.println("[Navegador] Voltando para: '" + anterior + "'. Novo tamanho do histórico: " + history.size());
		// Fecha o drawer menu se estiver aberto
		this.prim.fecharDrawerMenuSeAberto();
		// Mostra a tela anterior
		this.prim.mostrarTela(anterior);
		// Notifica o listener que o histórico mudou (para atualizar botões)
		if (historyListener != null)
			historyListener.run();
	}

	/**
	 * Limpa todo o histórico de navegação e notifica o listener registrado.
	 */
	public void clearHistory() {
		// Remove todas as entradas da pilha de histórico
		history.clear();
		// Notifica o listener que o histórico mudou
		if (historyListener != null) historyListener.run();
	}

	/**
	 * Notifica o listener de histórico (utilizado quando o estado pode ter mudado externamente).
	 */
	public void notifyHistoryChange() {
		// Executa o listener se ele estiver definido
		if (historyListener != null) historyListener.run();
	}

	/**
	 * Finaliza a aplicação após limpar imagens temporárias de perfil.
	 */
	public void sair() {
		// Limpa arquivos temporários de imagens de perfil antes de sair
		limparImagensPerfil();
		// Fecha e libera recursos da janela principal
		this.prim.dispose();
	}

	/**
	 * Adiciona um painel à barra WorkIt (área específica da interface).
	 * 
	 * @param tela painel a ser adicionado
	 */
	public void adicionarPainelWB(JPanel tela) {
		// Delega à janela principal a adição do painel à área WB
		this.prim.adicionarTelaWB(tela);
	}

	/**
	 * Define o usuário atualmente autenticado no sistema.
	 * 
	 * @param user objeto Usuario representando o usuário logado
	 */
	public void setCurrentUser(Usuario user) {
		// Atribui o usuário recebido ao atributo currentUser
		this.currentUser = user;
	}
	
	/**
	 * Obtém o usuário atualmente autenticado.
	 * 
	 * @return objeto Usuario ou null se nenhum usuário estiver logado
	 */
	public Usuario getCurrentUser() {
		// Retorna a referência ao usuário atual
		return this.currentUser;
	}
	
	/**
	 * Limpa a referência ao usuário atual (usado em logout).
	 */
	public void clearCurrentUser() {
		// Define o usuário atual como null (desautentica)
		this.currentUser = null;
	}
	
	/**
	 * Remove arquivos temporários de imagens de perfil gerados na pasta de imagens
	 * do projeto. Também limpa a referência ao usuário atual.
	 */
	public void limparImagensPerfil() {
		try {
			// Obtém o diretório raiz do projeto e adiciona o caminho para a pasta de imagens
			String diretorioImagens = System.getProperty("user.dir") + "/src/imagens/";
			// Cria um objeto File representando a pasta de imagens
			java.io.File pastaImagens = new java.io.File(diretorioImagens);
			
			// Verifica se a pasta existe E se é realmente um diretório
			if (pastaImagens.exists() && pastaImagens.isDirectory()) {
				// Lista todos os arquivos dentro da pasta
				java.io.File[] arquivos = pastaImagens.listFiles();
				// Verifica se a lista de arquivos não é nula
				if (arquivos != null) {
					// Percorre todos os arquivos da pasta
					for (java.io.File arquivo : arquivos) {
						// Deleta arquivos que começam com o prefixo de foto de perfil
						// Verifica se é um arquivo (não diretório) E se o nome começa com "FotoPerfil_"
						if (arquivo.isFile() && arquivo.getName().startsWith("FotoPerfil_")) {
							// Tenta deletar o arquivo e armazena o resultado
							boolean deletado = arquivo.delete();
							// Se foi deletado com sucesso, loga a informação
							if (deletado) {
								System.out.println("[Navegador] Imagem deletada: " + arquivo.getName());
							}
						}
					}
				}
			}
			// Limpa o currentUser após logout
			// Define o usuário atual como null
			this.currentUser = null;
		} catch (Exception e) {
			// Em caso de erro ao limpar imagens:
			// Loga mensagem de erro
			System.err.println("[Navegador] Erro ao limpar imagens de perfil: " + e.getMessage());
			// Imprime o stack trace completo
			e.printStackTrace();
		}
	}

	/**
	 * Remove todos os painéis cujo nome começa com o prefixo informado.
	 * Usado por fábricas de tela para limpar painéis dinâmicos com cache.
	 */
	public void removerPainelPorPrefixo(String prefixo) {
		try {
			java.util.Set<String> names = this.prim.getPainelNames();
			for (String name : names) {
				if (name != null && name.startsWith(prefixo)) {
					this.removerPainel(name);
				}
			}
		} catch (Exception e) {
			System.err.println("[Navegador] Erro ao remover painéis por prefixo: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}