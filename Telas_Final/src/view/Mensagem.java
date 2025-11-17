package view;

import javax.swing.JOptionPane;

public class Mensagem {
	
	public void Erro(String mensagem, String titulo){
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR);
	}
	public void Sucesso(String mensagem, String titulo){
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.PLAIN_MESSAGE);
	}
	

}
