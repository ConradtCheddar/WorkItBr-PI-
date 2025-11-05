// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa CardLayout para alternar entre múltiplos painéis na mesma área
import java.awt.CardLayout;
// Importa Component, classe base para todos os componentes visuais
import java.awt.Component;
// Importa Graphics para operações básicas de desenho
import java.awt.Graphics;
// Importa Image para manipulação de imagens
import java.awt.Image;
// Importa ComponentAdapter para responder a eventos de redimensionamento
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes
import java.awt.event.ComponentEvent;
// Importa ActionListener para capturar eventos de clique em botões
import java.awt.event.ActionListener;

// Importa JButton, componente de botão clicável
import javax.swing.JButton;
// Importa ImageIcon para trabalhar com ícones de imagem
import javax.swing.ImageIcon;
// Importa JLabel para exibição de textos e imagens
import javax.swing.JLabel;
// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa SwingConstants para constantes de alinhamento
import javax.swing.SwingConstants;
// Importa LineBorder para criar bordas com linha
import javax.swing.border.LineBorder;
// Importa TitledBorder para criar bordas com título
import javax.swing.border.TitledBorder;
// Importa Color para definição de cores personalizadas
import java.awt.Color;

// Importa a classe de modelo que representa um usuário no sistema
import model.Usuario;
// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;
// Importa ActionEvent que contém informações sobre eventos de ação
import java.awt.event.ActionEvent;
// Importa Font para trabalhar com fontes de texto
import java.awt.Font;
// Importa JTextArea para áreas de texto multilinhas
import javax.swing.JTextArea;
// Importa JScrollPane para adicionar barras de rolagem a componentes
import javax.swing.JScrollPane;
// Importa FontScaler, utilitário customizado para redimensionamento automático de fontes
import util.FontScaler;
// Importa enum FontSize que define tamanhos padrão de fonte
import util.FontScaler.FontSize;

/**
 * Tela de visualização de perfil de contratado.
 * <p>
 * Responsável por: exibir informações detalhadas de um usuário contratado,
 * mostrar foto de perfil com redimensionamento automático, exibir dados como
 * nome, GitHub, email e telefone, fornecer botão de voltar para navegação, e
 * integrar-se com o controller para processar interações do usuário.
 * </p>
 */
