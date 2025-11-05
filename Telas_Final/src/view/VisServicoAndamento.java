// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;
// Importa CardLayout para alternar entre múltiplos painéis na mesma área
import java.awt.CardLayout;
// Importa Font para trabalhar com fontes de texto
import java.awt.Font;
// Importa ActionListener para capturar eventos de clique em botões
import java.awt.event.ActionListener;
// Importa ComponentAdapter para responder a eventos de redimensionamento
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes
import java.awt.event.ComponentEvent;
// Importa MouseListener para capturar eventos de mouse
import java.awt.event.MouseListener;

// Importa JButton, componente de botão clicável
import javax.swing.JButton;
// Importa JLabel para exibição de textos e imagens
import javax.swing.JLabel;
// Importa SwingConstants para constantes de alinhamento
import javax.swing.SwingConstants;
// Importa LineBorder para criar bordas com linha
import javax.swing.border.LineBorder;
// Importa TitledBorder para criar bordas com título
import javax.swing.border.TitledBorder;
// Importa Color para definição de cores personalizadas
import java.awt.Color;

// Importa PromptSupport (não utilizado atualmente - possível legado)
import org.jdesktop.swingx.prompt.PromptSupport;

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
// Importa File para manipulação de arquivos (não utilizado atualmente)
import java.io.File;
// Importa URL para trabalhar com URLs (não utilizado atualmente)
import java.net.URL;
// Importa BufferedImage para manipulação de imagens em memória (não utilizado atualmente)
import java.awt.image.BufferedImage;
// Importa a classe de modelo que representa um usuário no sistema
import model.Usuario;
// Importa o DAO para acesso a dados de usuários no banco de dados
import model.UsuarioDAO;

// Importa ImageIO para leitura/escrita de imagens (não utilizado atualmente)
import javax.imageio.ImageIO;
// Importa ActionEvent que contém informações sobre eventos de ação (não utilizado atualmente)
import java.awt.event.ActionEvent;
// Importa FontScaler, utilitário customizado para redimensionamento automático de fontes
import util.FontScaler;
// Importa enum FontSize que define tamanhos padrão de fonte
import util.FontScaler.FontSize;

/**
 * Tela de visualização de serviço em andamento para o contratado.
 * <p>
 * Responsável por: exibir informações detalhadas de um serviço que está sendo executado,
 * mostrar dados do contratante (foto e informações), exibir título, modalidade, preço e
 * descrição do serviço, fornecer botão para finalizar o serviço quando concluído, e
 * integrar-se com o controller para processar a finalização do trabalho.
 * </p>
 */
