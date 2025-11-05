package view;

// Importa Color para definição de cores personalizadas
import java.awt.Color;
// Importa Component, classe base para todos os componentes visuais
import java.awt.Component;
// Importa ComponentOrientation para definir orientação de componentes (RTL/LTR)
import java.awt.ComponentOrientation;
// Importa Cursor para definir aparência do cursor do mouse
import java.awt.Cursor;
// Importa Dimension para definir dimensões de componentes
import java.awt.Dimension;
// Importa Font para trabalhar com fontes de texto
import java.awt.Font;
// Importa Image para manipulação de imagens
import java.awt.Image;
// Importa ComponentAdapter para responder a eventos de redimensionamento
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes
import java.awt.event.ComponentEvent;
// Importa ComponentListener para capturar eventos de componentes
import java.awt.event.ComponentListener;
// Importa MouseAdapter para responder a eventos de mouse de forma simplificada
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;
// Importa MouseListener para capturar eventos de mouse
import java.awt.event.MouseListener;

// Importa ImageIcon para trabalhar com ícones de imagem
import javax.swing.ImageIcon;
// Importa JLabel para exibição de textos e imagens
import javax.swing.JLabel;
// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa JScrollPane para adicionar barras de rolagem a componentes
import javax.swing.JScrollPane;
// Importa SwingConstants para constantes de alinhamento
import javax.swing.SwingConstants;
// Importa EmptyBorder para criar bordas vazias (espaçamento)
import javax.swing.border.EmptyBorder;

// Importa PromptSupport (não utilizado atualmente - possível legado)
import org.jdesktop.swingx.prompt.PromptSupport;

// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;
// Importa FontScaler, utilitário customizado para redimensionamento automático de fontes
import util.FontScaler;
// Importa enum FontSize que define tamanhos padrão de fonte
import util.FontScaler.FontSize;

// Importa JList para exibir listas de itens
import javax.swing.JList;

/**
 * Tela principal do contratado exibindo trabalhos disponíveis e em andamento.
 * <p>
 * Responsável por: exibir duas listas lado a lado (trabalhos disponíveis e em
 * andamento), permitir interação com duplo clique nos itens, atualizar
 * automaticamente ao ser exibida, fornecer callback para carregar dados, e
 * integrar-se com o controller para processar seleções e navegação.
 * </p>
 */
public class TelaContratado extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;
	// Painel com barra de rolagem para lista de trabalhos em andamento
	private JScrollPane scrollPane;

	// Lista de trabalhos disponíveis para aceitar
	private JList listaDisponivel;
	// Lista de trabalhos já aceitos e em andamento
	private JList listaAndamento;
	// Callback executado quando a tela é mostrada (para atualizar dados)
	private Runnable onShowCallback;
	// Label título da lista de trabalhos disponíveis
	private JLabel lblDisponiveis;
	// Label do botão voltar (não utilizado atualmente - possível legado)
	private JLabel lblVoltar;
	// Label título da lista de trabalhos em andamento
	private JLabel lblAndamento;

	/**
	 * Construtor que cria e configura a tela do contratado.
	 * <p>
	 * Inicializa as duas listas (disponíveis e em andamento), configura scrolls, e
	 * aplica redimensionamento automático de fontes.
	 * </p>
	 */
	public TelaContratado() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 20 20 20 20, gap 20", "[grow][grow]", "[grow 10][grow]"));

		lblVoltar = new JLabel("");
		add(lblVoltar, "cell 0 0");

		scrollPane = new JScrollPane();
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setBackground(Color.LIGHT_GRAY);
		add(scrollPane, "cell 0 1,grow");

		listaAndamento = new JList();
		scrollPane.setViewportView(listaAndamento);

		lblAndamento = new JLabel("Trabalhos em andamento");
		scrollPane.setColumnHeaderView(lblAndamento);
		lblAndamento.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, "cell 1 1,grow");

		listaDisponivel = new JList();
		scrollPane_1.setViewportView(listaDisponivel);

		lblDisponiveis = new JLabel("Trabalhos disponiveis");
		lblDisponiveis.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblDisponiveis);

		FontScaler.addAutoResize(this, new Object[] { lblAndamento, FontSize.SUBTITULO },
				new Object[] { lblDisponiveis, FontSize.SUBTITULO });
	}

	/**
	 * Retorna a lista de trabalhos disponíveis.
	 * 
	 * @return JList contendo os serviços disponíveis para aceitar
	 */
	public JList getListaDisponivel() {
		return listaDisponivel;
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
	 * Sobrescreve addNotify para executar callback quando a tela é adicionada ao
	 * container.
	 * <p>
	 * Permite que o controller seja notificado quando a tela se torna visível.
	 * </p>
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		if (onShowCallback != null) {
			onShowCallback.run();
		}
	}

	/**
	 * Adiciona um listener de componente para eventos de redimensionamento.
	 * 
	 * @param listener ComponentListener a ser adicionado
	 */
	public void adicionarOuvinte(ComponentListener listener) {
		this.addComponentListener(listener);
	}

	/**
	 * Adiciona listener de mouse à lista de trabalhos disponíveis.
	 * <p>
	 * Usado para capturar duplo clique e abrir detalhes do serviço.
	 * </p>
	 * 
	 * @param actionListener MouseListener para processar eventos
	 */
	public void cliqueDuploNoJList(MouseListener actionListener) {
		this.listaDisponivel.addMouseListener(actionListener);
	}

	/**
	 * Adiciona listener de mouse à lista de trabalhos em andamento.
	 * <p>
	 * Usado para capturar duplo clique e abrir detalhes do serviço.
	 * </p>
	 * 
	 * @param actionListener MouseListener para processar eventos
	 */
	public void cliqueDuploNoAndamento(MouseListener actionListener) {
		this.listaAndamento.addMouseListener(actionListener);
	}

	/**
	 * Define a lista de trabalhos disponíveis.
	 * 
	 * @param listaDisponivel nova JList para trabalhos disponíveis
	 */
	public void setListaDisponivel(JList listaDisponivel) {
		this.listaDisponivel = listaDisponivel;
	}

	/**
	 * Retorna a lista de trabalhos em andamento.
	 * 
	 * @return JList contendo os trabalhos em andamento
	 */
	public JList getListaAndamento() {
		return listaAndamento;
	}

	/**
	 * Define a lista de trabalhos em andamento.
	 * 
	 * @param listAndamento nova JList para trabalhos em andamento
	 */
	public void setListaAndamento(JList listAndamento) {
		this.listaAndamento = listAndamento;
	}
}