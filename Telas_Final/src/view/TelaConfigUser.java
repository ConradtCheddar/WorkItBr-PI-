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
import javax.swing.JPasswordField;
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
	private JPasswordField pfSenha;
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

	public TelaConfigUser() {
		setPreferredSize(new Dimension(837, 635));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 20 20 20 20, gap 20", "[grow][grow][grow]", "[][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][]"));

		lblTitulo = new JLabel("Configurações");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, "cell 0 0 3 1,grow");

		lblNome = new JLabel("Nome");
		add(lblNome, "cell 0 1,alignx left");

		fundo = new JPanel();
		add(fundo, "cell 1 1 2 9,grow");
		fundo.setLayout(null);

		foto = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagemSelecionada != null) {
					g.drawImage(imagemSelecionada, 0, 0, getWidth(), getHeight(), this);
				} else {
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

		FontScaler.addResizeCallback(fundo, () -> {
			int fundoWidth = fundo.getWidth();
			int fundoHeight = fundo.getHeight();

			int fotoWidth = foto.getWidth();
			int fotoHeight = foto.getHeight();

			int x = (fundoWidth - fotoWidth) / 2;
			int y = (fundoHeight - fotoHeight) / 2;

			foto.setLocation(x, y);
		});

		tfNome = new JTextField();
		add(tfNome, "cell 0 2,grow");
		tfNome.setColumns(10);
		tfNome.putClientProperty("JComponent.roundRect", true);
		tfNome.setOpaque(false);
		tfNome.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE,
				"focusedBackground: null;" + "background: null");

		lblSenha = new JLabel("Senha");
		add(lblSenha, "flowx,cell 0 3,alignx left");

		pfSenha = new JPasswordField();
		add(pfSenha, "cell 0 4,grow");
		pfSenha.setColumns(10);
		pfSenha.putClientProperty("JComponent.roundRect", true);
		pfSenha.setOpaque(false);
		pfSenha.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE,
				"focusedBackground: null;" + "background: null");

		lblEmail = new JLabel("Email");
		add(lblEmail, "cell 0 5,alignx left");

		tfEmail = new JTextField();
		add(tfEmail, "cell 0 6,grow");
		tfEmail.setColumns(10);
		tfEmail.putClientProperty("JComponent.roundRect", true);
		tfEmail.setOpaque(false);
		tfEmail.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE,
				"focusedBackground: null;" + "background: null");

		lblTelefone = new JLabel("Telefone");
		add(lblTelefone, "cell 0 7,alignx left");

		tfTelefone = new JTextField();
		add(tfTelefone, "cell 0 8,grow");
		tfTelefone.setColumns(10);
		tfTelefone.putClientProperty("JComponent.roundRect", true);
		tfTelefone.setOpaque(false);
		tfTelefone.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE,
				"focusedBackground: null;" + "background: null");
		((AbstractDocument) tfTelefone.getDocument()).setDocumentFilter(new FieldValidator.TelefoneDocumentFilter());

		lblCPF = new JLabel("CPF/CNPJ");
		add(lblCPF, "cell 0 9,alignx left");

		tfCPF = new JTextField();
		add(tfCPF, "cell 0 10,grow");
		tfCPF.setColumns(10);
		tfCPF.putClientProperty("JComponent.roundRect", true);
		tfCPF.setOpaque(false);
		tfCPF.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE,
				"focusedBackground: null;" + "background: null");
		((AbstractDocument) tfCPF.getDocument()).setDocumentFilter(new FieldValidator.CpfCnpjDocumentFilter());

		lblGithub = new JLabel("Github");
		add(lblGithub, "cell 0 11,alignx left");

		txtGithub = new JTextField();
		txtGithub.setColumns(10);
		txtGithub.putClientProperty("JComponent.roundRect", true);
		txtGithub.setOpaque(false);
		txtGithub.putClientProperty(com.formdev.flatlaf.FlatClientProperties.STYLE,
				"focusedBackground: null;" + "background: null");
		add(txtGithub, "cell 0 12,grow");

		btnAlterarImagem = new JButton("Alterar Imagem");
		add(btnAlterarImagem, "cell 1 12 2 1,grow");
		btnAlterarDados = new JButton("Alterar Dados");
		add(btnAlterarDados, "cell 0 13 3 1,grow");
		btnAlterarDados.putClientProperty("JComponent.roundRect", true);

		FontScaler.addAutoResize(this, new Object[] { lblTitulo, FontSize.TITULO },
				new Object[] { lblNome, FontSize.TEXTO }, new Object[] { lblSenha, FontSize.TEXTO },
				new Object[] { lblEmail, FontSize.TEXTO }, new Object[] { lblTelefone, FontSize.TEXTO },
				new Object[] { lblCPF, FontSize.TEXTO }, new Object[] { lblGithub, FontSize.TEXTO },
				new Object[] { tfNome, FontSize.TEXTO }, new Object[] { pfSenha, FontSize.TEXTO },
				new Object[] { tfEmail, FontSize.TEXTO },
				new Object[] { tfTelefone, FontSize.TEXTO }, new Object[] { tfCPF, FontSize.TEXTO },
				new Object[] { txtGithub, FontSize.TEXTO }, new Object[] { btnAlterarDados, FontSize.BOTAO },
				new Object[] { btnAlterarImagem, FontSize.BOTAO });
	}

	public void setUserData(Usuario usuario) {
		tfNome.setText(usuario.getUsuario());
		pfSenha.setText(usuario.getSenha());
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

	public String getNome() {
		return tfNome.getText();
	}

	public String getSenha() {
		return new String(pfSenha.getPassword());
	}

	public String getEmail() {
		return tfEmail.getText();
	}

	public String getTelefone() {
		return tfTelefone.getText();
	}

	public String getCpfCnpj() {
		return tfCPF.getText();
	}

	public String getGithub() {
		return txtGithub.getText();
	}

	public boolean isNomeValido() {
		String nome = tfNome.getText();
		return nome != null && !nome.trim().isEmpty();
	}

	public boolean isSenhaValida() {
		String senha = new String(pfSenha.getPassword());
		return senha != null && !senha.trim().isEmpty();
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

	public void AlterarDados(ActionListener actionListener) {
		this.btnAlterarDados.addActionListener(actionListener);
	}

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
		return pfSenha;
	}

	public void setTfSenha(JPasswordField pfSenha) {
		this.pfSenha = pfSenha;
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