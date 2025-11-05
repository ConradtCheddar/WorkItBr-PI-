package view;

// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;
// Importa CardLayout para alternar entre múltiplos painéis na mesma área
import java.awt.CardLayout;
// Importa ActionListener para capturar eventos de clique em botões
import java.awt.event.ActionListener;
// Importa MouseListener para capturar eventos de mouse
import java.awt.event.MouseListener;

// Importa JButton, componente de botão clicável
import javax.swing.JButton;
// Importa JLabel para exibição de textos e imagens
import javax.swing.JLabel;
// Importa SwingConstants para constantes de alinhamento
import javax.swing.SwingConstants;

// Importa a classe de modelo que representa um serviço no sistema
import model.Servico;

// Importa JTextPane para áreas de texto formatadas (não utilizado atualmente)
import javax.swing.JTextPane;
// Importa JTextArea para áreas de texto multilinhas
import javax.swing.JTextArea;
// Importa JScrollPane para adicionar barras de rolagem a componentes
import javax.swing.JScrollPane;
// Importa ImageIcon para trabalhar com ícones de imagem
import javax.swing.ImageIcon;
// Importa Image para manipulação de imagens
import java.awt.Image;
// Importa File para manipulação de arquivos
import java.io.File;
// Importa URL para trabalhar com URLs
import java.net.URL;
// Importa BufferedImage para manipulação de imagens em memória
import java.awt.image.BufferedImage;
// Importa a classe de modelo que representa um usuário no sistema
import model.Usuario;
// Importa o DAO para acesso a dados de usuários no banco de dados
import model.UsuarioDAO;

// Importa ImageIO para leitura/escrita de imagens
import javax.imageio.ImageIO;
// Importa LineBorder para criar bordas com linha
import javax.swing.border.LineBorder;
// Importa TitledBorder para criar bordas com título
import javax.swing.border.TitledBorder;
// Importa Color para definição de cores personalizadas
import java.awt.Color;
// Importa Font para trabalhar com fontes de texto
import java.awt.Font;
// Importa ComponentAdapter para responder a eventos de redimensionamento
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes
import java.awt.event.ComponentEvent;
// Importa FontScaler, utilitário customizado para redimensionamento automático de fontes
import util.FontScaler;
// Importa enum FontSize que define tamanhos padrão de fonte
import util.FontScaler.FontSize;

/**
 * Tela de visualização de serviço disponível para contratação (visão do
 * contratado).
 * <p>
 * Responsável por: exibir informações detalhadas de um serviço disponível,
 * mostrar foto e dados do contratante, exibir título, modalidade, preço e
 * descrição do serviço, fornecer botão para aceitar o serviço, carregar imagens
 * do contratante com fallback para placeholder, e integrar-se com o controller
 * para processar a aceitação do trabalho.
 * </p>
 */
