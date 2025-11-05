package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;

import org.jdesktop.swingx.prompt.PromptSupport;

import model.Usuario;
import net.miginfocom.swing.MigLayout;
import util.FieldValidator;
import util.FontScaler;
import util.FontScaler.FontSize;

import javax.swing.SwingConstants;

public class TelaConfigUser extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField tfPesquisar;
	private JPanel foto;
	private JPanel cPanel;
	private JTextField tfNome;
	private JTextField tfSenha;
	private JTextField tfEmail;
	private JTextField tfTelefone;
	private JTextField tfCPF;
	private Image imagemSelecionada;
	private JButton btnAlterarDados;
	private JButton btnAlterarImagem;
	private JLabel lblGithub;
	private JTextField txtGithub;
	private JLabel lblTitulo;
	private JPanel fundo;
	private JLabel lblNome;
	private JLabel lblSenha;
	private JLabel lblEmail;
	private JLabel lblTelefone;
	private JLabel lblCPF;

	/**
	 * Create the panel.
	 * 
	 * @param usuario
	 */

	public TelaConfigUser() {
		setPreferredSize(new Dimension(837, 635));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 20 20 20 20, gap 20", "[grow][grow][grow]",
				"[][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow]"));

		lblTitulo = new JLabel("Configurações");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, "cell 0 0 3 1,grow");

		lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblNome, "cell 0 1,alignx left");

		fundo = new JPanel();
		add(fundo, "cell 1 1 2 11,grow");
		fundo.setLayout(null);

		foto = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagemSelecionada != null) {
					g.drawImage(imagemSelecionada, 0, 0, getWidth(), getHeight(), this);
				} else {
					// Desenha "???" no centro quando não há imagem
					g.setColor(Color.GRAY);
					int panelWidth = getWidth();
					int panelHeight = getHeight();
					int fontSize = Math.min(panelWidth, panelHeight) / 3;
					Font questionFont = new Font("Tahoma", Font.BOLD, fontSize);
					g.setFont(questionFont);
					
					String text = "???";
					java.awt.FontMetrics fm = g.getFontMetrics();
					int textWidth = fm.stringWidth(text);
					int textHeight = fm.getAscent();
					
					int x = (panelWidth - textWidth) / 2;
					int y = (panelHeight + textHeight) / 2 - fm.getDescent();
					
					g.drawString(text, x, y);
				}
			}
		};
		foto.setBounds(10, 11, 476, 390);
		fundo.add(foto);

		fundo.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int fundoWidth = fundo.getWidth();
				int fundoHeight = fundo.getHeight();

				int fotoWidth = foto.getWidth();
				int fotoHeight = foto.getHeight();

				int x = (fundoWidth - fotoWidth) / 2;
				int y = (fundoHeight - fotoHeight) / 2;

				foto.setLocation(x, y);
			}
		});

		tfNome = new JTextField();
		add(tfNome, "cell 0 2,grow");
		tfNome.setColumns(10);
		tfNome.putClientProperty("JComponent.roundRect", true);
		tfNome.setOpaque(false);
		tfNome.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");

		lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblSenha, "flowx,cell 0 3,alignx left");

		tfSenha = new JTextField();
		add(tfSenha, "cell 0 4,grow");
		tfSenha.setColumns(10);
		tfSenha.putClientProperty("JComponent.roundRect", true);
		tfSenha.setOpaque(false);
		tfSenha.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");

		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblEmail, "cell 0 5,alignx left");

		tfEmail = new JTextField();
		add(tfEmail, "cell 0 6,grow");
		tfEmail.setColumns(10);
		tfEmail.putClientProperty("JComponent.roundRect", true);
		tfEmail.setOpaque(false);
		tfEmail.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");

		lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(lblTelefone, "cell 0 7,alignx left");

		tfTelefone = new JTextField();
		add(tfTelefone, "cell 0 8,grow");
		tfTelefone.setColumns(10);
		tfTelefone.putClientProperty("JComponent.roundRect", true);
		tfTelefone.setOpaque(false);
		tfTelefone.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");
		// Aplicar formatação automática de telefone
		((AbstractDocument) tfTelefone.getDocument()).setDocumentFilter(new FieldValidator.TelefoneDocumentFilter());

		lblCPF = new JLabel("CPF");
		lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCPF, "cell 0 9,alignx left");

		tfCPF = new JTextField();
		add(tfCPF, "cell 0 10,grow");
		tfCPF.setColumns(10);
		tfCPF.putClientProperty("JComponent.roundRect", true);
		tfCPF.setOpaque(false);
		tfCPF.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");
		// Aplicar formatação automática de CPF
		((AbstractDocument) tfCPF.getDocument()).setDocumentFilter(new FieldValidator.CPFDocumentFilter());

		lblGithub = new JLabel("Github");
		lblGithub.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblGithub, "cell 0 11,alignx left");

		txtGithub = new JTextField();
		txtGithub.setColumns(10);
		txtGithub.putClientProperty("JComponent.roundRect", true);
		txtGithub.setOpaque(false);
		txtGithub.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE, 
			"focusedBackground: null;" +
			"background: null");
		add(txtGithub, "cell 0 12,grow");

		btnAlterarImagem = new JButton("Alterar Imagem");
		add(btnAlterarImagem, "cell 1 12 2 1,grow");
		btnAlterarDados = new JButton("Alterar Dados");
		btnAlterarDados.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(btnAlterarDados, "cell 0 14 3 1,grow");
		btnAlterarDados.putClientProperty("JComponent.roundRect", true);

		FontScaler.addAutoResize(this,
			new Object[] {lblTitulo, FontSize.TITULO},
			new Object[] {lblNome, FontSize.TEXTO},
			new Object[] {lblSenha, FontSize.TEXTO},
			new Object[] {lblEmail, FontSize.TEXTO},
			new Object[] {lblTelefone, FontSize.TEXTO},
			new Object[] {lblCPF, FontSize.TEXTO},
			new Object[] {lblGithub, FontSize.TEXTO},
			new Object[] {tfNome, FontSize.TEXTO},
			new Object[] {tfSenha, FontSize.TEXTO},
			new Object[] {tfEmail, FontSize.TEXTO},
			new Object[] {tfTelefone, FontSize.TEXTO},
			new Object[] {tfCPF, FontSize.TEXTO},
			new Object[] {txtGithub, FontSize.TEXTO},
			new Object[] {btnAlterarDados, FontSize.BOTAO},
			new Object[] {btnAlterarImagem, FontSize.BOTAO}
		);
	}

	public void setUserData(Usuario usuario) {
		tfNome.setText(usuario.getUsuario());
		tfSenha.setText(usuario.getSenha());
		tfEmail.setText(usuario.getEmail());
		tfTelefone.setText(usuario.getTelefone());
		tfCPF.setText(usuario.getCpfCnpj());
		txtGithub.setText(usuario.getGithub());
		if (usuario.getCaminhoFoto() != null) {
			ImageIcon imgIcon = new ImageIcon(usuario.getCaminhoFoto());
			imagemSelecionada = imgIcon.getImage().getScaledInstance(foto.getWidth(), foto.getHeight(),
					Image.SCALE_SMOOTH);
			foto.repaint();
		} else {
			imagemSelecionada = null;
			foto.repaint();
		}
	}

	/**
	 * Retorna o texto atual do campo de nome.
	 * @return nome preenchido no formulário
	 */
	public String getNome() {
		return tfNome.getText();
	}

	/**
	 * Retorna o texto atual do campo de senha.
	 * @return senha preenchida no formulário
	 */
	public String getSenha() {
		return tfSenha.getText();
	}

	/**
	 * Retorna o texto atual do campo de email.
	 * @return email preenchido no formulário
	 */
	public String getEmail() {
		return tfEmail.getText();
	}

	/**
	 * Retorna o texto atual do campo de telefone.
	 * @return telefone preenchido no formulário
	 */
	public String getTelefone() {
		return tfTelefone.getText();
	}

	/**
	 * Retorna o texto atual do campo CPF/CNPJ.
	 * @return cpf/cnpj preenchido no formulário
	 */
	public String getCpfCnpj() {
		return tfCPF.getText();
	}

	/**
	 * Retorna o texto atual do campo Github.
	 * @return github preenchido no formulário
	 */
	public String getGithub() {
		return txtGithub.getText();
	}

	public void addAlterarDadosListener(ActionListener l) {
		btnAlterarDados.addActionListener(l);
	}

	public void addAlterarImagemListener(ActionListener l) {
		btnAlterarImagem.addActionListener(l);
	}

	public String selecionarImagem() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagens", "jpg", "png", "jpeg", "gif");
		fileChooser.setFileFilter(filter);
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String caminho = selectedFile.getAbsolutePath();
			ImageIcon selectedImageIcon = new ImageIcon(selectedFile.getAbsolutePath());
			imagemSelecionada = selectedImageIcon.getImage().getScaledInstance(foto.getWidth(), foto.getHeight(),
					Image.SCALE_SMOOTH);
			foto.repaint();
			return caminho;
		}
		return null;
	}

	/**
	 * Metodo responsavel pelo funcionamento do botão "Alterar dados"
	 */
	public void AlterarDados(ActionListener actionListener) {
		this.btnAlterarDados.addActionListener(actionListener);
	}

	/**
	 * getters e setters
	 */
	public JTextField getTfPesquisar() {
		return tfPesquisar;
	}

	public void setTfPesquisar(JTextField tfPesquisar) {
		this.tfPesquisar = tfPesquisar;
	}

	public JPanel getFoto() {
		return foto;
	}

	public void setFoto(JPanel foto) {
		this.foto = foto;
	}

	public JTextField getTfNome() {
		return tfNome;
	}

	public void setTfNome(JTextField tfNome) {
		this.tfNome = tfNome;
	}

	public JTextField getTfSenha() {
		return tfSenha;
	}

	public void setTfSenha(JTextField tfSenha) {
		this.tfSenha = tfSenha;
	}

	public JTextField getTfEmail() {
		return tfEmail;
	}

	public void setTfEmail(JTextField tfEmail) {
		this.tfEmail = tfEmail;
	}

	public JTextField getTfTelefone() {
		return tfTelefone;
	}

	public void setTfTelefone(JTextField tfTelefone) {
		this.tfTelefone = tfTelefone;
	}

	public JTextField getTfCPF() {
		return tfCPF;
	}

	public void setTfCPF(JTextField tfCPF) {
		this.tfCPF = tfCPF;
	}

	public JTextField getTxtGithub() {
		return txtGithub;
	}

	public void setTxtGithub(JTextField txtGithub) {
		this.txtGithub = txtGithub;
	}

	public Image getImagemSelecionada() {
		return imagemSelecionada;
	}

	public void setImagemSelecionada(Image imagemSelecionada) {
		this.imagemSelecionada = imagemSelecionada;
	}

	public JButton getBtnAlterarDados() {
		return btnAlterarDados;
	}

	public void setBtnAlterarDados(JButton btnAlterarDados) {
		this.btnAlterarDados = btnAlterarDados;
	}

	public JButton getBtnAlterarImagem() {
		return btnAlterarImagem;
	}

	public void setBtnAlterarImagem(JButton btnAlterarImagem) {
		this.btnAlterarImagem = btnAlterarImagem;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}