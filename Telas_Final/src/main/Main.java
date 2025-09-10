package main;

import javax.swing.UIManager;

import view.Primario;

public class Main {
	public static void main(String[] args) {
	       try {
	    	   UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
	        } catch (Exception ex) {
	            System.err.println("Falha ao carregar o tema FlatLaf");
	        }
	       
		Primario frame = new Primario();
		frame.setVisible(true);
	}
}
