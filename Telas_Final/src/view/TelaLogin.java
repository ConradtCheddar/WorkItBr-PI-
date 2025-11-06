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
// Importa KeyboardFocusManager para gerenciar foco do teclado
import java.awt.KeyboardFocusManager;
// Importa ActionEvent que contém informações sobre eventos de ação
import java.awt.event.ActionEvent;
// Importa ActionListener para capturar eventos de clique em botões
import java.awt.event.ActionListener;
// Importa ComponentAdapter para responder a eventos de redimensionamento
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes
import java.awt.event.ComponentEvent;
// Importa HierarchyEvent para eventos de hierarquia de componentes
import java.awt.event.HierarchyEvent;
// Importa MouseAdapter para responder a eventos de mouse de forma simplificada
import java.awt.event.MouseAdapter;
// Importa MouseEvent que contém informações sobre eventos de mouse
import java.awt.event.MouseEvent;
// Importa MouseListener para capturar eventos de mouse
import java.awt.event.MouseListener;

// Importa ImageIcon para trabalhar com ícones de imagem
import javax.swing.ImageIcon;
// Importa JButton, componente de botão clicável
import javax.swing.JButton;
// Importa JLabel para exibição de textos e imagens
import javax.swing.JLabel;
// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa JPasswordField, campo de texto para senhas (oculta caracteres)
import javax.swing.JPasswordField;
// Importa JTextField, campo de texto de uma linha
import javax.swing.JTextField;
// Importa SwingConstants para constantes de alinhamento
import javax.swing.SwingConstants;
// Importa SwingUtilities para operações seguras na thread de interface
import javax.swing.SwingUtilities;
// Importa EmptyBorder para criar bordas vazias (espaçamento)
import javax.swing.border.EmptyBorder;

// Importa PromptSupport para adicionar placeholders em campos de texto
import org.jdesktop.swingx.prompt.PromptSupport;

// Importa FlatClientProperties para propriedades customizadas do tema FlatLaf
import com.formdev.flatlaf.FlatClientProperties;

// Importa a classe de modelo que representa um usuário no sistema (não utilizado - possível legado)
import model.Usuario;
// Importa o DAO para acesso a dados de usuários (não utilizado - possível legado)
import model.UsuarioDAO;
// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;
// Importa FontScaler, utilitário customizado para redimensionamento automático de fontes
import util.FontScaler;
// Importa enum FontSize que define tamanhos padrão de fonte
import util.FontScaler.FontSize;

/**
 * Tela de login do sistema.
 * <p>
 * Responsável por: coletar credenciais do usuário (usuário e senha), validar o
 * login, fornecer link para tela de cadastro, gerenciar foco dos campos para
 * evitar autofoco, aplicar placeholders visuais, e integrar-se com o controller
 * para autenticação no banco.
 * </p>
 */
