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
	
	public void navegarPara(String nome) {
		this.prim.fecharDrawerMenuSeAberto(); // Fecha o DrawerMenu se estiver aberto
		this.prim.mostrarTela(nome);
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
	
	
}