public class VisServico extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;

	// Painel container para a foto do contratante
	private JPanel panel;
	// Painel que contém a foto de perfil do contratante
	private JPanel panelPerfil;
	// Painel que exibe informações do serviço (título, modalidade, preço)
	private JPanel panelInfo;
	// Painel que contém a descrição detalhada do serviço
	private JPanel panelDesc;
	// Área de texto que exibe o título/nome do serviço
	private JTextArea taTitulo;
	// Área de texto que exibe a modalidade do serviço (presencial, remoto, híbrido)
	private JTextArea taModalidade;
	// Área de texto que exibe o preço/valor do serviço
	private JTextArea taPreco;
	// Botão que aceita o serviço e inicia o trabalho
	private JButton btnAceitar;
	// Área de texto que exibe a descrição completa do serviço
	private JTextArea taDesc;
	// Label que exibe a foto do perfil do contratante
	private JLabel lblFoto;
	// Painel que contém os botões de ação
	private JPanel panelBotoes;

	/**
	 * Construtor que cria e configura a tela de visualização de serviço.
	 * <p>
	 * Inicializa todos os painéis e componentes, popula os campos com dados do
	 * serviço, carrega a foto do contratante, e organiza o layout visual.
	 * </p>
	 * 
	 * @param s objeto Servico contendo os dados a serem exibidos
	 */
	public VisServico(Servico s) {
		setLayout(new MigLayout("", "[grow][grow 170]", "[grow][grow 130][grow 10]"));

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Contratante"));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new CardLayout(0, 0));

		panelPerfil = new JPanel();
		// configurar painel de perfil com espaço para foto
		panelPerfil.setLayout(new MigLayout("", "[grow]", "[grow]"));
		panel.add(panelPerfil, "name_1709392782600");
		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		panelPerfil.add(lblFoto, "cell 0 0,alignx center,aligny center");

		panelInfo = new JPanel();
		panelInfo.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Serviço"));
		add(panelInfo, "cell 1 0,grow");
		panelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));

		taTitulo = new JTextArea("Título");
		taTitulo.setEditable(false);
		taTitulo.setFocusable(false);
		taTitulo.setLineWrap(true);
		taTitulo.setWrapStyleWord(true);
		taTitulo.setRows(1);
		taTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		taTitulo.setBackground(panelInfo.getBackground());
		taTitulo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		panelInfo.add(taTitulo, "cell 0 0,grow");

		taModalidade = new JTextArea("Modalidade");
		taModalidade.setEditable(false);
		taModalidade.setFocusable(false);
		taModalidade.setLineWrap(true);
		taModalidade.setWrapStyleWord(true);
		taModalidade.setRows(1);
		taModalidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taModalidade.setBackground(panelInfo.getBackground());
		taModalidade.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		panelInfo.add(taModalidade, "cell 0 1,grow");

		taPreco = new JTextArea("Preço");
		taPreco.setEditable(false);
		taPreco.setFocusable(false);
		taPreco.setLineWrap(true);
		taPreco.setWrapStyleWord(true);
		taPreco.setRows(1);
		taPreco.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taPreco.setBackground(panelInfo.getBackground());
		taPreco.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		panelInfo.add(taPreco, "cell 0 2,grow");

		panelDesc = new JPanel();
		panelDesc.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Descrição"));
		add(panelDesc, "cell 0 1 2 1,grow");
		panelDesc.setLayout(new MigLayout("", "[grow]", "[grow]"));

		taDesc = new JTextArea();
		taDesc.setEditable(false);
		taDesc.setFocusable(false);
		taDesc.setLineWrap(true);
		taDesc.setWrapStyleWord(true);
		taDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		taDesc.setBackground(panelDesc.getBackground());
		taDesc.setText(s.getDescricao());

		JScrollPane scrollPane = new JScrollPane(taDesc);
		scrollPane.setBorder(null);
		panelDesc.add(scrollPane, "cell 0 0,grow");

		taTitulo.setText(s.getNome_Servico());
		taModalidade.setText(s.getModalidade());
		taPreco.setText(String.format("R$ %.2f", s.getValor()));

		// Carrega a foto do contratante (se disponível). Se o objeto Usuario não
		// estiver presente,
		// tenta recuperar pelo idContratante via UsuarioDAO.
		Usuario u = s.getContratante();
		if (u == null && s.getIdContratante() != 0) {
			UsuarioDAO udao = new UsuarioDAO();
			u = udao.getUsuarioById(s.getIdContratante());
		}
		ImageIcon foto = loadUserImage(u, 150, 150);
		lblFoto.setIcon(foto);

		panelBotoes = new JPanel();
		add(panelBotoes, "cell 0 2 2 1,growx");

		btnAceitar = new JButton("Aceitar");
		btnAceitar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelBotoes.add(btnAceitar);

		// Aplicar FontScaler padronizado
		FontScaler.addAutoResize(this, new Object[] { taTitulo, FontSize.SUBTITULO },
				new Object[] { taModalidade, FontSize.TEXTO }, new Object[] { taPreco, FontSize.TEXTO },
				new Object[] { taDesc, FontSize.TEXTO }, new Object[] { btnAceitar, FontSize.BOTAO });
	}

	/**
	 * Carrega e escala a imagem do usuário.
	 * <p>
	 * Tenta carregar de: caminho do banco → recurso no classpath → imagem padrão →
	 * placeholder vazio. Fornece fallback robusto para garantir que sempre haja
	 * alguma imagem exibida.
	 * </p>
	 * 
	 * @param u      usuário do qual carregar a foto
	 * @param width  largura desejada da imagem
	 * @param height altura desejada da imagem
	 * @return ImageIcon com a imagem carregada e escalada
	 */
	private ImageIcon loadUserImage(Usuario u, int width, int height) {
		try {
			String caminho = null;
			if (u != null)
				caminho = u.getCaminhoFoto();
			// Tentar caminho informado
			if (caminho != null && !caminho.trim().isEmpty()) {
				File f = new File(caminho);
				if (f.exists()) {
					Image img = ImageIO.read(f);
					Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					return new ImageIcon(scaled);
				}
				// tentar recurso no classpath
				URL res = getClass().getResource(caminho.startsWith("/") ? caminho : "/" + caminho);
				if (res != null) {
					Image img = ImageIO.read(res);
					Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					return new ImageIcon(scaled);
				}
			}
			// alternativa: usar imagem padrão do projeto (imagens/clickable_icon.png ou
			// Casa.png)
			URL fallback = getClass().getResource("/imagens/clickable_icon.png");
			if (fallback == null)
				fallback = getClass().getResource("/imagens/Casa.png");
			if (fallback != null) {
				Image img = ImageIO.read(fallback);
				Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaled);
			}
			// última tentativa: arquivo relativo
			File alt = new File("imagens/clickable_icon.png");
			if (alt.exists()) {
				Image img = ImageIO.read(alt);
				Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaled);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// se tudo falhar, retorna um ícone vazio
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		return new ImageIcon(bi);
	}

	/**
	 * Adiciona um listener ao botão aceitar para processar a aceitação do serviço.
	 * 
	 * @param actionListener listener que processará o evento de clique
	 */
	public void aceitar(ActionListener actionListener) {
		this.btnAceitar.addActionListener(actionListener);
	}
}