public class VisServicoAndamento extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;

	// Painel que contém a foto do contratante
	private JPanel Perfil;
	// Painel que exibe informações do serviço (título, modalidade, preço)
	private JPanel PanelInfo;
	// Painel que contém a descrição detalhada do serviço
	private JPanel PanelDesc;
	// Área de texto que exibe o título/nome do serviço
	private JTextArea taTitulo;
	// Área de texto que exibe a modalidade do serviço (presencial, remoto, híbrido)
	private JTextArea taModalidade;
	// Área de texto que exibe o preço/valor do serviço
	private JTextArea taPreco;
	// Área de texto que exibe a descrição completa do serviço
	private JTextArea tpDesc;
	// Label que exibe a foto do perfil do contratante
	private JLabel lblFoto;
	// Botão que marca o serviço como finalizado
	private JButton btnFinalizar;

	/**
	 * Construtor que cria e configura a tela de visualização de serviço em andamento.
	 * <p>
	 * Inicializa todos os painéis e componentes, popula os campos com dados do serviço,
	 * e organiza o layout visual.
	 * </p>
	 * 
	 * @param s objeto Servico contendo os dados a serem exibidos
	 */
	public VisServicoAndamento(Servico s) {
		// Configura MigLayout: 2 colunas [crescente][170px fixo], 3 linhas [crescente][130px][10px]
		setLayout(new MigLayout("", "[grow][grow 170]", "[grow][grow 130][grow 10]"));

		// Cria painel para foto do contratante com borda titulada
		Perfil = new JPanel();
		Perfil.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Contratante"));
		Perfil.setLayout(new MigLayout("", "[grow]", "[grow]"));
		add(Perfil, "cell 0 0,grow");
		
		// Cria e configura label para exibir a foto
		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		Perfil.add(lblFoto, "cell 0 0,alignx center,aligny center");

		// Cria painel para informações do serviço com borda titulada
		PanelInfo = new JPanel();
		PanelInfo.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Serviço"));
		add(PanelInfo, "cell 1 0,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));

		// Cria área de texto para título do serviço
		taTitulo = new JTextArea("Titulo");
		taTitulo.setEditable(false);
		taTitulo.setFocusable(false);
		taTitulo.setLineWrap(true);
		taTitulo.setWrapStyleWord(true);
		taTitulo.setRows(1);
		taTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		taTitulo.setBackground(PanelInfo.getBackground());
		taTitulo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taTitulo, "cell 0 0,grow");

		// Cria área de texto para modalidade do serviço
		taModalidade = new JTextArea("Modalidade");
		taModalidade.setEditable(false);
		taModalidade.setFocusable(false);
		taModalidade.setLineWrap(true);
		taModalidade.setWrapStyleWord(true);
		taModalidade.setRows(1);
		taModalidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taModalidade.setBackground(PanelInfo.getBackground());
		taModalidade.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taModalidade, "cell 0 1,grow");

		// Cria área de texto para preço do serviço
		taPreco = new JTextArea("Preco");
		taPreco.setEditable(false);
		taPreco.setFocusable(false);
		taPreco.setLineWrap(true);
		taPreco.setWrapStyleWord(true);
		taPreco.setRows(1);
		taPreco.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taPreco.setBackground(PanelInfo.getBackground());
		taPreco.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taPreco, "cell 0 2,grow");

		PanelDesc = new JPanel();
		PanelDesc.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Descrição"));
		add(PanelDesc, "cell 0 1 2 1,grow");
		PanelDesc.setLayout(new MigLayout("", "[grow]", "[grow]"));

		tpDesc = new JTextArea();
		tpDesc.setEditable(false);
		tpDesc.setFocusable(false);
		tpDesc.setLineWrap(true);
		tpDesc.setWrapStyleWord(true);
		tpDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tpDesc.setBackground(PanelDesc.getBackground());
		tpDesc.setText(s.getDescricao());
		
		JScrollPane scrollPane = new JScrollPane(tpDesc);
		scrollPane.setBorder(null);
		PanelDesc.add(scrollPane, "cell 0 0,grow");

		btnFinalizar = new JButton("Finalizar trabalho");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFinalizar.setFont(new Font("Tahoma", Font.BOLD, 16));
		add(btnFinalizar, "cell 0 2 2 1,alignx center");

		taTitulo.setText(s.getNome_Servico());
		taModalidade.setText(s.getModalidade());
		taPreco.setText(String.format("R$ %.2f", s.getValor()));

		// Carrega a foto do contratante (se disponível)
		Usuario u = s.getContratante();
		if (u == null && s.getIdContratante() != 0) {
			UsuarioDAO udao = new UsuarioDAO();
			u = udao.getUsuarioById(s.getIdContratante());
		}
		ImageIcon foto = loadUserImage(u, 150, 150);
		lblFoto.setIcon(foto);
		
		// Aplicar FontScaler padronizado
		FontScaler.addAutoResize(this,
			new Object[] {taTitulo, FontSize.SUBTITULO},
			new Object[] {taModalidade, FontSize.TEXTO},
			new Object[] {taPreco, FontSize.TEXTO},
			new Object[] {tpDesc, FontSize.TEXTO},
			new Object[] {btnFinalizar, FontSize.BOTAO}
		);
	}

	private ImageIcon loadUserImage(Usuario u, int width, int height) {
		try {
			String caminho = null;
			if (u != null)
				caminho = u.getCaminhoFoto();
			if (caminho != null && !caminho.trim().isEmpty()) {
				File f = new File(caminho);
				if (f.exists()) {
					Image img = ImageIO.read(f);
					Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					return new ImageIcon(scaled);
				}
				URL res = getClass().getResource(caminho.startsWith("/") ? caminho : "/" + caminho);
				if (res != null) {
					Image img = ImageIO.read(res);
					Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					return new ImageIcon(scaled);
				}
			}
			// alternativa: usar imagem padrão do projeto (imagens/clickable_icon.png ou Casa.png)
			URL fallback = getClass().getResource("/imagens/clickable_icon.png");
			if (fallback == null)
				fallback = getClass().getResource("/imagens/Casa.png");
			if (fallback != null) {
				Image img = ImageIO.read(fallback);
				Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaled);
			}
			File alt = new File("imagens/clickable_icon.png");
			if (alt.exists()) {
				Image img = ImageIO.read(alt);
				Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaled);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		return new ImageIcon(bi);
	}
	
	public void finalizar(ActionListener actionlistener) {
		this.btnFinalizar.addActionListener(actionlistener);
	}

}