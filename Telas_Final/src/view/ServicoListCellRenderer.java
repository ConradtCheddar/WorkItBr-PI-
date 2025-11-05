// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa a classe de modelo que representa um serviço no sistema
import model.Servico;
// Importa componentes Swing para construção da interface gráfica
import javax.swing.*;
// Importa classes AWT para gerenciamento de layout, cores e fontes
import java.awt.*;

/**
 * Renderizador customizado para exibir objetos Servico em JList.
 * <p>
 * Responsável por: criar uma representação visual personalizada para cada
 * serviço na lista, exibir informações como nome, modalidade, descrição e valor
 * formatados, aplicar cores e estilos específicos para estados
 * selecionado/não-selecionado, e organizar os elementos visualmente de forma
 * hierárquica e legível.
 * </p>
 */
public class ServicoListCellRenderer extends JPanel implements ListCellRenderer<Servico> {
	// Label que exibe o título/nome do serviço em negrito
	private JLabel lblTitle = new JLabel();
	// Label que exibe a modalidade do serviço (presencial, remoto, híbrido)
	private JLabel lblModalidade = new JLabel();
	// Label que exibe a descrição detalhada do serviço
	private JLabel lblDesc = new JLabel();
	// Label que exibe o valor/preço do serviço formatado
	private JLabel lblValue = new JLabel();
	// Painel superior que agrupa título e modalidade horizontalmente
	private JPanel topPanel = new JPanel();
	// Painel central que organiza os elementos verticalmente
	private JPanel centerPanel = new JPanel();

	/**
	 * Construtor que configura o layout e estilo dos componentes do renderizador.
	 * <p>
	 * Define fontes, cores, alinhamentos e organização visual dos elementos.
	 * </p>
	 */
	public ServicoListCellRenderer() {
		// Define BorderLayout como gerenciador de layout principal (centro e lateral
		// direita)
		setLayout(new BorderLayout(10, 0));

		// Configura o painel superior com layout horizontal (BoxLayout em eixo X)
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		// Torna o painel transparente para herdar cor de fundo do pai
		topPanel.setOpaque(false);
		// Configura o título com fonte grande e negrito
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		// Alinha o título à esquerda
		lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
		// Configura a modalidade com fonte menor e cor branca
		lblModalidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModalidade.setForeground(Color.WHITE);
		// Alinha a modalidade à esquerda
		lblModalidade.setAlignmentX(Component.LEFT_ALIGNMENT);
		// Adiciona título ao painel superior
		topPanel.add(lblTitle);
		// Adiciona espaçamento horizontal de 12 pixels entre título e modalidade
		topPanel.add(Box.createHorizontalStrut(12));
		// Adiciona modalidade ao painel superior
		topPanel.add(lblModalidade);
		// Alinha o painel superior à esquerda
		topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Configura o painel central com layout vertical (BoxLayout em eixo Y)
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		// Torna o painel transparente para herdar cor de fundo
		centerPanel.setOpaque(false);
		// Adiciona o painel superior (título + modalidade) ao painel central
		centerPanel.add(topPanel);
		// Adiciona espaçamento vertical de 4 pixels
		centerPanel.add(Box.createVerticalStrut(4));
		// Configura a descrição com fonte normal e cor branca
		lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDesc.setForeground(Color.WHITE);
		// Alinha a descrição à esquerda
		lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
		// Adiciona descrição ao painel central
		centerPanel.add(lblDesc);
		// Alinha o painel central à esquerda
		centerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Adiciona o painel central ao centro do layout principal
		add(centerPanel, BorderLayout.CENTER);
		// Define cor branca para o valor
		lblValue.setForeground(Color.WHITE);

		// Configura o valor com fonte grande e negrito
		lblValue.setFont(new Font("Tahoma", Font.BOLD, 16));
		// Alinha o valor à direita
		lblValue.setHorizontalAlignment(SwingConstants.RIGHT);
		// Adiciona o valor ao lado direito do layout principal
		add(lblValue, BorderLayout.EAST);

		// Adiciona borda inferior cinza de 1 pixel para separar visualmente os itens
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
	}

	/**
	 * Renderiza uma célula da lista com os dados de um serviço.
	 * <p>
	 * Atualiza os textos dos labels com informações do serviço e aplica cores
	 * apropriadas baseadas no estado de seleção.
	 * </p>
	 * 
	 * @param list         lista que contém o elemento
	 * @param value        objeto Servico a ser renderizado
	 * @param index        índice do elemento na lista
	 * @param isSelected   true se o elemento está selecionado
	 * @param cellHasFocus true se a célula tem foco do teclado
	 * @return componente configurado para renderizar a célula
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Servico> list, Servico value, int index,
			boolean isSelected, boolean cellHasFocus) {
		// Define o texto do título com o nome do serviço
		lblTitle.setText(value.getNome_Servico());
		// Define o texto da modalidade
		lblModalidade.setText(value.getModalidade());
		// Define o texto da descrição
		lblDesc.setText(value.getDescricao());
		// Define o texto do valor formatado como moeda brasileira
		lblValue.setText("R$ " + value.getValor());

		// Se o elemento está selecionado, usa cores de seleção da lista
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			// Se não está selecionado, usa cores padrão da lista
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		// Retorna este painel configurado como componente renderizador
		return this;
	}
}
