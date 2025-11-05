// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa BorderLayout para organizar componentes nas bordas e centro
import java.awt.BorderLayout;
// Importa CardLayout para alternar entre múltiplos painéis na mesma área
import java.awt.CardLayout;
// Importa Color para definir cores personalizadas
import java.awt.Color;
// Importa Dimension para definir dimensões de componentes
import java.awt.Dimension;
// Importa Image para manipulação de imagens
import java.awt.Image;
// Importa ArrayList para listas dinâmicas
import java.util.ArrayList;
// Importa List, interface para coleções ordenadas
import java.util.List;
// Importa ImageIcon para trabalhar com ícones de imagem
import javax.swing.ImageIcon;
// Importa JFrame, janela principal da aplicação
import javax.swing.JFrame;
// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa JTextField, campo de texto de uma linha
import javax.swing.JTextField;
// Importa SwingUtilities para execução segura de código na thread de interface
import javax.swing.SwingUtilities;
// Importa UIManager para configurar propriedades visuais globais
import javax.swing.UIManager;
// Importa Timer para executar ações após um delay
import javax.swing.Timer;
// Importa EmptyBorder para criar bordas vazias (espaçamento)
import javax.swing.border.EmptyBorder;

/**
 * Janela principal da aplicação WorkITBr.
 * <p>
 * Responsável por: gerenciar a estrutura base da interface (barra superior,
 * painéis de conteúdo), implementar o sistema de navegação entre telas usando
 * CardLayout, gerenciar o menu lateral (DrawerMenu) com animações, manter o
 * mapa de painéis registrados, e controlar o ciclo de vida da aplicação
 * (limpeza de recursos ao fechar).
 * </p>
 */
public class Primario extends JFrame {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;
	// Painel de conteúdo principal que contém todos os outros componentes
	private JPanel contentPanel;
	// Painel central que usa CardLayout para alternar entre diferentes telas
	private JPanel cPanel;
	// Referência ao menu lateral deslizante
	private DrawerMenu dm;
	// Gerenciador de layout que permite alternar entre painéis (telas)
	private CardLayout cardLayout;
	// Campo de texto (não utilizado atualmente - possível legado)
	private JTextField textField;
	// Barra superior da aplicação que contém título e botões de navegação
	private wbBarra wbb;
	// Camada de sobreposição (glass pane) onde o DrawerMenu é renderizado
	private JPanel menuLayer;
	// Referência ao navegador que controla a navegação entre telas e estado do
	// usuário
	private controller.Navegador navegador;
	// Mapa que armazena todos os painéis registrados, indexados por nome
	private java.util.Map<String, JPanel> painelMap = new java.util.HashMap<>();
	// Nome do painel atualmente visível na tela
	private String currentPanelName;

