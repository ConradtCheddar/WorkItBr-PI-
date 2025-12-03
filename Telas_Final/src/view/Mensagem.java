package view;

import javax.swing.*;
import util.FontScaler;
import util.FontScaler.FontSize;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Mensagem {

    public void Erro(String mensagem, String titulo) {
        // Estimativa de tamanho inicial baseada no comprimento da mensagem
        int estWidth = Math.max(480, Math.min(900, mensagem != null ? mensagem.length() * 7 + 200 : 480));
        int estHeight = 220;

        // Calcular fontes baseadas na estimativa (antes de criar o OptionPane)
        int maiorDimensao = Math.max(estWidth, estHeight);
        Font textoFont = new Font("Tahoma", Font.PLAIN, Math.max(FontScaler.calculateFontSize(maiorDimensao, FontSize.TEXTO), 12));
        Font botaoFont = new Font("Tahoma", Font.BOLD, Math.max(FontScaler.calculateFontSize(maiorDimensao, FontSize.BOTAO), 14));

        // Salvar valores antigos do UIManager e aplicar temporariamente
        Object oldMsgFont = UIManager.get("OptionPane.messageFont");
        Object oldBtnFont = UIManager.get("OptionPane.buttonFont");
        UIManager.put("OptionPane.messageFont", textoFont);
        UIManager.put("OptionPane.buttonFont", botaoFont);

        JOptionPane optionPane = new JOptionPane(mensagem, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog(titulo);

        // Aplicar prefered size estimado e forçar layout com a fonte já configurada
        dialog.setPreferredSize(new Dimension(estWidth, estHeight));
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        // Aplicar fontes explicitamente na hierarquia (resiliência)
        aplicarFonts(dialog, optionPane);

        dialog.addComponentListener(new ComponentAdapter() {
            private boolean fonteAplicada = true;

            @Override
            public void componentShown(ComponentEvent e) {
                if (!fonteAplicada) {
                    fonteAplicada = true;
                    aplicarFonts(dialog, optionPane);
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                }
            }

            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> aplicarFonts(dialog, optionPane));
            }
        });

        dialog.setVisible(true);

        // Restaurar valores antigos do UIManager
        UIManager.put("OptionPane.messageFont", oldMsgFont);
        UIManager.put("OptionPane.buttonFont", oldBtnFont);
    }

    public void Sucesso(String mensagem, String titulo) {
        int estWidth = Math.max(480, Math.min(900, mensagem != null ? mensagem.length() * 7 + 200 : 480));
        int estHeight = 200;

        int maiorDimensao = Math.max(estWidth, estHeight);
        Font textoFont = new Font("Tahoma", Font.PLAIN, Math.max(FontScaler.calculateFontSize(maiorDimensao, FontSize.TEXTO), 12));
        Font botaoFont = new Font("Tahoma", Font.BOLD, Math.max(FontScaler.calculateFontSize(maiorDimensao, FontSize.BOTAO), 14));

        Object oldMsgFont = UIManager.get("OptionPane.messageFont");
        Object oldBtnFont = UIManager.get("OptionPane.buttonFont");
        UIManager.put("OptionPane.messageFont", textoFont);
        UIManager.put("OptionPane.buttonFont", botaoFont);

        JOptionPane optionPane = new JOptionPane(mensagem, JOptionPane.PLAIN_MESSAGE);
        JDialog dialog = optionPane.createDialog(titulo);

        dialog.setPreferredSize(new Dimension(estWidth, estHeight));
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        aplicarFonts(dialog, optionPane);

        dialog.addComponentListener(new ComponentAdapter() {
            private boolean fonteAplicada = true;

            @Override
            public void componentShown(ComponentEvent e) {
                if (!fonteAplicada) {
                    fonteAplicada = true;
                    aplicarFonts(dialog, optionPane);
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                }
            }

            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> aplicarFonts(dialog, optionPane));
            }
        });

        dialog.setVisible(true);

        UIManager.put("OptionPane.messageFont", oldMsgFont);
        UIManager.put("OptionPane.buttonFont", oldBtnFont);
    }

    public void Aviso(String mensagem, String titulo) {
        int estWidth = Math.max(480, Math.min(900, mensagem != null ? mensagem.length() * 7 + 200 : 480));
        int estHeight = 200;

        int maiorDimensao = Math.max(estWidth, estHeight);
        Font textoFont = new Font("Tahoma", Font.PLAIN, Math.max(FontScaler.calculateFontSize(maiorDimensao, FontSize.TEXTO), 12));
        Font botaoFont = new Font("Tahoma", Font.BOLD, Math.max(FontScaler.calculateFontSize(maiorDimensao, FontSize.BOTAO), 14));

        Object oldMsgFont = UIManager.get("OptionPane.messageFont");
        Object oldBtnFont = UIManager.get("OptionPane.buttonFont");
        UIManager.put("OptionPane.messageFont", textoFont);
        UIManager.put("OptionPane.buttonFont", botaoFont);

        JOptionPane optionPane = new JOptionPane(mensagem, JOptionPane.WARNING_MESSAGE);
        JDialog dialog = optionPane.createDialog(titulo);

        dialog.setPreferredSize(new Dimension(estWidth, estHeight));
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        aplicarFonts(dialog, optionPane);

        dialog.addComponentListener(new ComponentAdapter() {
            private boolean fonteAplicada = true;

            @Override
            public void componentShown(ComponentEvent e) {
                if (!fonteAplicada) {
                    fonteAplicada = true;
                    aplicarFonts(dialog, optionPane);
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                }
            }

            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> aplicarFonts(dialog, optionPane));
            }
        });

        dialog.setVisible(true);

        UIManager.put("OptionPane.messageFont", oldMsgFont);
        UIManager.put("OptionPane.buttonFont", oldBtnFont);
    }
    
    // Método para aplicar fontes escaladas
    private void aplicarFonts(JDialog dialog, JOptionPane optionPane) {
        int dialogHeight = dialog.getHeight();
        int dialogWidth = dialog.getWidth();
        if (dialogHeight <= 0) {
            dialogHeight = 200;
        }
        if (dialogWidth <= 0) {
            dialogWidth = 450;
        }
        
        // Usar a maior dimensão para calcular fonte
        int maiorDimensao = Math.max(dialogHeight, dialogWidth);
        
        // Calcular tamanho de fonte baseado na altura do dialog
        int textoFontSize = FontScaler.calculateFontSize(maiorDimensao, FontSize.TEXTO);
        int botaoFontSize = FontScaler.calculateFontSize(maiorDimensao, FontSize.BOTAO);
        
        // Garantir tamanho mínimo
        textoFontSize = Math.max(textoFontSize, 12);
        botaoFontSize = Math.max(botaoFontSize, 14);
        
        Font textoFont = new Font("Tahoma", Font.PLAIN, textoFontSize);
        Font botaoFont = new Font("Tahoma", Font.BOLD, botaoFontSize);
        
        // Aplicar fonte recursivamente em TODO o dialog
        aplicarFontRecursivoEmTodos(dialog.getContentPane(), textoFont, botaoFont);
        
        // Aplicar também diretamente no JOptionPane
        optionPane.setFont(textoFont);
        
        // Aplicar no título da janela se houver
        if (dialog.getTitle() != null) {
            dialog.setFont(textoFont);
        }
        
        dialog.revalidate();
        dialog.repaint();
    }
    
    // Método recursivo para aplicar fonte em TODOS os componentes
    private void aplicarFontRecursivoEmTodos(Component comp, Font textoFont, Font botaoFont) {
        if (comp == null) return;
        
        // Aplicar fonte ao componente
        if (comp instanceof JButton) {
            ((JButton) comp).setFont(botaoFont);
        } else if (comp instanceof JLabel) {
            ((JLabel) comp).setFont(textoFont);
        } else if (comp instanceof JOptionPane) {
            comp.setFont(textoFont);
        } else {
            comp.setFont(textoFont);
        }
        
        // Navegar pelos filhos recursivamente
        if (comp instanceof Container) {
            Component[] children = ((Container) comp).getComponents();
            for (Component child : children) {
                aplicarFontRecursivoEmTodos(child, textoFont, botaoFont);
            }
        }
    }
    
    // Método para encontrar botão dentro da hierarquia de componentes
    private JButton findButton(Container container, int index) {
        int[] count = {index};
        return findButtonRecursive(container, count);
    }
    
    private JButton findButtonRecursive(Component comp, int[] count) {
        if (comp instanceof JButton) {
            if (count[0] == 0) {
                return (JButton) comp;
            }
            count[0]--;
            return null;
        }
        
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                JButton btn = findButtonRecursive(child, count);
                if (btn != null) {
                    return btn;
                }
            }
        }
        
        return null;
    }
}