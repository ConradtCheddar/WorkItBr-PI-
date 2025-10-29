package controller;

import java.awt.CardLayout;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

import javax.swing.JPanel;

import model.Usuario;
import model.UsuarioDAO;
import view.Primario;
import view.wbBarra;

public class Navegador {
	private Primario prim;
	private Usuario currentUser;
	private UsuarioDAO usuarioDAO;
	
	// pilha para armazenar o histórico de navegação (nomes dos painéis)
	private final Deque<String> history = new ArrayDeque<>();
	// ouvinte opcional para notificar controladores de UI quando o histórico mudar
	private Runnable historyListener;

	public Navegador(Primario prim) {
		this.prim = prim;

	}
	
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	/**
	 * metodo para adicionar um Jpanel
	 * 
	 * @param nome
	 * @param tela
	 */
	public void adicionarPainel(String nome, JPanel tela) {
		this.prim.adicionarTela(nome, tela);

	}
	
	/**
	 * Remove um painel do CardLayout
	 * 
	 * @param nome Nome do painel a ser removido
	 */
	public void removerPainel(String nome) {
		this.prim.removerTela(nome);
	}

	/**
	 * Navega para uma tela. Por retrocompatibilidade empilha a tela atual no histórico.
	 */
	public void navegarPara(String nome) {
		this.navegarPara(nome, true);
		// Atualiza a imagem do perfil do usuário atual antes de navegar
		if (currentUser != null && usuarioDAO != null) {
			try {
				// Recarrega os dados do usuário do banco de dados
				Usuario usuarioAtualizado = usuarioDAO.getUsuarioById(currentUser.getIdUsuario());
				if (usuarioAtualizado != null) {
					// Decodifica a imagem64 e salva no disco com o ID do usuário
					usuarioDAO.decode64(usuarioAtualizado);
					// Atualiza o currentUser com os dados mais recentes
					currentUser = usuarioAtualizado;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
	public void navegarPara(String nome, boolean pushCurrent) {
		this.prim.fecharDrawerMenuSeAberto(); // Fecha o DrawerMenu se estiver aberto
		// Empilha o painel atual no histórico antes de navegar, se presente e diferente
		try {
			String current = this.prim.getCurrentPanelName();
			boolean shouldPush = pushCurrent;
			// Não empilha telas de cadastro no histórico (assim o botão voltar não as retorna)
			if (current != null) {
				if (current.startsWith("CADASTRO") || current.equals("CADASTRO_CONTRATANTE")) {
					shouldPush = false;
				}
			}
			if (shouldPush && current != null && !current.equals(nome)) {
				history.push(current);
				// notifica o ouvinte de que o histórico mudou
				if (historyListener != null)
					historyListener.run();
			}
		} catch (Exception ex) {
			// Se o prim não rastrear o painel atual por algum motivo, ignora
		}
		this.prim.mostrarTela(nome);
		if ("SERVICOS".equals(nome)) {
			controller.ListaServicosController.atualizarTabelaSeExistir();
		}
	}

	/**
	 * Navega de volta para a tela anterior registrada no histórico. Se não houver
	 * histórico, não faz nada.
	 */
	public void voltar() {
		if (history.isEmpty()) {
			return;
		}
		String anterior = history.pop();
		this.prim.fecharDrawerMenuSeAberto();
		this.prim.mostrarTela(anterior);
		if (historyListener != null)
			historyListener.run();
	}

	/**
	 * Limpa todo o histórico de navegação e notifica listener (usado no logout)
	 */
	public void clearHistory() {
		history.clear();
		if (historyListener != null) historyListener.run();
	}

	public void sair() {
		// Limpa todas as imagens de perfil antes de fechar a aplicação
		limparImagensPerfil();
		this.prim.dispose();
	}

	public void adicionarPainelWB(JPanel tela) {
		this.prim.adicionarTelaWB(tela);
	}

	public void setCurrentUser(Usuario user) {
		this.currentUser = user;
	}
	public Usuario getCurrentUser() {
		return this.currentUser;
	}
	
	/**
	 * Limpa o usuário atual (útil para logout)
	 */
	public void clearCurrentUser() {
		this.currentUser = null;
	}

	/**
	 * Retorna true se houver histórico para voltar
	 */
	public boolean hasHistory() {
		return !this.history.isEmpty();
	}

	/**
	 * Registra um listener que será chamado quando o histórico mudar (push/pop)
	 */
	public void setOnHistoryChange(Runnable listener) {
		this.historyListener = listener;
	}

	/**
	 * Limpa todas as imagens de perfil salvas no disco ao fazer logout
	 */
	public void limparImagensPerfil() {
		try {
			String diretorioImagens = System.getProperty("user.dir") + "/src/imagens/";
			java.io.File pastaImagens = new java.io.File(diretorioImagens);
			
			if (pastaImagens.exists() && pastaImagens.isDirectory()) {
				java.io.File[] arquivos = pastaImagens.listFiles();
				if (arquivos != null) {
					for (java.io.File arquivo : arquivos) {
						// Deleta todos os arquivos que começam com "FotoPerfil_"
						if (arquivo.isFile() && arquivo.getName().startsWith("FotoPerfil_")) {
							boolean deletado = arquivo.delete();
							if (deletado) {
								System.out.println("[Navegador] Imagem deletada: " + arquivo.getName());
							}
						}
					}
				}
			}
			// Limpa o currentUser após logout
			this.currentUser = null;
		} catch (Exception e) {
			System.err.println("[Navegador] Erro ao limpar imagens de perfil: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}