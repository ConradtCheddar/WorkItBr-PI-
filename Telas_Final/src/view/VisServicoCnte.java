package view;

// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;
// Importa CardLayout para alternar entre múltiplos painéis na mesma área
import java.awt.CardLayout;
// Importa ActionListener para capturar eventos de clique em botões
import java.awt.event.ActionListener;
// Importa MouseListener para capturar eventos de mouse (não utilizado - possível legado)
import java.awt.event.MouseListener;

// Importa JButton, componente de botão clicável
import javax.swing.JButton;
// Importa JLabel para exibição de textos e imagens (não utilizado - possível legado)
import javax.swing.JLabel;
// Importa SwingConstants para constantes de alinhamento (não utilizado - possível legado)
import javax.swing.SwingConstants;
// Importa LineBorder para criar bordas com linha
import javax.swing.border.LineBorder;
// Importa TitledBorder para criar bordas com título
import javax.swing.border.TitledBorder;
// Importa Color para definição de cores personalizadas
import java.awt.Color;

// Importa a classe de modelo que representa um serviço no sistema
import model.Servico;

// Importa JTextPane para áreas de texto formatadas (não utilizado - possível legado)
import javax.swing.JTextPane;
// Importa JTextArea para áreas de texto multilinhas
import javax.swing.JTextArea;
// Importa JScrollPane para adicionar barras de rolagem a componentes
import javax.swing.JScrollPane;
// Importa Font para trabalhar com fontes de texto
import java.awt.Font;
// Importa ComponentAdapter para responder a eventos de redimensionamento (não utilizado - possível legado)
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes (não utilizado - possível legado)
import java.awt.event.ComponentEvent;
// Importa FontScaler, utilitário customizado para redimensionamento automático de fontes
import util.FontScaler;
// Importa enum FontSize que define tamanhos padrão de fonte
import util.FontScaler.FontSize;

/**
 * Tela de visualização de serviço do contratante (ainda não aceito).
 * <p>
 * Responsável por: exibir informações detalhadas de um serviço cadastrado pelo
 * contratante, mostrar título, modalidade, preço e descrição, fornecer botão
 * para editar o serviço, permitir visualização antes que seja aceito por um
 * contratado, e integrar-se com o controller para processar edições.
 * </p>
 */
public class VisServicoCnte extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;

	// Painel container para foto/imagem do serviço (placeholder)
	private JPanel panel;
	// Painel de perfil (não utilizado atualmente - possível funcionalidade futura)
	private JPanel Perfil;
	// Painel que exibe informações do serviço (título, modalidade, preço)
	private JPanel PanelInfo;
	// Painel que contém a descrição detalhada do serviço
	private JPanel PanelDesc;
	// Área de texto que exibe o título/nome do serviço
	private JTextArea taTitulo;
	// Área de texto que exibe a modalidade do serviço
	private JTextArea taModalidade;
	// Área de texto que exibe o preço/valor do serviço
	private JTextArea taPreco;
	// Botão que permite editar o serviço
	private JButton btnEditar;
	// Área de texto que exibe a descrição completa do serviço
	private JTextArea tpDesc;

	/**
	 * Construtor que cria e configura a tela de visualização de serviço do
	 * contratante.
	 * <p>
	 * Inicializa todos os painéis e componentes, popula os campos com dados do
	 * serviço, e organiza o layout visual com botão de edição.
	 * </p>
	 * 
	 * @param s objeto Servico contendo os dados a serem exibidos
	 */
	public VisServicoCnte(Servico s) {
		setLayout(new MigLayout("", "[grow][grow 170]", "[grow][grow 130][grow 10]"));

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Serviço"));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new CardLayout(0, 0));

		Perfil = new JPanel();
		panel.add(Perfil, "name_1709392782600");

		PanelInfo = new JPanel();
		PanelInfo.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Serviço"));
		add(PanelInfo, "cell 1 0,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));

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

		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(btnEditar, "cell 0 2 2 1,alignx center");

		taTitulo.setText(s.getNome_Servico());
		taModalidade.setText(s.getModalidade());
		taPreco.setText(String.format("R$ %.2f", s.getValor()));
		tpDesc.setText(s.getDescricao());

		// Aplicar FontScaler padronizado
		FontScaler.addAutoResize(this, new Object[] { taTitulo, FontSize.SUBTITULO },
				new Object[] { taModalidade, FontSize.TEXTO }, new Object[] { taPreco, FontSize.TEXTO },
				new Object[] { tpDesc, FontSize.TEXTO }, new Object[] { btnEditar, FontSize.BOTAO });
	}

	/**
	 * Adiciona um listener ao botão editar para processar a edição do serviço.
	 * 
	 * @param actionListener listener que processará o evento de clique
	 */
	public void Editar(ActionListener actionListener) {
		this.btnEditar.addActionListener(actionListener);
	}
}