public class VisContratado extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;

	// Painel container para a foto de perfil
	private JPanel panel;
	// Painel customizado que renderiza a foto do usuário
	private JPanel Perfil;
	// Painel que contém as informações textuais do contratado
	private JPanel PanelInfo;
	// Área de texto que exibe o nome do contratado
	private JTextArea taNome;
	// Área de texto que exibe o perfil GitHub do contratado
	private JTextArea taGithub;
	// Área de texto que exibe o email do contratado
	private JTextArea taEmail;
	// Área de texto que exibe o telefone do contratado
	private JTextArea taTelefone;
	// Botão para retornar à tela anterior
	private JButton btnVoltar;
	// Imagem atualmente exibida (redimensionada para o tamanho do painel)
	private Image imagemSelecionada;
	// Imagem original carregada (mantida para redimensionamentos futuros)
	private Image imagemOriginal;

	/**
	 * Construtor que cria e configura a tela de visualização de contratado.
	 * <p>
	 * Inicializa todos os componentes, popula os campos com dados do usuário,
	 * carrega e exibe a foto de perfil, e configura redimensionamento automático.
	 * </p>
	 * 
	 * @param u objeto Usuario contendo os dados do contratado a serem exibidos
	 */
	public VisContratado(Usuario u) {
		// Configura MigLayout com margens e espaçamento: 3 colunas crescentes, 3 linhas
		// crescentes
		setLayout(new MigLayout("fill, insets 20 20 20 20, gap 20", "[grow][grow][grow]", "[grow][grow][grow]"));

		// Cria painel container para foto com borda titulada
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Perfil"));
		add(panel, "cell 0 0 1 2,grow");
		panel.setLayout(new CardLayout(0, 0));

		// Cria painel customizado que renderiza a foto do usuário quando disponível
		Perfil = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// Chama método da classe pai para manter comportamento padrão
				super.paintComponent(g);
				// Se há imagem carregada, desenha-a preenchendo todo o painel
				if (imagemSelecionada != null) {
					g.drawImage(imagemSelecionada, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};
		panel.add(Perfil, "name_12377154952900");

		// Adiciona listener para redimensionar a imagem quando o painel mudar de
		// tamanho
		Perfil.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Se há imagem original carregada, redimensiona para o novo tamanho
				if (imagemOriginal != null) {
					imagemSelecionada = imagemOriginal.getScaledInstance(Perfil.getWidth(), Perfil.getHeight(),
							Image.SCALE_SMOOTH);
					Perfil.repaint();
				}
			}
		});

		// Cria painel para informações textuais com borda titulada
		PanelInfo = new JPanel();
		PanelInfo.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Contratado"));
		add(PanelInfo, "cell 1 0 2 2,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow][grow]"));

		// Cria área de texto para nome
		taNome = new JTextArea("Nome");
		taNome.setEditable(false);
		taNome.setFocusable(false);
		taNome.setLineWrap(true);
		taNome.setWrapStyleWord(true);
		taNome.setRows(1);
		taNome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		taNome.setBackground(PanelInfo.getBackground());
		taNome.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taNome, "cell 0 0,grow");

		// Cria área de texto para GitHub
		taGithub = new JTextArea("Github");
		taGithub.setEditable(false);
		taGithub.setFocusable(false);
		taGithub.setLineWrap(true);
		taGithub.setWrapStyleWord(true);
		taGithub.setRows(1);
		taGithub.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taGithub.setBackground(PanelInfo.getBackground());
		taGithub.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taGithub, "cell 0 1,grow");

		// Cria área de texto para email
		taEmail = new JTextArea("Email");
		taEmail.setEditable(false);
		taEmail.setFocusable(false);
		taEmail.setLineWrap(true);
		taEmail.setWrapStyleWord(true);
		taEmail.setRows(1);
		taEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taEmail.setBackground(PanelInfo.getBackground());
		taEmail.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taEmail, "cell 0 2,grow");

		// Cria área de texto para telefone
		taTelefone = new JTextArea("Telefone");
		taTelefone.setEditable(false);
		taTelefone.setFocusable(false);
		taTelefone.setLineWrap(true);
		taTelefone.setWrapStyleWord(true);
		taTelefone.setRows(1);
		taTelefone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taTelefone.setBackground(PanelInfo.getBackground());
		taTelefone.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taTelefone, "cell 0 3,grow");

		// Popula os campos com dados do usuário
		taNome.setText(u.getUsuario());
		// Exibe GitHub com alternativa caso não esteja preenchido no banco
		String githubText = (u.getGithub() != null && !u.getGithub().isBlank()) ? u.getGithub() : "Não informado";
		taGithub.setText(githubText);
		taEmail.setText(u.getEmail());
		taTelefone.setText(u.getTelefone());

		// Cria botão de voltar
		btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnVoltar, "cell 0 2 3 1,alignx center,aligny center");
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 16));

		// Configura redimensionamento automático de fontes
		FontScaler.addAutoResize(this, new Object[] { taNome, FontSize.SUBTITULO },
				new Object[] { taGithub, FontSize.TEXTO }, new Object[] { taEmail, FontSize.TEXTO },
				new Object[] { taTelefone, FontSize.TEXTO }, new Object[] { btnVoltar, FontSize.BOTAO });

		// Carrega a imagem do usuário caso exista caminho válido
		if (u != null && u.getCaminhoFoto() != null && !u.getCaminhoFoto().isBlank()) {
			try {
				ImageIcon imgIcon = new ImageIcon(u.getCaminhoFoto());
				imagemOriginal = imgIcon.getImage();
				// Se o painel já tem tamanho, escala imediatamente, senão será escalada no
				// componentResized
				int w = Perfil.getWidth() > 0 ? Perfil.getWidth() : 200;
				int h = Perfil.getHeight() > 0 ? Perfil.getHeight() : 200;
				imagemSelecionada = imagemOriginal.getScaledInstance(w, h, Image.SCALE_SMOOTH);
				Perfil.repaint();
			} catch (Exception ex) {
				// Falha ao carregar imagem: apenas não exibe, sem quebrar a interface
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Adiciona um listener ao botão voltar para processar a navegação.
	 * 
	 * @param actionListener listener que processará o evento de clique
	 */
	public void voltar(ActionListener actionListener) {
		this.btnVoltar.addActionListener(actionListener);
	}
}