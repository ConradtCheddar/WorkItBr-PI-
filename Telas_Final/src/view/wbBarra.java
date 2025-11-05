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
// Importa ComponentAdapter para responder a eventos de redimensionamento
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes
import java.awt.event.ComponentEvent;
// Importa MouseAdapter para responder a eventos de mouse de forma simplificada
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;
// Importa MouseListener para capturar eventos de mouse
import java.awt.event.MouseListener;
// Importa MouseMotionAdapter para responder a movimentos do mouse
import java.awt.event.MouseMotionAdapter;
// Importa MouseMotionListener para capturar eventos de movimento do mouse
import java.awt.event.MouseMotionListener;

// Importa BorderFactory para criar bordas decorativas
import javax.swing.BorderFactory;
// Importa ImageIcon para trabalhar com ícones de imagem
import javax.swing.ImageIcon;
// Importa JLabel para exibição de textos e imagens
import javax.swing.JLabel;
// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa SwingConstants para constantes de alinhamento
import javax.swing.SwingConstants;
// Importa EmptyBorder para criar bordas vazias (espaçamento)
import javax.swing.border.EmptyBorder;

// Importa PopupController (não utilizado atualmente - possível legado)
import controller.PopupController;
// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;
// Importa FontScaler, utilitário customizado para redimensionamento automático de fontes
import util.FontScaler;
// Importa enum FontSize que define tamanhos padrão de fonte
import util.FontScaler.FontSize;

/**
 * Barra superior da aplicação (toolbar/header).
 * <p>
 * Responsável por: exibir o título da aplicação centralizado, fornecer botão de
 * voltar (navegação para tela anterior), fornecer botão de menu (abre drawer
 * lateral), ajustar automaticamente o tamanho dos ícones e fontes baseado no
 * redimensionamento da janela, e controlar a visibilidade dos botões de
 * navegação conforme contexto.
 * </p>
 */
