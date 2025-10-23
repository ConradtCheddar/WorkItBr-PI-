package controller;

import java.awt.CardLayout;

import javax.swing.JPanel;

import model.Usuario;
import view.Primario;
import view.wbBarra;

public class Navegador {
	private Primario prim;
	private Usuario currentUser;
	
	public Navegador(Primario prim) {
		this.prim = prim;
		
	}
	/**
	 * metodo para adicionar um Jpanel
	 * @param nome
	 * @param tela
	 */
	public void adicionarPainel(String nome, JPanel tela) {
		this.prim.adicionarTela(nome, tela);
		
	}
	
	/**
	 * Remove um painel do CardLayout
	 * @param nome Nome do painel a ser removido
	 */
	public void removerPainel(String nome) {
		this.prim.removerTela(nome);
	}
	
	public void navegarPara(String nome) {
		System.out.println("[Navegador] navegarPara(" + nome + ") chamado");
		this.prim.fecharDrawerMenuSeAberto(); // Fecha o DrawerMenu se estiver aberto
		this.prim.mostrarTela(nome);
		System.out.println("[Navegador] Tela " + nome + " mostrada");
		if ("SERVICOS".equals(nome)) {
			controller.ListaServicosController.atualizarTabelaSeExistir();
		}
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
	
}