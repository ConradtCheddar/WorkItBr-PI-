package controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class PopupController {
    
    public void PopupMenu(MouseEvent e, JComponent parent) {
        JPopupMenu popupMenu = new JPopupMenu() {
            @Override
            public Dimension getPreferredSize() {  
                return new Dimension(220, 250);
            }
        };
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(220, 250));

        JPanel topPanel = new JPanel(new GridLayout(3, 1, 0, 0));
        topPanel.setBackground(Color.WHITE);

        topPanel.add(criarBotaoMenu("Botão 1"));
        topPanel.add(criarBotaoMenu("Botão 2"));
        topPanel.add(criarBotaoMenu("Botão 3"));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        bottomPanel.setBackground(Color.WHITE);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        popupMenu.add(panel);

      
        popupMenu.show(parent, e.getX(), e.getY());
    }

    private JButton criarBotaoMenu(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(Color.WHITE);

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(new Color(240, 240, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(Color.WHITE);
            }
        });

        return botao;
    }
}