public class wbBarra extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;

	// Painel interno (não utilizado atualmente - possível legado)
	JPanel wbPanel;
	// Label que funciona como botão para abrir o menu lateral
	private JLabel lblBarra;
	// Label que funciona como botão de voltar/retornar
	JLabel lblVoltar;
	// Label que exibe o título da aplicação
	JLabel lblTitulo;
	// Flag que controla se o botão voltar está habilitado
	private boolean backEnabled = true;
	// Flag que controla se o botão menu está habilitado
	private boolean menuEnabled = true;
	// Flag que controla se os listeners de redimensionamento devem executar
	private boolean resizeListenersEnabled = false;

	/**
	 * Construtor que cria e configura a barra superior da aplicação.
	 * <p>
	 * Inicializa o layout, adiciona os ícones de navegação, configura o título, e
	 * prepara os listeners de redimensionamento (desabilitados inicialmente).
	 * </p>
	 */
	public wbBarra() {

		// Define tamanho preferencial inicial da barra
		setPreferredSize(new Dimension(900, 85));
		// Define cor de fundo azul característica da aplicação
		setBackground(new Color(0, 102, 204));
		// Remove bordas para maximizar área útil
		setBorder(new EmptyBorder(0, 0, 0, 0));
		// Configura MigLayout com 3 colunas: [esquerda fixa 80px][centro
		// crescente][direita fixa 80px]
		// Isso garante centralização do título e posicionamento fixo dos botões
		setLayout(new MigLayout("fill", "[80:80:80][grow,fill][80:80:80]", "[grow]"));

		// Cria label para o botão de voltar
		lblVoltar = new JLabel();
		// Alinha à esquerda dentro do seu espaço
		lblVoltar.setHorizontalAlignment(SwingConstants.LEFT);
		// Define cursor de mão quando passar sobre o elemento
		lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// Habilita o componente
		lblVoltar.setEnabled(true);

		// Carrega ícone da casa do diretório de recursos
		ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
		Image img = menuIcon.getImage();
		// Redimensiona ícone para 32x32 pixels com interpolação suave
		Image scaled = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		lblVoltar.setIcon(new ImageIcon(scaled));
		// Adiciona margem interna de 4 pixels ao redor do ícone
		lblVoltar.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		// Adiciona o botão voltar à primeira coluna (esquerda)
		add(lblVoltar, "cell 0 0,alignx left,aligny center");

		// Adiciona listener de movimento do mouse ao botão voltar
		lblVoltar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// Se o botão está habilitado e visível, mostra cursor de mão
				if (backEnabled && lblVoltar.isVisible()) {
					lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		});

		// Adiciona listener de movimento do mouse à barra inteira
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// Obtém posição do mouse
				java.awt.Point p = e.getPoint();
				// Obtém área ocupada pelo botão voltar
				java.awt.Rectangle r = lblVoltar.getBounds();
				// Se o mouse está sobre o botão voltar
				if (r.contains(p)) {
					if (backEnabled && lblVoltar.isVisible()) {
						// Mostra cursor de mão
						setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}
				} else {
					// Caso contrário, mostra cursor padrão
					setCursor(Cursor.getDefaultCursor());
				}
			}
		});

		// Cria label para o título da aplicação
		lblTitulo = new JLabel("WorkITBr");
		// Centraliza o texto horizontalmente
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		// Define fonte inicial de tamanho 25
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 25));
		// Define cor branca para o texto
		lblTitulo.setForeground(Color.WHITE);
		// Adiciona o título à coluna central (cresce para preencher espaço)
		add(lblTitulo, "cell 1 0,growx,aligny center");

		// Cria label para o botão de menu
		setLblBarra(new JLabel());
		// Alinha à direita dentro do seu espaço
		getLblBarra().setHorizontalAlignment(SwingConstants.RIGHT);
		// Define cursor de mão quando passar sobre o elemento
		getLblBarra().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// Carrega ícone do menu do diretório de recursos
		ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
		Image imgBarra = barraIcon.getImage();
		// Redimensiona ícone para 64x40 pixels com interpolação suave
		Image scaledBarra = imgBarra.getScaledInstance(64, 40, Image.SCALE_SMOOTH);
		getLblBarra().setIcon(new ImageIcon(scaledBarra));
		// Adiciona o botão de menu à terceira coluna (direita)
		add(getLblBarra(), "cell 2 0,alignx right,aligny center");

		// Ajusta tamanhos iniciais dos ícones
		ajustarIcones();
		// Adiciona listener para redimensionar ícones quando a barra for redimensionada
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Ignora durante inicialização para evitar múltiplos redimensionamentos
				if (!resizeListenersEnabled)
					return;
				// Ajusta tamanhos dos ícones
				ajustarIcones();
				// Recarrega e redimensiona ícone do menu baseado no novo tamanho
				ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
				Image imgbarra = barraIcon.getImage();
				Image scaledBarra = imgbarra.getScaledInstance(getWidth() / 40, getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
				getLblBarra().setIcon(new ImageIcon(scaledBarra));
			}
		});

		// Adiciona listener para redimensionar fonte do título quando a barra for
		// redimensionada
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Ignora durante inicialização
				if (!resizeListenersEnabled)
					return;
				// Calcula tamanho da fonte baseado na altura da barra (mínimo 18, máximo
				// altura/3)
				int panelHeight = getHeight();
				int fontSizeTitulo = Math.max(18, panelHeight / 3);
				lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, fontSizeTitulo));
				// Chama método adicional de ajuste de fonte
				ajustarFonte();
			}
		});
	}

	/**
	 * Ativa os listeners de redimensionamento após a inicialização completa.
	 * <p>
	 * Isso evita múltiplos redimensionamentos durante o carregamento inicial,
	 * melhorando a performance e evitando glitches visuais.
	 * </p>
	 */
	public void habilitarResizeListeners() {
		this.resizeListenersEnabled = true;
	}

	/**
	 * Sobrescreve addNotify para ajustar ícones quando o componente é adicionado à
	 * hierarquia.
	 * <p>
	 * Garante que os ícones tenham tamanho correto assim que a barra é exibida.
	 * </p>
	 */
	@Override
	public void addNotify() {
		// Chama o método da classe pai para manter comportamento padrão
		super.addNotify();
		// Ajusta tamanhos dos ícones baseado no tamanho atual
		ajustarIcones();
	}

	/**
	 * Adiciona um listener de mouse ao ícone da barra (menu).
	 * <p>
	 * Usado para capturar cliques no botão de menu.
	 * </p>
	 * 
	 * @param actionListener listener a ser adicionado
	 */
	public void barra(MouseListener actionListener) {
		this.getLblBarra().addMouseListener(actionListener);
	}

	/**
	 * Adiciona um listener de mouse ao ícone de voltar.
	 * <p>
	 * Usado para capturar cliques no botão de voltar/retornar.
	 * </p>
	 * 
	 * @param mouseAdapter adapter de mouse a ser adicionado
	 */
	public void menu(MouseAdapter mouseAdapter) {
		this.lblVoltar.addMouseListener(mouseAdapter);
	}

	/**
	 * Define um listener de clique para o ícone de menu, removendo listeners
	 * anteriores.
	 * <p>
	 * Garante que apenas um listener esteja ativo, evitando múltiplas chamadas.
	 * </p>
	 * 
	 * @param listener listener de mouse a ser configurado
	 */
	public void setMenuClickListener(MouseListener listener) {
		// Remove todos os listeners existentes para evitar duplicação
		for (MouseListener ml : getLblBarra().getMouseListeners()) {
			getLblBarra().removeMouseListener(ml);
		}
		// Adiciona novo listener que delega todos os eventos para o listener fornecido
		getLblBarra().addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				listener.mouseClicked(e);
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				listener.mousePressed(e);
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				listener.mouseReleased(e);
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				listener.mouseEntered(e);
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				listener.mouseExited(e);
			}
		});
	}

	/**
	 * Habilita ou desabilita o botão de voltar.
	 * <p>
	 * Quando desabilitado, o botão fica invisível. Quando habilitado, fica visível
	 * com cursor de mão.
	 * </p>
	 * 
	 * @param enabled true para habilitar, false para desabilitar
	 */
	public void setBackEnabled(boolean enabled) {
		// Armazena o estado
		this.backEnabled = enabled;
		// Controla visibilidade do botão
		this.lblVoltar.setVisible(enabled);
		// Se habilitado, define cursor de mão
		if (enabled) {
			this.lblVoltar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	 * Habilita ou desabilita o botão de menu.
	 * <p>
	 * Quando desabilitado, o botão fica invisível. Quando habilitado, fica visível
	 * com cursor de mão.
	 * </p>
	 * 
	 * @param enabled true para habilitar, false para desabilitar
	 */
	public void setMenuEnabled(boolean enabled) {
		// Armazena o estado
		this.menuEnabled = enabled;
		// Controla visibilidade do botão
		this.lblBarra.setVisible(enabled);
		// Se habilitado, define cursor de mão
		if (enabled) {
			this.lblBarra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	 * Ajusta o tamanho da fonte do título baseado na largura da barra.
	 * <p>
	 * Calcula tamanho proporcional com mínimo de 12 pixels.
	 * </p>
	 */
	public void ajustarFonte() {
		// Obtém largura atual da barra
		int w = getWidth();
		// Se largura inválida, não faz nada
		if (w <= 0)
			return;

		// Calcula tamanho da fonte: largura/40, mínimo 12
		float novoTamanho = Math.max(12f, w / 40f);

		// Aplica o novo tamanho mantendo estilo atual
		lblTitulo.setFont(lblTitulo.getFont().deriveFont(novoTamanho));
		// Revalida e redesenha para aplicar mudanças
		revalidate();
		repaint();
	}

	/**
	 * Ajusta os tamanhos dos ícones de voltar e menu baseado no tamanho atual da
	 * barra.
	 * <p>
	 * Calcula dimensões proporcionais, recarrega as imagens dos recursos,
	 * redimensiona-as e atualiza os ícones. Também ajusta a área clicável do botão
	 * voltar.
	 * </p>
	 */
	public void ajustarIcones() {
		// Obtém largura atual (usa 900 como padrão se ainda não foi definida)
		int w = getWidth() > 0 ? getWidth() : 900;
		// Obtém altura atual (usa 85 como padrão se ainda não foi definida)
		int h = getHeight() > 0 ? getHeight() : 85;
		// Calcula largura do ícone do menu: largura/25, mínimo 64
		int largura = Math.max(64, w / 25);
		// Calcula altura do ícone do menu: altura*2/5, mínimo 40
		int altura = Math.max(40, h * 2 / 5);

		// Calcula largura do ícone de voltar: largura/45, mínimo 32
		int larguraCasa = Math.max(32, w / 45);
		// Calcula altura do ícone de voltar: altura*2/3, mínimo 32, máximo 40
		int alturaCasa = Math.max(32, Math.min(40, h * 2 / 3));

		// Carrega ícone de seta/retorno do diretório de recursos
		ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Seta-retorno.png"));
		Image img = menuIcon.getImage();
		// Redimensiona para o tamanho calculado com interpolação suave
		Image scaled = img.getScaledInstance(larguraCasa, alturaCasa, Image.SCALE_SMOOTH);

		// Carrega ícone do menu do diretório de recursos
		ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
		Image imgBarra = barraIcon.getImage();
		// Redimensiona para o tamanho calculado com interpolação suave
		Image scaledBarra = imgBarra.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

		// Aplica os ícones redimensionados aos labels
		lblVoltar.setIcon(new ImageIcon(scaled));
		lblBarra.setIcon(new ImageIcon(scaledBarra));

		// Calcula padding proporcional ao tamanho (largura/200, mínimo 4)
		int padding = Math.max(4, w / 200);
		// Aplica padding ao redor do ícone de voltar
		lblVoltar.setBorder(javax.swing.BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		// Calcula área clicável incluindo padding
		java.awt.Dimension hitArea = new java.awt.Dimension(larguraCasa + (padding * 2), alturaCasa + (padding * 2));
		// Define todos os tamanhos do label para garantir área clicável consistente
		lblVoltar.setPreferredSize(hitArea);
		lblVoltar.setMinimumSize(hitArea);
		lblVoltar.setMaximumSize(hitArea);
	}

	/**
	 * Retorna o JLabel usado como botão/ícone da barra lateral (menu).
	 * 
	 * @return JLabel que representa o ícone de menu/barra
	 */
	public JLabel getLblBarra() {
		return lblBarra;
	}

	/**
	 * Define o JLabel usado como botão/ícone da barra lateral (menu).
	 * 
	 * @param lblBarra novo JLabel para o botão de menu
	 */
	public void setLblBarra(JLabel lblBarra) {
		this.lblBarra = lblBarra;
	}

	/**
	 * Retorna o JLabel usado como botão de voltar (ícone casa/retorno).
	 * 
	 * @return JLabel que representa o controle de voltar
	 */
	public JLabel getLblVoltar() {
		return lblVoltar;
	}

	/**
	 * Define o texto do título exibido na barra.
	 * 
	 * @param titulo novo texto a ser exibido
	 */
	public void setTitulo(String titulo) {
		this.lblTitulo.setText(titulo);
	}

	/**
	 * Retorna o texto atual do título exibido na barra.
	 * 
	 * @return texto do título
	 */
	public String getTitulo() {
		return this.lblTitulo.getText();
	}
}
