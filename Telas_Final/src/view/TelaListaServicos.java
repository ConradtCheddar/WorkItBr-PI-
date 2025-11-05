// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa Color para definição de cores personalizadas
import java.awt.Color;
// Importa Cursor para definir aparência do cursor do mouse
import java.awt.Cursor;
// Importa Dimension para definir dimensões de componentes
import java.awt.Dimension;
// Importa Font para trabalhar com fontes de texto
import java.awt.Font;
// Importa Image para manipulação de imagens
import java.awt.Image;
// Importa ActionListener para capturar eventos de clique em botões
import java.awt.event.ActionListener;
// Importa ComponentAdapter para responder a eventos de redimensionamento
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes
import java.awt.event.ComponentEvent;
// Importa MouseListener para capturar eventos de mouse
import java.awt.event.MouseListener;
// Importa ArrayList para listas dinâmicas
import java.util.ArrayList;

// Importa ButtonGroup para agrupar botões de opção exclusiva
import javax.swing.ButtonGroup;
// Importa ImageIcon para trabalhar com ícones de imagem
import javax.swing.ImageIcon;
// Importa JButton, componente de botão clicável
import javax.swing.JButton;
// Importa JLabel para exibição de textos e imagens
import javax.swing.JLabel;
// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa JRadioButton para botões de seleção exclusiva
import javax.swing.JRadioButton;
// Importa SwingConstants para constantes de alinhamento
import javax.swing.SwingConstants;
// Importa EmptyBorder para criar bordas vazias (espaçamento)
import javax.swing.border.EmptyBorder;

// Importa FlatClientProperties para propriedades customizadas do tema FlatLaf
import com.formdev.flatlaf.FlatClientProperties;

// Importa a classe de modelo que representa um serviço no sistema
import model.Servico;
// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;
// Importa JTextArea para áreas de texto multilinhas
import javax.swing.JTextArea;
// Importa JScrollPane para adicionar barras de rolagem a componentes
import javax.swing.JScrollPane;
// Importa JTable para exibir dados em formato tabular
import javax.swing.JTable;
// Importa DefaultTableModel, modelo de dados padrão e editável para JTable
import javax.swing.table.DefaultTableModel;

/**
 * Tela que exibe uma lista/tabela de serviços do usuário contratante.
 * <p>
 * Responsável por: exibir serviços em formato de tabela editável, fornecer
 * botões para visualizar, editar, deletar e cadastrar serviços, atualizar
 * dinamicamente a tabela com dados do banco, permitir edição inline de células
 * (exceto ID), e integrar-se com o controller para processar ações do usuário.
 * </p>
 */
