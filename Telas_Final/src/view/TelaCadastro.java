// Define o pacote onde esta classe está localizada (agrupamento lógico de classes relacionadas)
package view;

// Importa Color para definição de cores personalizadas
import java.awt.Color;
// Importa Font para trabalhar com fontes de texto
import java.awt.Font;
// Importa ActionListener para capturar eventos de clique em botões
import java.awt.event.ActionListener;
// Importa ComponentAdapter para responder a eventos de redimensionamento
import java.awt.event.ComponentAdapter;
// Importa ComponentEvent que contém informações sobre eventos de componentes
import java.awt.event.ComponentEvent;

// Importa ButtonGroup para agrupar botões de opção exclusiva
import javax.swing.ButtonGroup;
// Importa JButton, componente de botão clicável
import javax.swing.JButton;
// Importa JPanel, container básico para agrupar componentes
import javax.swing.JPanel;
// Importa JPasswordField, campo de texto para senhas (oculta caracteres)
import javax.swing.JPasswordField;
// Importa JTextField, campo de texto de uma linha
import javax.swing.JTextField;
// Importa EmptyBorder para criar bordas vazias (espaçamento)
import javax.swing.border.EmptyBorder;
// Importa AbstractDocument para aplicar filtros de formatação em campos de texto
import javax.swing.text.AbstractDocument;

// Importa FlatClientProperties para propriedades customizadas do tema FlatLaf
import com.formdev.flatlaf.FlatClientProperties;

// Importa MigLayout, gerenciador de layout avançado e flexível
import net.miginfocom.swing.MigLayout;
// Importa FieldValidator, classe utilitária para validação e formatação de campos
import util.FieldValidator;
// Importa FontScaler, utilitário customizado para redimensionamento automático de fontes
import util.FontScaler;
// Importa enum FontSize que define tamanhos padrão de fonte
import util.FontScaler.FontSize;

// Importa JLabel para exibição de textos e imagens
import javax.swing.JLabel;
// Importa SwingConstants para constantes de alinhamento
import javax.swing.SwingConstants;
// Importa ComponentOrientation para definir orientação de componentes (RTL/LTR)
import java.awt.ComponentOrientation;

/**
 * Tela de cadastro de novos usuários no sistema.
 * <p>
 * Responsável por: coletar dados pessoais (email, telefone, CPF/CNPJ, usuário,
 * senha), permitir seleção do tipo de usuário (contratante ou contratado),
 * aplicar formatação automática em campos específicos (telefone, CPF), validar
 * confirmação de senha, fornecer feedback visual ao usuário, e integrar-se com
 * o controller para processar o cadastro no banco de dados.
 * </p>
 */
