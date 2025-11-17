package view;

import javax.swing.JOptionPane;

public class Mensagem {
	
	public void Erro(String mensagem, String titulo){
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
	}
	public void Sucesso(String mensagem, String titulo){
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.PLAIN_MESSAGE);
	}
	public void Aviso(String mensagem, String titulo){
		JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.WARNING_MESSAGE);
	}
	

}
