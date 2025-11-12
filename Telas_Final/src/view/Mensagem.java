package view;

import javax.swing.JOptionPane;

public class Mensagem {
	
	public void camposVazios{
		JOptionPane.showMessageDialog(null, "Preencha todos os campos requisitados", "Erro!!",
				JOptionPane.ERROR);
	}

}