public class TelaLogin extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;

	// Campo de texto para nome de usuário
	private JTextField txtUsuario;
	// Campo de senha (oculta caracteres)
	private JPasswordField passwordField;
	// Botão que executa a ação de login
	private JButton btnLogin;
	// Label clicável que navega para tela de cadastro
	private JLabel lblCadastrese;
	// Label que exibe o título da tela
	private JLabel lblTitulo;
	// Label que exibe mensagem "Ainda não tem um Login?"
	private JLabel lblntlg;

	/**
	 * Construtor que cria e configura a tela de login.
	 * <p>
	 * Inicializa todos os campos, configura placeholders, gerencia foco automático,
	 * adiciona efeitos visuais ao link de cadastro, e aplica redimensionamento de
	 * fontes.
	 * </p>
	 */
	public TelaLogin() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]",
				"[20px,grow][20px,grow 40][grow][grow 40][grow][grow 20][grow][grow]"));
		// Removida chamada direta que poderia forçar foco em um dos campos:
		// requestFocusInWindow();
		// Garante que o painel possa receber foco e, quando for exibido, limpa o foco
		// global para evitar que o primeiro campo seja focado automaticamente.
		setFocusable(true);
		addHierarchyListener(e -> {
			if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
				// Executa no próximo ciclo do EDT para garantir que a janela esteja
				// totalmente exibida antes de manipular o foco.
				SwingUtilities.invokeLater(() -> {
					// Limpa o foco global: nenhum componente terá foco inicial.
					KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
					// Requisita foco no painel (opcional) para que o foco não caia em nenhum
					// JTextField
					requestFocusInWindow();
				});
			}
		});

		lblTitulo = new JLabel("Login");
		add(lblTitulo, "cell 2 0,alignx center,growy");

		txtUsuario = new JTextField();
		txtUsuario.setForeground(Color.BLACK);
		txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		add(txtUsuario, "cell 1 1 3 1,grow");
		txtUsuario.setColumns(10);
		txtUsuario.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		txtUsuario.putClientProperty("JComponent.roundRect", true);
		txtUsuario.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, false);
		txtUsuario.setOpaque(false);
		// Usa @background para referenciar a cor definida em setBackground
		txtUsuario.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setLayout(new MigLayout("fill, insets 0", "[grow]", "[grow]"));
		passwordField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");
		passwordField.putClientProperty("JComponent.roundRect", true);
		passwordField.putClientProperty(FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON, false);
		passwordField.setOpaque(false);
		// Usa @background para referenciar a cor definida em setBackground
		passwordField.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");
		add(passwordField, "cell 1 3 3 1,grow");

		// Define os prompts imediatamente para garantir que os placeholders apareçam
		PromptSupport.setPrompt("Senha", passwordField);
		PromptSupport.setPrompt("Usuario", txtUsuario);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogin.setFocusTraversalPolicyProvider(true);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(0, 102, 204));
		btnLogin.putClientProperty(FlatClientProperties.STYLE, "arc:999;");
		add(btnLogin, "cell 2 5,grow");

		lblntlg = new JLabel("Ainda não tem um Login?");
		lblntlg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblntlg.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblntlg, "flowx,cell 2 6,alignx center,aligny center");

		lblCadastrese = new JLabel("Cadastre-se");
		lblCadastrese.setCursor(new Cursor(Cursor.HAND_CURSOR));

		lblCadastrese.setForeground(new Color(0, 102, 204));
		lblCadastrese.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastrese.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCadastrese, "cell 2 6,growy");
		lblCadastrese.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblCadastrese.setText(
						"<html><a style='text-decoration: underline; color: #0066cc;' href=''>Cadastre-se</a></html>");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblCadastrese.setText(
						"<html><a style='text-decoration: none; color: #0066cc;' href=''>Cadastre-se</a></html>");
			}

		});

		FontScaler.addAutoResize(this, new Object[] { lblTitulo, FontSize.TITULO },
				new Object[] { txtUsuario, FontSize.TEXTO }, new Object[] { passwordField, FontSize.TEXTO },
				new Object[] { btnLogin, FontSize.BOTAO }, new Object[] { lblCadastrese, FontSize.TEXTO },
				new Object[] { lblntlg, FontSize.TEXTO });
	}

	/**
	 * Retorna o texto digitado no campo de usuário.
	 * 
	 * @return texto do campo usuário
	 */
	public String getUsuario() { // Método getter público que retorna String
		return this.txtUsuario.getText(); // Retorna texto atual do campo txtUsuario
	} // Fim do método getUsuario

	/**
	 * Retorna o texto digitado no campo de senha.
	 * 
	 * @return texto do campo senha
	 */
	public char[] getSenha() { // Método getter público que retorna String
		return this.passwordField.getPassword(); // Retorna texto atual do campo passwordField (senha em texto simples)
	} // Fim do método getSenha

	/**
	 * Adiciona um listener ao botão login para processar a autenticação.
	 * 
	 * @param actionListener listener que processará o evento de clique
	 */
	public void login(ActionListener actionListener) { // Método público que recebe ActionListener como parâmetro
		this.btnLogin.addActionListener(actionListener); // Adiciona listener ao botão login para capturar evento de clique
	} // Fim do método login

	/**
	 * Adiciona um listener ao label "Cadastre-se" para navegar à tela de cadastro.
	 * 
	 * @param actionListener MouseListener para processar eventos de mouse
	 */
	public void cadastro(MouseListener actionListener) { // Método público que recebe MouseListener como parâmetro
		this.lblCadastrese.addMouseListener(actionListener); // Adiciona listener ao label "Cadastre-se" para capturar eventos de mouse (clique)
	} // Fim do método cadastro

	/**
	 * Limpa todos os campos do formulário.
	 * <p>
	 * Útil após login ou para resetar o formulário.
	 * </p>
	 */
	public void limparFormulario() { // Método público para limpar os campos do formulário
		txtUsuario.setText(""); // Define texto vazio no campo de usuário
		passwordField.setText(""); // Define texto vazio no campo de senha
	} // Fim do método limparFormulario
} // Fim da classe TelaLogin