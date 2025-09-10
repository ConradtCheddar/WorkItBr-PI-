package controller;

import java.awt.CardLayout;

import javax.swing.JPanel;

import view.Primario;

public class TelaController {
	private Primario prim;
	
	public TelaController(Primario prim) {
		this.prim = prim;
	}
	public void adicionarPainel(String nome, JPanel tela) {
		this.prim.adicionarTela(nome, tela);
	}
	




}