public class TelaListaServicos extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;
	// Painel com barra de rolagem para a tabela (não utilizado atualmente -
	// possível legado)
	private JScrollPane scrollPane;
	// Modelo de dados que gerencia o conteúdo e estrutura da tabela
	private DefaultTableModel tableModel;
	// Callback executado quando a tela é mostrada (para atualizar dados)
	private Runnable onShowCallback;
	// Tabela Swing que exibe os serviços visualmente
	private JTable tableServicos;
	// Botões de ação para interagir com os serviços
	private JButton btnVisualizar, btnEditar, btnDeletar, btnCadastrar;
	// Cópia dos dados da tabela em formato de matriz (para backup/comparação)
	private Object[][] tableData;

	/**
	 * Construtor que cria e configura a tela de listagem de serviços.
	 * <p>
	 * Inicializa a tabela com colunas específicas, configura editabilidade, oculta
	 * a coluna de ID, e adiciona botões de ação ao layout.
	 * </p>
	 */
	public TelaListaServicos() {
		// Define tamanho preferencial do painel
		setPreferredSize(new Dimension(597, 364));
		// Remove bordas para maximizar área útil
		setBorder(new EmptyBorder(0, 0, 0, 0));
		// Configura MigLayout: preenche todo espaço, margens personalizadas, gap de
		// 40px
		// Colunas: [crescente][200px fixo], Linhas: 4 linhas crescentes
		setLayout(new MigLayout("fill, insets 20 20 20 40, gap 40", "[grow][200]", "[grow][grow][grow][grow]"));

		// Define os nomes das colunas da tabela
		String colunas[] = { "ID", "Nome", "Valor", "Modalidade", "Aceito?", "Descrição" };
		// Cria matriz vazia inicial (0 linhas, 6 colunas)
		Object dados[][] = new Object[0][6];
		// Cria o modelo da tabela com dados iniciais e colunas, sobrescrevendo
		// isCellEditable
		this.tableModel = new DefaultTableModel(dados, colunas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Permite edição de todas as colunas exceto a coluna 0 (ID)
				return column != 0;
			}
		};
		// Cria a JTable usando o modelo configurado
		tableServicos = new JTable(tableModel);
		// Obtém os itens atuais da tabela para backup inicial
		this.tableData = this.getItems();

		// Cria painel de rolagem para envolver a tabela
		JScrollPane scrollPane_1 = new JScrollPane();
		// Adiciona o scroll pane ocupando toda a primeira coluna (4 linhas)
		add(scrollPane_1, "cell 0 0 1 4,grow");

		// Define a tabela como conteúdo do scroll pane
		scrollPane_1.setViewportView(tableServicos);
		// Define cor branca para o texto da tabela
		tableServicos.setForeground(new Color(255, 255, 255));
		// Aplica o modelo de dados à tabela
		tableServicos.setModel(tableModel);
		// Oculta a coluna ID do usuário (coluna 0) - segurança e interface limpa
		// Define largura mínima 0
		tableServicos.getColumnModel().getColumn(0).setMinWidth(0);
		// Define largura máxima 0
		tableServicos.getColumnModel().getColumn(0).setMaxWidth(0);
		// Define largura atual 0 (torna a coluna invisível)
		tableServicos.getColumnModel().getColumn(0).setWidth(0);

		// Cria botão "Visualizar" para abrir detalhes do serviço selecionado
		btnVisualizar = new JButton("Visualizar");
		// Adiciona na célula 1,0 (segunda coluna, primeira linha), crescendo
		// horizontalmente
		add(btnVisualizar, "cell 1 0,growx,height 20:40:60");

		// Cria botão "Deletar" para excluir serviço selecionado
		btnDeletar = new JButton("Deletar");
		// Adiciona na célula 1,1
		add(btnDeletar, "cell 1 1,growx,height 20:40:60");

		// Cria botão "Cadastrar" para navegar à tela de cadastro de novo serviço
		btnCadastrar = new JButton("Cadastrar");
		// Adiciona na célula 1,2
		add(btnCadastrar, "cell 1 2,growx,height 20:40:60");

		// Cria botão "Editar" para salvar alterações feitas na tabela
		btnEditar = new JButton("Editar");
		// Adiciona na célula 1,3 (última linha de botões)
		add(btnEditar, "cell 1 3,growx,height 20:40:60");
	}

	/**
	 * Atualiza a tabela com uma nova lista de serviços.
	 * <p>
	 * Remove todas as linhas existentes e adiciona novas linhas baseadas nos
	 * serviços fornecidos. Converte o boolean "aceito" em texto legível.
	 * </p>
	 * 
	 * @param lista lista de objetos Servico a serem exibidos na tabela
	 */
	public void atualizarTable(ArrayList<Servico> lista) {
		// Limpa todas as linhas existentes na tabela
		this.tableModel.setRowCount(0);
		// Percorre cada serviço na lista fornecida
		for (int i = 0; i < lista.size(); i++) {
			// Obtém o serviço atual
			Servico s = lista.get(i);
			// Converte o boolean aceito em texto legível ("Sim" ou "Não")
			String aceitoTexto = Boolean.TRUE.equals(s.getAceito()) ? "Sim" : "Não";
			// Cria array com os dados do serviço na ordem das colunas
			Object[] newRowData = { s.getId(), s.getNome_Servico(), s.getValor(), s.getModalidade(), aceitoTexto,
					s.getDescricao() };
			// Adiciona a nova linha ao modelo da tabela
			this.tableModel.addRow(newRowData);
		}

	}

	/**
	 * Sobrescreve addNotify para executar callback quando a tela é adicionada ao
	 * container.
	 * <p>
	 * Permite que o controller seja notificado quando a tela se torna visível,
	 * possibilitando atualização automática dos dados.
	 * </p>
	 */
	@Override
	public void addNotify() {
		// Chama o método da classe pai para manter comportamento padrão
		super.addNotify();
		// Se há um callback registrado, executa-o
		if (onShowCallback != null) {
			onShowCallback.run();
		}
	}

	/**
	 * Registra um callback a ser executado quando a tela é mostrada.
	 * 
	 * @param r Runnable a ser executado (geralmente atualização de dados)
	 */
	public void setOnShow(Runnable r) {
		this.onShowCallback = r;
	}

	/**
	 * Obtém todos os valores atuais da tabela como matriz bidimensional.
	 * 
	 * @return matriz de objetos contendo todos os valores das células
	 */
	public Object[][] getItems() {
		// Obtém número de linhas na tabela
		int rowCount = tableModel.getRowCount();
		// Obtém número de colunas na tabela
		int colCount = tableModel.getColumnCount();

		// Cria matriz para armazenar todos os valores
		Object[][] allValues = new Object[rowCount][colCount];

		// Percorre cada linha da tabela
		for (int row = 0; row < rowCount; row++) {
			// Percorre cada coluna da linha
			for (int col = 0; col < colCount; col++) {
				// Copia o valor da célula para a matriz
				allValues[row][col] = tableModel.getValueAt(row, col);
			}
		}
		// Retorna a matriz com todos os valores
		return allValues;
	}

	/**
	 * Retorna a tabela Swing que exibe os serviços.
	 * <p>
	 * Usado pelo controller para acessar a tabela e obter linhas selecionadas.
	 * </p>
	 * 
	 * @return JTable usada para renderizar os serviços
	 */
	public javax.swing.JTable getTableServicos() {
		return tableServicos;
	}

	/**
	 * Define uma nova instância de JTable para exibir os serviços.
	 * 
	 * @param table nova instância de JTable
	 */
	public void setTable(JTable table) {
		this.tableServicos = table;
	}

	/**
	 * Retorna uma cópia dos dados atualmente armazenados em cache.
	 * 
	 * @return matriz de objetos com os valores das células (pode estar
	 *         desatualizado)
	 */
	public Object[][] getTableData() {
		return tableData;
	}

	/**
	 * Armazena uma cópia dos dados da tabela em cache.
	 * 
	 * @param tableData matriz de objetos a ser armazenada
	 */
	public void setTableData(Object[][] tableData) {
		this.tableData = tableData;
	}

	/**
	 * Retorna o modelo de tabela (DefaultTableModel) usado pelo JTable de serviços.
	 * <p>
	 * Permite acesso direto ao modelo para manipulação avançada de dados.
	 * </p>
	 * 
	 * @return DefaultTableModel da tabela de serviços
	 */
	public javax.swing.table.DefaultTableModel getTableModelServicos() {
		return (javax.swing.table.DefaultTableModel) this.tableServicos.getModel();
	}

	/**
	 * Define um novo modelo de tabela para a JTable de serviços.
	 * 
	 * @param tableModel novo DefaultTableModel a ser usado
	 */
	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	// Remove todos os ActionListeners de um botão antes de adicionar um novo
	private void replaceActionListener(javax.swing.JButton btn, java.awt.event.ActionListener l) {
		if (btn == null)
			return;
		java.awt.event.ActionListener[] existing = btn.getActionListeners();
		for (java.awt.event.ActionListener al : existing) {
			btn.removeActionListener(al);
		}
		if (l != null)
			btn.addActionListener(l);
	}

	// Métodos chamados pelo controller — registram um ActionListener nos botões
	public void cadastrar(java.awt.event.ActionListener l) {
		replaceActionListener(this.btnCadastrar, l);
	}

	public void visualizar(java.awt.event.ActionListener l) {
		replaceActionListener(this.btnVisualizar, l);
	}

	public void deletar(java.awt.event.ActionListener l) {
		replaceActionListener(this.btnDeletar, l);
	}

	public void editar(java.awt.event.ActionListener l) {
		replaceActionListener(this.btnEditar, l);
	}

}