public class TelaCadastro extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;
	// Campo de texto para email do usuário
	private JTextField tfEmail;
	// Campo de texto para telefone (formatação automática aplicada)
	private JTextField tfTelefone;
	// Campo de texto para CPF ou CNPJ (formatação automática aplicada)
	private JTextField tfCPF;
	// Campo de texto para nome de usuário (login)
	private JTextField tfUsuario;
	// Campo de senha (oculta caracteres)
	private JPasswordField senha;
	// Campo de confirmação de senha (oculta caracteres)
	private JPasswordField senha2;
	// RadioButton customizado para selecionar tipo "Contratante"
	private ScalableRadioButton rdbtnContratante;
	// RadioButton customizado para selecionar tipo "Contratado"
	private ScalableRadioButton rdbtnContratado;
	// Botão que executa a ação de cadastro
	private JButton btnCadastrar;
	// Grupo que garante seleção exclusiva entre os RadioButtons
	private ButtonGroup div;
	// Label que exibe o título da tela
	private JLabel lblTitulo;

	/**
	 * Construtor que cria e configura a tela de cadastro.
	 * <p>
	 * Inicializa todos os campos, configura validadores e formatadores, organiza o
	 * layout, e aplica redimensionamento automático de fontes.
	 * </p>
	 */
	public TelaCadastro() {
		// Remove bordas para maximizar área útil
		setBorder(new EmptyBorder(0, 0, 0, 0));
		// Configura MigLayout: 5 colunas crescentes, 10 linhas crescentes
		setLayout(new MigLayout("fill, insets 0", "[grow][grow][grow][grow][grow]",
				"[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]"));

		// Cria grupo para radiobuttons (garante seleção exclusiva)
		div = new ButtonGroup();

		// Cria botão de cadastro com cores personalizadas
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBackground(new Color(0, 102, 204));
		// Aplica cantos arredondados ao botão
		btnCadastrar.putClientProperty("JComponent.roundRect", true);

		// Cria e configura o título da tela
		lblTitulo = new JLabel("Cadastro");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, "cell 2 0,grow");

		// Cria campo de email com propriedades customizadas
		tfEmail = new JTextField();
		add(tfEmail, "cell 1 1 3 1,growx");
		tfEmail.setColumns(10);
		// Define texto de placeholder
		tfEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
		// Aplica cantos arredondados
		tfEmail.putClientProperty("JComponent.roundRect", true);
		// Torna o fundo transparente
		tfEmail.setOpaque(false);
		// Remove cor de fundo ao focar
		tfEmail.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");

		// Cria campo de telefone com formatação automática
		tfTelefone = new JTextField();
		add(tfTelefone, "cell 1 2 3 1,growx");
		tfTelefone.setColumns(10);
		tfTelefone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Telefone");
		tfTelefone.putClientProperty("JComponent.roundRect", true);
		tfTelefone.setOpaque(false);
		tfTelefone.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");
		// Aplica formatador automático de telefone (adiciona parênteses e hífen)
		((AbstractDocument) tfTelefone.getDocument()).setDocumentFilter(new FieldValidator.TelefoneDocumentFilter());

		// Cria campo de CPF/CNPJ com formatação automática
		tfCPF = new JTextField();
		add(tfCPF, "cell 1 3 3 1,growx");
		tfCPF.setColumns(10);
		tfCPF.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "CPF ou CNPJ");
		tfCPF.putClientProperty("JComponent.roundRect", true);
		tfCPF.setOpaque(false);
		tfCPF.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");
		// Aplica formatador automático de CPF (adiciona pontos e hífen)
		((AbstractDocument) tfCPF.getDocument()).setDocumentFilter(new FieldValidator.CPFDocumentFilter());

		// Cria campo de usuário (nome de login)
		tfUsuario = new JTextField();
		add(tfUsuario, "cell 1 4 3 1,growx");
		tfUsuario.setColumns(10);
		tfUsuario.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
		tfUsuario.putClientProperty("JComponent.roundRect", true);
		tfUsuario.setOpaque(false);
		tfUsuario.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");

		// Cria campo de senha
		senha = new JPasswordField();
		add(senha, "cell 1 5 3 1,growx");
		senha.setColumns(10);
		senha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");
		senha.putClientProperty("JComponent.roundRect", true);
		senha.setOpaque(false);
		senha.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");

		// Cria campo de confirmação de senha
		senha2 = new JPasswordField();
		add(senha2, "cell 1 6 3 1,growx");
		senha2.setColumns(10);
		senha2.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Confirmar Senha");
		senha2.putClientProperty("JComponent.roundRect", true);
		senha2.setOpaque(false);
		senha2.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");

		// Cria RadioButton para tipo "Contratante" com orientação RTL (texto à
		// esquerda, botão à direita)
		rdbtnContratante = new ScalableRadioButton("Contratante");
		rdbtnContratante.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		rdbtnContratante.setHorizontalAlignment(SwingConstants.RIGHT);
		add(rdbtnContratante, "cell 1 7,alignx center,growy");
		// Adiciona ao grupo para garantir seleção exclusiva
		div.add(rdbtnContratante);

		// Cria RadioButton para tipo "Contratado"
		rdbtnContratado = new ScalableRadioButton("Contratado");
		add(rdbtnContratado, "cell 3 7,alignx center,growy");
		// Adiciona ao grupo para garantir seleção exclusiva
		div.add(rdbtnContratado);

		// Adiciona botão de cadastro
		add(btnCadastrar, "cell 2 8,grow");

		// Configura redimensionamento automático de fontes para todos os componentes
		FontScaler.addAutoResize(this, new Object[] { lblTitulo, FontSize.TITULO },
				new Object[] { tfEmail, FontSize.TEXTO }, new Object[] { tfTelefone, FontSize.TEXTO },
				new Object[] { tfCPF, FontSize.TEXTO }, new Object[] { tfUsuario, FontSize.TEXTO },
				new Object[] { senha, FontSize.TEXTO }, new Object[] { senha2, FontSize.TEXTO },
				new Object[] { btnCadastrar, FontSize.BOTAO }, new Object[] { rdbtnContratante, FontSize.TEXTO },
				new Object[] { rdbtnContratado, FontSize.TEXTO });

		// Atualizar ícones dos radio buttons quando redimensionar
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				rdbtnContratante.updateIconSize();
				rdbtnContratado.updateIconSize();
			}
		});
	}

	/**
	 * Adiciona um listener ao botão cadastrar para processar a ação de cadastro.
	 * <p>
	 * O controller deve implementar a validação dos dados e inserção no banco.
	 * </p>
	 * 
	 * @param actionlistener listener que processará o evento de clique
	 */
	public void cadastrar(ActionListener actionlistener) {
		this.btnCadastrar.addActionListener(actionlistener);
	}

	/**
	 * Limpa todos os campos de texto e desmarca os radiobuttons.
	 * <p>
	 * Útil após cadastro bem-sucedido ou para resetar o formulário.
	 * </p>
	 */
	public void limparCampos() {
		tfEmail.setText("");
		tfTelefone.setText("");
		tfCPF.setText("");
		tfUsuario.setText("");
		senha.setText("");
		senha2.setText("");
		div.clearSelection();
	}

	// ==================== Getters & Setters ====================

	/**
	 * Retorna o campo de texto de email.
	 * 
	 * @return JTextField do email
	 */
	public JTextField getTfEmail() {
		return tfEmail;
	}

	/**
	 * Define o campo de texto de email.
	 * 
	 * @param tfEmail novo JTextField para email
	 */
	public void setTfEmail(JTextField tfEmail) {
		this.tfEmail = tfEmail;
	}

	/**
	 * Retorna o campo de texto de telefone.
	 * 
	 * @return JTextField do telefone
	 */
	public JTextField getTfTelefone() {
		return tfTelefone;
	}

	/**
	 * Define o campo de texto de telefone.
	 * 
	 * @param tfTelefone novo JTextField para telefone
	 */
	public void setTfTelefone(JTextField tfTelefone) {
		this.tfTelefone = tfTelefone;
	}

	/**
	 * Retorna o campo de texto de CPF.
	 * 
	 * @return JTextField do CPF
	 */
	public JTextField getTfCPF() {
		return tfCPF;
	}

	/**
	 * Define o campo de texto de CPF.
	 * 
	 * @param tfCPF novo JTextField para CPF
	 */
	public void setTfCPF(JTextField tfCPF) {
		this.tfCPF = tfCPF;
	}

	/**
	 * Retorna o campo de texto de usuário.
	 * 
	 * @return JTextField do usuário
	 */
	public JTextField getTfUsuario() {
		return tfUsuario;
	}

	/**
	 * Define o campo de texto de usuário.
	 * 
	 * @param tfUsuario novo JTextField para usuário
	 */
	public void setTfUsuario(JTextField tfUsuario) {
		this.tfUsuario = tfUsuario;
	}

	/**
	 * Retorna o campo de senha.
	 * 
	 * @return JPasswordField da senha
	 */
	public JPasswordField getSenha() {
		return senha;
	}

	/**
	 * Define o campo de senha.
	 * 
	 * @param senha novo JPasswordField para senha
	 */
	public void setSenha(JPasswordField senha) {
		this.senha = senha;
	}

	/**
	 * Retorna o campo de confirmação de senha.
	 * 
	 * @return JPasswordField da confirmação de senha
	 */
	public JPasswordField getSenha2() {
		return senha2;
	}

	/**
	 * Define o campo de confirmação de senha.
	 * 
	 * @param senha2 novo JPasswordField para confirmação de senha
	 */
	public void setSenha2(JPasswordField senha2) {
		this.senha2 = senha2;
	}

	/**
	 * Retorna o RadioButton de tipo "Contratante".
	 * 
	 * @return ScalableRadioButton do contratante
	 */
	public ScalableRadioButton getRdbtnContratante() {
		return rdbtnContratante;
	}

	/**
	 * Define o RadioButton de tipo "Contratante".
	 * 
	 * @param rdbtnContratante novo ScalableRadioButton para contratante
	 */
	public void setRdbtnContratante(ScalableRadioButton rdbtnContratante) {
		this.rdbtnContratante = rdbtnContratante;
	}

	/**
	 * Retorna o RadioButton de tipo "Contratado".
	 * 
	 * @return ScalableRadioButton do contratado
	 */
	public ScalableRadioButton getRdbtnContratado() {
		return rdbtnContratado;
	}

	/**
	 * Define o RadioButton de tipo "Contratado".
	 * 
	 * @param rdbtnContratado novo ScalableRadioButton para contratado
	 */
	public void setRdbtnContratado(ScalableRadioButton rdbtnContratado) {
		this.rdbtnContratado = rdbtnContratado;
	}

	/**
	 * Retorna o botão de cadastrar.
	 * 
	 * @return JButton do cadastrar
	 */
	public JButton getBtnCadastrar() {
		return btnCadastrar;
	}

	/**
	 * Define o botão de cadastrar.
	 * 
	 * @param btnCadastrar novo JButton para cadastrar
	 */
	public void setBtnCadastrar(JButton btnCadastrar) {
		this.btnCadastrar = btnCadastrar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
