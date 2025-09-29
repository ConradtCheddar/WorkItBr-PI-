package controller;

import javax.swing.*;

import view.wbBarra;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PopupController {
	wbBarra view;
	
	public void PopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu() {
			public Dimension getPreferedSize() {
				return new Dimension(220,250);
			}
		};
		
		JPanel panel = new JPanel(new BorderLayout());
	    panel.setBackground(Color.WHITE);
	    panel.setPreferredSize(new Dimension(220, 250));
	    
	    JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 1, 0, 0)); // 3 linhas, 1 coluna
        topPanel.setBackground(Color.WHITE);
        
        topPanel.add(criarBotaoMenu("Botão 1"));
        topPanel.add(criarBotaoMenu("Botão 2"));
        topPanel.add(criarBotaoMenu("Botão 3"));
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setBackground(Color.WHITE);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        popupMenu.add(panel);
		
	}
	private JButton criarBotaoMenu(String texto) {
	    JButton botao = new JButton(texto);
	    botao.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	            botao.setBackground(new Color(240, 240, 240)); // cinza claro
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	            botao.setBackground(Color.WHITE); // volta para branco
	        }
	    });

	    return botao;
	}

}

