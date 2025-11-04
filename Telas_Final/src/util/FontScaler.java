package util;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JComponent;

/**
 * Utilitário para padronizar o redimensionamento de fontes em toda a aplicação.
 * 
 * Categorias de tamanho:
 * - TITULO: Títulos principais (ex: "Login", "Cadastro")
 * - SUBTITULO: Subtítulos e labels importantes
 * - TEXTO: Texto padrão, campos de entrada
 * - BOTAO: Botões de ação
 * - PEQUENO: Textos auxiliares, hints
 */
public class FontScaler {
    
    // Constantes para os divisores de altura
    private static final int DIVISOR_TITULO = 20;      // panelHeight / 20
    private static final int DIVISOR_SUBTITULO = 25;   // panelHeight / 25
    private static final int DIVISOR_BOTAO = 25;       // panelHeight / 25
    private static final int DIVISOR_TEXTO = 35;       // panelHeight / 35
    private static final int DIVISOR_PEQUENO = 45;     // panelHeight / 45
    
    // Tamanhos mínimos para cada categoria
    private static final int MIN_TITULO = 16;
    private static final int MIN_SUBTITULO = 14;
    private static final int MIN_BOTAO = 14;
    private static final int MIN_TEXTO = 12;
    private static final int MIN_PEQUENO = 10;
    
    /**
     * Enum para as categorias de tamanho de fonte
     */
    public enum FontSize {
        TITULO(DIVISOR_TITULO, MIN_TITULO),
        SUBTITULO(DIVISOR_SUBTITULO, MIN_SUBTITULO),
        BOTAO(DIVISOR_BOTAO, MIN_BOTAO),
        TEXTO(DIVISOR_TEXTO, MIN_TEXTO),
        PEQUENO(DIVISOR_PEQUENO, MIN_PEQUENO);
        
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
    
    /**
     * Calcula o tamanho da fonte baseado na altura do painel e categoria
     * 
     * @param panelHeight Altura atual do painel
     * @param category Categoria do tamanho da fonte
     * @return Tamanho calculado da fonte
     */
    public static int calculateFontSize(int panelHeight, FontSize category) {
        return Math.max(category.getMinSize(), panelHeight / category.getDivisor());
    }
    
    /**
     * Cria uma fonte com tamanho escalável
     * 
     * @param panelHeight Altura atual do painel
     * @param category Categoria do tamanho da fonte
     * @param fontName Nome da fonte (ex: "Tahoma")
     * @param style Estilo da fonte (Font.PLAIN, Font.BOLD, etc.)
     * @return Font configurada
     */
    public static Font createScaledFont(int panelHeight, FontSize category, String fontName, int style) {
        int fontSize = calculateFontSize(panelHeight, category);
        return new Font(fontName, style, fontSize);
    }
    
    /**
     * Cria uma fonte Tahoma PLAIN com tamanho escalável
     * 
     * @param panelHeight Altura atual do painel
     * @param category Categoria do tamanho da fonte
     * @return Font configurada
     */
    public static Font createScaledFont(int panelHeight, FontSize category) {
        return createScaledFont(panelHeight, category, "Tahoma", Font.PLAIN);
    }
    
    /**
     * Aplica fonte escalável a um componente
     * 
     * @param component Componente a ser atualizado
     * @param panelHeight Altura atual do painel
     * @param category Categoria do tamanho da fonte
     */
    public static void applyScaledFont(Component component, int panelHeight, FontSize category) {
        if (component != null) {
            component.setFont(createScaledFont(panelHeight, category));
        }
    }
    
    /**
     * Aplica fonte escalável a um componente com nome e estilo customizados
     * 
     * @param component Componente a ser atualizado
     * @param panelHeight Altura atual do painel
     * @param category Categoria do tamanho da fonte
     * @param fontName Nome da fonte
     * @param style Estilo da fonte
     */
    public static void applyScaledFont(Component component, int panelHeight, FontSize category, 
                                      String fontName, int style) {
        if (component != null) {
            component.setFont(createScaledFont(panelHeight, category, fontName, style));
        }
    }
    
    /**
     * Cria um ComponentAdapter para redimensionamento automático de múltiplos componentes
     * 
     * @param components Array de objetos, onde cada objeto é um array [Component, FontSize]
     * @return ComponentAdapter configurado
     */
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
    
    /**
     * Adiciona listener de redimensionamento a um JComponent
     * 
     * @param panel Painel que será monitorado
     * @param components Array de objetos [Component, FontSize]
     */
    public static void addAutoResize(JComponent panel, Object[]... components) {
        panel.addComponentListener(createResizeListener(components));
    }
}
