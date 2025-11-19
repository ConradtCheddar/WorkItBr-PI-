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
	
	private final Deque<String> history = new ArrayDeque<>();
	private Runnable historyListener;
	
	public Navegador(Primario prim) {
		this.prim = prim;

	}
	
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	
	public void setOnHistoryChange(Runnable listener) {
		this.historyListener = listener;
	}
	
	public boolean hasHistory() {
		return !history.isEmpty();
	}
	
	public void adicionarPainel(String nome, JPanel tela) {
		this.prim.adicionarTela(nome, tela);

	}
	
	public void removerPainel(String nome) {
		this.prim.removerTela(nome);
	}

	public void navegarPara(String nome) {
		navegarPara(nome, true);
	}
	
	public void navegarPara(String nome, boolean pushCurrent) {
		if (currentUser != null && usuarioDAO != null) {
			try {
				Usuario usuarioAtualizado = usuarioDAO.getUsuarioById(currentUser.getIdUsuario());
				if (usuarioAtualizado != null) {
					usuarioDAO.decode64(usuarioAtualizado);
					currentUser = usuarioAtualizado;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.prim.fecharDrawerMenuSeAberto();  
		
		try {
			String current = this.prim.getCurrentPanelName();
			String currentUpper = (current != null) ? current.toUpperCase() : null;
			String nomeUpper = (nome != null) ? nome.toUpperCase() : null;
			
			System.out.println("[Navegador] Navegando de '" + current + "' para '" + nome + "'");
			System.out.println("[Navegador] pushCurrent: " + pushCurrent);
			
			if (currentUpper != null && currentUpper.equals(nomeUpper)) {
				System.out.println("[Navegador] Mesma tela, não empilha");
				this.prim.mostrarTela(nome);
				return;
			}
			
			if (currentUpper != null && currentUpper.equals("LOGIN") && nomeUpper != null &&
			    (nomeUpper.startsWith("CADASTRO") || nomeUpper.equals("CADASTRO_CONTRATANTE"))) {
				history.push(current);
				System.out.println("[Navegador] De LOGIN para CADASTRO, empilhado. Histórico: " + history.size());
				if (historyListener != null) historyListener.run();
			}
			else if (currentUpper != null && 
			         (currentUpper.startsWith("CADASTRO") || currentUpper.equals("CADASTRO_CONTRATANTE"))) {
				System.out.println("[Navegador] Saindo de CADASTRO, não empilha");
			}
			else if (pushCurrent && current != null) {
				history.push(current);
				System.out.println("[Navegador] Empilhado '" + current + "'. Histórico: " + history.size());
				if (historyListener != null) historyListener.run();
			} else {
				System.out.println("[Navegador] Não empilhou. pushCurrent=" + pushCurrent);
			}
		} catch (Exception ex) {
			System.err.println("[Navegador] Erro ao empilhar histórico: " + ex.getMessage());
			ex.printStackTrace();
		}
		
		this.prim.mostrarTela(nome);
		if ("SERV".equals(nome)) {
			controller.ListaServicosController.atualizarTabelaSeExistir();
		}
	}

	public void voltar() {
		System.out.println("[Navegador] Método voltar() chamado. Tamanho do histórico: " + history.size());
		if (history.isEmpty()) {
			System.out.println("[Navegador] Histórico vazio, não há para onde voltar");
			return;
		}
		String anterior = history.pop();
		System.out.println("[Navegador] Voltando para: '" + anterior + "'. Novo tamanho do histórico: " + history.size());
		this.prim.fecharDrawerMenuSeAberto();
		this.prim.mostrarTela(anterior);
		if (historyListener != null)
			historyListener.run();
	}

	public void clearHistory() {
		history.clear();
		if (historyListener != null) historyListener.run();
	}

	public void notifyHistoryChange() {
		if (historyListener != null) historyListener.run();
	}

	public void sair() {
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
	
	public void clearCurrentUser() {
		this.currentUser = null;
	}
	
	public void limparImagensPerfil() {
		try {
			String diretorioImagens = System.getProperty("user.dir") + "/src/imagens/";
			java.io.File pastaImagens = new java.io.File(diretorioImagens);
			
			if (pastaImagens.exists() && pastaImagens.isDirectory()) {
				java.io.File[] arquivos = pastaImagens.listFiles();
				if (arquivos != null) {
					for (java.io.File arquivo : arquivos) {
						if (arquivo.isFile() && arquivo.getName().startsWith("FotoPerfil_")) {
							boolean deletado = arquivo.delete();
							if (deletado) {
								System.out.println("[Navegador] Imagem deletada: " + arquivo.getName());
							}
						}
					}
				}
			}
			this.currentUser = null;
		} catch (Exception e) {
			System.err.println("[Navegador] Erro ao limpar imagens de perfil: " + e.getMessage());
			e.printStackTrace();
		}
	}

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