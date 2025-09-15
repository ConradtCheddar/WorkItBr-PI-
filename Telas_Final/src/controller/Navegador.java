package controller;

import java.awt.CardLayout;

import javax.swing.JPanel;

import view.Primario;

public class Navegador {
	private Primario prim;
	
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
		this.prim.mostrarTela(nome);
	}
	
	public void sair() {
		this.prim.dispose();
	}
	




}
