// Define o pacote onde esta classe está localizada
package controller;

// Importa BorderLayout, gerenciador de layout que organiza componentes em 5 regiões
import java.awt.BorderLayout;
// Importa Color para definir cores de componentes
import java.awt.Color;
// Importa Dimension para definir tamanhos de componentes
import java.awt.Dimension;
// Importa FlowLayout, gerenciador que organiza componentes em linha
import java.awt.FlowLayout;
// Importa GridLayout, gerenciador que organiza componentes em grade
import java.awt.GridLayout;
// Importa MouseAdapter para criar listeners de eventos de mouse
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;

// Importa JButton, componente de botão Swing
import javax.swing.JButton;
// Importa JComponent, classe base para componentes Swing
import javax.swing.JComponent;
// Importa JPanel, componente painel Swing
import javax.swing.JPanel;
// Importa JPopupMenu, componente de menu popup Swing
import javax.swing.JPopupMenu;

/**
 * Controlador utilitário para exibir um menu popup customizado.
 * <p>
 * Constrói um {@link JPopupMenu} com aparência personalizada (tamanho fixo,
 * painel superior com botões e painel inferior reservado para futuras ações).
 * O método público {@link #PopupMenu(MouseEvent, JComponent)} mostra o popup
 * na posição do evento do mouse passado como parâmetro.
 * </p>
 */
public class PopupController {

	/**
	 * Cria e exibe um menu popup na posição do evento do mouse.
	 *
	 * @param e evento de mouse que determina onde o popup aparecerá
	 * @param parent componente pai usado como referência para posicionamento
	 */
	public void PopupMenu(MouseEvent e, JComponent parent) {
		// Popup com tamanho preferencial fixo
		// Cria um JPopupMenu customizado com tamanho fixo definido
		JPopupMenu popupMenu = new JPopupMenu() {
			@Override
			public Dimension getPreferredSize() {
				// Define o tamanho preferencial como 220x250 pixels
				return new Dimension(220, 250);
			}
		};

		// Painel principal do popup com background escuro
		// Cria o painel principal que conterá todos os elementos do popup
		JPanel panel = new JPanel(new BorderLayout());
		// Define a cor de fundo como cinza escuro
		panel.setBackground(Color.DARK_GRAY);
		// Define o tamanho preferencial do painel
		panel.setPreferredSize(new Dimension(220, 250));

		// Topo: grade vertical de botões (3x1)
		// Cria um painel superior com layout de grade de 3 linhas e 1 coluna
		JPanel topPanel = new JPanel(new GridLayout(3, 1, 0, 0));
		// Define a cor de fundo como cinza escuro
		topPanel.setBackground(Color.DARK_GRAY);

		// Adiciona três botões ao painel superior
		topPanel.add(criarBotaoMenu("Botão 1"));
		topPanel.add(criarBotaoMenu("Botão 2"));
		topPanel.add(criarBotaoMenu("Botão 3"));

		// Rodapé: espaço para botões auxiliares (centralizados)
		// Cria um painel inferior com layout FlowLayout centralizado
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		// Define a cor de fundo como cinza escuro
		bottomPanel.setBackground(Color.DARK_GRAY);

		// Adiciona o painel superior à região NORTH do BorderLayout
		panel.add(topPanel, BorderLayout.NORTH);
		// Adiciona o painel inferior à região SOUTH do BorderLayout
		panel.add(bottomPanel, BorderLayout.SOUTH);

		// Adiciona o painel principal ao popup menu
		popupMenu.add(panel);

		// Mostra o popup na posição do evento dentro do componente pai
		// Exibe o popup nas coordenadas x,y do evento de mouse relativas ao parent
		popupMenu.show(parent, e.getX(), e.getY());
	}

	/**
	 * Cria um botão estilizado para o menu popup com handlers de hover.
	 *
	 * @param texto texto do botão
	 * @return instância de {@link JButton}
	 */
	private JButton criarBotaoMenu(String texto) {
		// Cria um novo botão com o texto especificado
		JButton botao = new JButton(texto);
		// Define a cor de fundo do botão como azul
		botao.setBackground(new Color(0, 102, 204));

		// Alteração de cor ao passar/retirar o mouse para efeito visual
		// Adiciona listener de mouse para efeito hover
		botao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// Quando o mouse entra no botão, muda para um azul ligeiramente mais claro
				botao.setBackground(new Color(0, 110, 204));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Quando o mouse sai do botão, volta para a cor original
				botao.setBackground(new Color(0, 102, 204));
			}
		});

		// Retorna o botão configurado
		return botao;
	}

} // Fim da classe PopupController