package view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;

/**
 * RadioButton customizado que redimensiona automaticamente o �cone de sele��o
 * baseado no tamanho da fonte do componente.
 */
public class ScalableRadioButton extends JRadioButton {
    
    private static final long serialVersionUID = 1L;
    private int iconSize = 16; // Tamanho padr�o
    
    public ScalableRadioButton() {
        super();
        updateIconSize();
    }
    
    public ScalableRadioButton(String text) {
        super(text);
        updateIconSize();
    }
    
    /**
     * Atualiza o tamanho do ícone baseado no tamanho da fonte
     */
    public void updateIconSize() {
        Font font = getFont();
        if (font != null) {
            // Usa 75% do tamanho da fonte para um ícone proporcional
            iconSize = Math.max(11, (int)(font.getSize() * 0.75));
            setIcon(new ScalableRadioIcon(iconSize, false));
            setSelectedIcon(new ScalableRadioIcon(iconSize, true));
        }
    }
    
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        updateIconSize();
    }
    
    /**
     * Define manualmente o tamanho do �cone
     */
    public void setIconSize(int size) {
        this.iconSize = size;
        setIcon(new ScalableRadioIcon(iconSize, false));
        setSelectedIcon(new ScalableRadioIcon(iconSize, true));
    }
    
    /**
     * Classe interna para renderizar o �cone do RadioButton de forma escal�vel
     */
    private static class ScalableRadioIcon implements Icon {
        private int size;
        private boolean selected;
        
        public ScalableRadioIcon(int size, boolean selected) {
            this.size = size;
            this.selected = selected;
        }
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            AbstractButton button = (AbstractButton) c;
            
            // Cores
            Color borderColor = button.isEnabled() ? 
                UIManager.getColor("RadioButton.icon.borderColor") != null ? 
                    UIManager.getColor("RadioButton.icon.borderColor") : new Color(120, 120, 120) 
                : new Color(200, 200, 200);
            
            Color fillColor = button.isEnabled() ? 
                UIManager.getColor("RadioButton.icon.selectedForeground") != null ? 
                    UIManager.getColor("RadioButton.icon.selectedForeground") : new Color(0, 102, 204) 
                : new Color(180, 180, 180);
            
            Color backgroundColor = button.isEnabled() ? 
                UIManager.getColor("RadioButton.background") != null ? 
                    UIManager.getColor("RadioButton.background") : Color.WHITE 
                : new Color(240, 240, 240);
            
            // Desenha o círculo externo (fundo)
            g2.setColor(backgroundColor);
            g2.fillOval(x, y, size, size);
            
            // Desenha a borda
            g2.setColor(borderColor);
            g2.setStroke(new java.awt.BasicStroke(Math.max(1, size / 12f)));
            g2.drawOval(x + 1, y + 1, size - 2, size - 2);
            
            // Se selecionado, desenha o círculo interno
            if (selected) {
                g2.setColor(fillColor);
                // Calcula o tamanho do círculo interno (48% do tamanho total)
                int innerSize = (int) Math.round(size * 0.48);
                // Garante que o tamanho seja ímpar se size for ímpar, par se size for par
                // Isso ajuda na centralização perfeita
                if ((size % 2) != (innerSize % 2)) {
                    innerSize = Math.max(1, innerSize - 1);
                }
                // Centraliza o círculo interno
                int offset = (size - innerSize) / 2;
                g2.fillOval(x + offset, y + offset, innerSize, innerSize);
            }
            
            g2.dispose();
        }
        
        @Override
        public int getIconWidth() {
            return size;
        }
        
        @Override
        public int getIconHeight() {
            return size;
        }
    }
}
