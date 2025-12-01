package util;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;

public class FontScaler {
    
    private static final int DIVISOR_TITULO = 20;
    private static final int DIVISOR_SUBTITULO = 25;
    private static final int DIVISOR_BOTAO = 25;
    private static final int DIVISOR_TEXTO = 35;
    private static final int DIVISOR_PEQUENO = 45;
    private static final int DIVISOR_BARRA_TITULO = 2;
    
    private static final int MIN_TITULO = 16;
    private static final int MIN_SUBTITULO = 14;
    private static final int MIN_BOTAO = 14;
    private static final int MIN_TEXTO = 12;
    private static final int MIN_PEQUENO = 10;
    private static final int MIN_BARRA_TITULO = 18;
    
    public enum FontSize {
        TITULO(DIVISOR_TITULO, MIN_TITULO),
        SUBTITULO(DIVISOR_SUBTITULO, MIN_SUBTITULO),
        BOTAO(DIVISOR_BOTAO, MIN_BOTAO),
        TEXTO(DIVISOR_TEXTO, MIN_TEXTO),
        PEQUENO(DIVISOR_PEQUENO, MIN_PEQUENO),
        BARRA_TITULO(DIVISOR_BARRA_TITULO, MIN_BARRA_TITULO);
        
        private final int divisor;
        private final int minSize;
        
        FontSize(int divisor, int minSize) {
            this.divisor = divisor;
            this.minSize = minSize;
        }
        
        public int getDivisor() {
            return divisor;
        }
        
        public int getMinSize() {
            return minSize;
        }
    }
    
    public static int calculateFontSize(int panelHeight, FontSize category) {
        return Math.max(category.getMinSize(), panelHeight / category.getDivisor());
    }
    
    public static Font createScaledFont(int panelHeight, FontSize category, String fontName, int style) {
        int fontSize = calculateFontSize(panelHeight, category);
        return new Font(fontName, style, fontSize);
    }
    
    public static Font createScaledFont(int panelHeight, FontSize category) {
        return createScaledFont(panelHeight, category, "Tahoma", Font.PLAIN);
    }
    
    public static void applyScaledFont(Component component, int panelHeight, FontSize category) {
        if (component != null) {
            component.setFont(createScaledFont(panelHeight, category));
        }
    }
    
    public static void applyScaledFont(Component component, int panelHeight, FontSize category, 
                                      String fontName, int style) {
        if (component != null) {
            component.setFont(createScaledFont(panelHeight, category, fontName, style));
        }
    }
    
    public static ComponentAdapter createResizeListener(Object[]... components) {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelHeight = e.getComponent().getHeight();
                for (Object[] pair : components) {
                    if (pair.length >= 2 && pair[0] instanceof Component && pair[1] instanceof FontSize) {
                        Component comp = (Component) pair[0];
                        FontSize size = (FontSize) pair[1];
                        applyScaledFont(comp, panelHeight, size);
                    }
                }
            }
        };
    }
    
    public static void addAutoResize(JComponent panel, Object[]... components) {
        panel.addComponentListener(createResizeListener(components));
    }
    
    /**
     * Adds auto resize listener with custom callback.
     * The callback is executed after font scaling.
     */
    public static void addAutoResizeWithCallback(JComponent panel, Runnable callback, Object[]... components) {
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelHeight = e.getComponent().getHeight();
                for (Object[] pair : components) {
                    if (pair.length >= 2 && pair[0] instanceof Component && pair[1] instanceof FontSize) {
                        Component comp = (Component) pair[0];
                        FontSize size = (FontSize) pair[1];
                        applyScaledFont(comp, panelHeight, size);
                    }
                }
                if (callback != null) {
                    callback.run();
                }
            }
        });
    }
    
    
    
    /**
     * Adds a resize listener that only runs a callback (no automatic font scaling).
     */
    public static void addResizeCallback(JComponent panel, Runnable callback) {
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (callback != null) {
                    callback.run();
                }
            }
        });
    }
}