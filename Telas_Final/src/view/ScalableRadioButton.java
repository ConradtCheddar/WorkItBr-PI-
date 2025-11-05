// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa Component, classe base para todos os componentes visuais
import java.awt.Component;
// Importa Graphics para operações básicas de desenho
import java.awt.Graphics;
// Importa Graphics2D para desenho avançado com antialiasing e transformações
import java.awt.Graphics2D;
// Importa RenderingHints para configurar qualidade de renderização (suavização)
import java.awt.RenderingHints;
// Importa AbstractButton, classe base para todos os tipos de botões
import javax.swing.AbstractButton;
// Importa Icon, interface para objetos que podem ser desenhados como ícones
import javax.swing.Icon;
// Importa JRadioButton, componente padrão de seleção única em grupo
import javax.swing.JRadioButton;
// Importa UIManager para acessar cores e propriedades do tema visual
import javax.swing.UIManager;
// Importa Color para definição de cores personalizadas
import java.awt.Color;
// Importa Font para trabalhar com fontes de texto
import java.awt.Font;

/**
 * RadioButton customizado que redimensiona automaticamente o ícone de seleção
 * baseado no tamanho da fonte do componente.
 * <p>
 * Responsável por: criar um RadioButton que se adapta ao tamanho da fonte,
 * renderizar ícones personalizados com antialiasing, garantir proporcionalidade
 * visual mesmo quando a fonte é escalada, e fornecer ícones customizados para
 * estados selecionado e não-selecionado.
 * </p>
 */
public class ScalableRadioButton extends JRadioButton {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;
	// Tamanho atual do ícone em pixels (calculado baseado na fonte)
	private int iconSize = 16;

	/**
	 * Construtor padrão sem texto.
	 * <p>
	 * Cria um RadioButton vazio e atualiza o tamanho do ícone baseado na fonte
	 * atual.
	 * </p>
	 */
	public ScalableRadioButton() {
		// Chama o construtor da classe pai (JRadioButton)
		super();
		// Calcula e define o tamanho apropriado do ícone
		updateIconSize();
	}

	/**
	 * Construtor com texto de rótulo.
	 * <p>
	 * Cria um RadioButton com texto e atualiza o tamanho do ícone baseado na fonte
	 * atual.
	 * </p>
	 * 
	 * @param text texto que aparecerá ao lado do ícone de seleção
	 */
	public ScalableRadioButton(String text) {
		// Chama o construtor da classe pai passando o texto
		super(text);
		// Calcula e define o tamanho apropriado do ícone
		updateIconSize();
	}

	/**
	 * Atualiza o tamanho do ícone baseado no tamanho da fonte atual do componente.
	 * <p>
	 * Calcula o tamanho do ícone como 75% do tamanho da fonte, com mínimo de 11
	 * pixels. Recria os ícones (normal e selecionado) com o novo tamanho.
	 * </p>
	 */
	public void updateIconSize() {
		// Obtém a fonte atualmente configurada no componente
		Font font = getFont();
		// Verifica se a fonte não é nula (pode ser nula durante inicialização)
		if (font != null) {
			// Calcula o tamanho do ícone como 75% do tamanho da fonte, mínimo de 11 pixels
			// Isso garante proporcionalidade visual com o texto
			iconSize = Math.max(11, (int) (font.getSize() * 0.75));
			// Cria e define o ícone para o estado não-selecionado
			setIcon(new ScalableRadioIcon(iconSize, false));
			// Cria e define o ícone para o estado selecionado
			setSelectedIcon(new ScalableRadioIcon(iconSize, true));
		}
	}

	/**
	 * Sobrescreve o método setFont para atualizar o ícone quando a fonte muda.
	 * <p>
	 * Garante que o ícone seja redimensionado automaticamente sempre que a fonte é
	 * alterada.
	 * </p>
	 * 
	 * @param font nova fonte a ser aplicada ao componente
	 */
	@Override
	public void setFont(Font font) {
		// Chama o método da classe pai para aplicar a fonte
		super.setFont(font);
		// Recalcula e atualiza o tamanho do ícone baseado na nova fonte
		updateIconSize();
	}

	/**
	 * Define manualmente o tamanho do ícone, sobrescrevendo o cálculo automático.
	 * <p>
	 * Útil quando se deseja um tamanho específico independente da fonte.
	 * </p>
	 * 
	 * @param size tamanho desejado do ícone em pixels
	 */
	public void setIconSize(int size) {
		// Armazena o novo tamanho
		this.iconSize = size;
		// Recria o ícone não-selecionado com o novo tamanho
		setIcon(new ScalableRadioIcon(iconSize, false));
		// Recria o ícone selecionado com o novo tamanho
		setSelectedIcon(new ScalableRadioIcon(iconSize, true));
	}

