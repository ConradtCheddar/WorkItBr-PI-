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
import util.FontScaler;
import util.FontScaler.FontSize;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class TelaCadastroContratante extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfNome;
	private JButton btnCadastrarTrabalho;
	private JTextField tfModalidade;
	private JTextField tfValor;
	private JTextArea tfDescricao;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel lblTitulo;

	public TelaCadastroContratante() {
		setPreferredSize(new Dimension(543, 388));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 30 20 20 20, gap 20", "[grow][grow,right]",
				"[grow 1][grow][grow][grow][grow 20]"));

		lblTitulo = new JLabel("Cadastrar Trabalho");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, "cell 0 0 2 1,growx,aligny center");

		tfNome = new JTextField();
		add(tfNome, "cell 0 1,grow");
		tfNome.setColumns(25);
		tfNome.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nome");
		tfNome.putClientProperty("JComponent.roundRect", true);
		tfNome.setOpaque(false);
		tfNome.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");

		scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 1 1 3,wmax 600,grow");

		tfDescricao = new JTextArea();
		scrollPane.setViewportView(tfDescricao);
		tfDescricao.setLineWrap(true);
		tfDescricao.setWrapStyleWord(true);

		tfModalidade = new JTextField();
		add(tfModalidade, "cell 0 2,grow");
		tfModalidade.setColumns(10);
		tfModalidade.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Modalidade");
		tfModalidade.putClientProperty("JComponent.roundRect", true);
		tfModalidade.setOpaque(false);
		tfModalidade.putClientProperty(FlatClientProperties.STYLE, "focusedBackground: null;" + "background: null");

		tfValor = new JTextField();
		add(tfValor, "cell 0 3,grow");
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
		btnCadastrarTrabalho.setForeground(Color.WHITE);
		btnCadastrarTrabalho.setBackground(new Color(0, 102, 204));
		btnCadastrarTrabalho.putClientProperty("JComponent.roundRect", true);

		FontScaler.addAutoResizeWithCallback(this,
			() -> {
				// Redimensionar componentes dinamicamente
				int panelHeight = getHeight();
				int panelWidth = getWidth();
				
				// Altura dos campos baseada no tamanho do painel
				int fieldHeight = Math.max(25, panelHeight / 15);
				
				// Atualizar preferred size dos campos
				java.awt.Dimension fieldSize = new java.awt.Dimension(tfNome.getWidth(), fieldHeight);
				tfNome.setPreferredSize(new java.awt.Dimension(fieldSize.width, fieldHeight));
				tfModalidade.setPreferredSize(new java.awt.Dimension(fieldSize.width, fieldHeight));
				tfValor.setPreferredSize(new java.awt.Dimension(fieldSize.width, fieldHeight));
				
				// Atualizar botão
				int buttonHeight = Math.max(30, panelHeight / 12);
				btnCadastrarTrabalho.setPreferredSize(new java.awt.Dimension(
					btnCadastrarTrabalho.getWidth(), buttonHeight));
				
				// Revalidar layout
				revalidate();
				repaint();
			},
			new Object[] { lblTitulo, FontSize.TITULO },
			new Object[] { tfNome, FontSize.TEXTO },
			new Object[] { tfModalidade, FontSize.TEXTO },
			new Object[] { tfValor, FontSize.TEXTO },
			new Object[] { tfDescricao, FontSize.TEXTO },
			new Object[] { btnCadastrarTrabalho, FontSize.BOTAO }
		);

	}

	public void cadastrar(ActionListener actionlistener) {
		this.btnCadastrarTrabalho.addActionListener(actionlistener);
	}

	public void limparCampos() {
		tfNome.setText("");
		tfModalidade.setText("");
		tfValor.setText("");
		tfDescricao.setText("");
	}

	public JTextField getTfNome() {
		return tfNome;
	}

	public void setTfNome(JTextField tfNome) {
		this.tfNome = tfNome;
	}

	public JButton getBtnCadastrarTrabalho() {
		return btnCadastrarTrabalho;
	}

	public void setBtnCadastrarTrabalho(JButton btnCadastrarTrabalho) {
		this.btnCadastrarTrabalho = btnCadastrarTrabalho;
	}

	public JTextField getTfModalidade() {
		return tfModalidade;
	}

	public void setTfModalidade(JTextField tfModalidade) {
		this.tfModalidade = tfModalidade;
	}

	public JTextField getTfValor() {
		return tfValor;
	}

	public void setTfValor(JTextField tfValor) {
		this.tfValor = tfValor;
	}

	public JTextArea getTfDescricao() {
		return tfDescricao;
	}

	public void setTfDescricao(JTextArea tfDescricao) {
		this.tfDescricao = tfDescricao;
	}
}
