package controller;

import java.awt.CardLayout;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Consumer;

import javax.swing.JPanel;

import model.Usuario;
import view.Primario;
import view.wbBarra;

public class Navegador {
	private Primario prim;
	private Usuario currentUser;
	// stack to hold navigation history (names of panels)
	private final Deque<String> history = new ArrayDeque<>();
	// optional listener to notify UI controllers when history changes
	private Runnable historyListener;

	public Navegador(Primario prim) {
		this.prim = prim;

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

	public void navegarPara(String nome) {
		this.prim.fecharDrawerMenuSeAberto(); // Fecha o DrawerMenu se estiver aberto
		// Push current panel onto history before navigating, if present and different
		try {
			String current = this.prim.getCurrentPanelName();
			if (current != null && !current.equals(nome)) {
				history.push(current);
				// notify listener that history changed
				if (historyListener != null)
					historyListener.run();
			}
		} catch (Exception ex) {
			// If prim doesn't track current panel for some reason, ignore
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

	public void sair() {
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

}