	/**
	 * Classe interna para renderizar o ícone do RadioButton de forma escalável.
	 * <p>
	 * Responsável por: desenhar círculos personalizados para representar o
	 * RadioButton, aplicar antialiasing para bordas suaves, usar cores do tema
	 * atual (UIManager), e renderizar o indicador visual de seleção (círculo
	 * interno preenchido).
	 * </p>
	 */
	private static class ScalableRadioIcon implements Icon {
		// Tamanho total do ícone em pixels
		private int size;
		// Flag que indica se o ícone representa o estado selecionado
		private boolean selected;

		/**
		 * Construtor do ícone personalizado.
		 * 
		 * @param size     tamanho do ícone em pixels
		 * @param selected true se o ícone representa estado selecionado, false caso
		 *                 contrário
		 */
		public ScalableRadioIcon(int size, boolean selected) {
			// Armazena o tamanho do ícone
			this.size = size;
			// Armazena o estado de seleção
			this.selected = selected;
		}

		/**
		 * Renderiza o ícone do RadioButton com qualidade visual aprimorada.
		 * <p>
		 * Desenha: círculo de fundo, borda circular, e círculo interno se selecionado.
		 * Aplica antialiasing para suavização e usa cores adaptativas do tema.
		 * </p>
		 * 
		 * @param c componente onde o ícone será desenhado
		 * @param g contexto gráfico para desenho
		 * @param x posição horizontal onde desenhar
		 * @param y posição vertical onde desenhar
		 */
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			// Cria uma cópia do contexto gráfico como Graphics2D para recursos avançados
			Graphics2D g2 = (Graphics2D) g.create();
			// Ativa antialiasing para bordas suaves e círculos sem serrilhado
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Converte o componente para AbstractButton para acessar estado
			// (habilitado/desabilitado)
			AbstractButton button = (AbstractButton) c;

			// Define cores adaptativas baseadas no estado do botão e tema atual
			// Cor da borda: cinza escuro se habilitado, cinza claro se desabilitado
			Color borderColor = button.isEnabled() ? UIManager.getColor("RadioButton.icon.borderColor") != null
					? UIManager.getColor("RadioButton.icon.borderColor")
					: new Color(120, 120, 120) : new Color(200, 200, 200);

			// Cor de preenchimento quando selecionado: azul se habilitado, cinza se
			// desabilitado
			Color fillColor = button.isEnabled() ? UIManager.getColor("RadioButton.icon.selectedForeground") != null
					? UIManager.getColor("RadioButton.icon.selectedForeground")
					: new Color(0, 102, 204) : new Color(180, 180, 180);

			// Cor de fundo: branco se habilitado, cinza claro se desabilitado
			Color backgroundColor = button.isEnabled() ? UIManager.getColor("RadioButton.background") != null
					? UIManager.getColor("RadioButton.background")
					: Color.WHITE : new Color(240, 240, 240);

			// Desenha o círculo externo (fundo branco/cinza)
			g2.setColor(backgroundColor);
			g2.fillOval(x, y, size, size);

			// Desenha a borda circular com espessura proporcional ao tamanho
			g2.setColor(borderColor);
			g2.setStroke(new java.awt.BasicStroke(Math.max(1, size / 12f)));
			g2.drawOval(x + 1, y + 1, size - 2, size - 2);

			// Se o RadioButton está selecionado, desenha o círculo interno preenchido
			if (selected) {
				g2.setColor(fillColor);
				// Calcula o tamanho do círculo interno (48% do tamanho total)
				int innerSize = (int) Math.round(size * 0.48);
				// Garante que o tamanho tenha a mesma paridade que size (ambos pares ou
				// ímpares)
				// Isso ajuda na centralização perfeita pixel-a-pixel
				if ((size % 2) != (innerSize % 2)) {
					innerSize = Math.max(1, innerSize - 1);
				}
				// Calcula o deslocamento para centralizar o círculo interno
				int offset = (size - innerSize) / 2;
				// Desenha o círculo interno preenchido
				g2.fillOval(x + offset, y + offset, innerSize, innerSize);
			}

			// Libera os recursos do contexto gráfico
			g2.dispose();
		}

		/**
		 * Retorna a largura do ícone.
		 * 
		 * @return largura em pixels
		 */
		@Override
		public int getIconWidth() {
			return size;
		}

		/**
		 * Retorna a altura do ícone.
		 * 
		 * @return altura em pixels
		 */
		@Override
		public int getIconHeight() {
			return size;
		}
	}
}
