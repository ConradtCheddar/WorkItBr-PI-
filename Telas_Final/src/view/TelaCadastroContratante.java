package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * Tela de cadastro de novo trabalho/serviço pelo contratante.
 * <p>
 * Responsável por: coletar informações do serviço (nome, modalidade, valor,
 * descrição), validar dados inseridos, fornecer área de texto multilinha para
 * descrição, aplicar formatação e estilos visuais, e integrar-se com o
 * controller para processar o cadastro no banco de dados.
 * </p>
 */
public class TelaCadastroContratante extends JPanel {

	// Identificador de versão para serialização (compatibilidade entre versões)
	private static final long serialVersionUID = 1L;
	// Campo de texto para nome do trabalho/serviço
	private JTextField tfNome;
	// Botão que executa a ação de cadastro
	private JButton btnCadastrarTrabalho;
	// Campo de texto para modalidade do serviço (presencial, remoto, híbrido)
	private JTextField tfModalidade;
	// Campo de texto para valor/preço do serviço
	private JTextField tfValor;
	// Área de texto multilinha para descrição detalhada do serviço
	private JTextArea tfDescricao;
	// Scroll pane que envolve a área de descrição
	private JScrollPane scrollPane;
	// Painel container para o botão de cadastro
	private JPanel panel;
	// Label que exibe o título da tela
	private JLabel lblTitulo;

	/**
	 * Construtor que cria e configura a tela de cadastro de trabalho.
	 * <p>
	 * Inicializa todos os campos, configura propriedades visuais, organiza o
	 * layout, e aplica redimensionamento de título.
	 * </p>
	 */
	public TelaCadastroContratante() {
		setPreferredSize(new Dimension(543, 388));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 30 20 20 20, gap 20", "[grow][grow,right]",
				"[grow 1][grow][grow][grow][grow 20]"));

		lblTitulo = new JLabel("Cadastrar Trabalho");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, "cell 0 0 2 1,growx,aligny center");

		tfNome = new JTextField();
		add(tfNome, "cell 0 1,height 20:20:40,grow");
		tfNome.setColumns(25);
		tfNome.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nome");
		tfNome.putClientProperty("JComponent.roundRect", true);
		tfNome.setOpaque(false);
		tfNome.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");
		tfNome.setSize(new Dimension(50, 50));

		scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 1 1 3,wmax 600,grow");

		tfDescricao = new JTextArea();
		scrollPane.setViewportView(tfDescricao);
		tfDescricao.setLineWrap(true);
		tfDescricao.setWrapStyleWord(true);
		tfDescricao.setFont(new Font("Monospaced", Font.PLAIN, 13));

		tfModalidade = new JTextField();
		add(tfModalidade, "cell 0 2,height 20:20:40,grow");
		tfModalidade.setColumns(10);
		tfModalidade.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Modalidade");
		tfModalidade.putClientProperty("JComponent.roundRect", true);
		tfModalidade.setOpaque(false);
		tfModalidade.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");

		tfValor = new JTextField();
		add(tfValor, "cell 0 3,height 20:20:40,grow");
		tfValor.setColumns(10);
		tfValor.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Valor");
		tfValor.putClientProperty("JComponent.roundRect", true);
		tfValor.setOpaque(false);
		tfValor.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");

		panel = new JPanel();
		add(panel, "cell 0 4 2 1,grow");
		panel.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[grow]"));

		btnCadastrarTrabalho = new JButton("Cadastrar Trabalho");
		panel.add(btnCadastrarTrabalho, "cell 2 0,grow");
		btnCadastrarTrabalho.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCadastrarTrabalho.setForeground(Color.WHITE);
		btnCadastrarTrabalho.setBackground(new Color(0, 102, 204));
		btnCadastrarTrabalho.putClientProperty("JComponent.roundRect", true);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSizeTitulo = Math.max(16, panelHeight / 20);
				lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, fontSizeTitulo));

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
		this.btnCadastrarTrabalho.addActionListener(actionlistener);
	}

	/**
	 * Limpa todos os campos de texto.
	 * <p>
	 * Útil após cadastro bem-sucedido ou para resetar o formulário.
	 * </p>
	 */
	public void limparCampos() {
		tfNome.setText("");
		tfModalidade.setText("");
		tfValor.setText("");
		tfDescricao.setText("");
	}

	// ==================== Getters & Setters ====================

	/**
	 * Retorna o campo de texto de nome.
	 * 
	 * @return JTextField do nome
	 */
	public JTextField getTfNome() {
		return tfNome;
	}

	/**
	 * Define o campo de texto de nome.
	 * 
	 * @param tfNome novo JTextField para nome
	 */
	public void setTfNome(JTextField tfNome) {
		this.tfNome = tfNome;
	}

	/**
	 * Retorna o botão de cadastrar trabalho.
	 * 
	 * @return JButton de cadastro
	 */
	public JButton getBtnCadastrarTrabalho() {
		return btnCadastrarTrabalho;
	}

	/**
	 * Define o botão de cadastrar trabalho.
	 * 
	 * @param btnCadastrarTrabalho novo JButton para cadastro
	 */
	public void setBtnCadastrarTrabalho(JButton btnCadastrarTrabalho) {
		this.btnCadastrarTrabalho = btnCadastrarTrabalho;
	}

	/**
	 * Retorna o campo de texto de modalidade.
	 * 
	 * @return JTextField da modalidade
	 */
	public JTextField getTfModalidade() {
		return tfModalidade;
	}

	/**
	 * Define o campo de texto de modalidade.
	 * 
	 * @param tfModalidade novo JTextField para modalidade
	 */
	public void setTfModalidade(JTextField tfModalidade) {
		this.tfModalidade = tfModalidade;
	}

	/**
	 * Retorna o campo de texto de valor.
	 * 
	 * @return JTextField do valor
	 */
	public JTextField getTfValor() {
		return tfValor;
	}

	/**
	 * Define o campo de texto de valor.
	 * 
	 * @param tfValor novo JTextField para valor
	 */
	public void setTfValor(JTextField tfValor) {
		this.tfValor = tfValor;
	}

	/**
	 * Retorna a área de texto de descrição.
	 * 
	 * @return JTextArea da descrição
	 */
	public JTextArea getTfDescricao() {
		return tfDescricao;
	}

	/**
	 * Define a área de texto de descrição.
	 * 
	 * @param tfDescricao nova JTextArea para descrição
	 */
	public void setTfDescricao(JTextArea tfDescricao) {
		this.tfDescricao = tfDescricao;
	}
}