	/**
	 * Construtor que cria e configura a janela principal da aplicação.
	 * <p>
	 * Inicializa todos os componentes visuais, configura a estrutura de layout,
	 * adiciona o menu lateral com animação, e prepara o sistema de navegação entre
	 * telas.
	 * </p>
	 * 
	 * @param wbb barra superior da aplicação que contém navegação e título
	 * @param dm  menu lateral (drawer) que contém opções de navegação do usuário
	 */
	public Primario(wbBarra wbb, DrawerMenu dm) {
		// Armazena a referência da barra superior recebida
		this.wbb = wbb;
		// Armazena a referência do menu lateral recebido
		this.dm = dm;
		// Habilita o gerenciamento customizado de foco entre componentes
		setFocusTraversalPolicyProvider(true);
		// Define o tamanho mínimo que a janela pode ter (largura x altura)
		setMinimumSize(new Dimension(1200, 900));
		// Define o título da janela que aparece na barra de título
		setTitle("WorkITBr");
		// Define que a aplicação deve encerrar quando a janela for fechada
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Define posição (x, y) e tamanho (largura, altura) inicial da janela
		setBounds(100, 100, 1200, 800);
		// Centraliza a janela na tela (null = centro da tela)
		setLocationRelativeTo(null);
		// Cria lista para armazenar múltiplos tamanhos do ícone da aplicação
		List<Image> icons = new ArrayList<>();
		// Adiciona o mesmo ícone em diferentes tamanhos para melhor renderização
		// (sistema operacional escolhe o tamanho mais adequado para cada contexto)
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		icons.add(new ImageIcon(getClass().getResource("/imagens/w.png")).getImage());
		// Define os ícones da aplicação (aparecem na barra de tarefas e título)
		setIconImages(icons);
		// Configura botões para terem cantos arredondados (999 = totalmente
		// arredondado)
		UIManager.put("Button.arc", 999);

		// Define BorderLayout como gerenciador de layout principal da janela
		setLayout(new BorderLayout());

		// Cria o painel de conteúdo principal com BorderLayout
		contentPanel = new JPanel(new BorderLayout());
		// Define cor de fundo azul característica da aplicação
		contentPanel.setBackground(new Color(0, 102, 204));
		// Remove bordas para maximizar área útil
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		// Adiciona o painel de conteúdo ao centro da janela
		add(contentPanel, BorderLayout.CENTER);

		// Adiciona a barra superior ao topo do painel de conteúdo
		contentPanel.add(wbb, BorderLayout.NORTH);
		// Cria o CardLayout que permitirá alternar entre diferentes telas
		this.cardLayout = new CardLayout();
		// Cria o painel central que conterá todas as telas alternáveis
		this.cPanel = new JPanel(cardLayout);
		// Remove bordas do painel central
		this.cPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		// Adiciona o painel de telas ao centro do painel de conteúdo
		contentPanel.add(cPanel, BorderLayout.CENTER);

		// Cria camada de sobreposição (glass pane) para renderizar o menu deslizante
		// Usa layout null para posicionamento manual/absoluto do menu
		menuLayer = new JPanel(null);
		// Define como transparente para não bloquear visualização do conteúdo
		menuLayer.setOpaque(false);
		// Define este painel como glass pane da janela (fica sobre tudo)
		setGlassPane(menuLayer);
		// Inicia invisível - só aparece quando menu é aberto
		menuLayer.setVisible(false);

		// Configura o menu como opaco para ter fundo próprio
		dm.setOpaque(true);
		// Inicia o menu invisível - será mostrado com animação quando necessário
		dm.setVisible(false);
		// Adiciona o menu à camada de sobreposição
		menuLayer.add(dm);

		// Adiciona listener para fechar o menu ao clicar fora dele
		menuLayer.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// Verifica se o clique foi fora do DrawerMenu
				if (dm.isOpen() && !dm.getBounds().contains(e.getPoint())) {
					dm.toggleMenu();
				}
			}
		});

		addComponentListener(new java.awt.event.ComponentAdapter() {
			@Override
			public void componentResized(java.awt.event.ComponentEvent e) {
				// Atualiza a posição do menu ao redimensionar a janela
				if (dm.isOpen() && !dm.isAnimating()) {
					int menuWidth = 250;
					int x = getWidth() - menuWidth;
					int y = 0;
					int height = getHeight();
					dm.setBounds(x, y, menuWidth, height);
				}
				menuLayer.revalidate();
				menuLayer.repaint();
			}
		});

		dm.setOnStateChange(isOpen -> {
			if (isOpen) {
				dm.setVisible(true);
				menuLayer.setVisible(true);
			} else {
				// Aguarda o fim da animação para esconder
				Timer hideTimer = new Timer(320, e -> {
					if (!dm.isOpen()) {
						dm.setVisible(false);
						menuLayer.setVisible(false);
					}
				});
				hideTimer.setRepeats(false);
				hideTimer.start();
			}
			menuLayer.revalidate();
			menuLayer.repaint();
		});

		wbb.setMenuClickListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				dm.toggleMenu();
			}
		});
	}

	public void mostrarTela(String panelName) {
		this.cardLayout.show(this.cPanel, panelName);
		this.cPanel.revalidate();
		this.cPanel.repaint();
		this.currentPanelName = panelName;
	}

	/**
	 * Remove uma tela do CardLayout se existir
	 * 
	 * @param panelName Nome do painel a ser removido
	 */
	public void removerTela(String panelName) {
		if (painelMap.containsKey(panelName)) {
			JPanel painel = painelMap.get(panelName);
			this.cPanel.remove(painel);
			painelMap.remove(panelName);
			this.cPanel.revalidate();
			this.cPanel.repaint();
		}
	}

	public void adicionarTelaWB(JPanel tela) {
		this.wbb.add(tela);
	}

	public void adicionarTela(String panelName, JPanel tela) {
		if (panelName == null || tela == null)
			return;
		if (painelMap.containsKey(panelName)) {
			JPanel antigo = painelMap.get(panelName);
			this.cPanel.remove(antigo);
		}
		painelMap.put(panelName, tela);
		this.cPanel.add(tela, panelName);
		this.cPanel.revalidate();
		this.cPanel.repaint();
	}

	public String getCurrentPanelName() {
		return this.currentPanelName;
	}

	public java.util.Set<String> getPainelNames() {
		return new java.util.HashSet<>(painelMap.keySet());
	}

	private boolean getDrawerMenuOpenState(DrawerMenu dm) {
		// Use public API on DrawerMenu to determine open state
		if (dm == null)
			return false;
		try {
			return dm.isOpen();
		} catch (Exception e) {
			return false;
		}
	}

	public void fecharDrawerMenuSeAberto() {
		if (getDrawerMenuOpenState(dm) && dm != null) {
			try {
				dm.closeMenu();
			} catch (Exception e) {
				// fallback
				dm.toggleMenu();
			}
		}
	}

	/**
	 * Sobrescreve setVisible para controlar visibilidade da janela.
	 * 
	 * @param b true para tornar janela visível, false para ocultá-la
	 */
	@Override
	public void setVisible(boolean b) { // Método sobrescrito que recebe boolean como parâmetro
		super.setVisible(b); // Chama método da classe pai (JFrame) para aplicar visibilidade
	} // Fim do método setVisible

	/**
	 * Configura o Navegador e adiciona WindowListener para limpar imagens ao fechar
	 */
	public void setNavegador(controller.Navegador navegador) { // Método público que recebe Navegador como parâmetro
		this.navegador = navegador; // Armazena referência do navegador na variável de instância

		// Adiciona listener para limpar imagens quando a janela for fechada
		addWindowListener(new java.awt.event.WindowAdapter() { // Cria adaptador de eventos de janela
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) { // Método chamado quando janela está sendo fechada
				if (Primario.this.navegador != null) { // Verifica se navegador foi definido
					Primario.this.navegador.limparImagensPerfil(); // Limpa cache de imagens de perfil para liberar memória
				} // Fim da verificação do navegador
			} // Fim do windowClosing
		}); // Fim do listener
	} // Fim do método setNavegador

	/**
	 * Pré-inicializa a janela forçando todos os layouts e renderizações antes de
	 * torná-la visível. Isso evita redimensionamentos visíveis.
	 */
	public void preinicializar() { // Método público para inicialização prévia da janela
		// Força o cálculo de todos os tamanhos e layouts
		pack(); // Ajusta tamanho da janela para acomodar todos os componentes (calcula tamanhos preferidos)

		// Restaura o tamanho desejado
		setSize(1200, 800); // Define tamanho da janela para 1200x800 pixels (sobrescreve pack())
		setLocationRelativeTo(null); // Centraliza janela na tela

		// Força validação completa de todos os componentes
		validate(); // Valida hierarquia de componentes garantindo que layouts sejam calculados

		// Força a renderização de todos os componentes
		doLayout(); // Executa layout de todos os componentes forçando posicionamento

		// Força repaint completo
		revalidate(); // Revalida layout da janela
		repaint(); // Marca janela para redesenho

		// Força a pintura imediata de todos os componentes
		// Nota: getGraphics() pode ser null se não houver peer ainda
		java.awt.Graphics g = getGraphics(); // Obtém contexto gráfico da janela (pode ser null se janela ainda não foi exibida)
		if (g != null) { // Se contexto gráfico está disponível
			paintAll(g); // Força pintura imediata de todos os componentes
			g.dispose(); // Libera recursos do contexto gráfico
		} // Fim da verificação do contexto gráfico
	} // Fim do método preinicializar
} // Fim da classe Primario