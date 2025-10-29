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

import org.jdesktop.swingx.prompt.PromptSupport;

import model.Usuario;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class TelaConfigUser extends JPanel {

	private static final long serialVersionUID = 1L;
	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);

	ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
	Image scaledImage3 = barraIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon barraResized = new ImageIcon(scaledImage3);

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

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblSenha, "flowx,cell 0 3,alignx left");

		tfSenha = new JTextField();
		add(tfSenha, "cell 0 4,grow");
		tfSenha.setColumns(10);
		tfSenha.putClientProperty("JComponent.roundRect", true);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblEmail, "cell 0 5,alignx left");

		tfEmail = new JTextField();
		add(tfEmail, "cell 0 6,grow");
		tfEmail.setColumns(10);
		tfEmail.putClientProperty("JComponent.roundRect", true);

		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblTelefone, "cell 0 7,alignx left");

		tfTelefone = new JTextField();
		add(tfTelefone, "cell 0 8,grow");
		tfTelefone.setColumns(10);
		tfTelefone.putClientProperty("JComponent.roundRect", true);

		JLabel lblCPF = new JLabel("CPF");
		lblCPF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCPF, "cell 0 9,alignx left");

		tfCPF = new JTextField();
		add(tfCPF, "cell 0 10,grow");
		tfCPF.setColumns(10);
		tfCPF.putClientProperty("JComponent.roundRect", true);

		lblGithub = new JLabel("Github");
		lblGithub.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblGithub, "cell 0 11,alignx left");

		txtGithub = new JTextField();
		txtGithub.setColumns(10);
		txtGithub.putClientProperty("JComponent.roundRect", true);
		add(txtGithub, "cell 0 12,grow");

		btnAlterarImagem = new JButton("Alterar Imagem");
		add(btnAlterarImagem, "cell 1 12 2 1,grow");
		btnAlterarDados = new JButton("Alterar Dados");
		btnAlterarDados.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(btnAlterarDados, "cell 0 14 3 1,grow");
		btnAlterarDados.putClientProperty("JComponent.roundRect", true);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 37);
				int fontSizebutt = Math.max(15, panelHeight / 30);
				int fontSize2 = Math.max(15, panelHeight / 23);
				Font italicPlaceholderFont = new Font("Tahoma", Font.PLAIN, fontSize);
				lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, fontSize2 + 5));
				btnAlterarDados.setFont(new Font("Tahoma", Font.PLAIN, fontSizebutt));
				btnAlterarImagem.setFont(new Font("Tahoma", Font.PLAIN, fontSizebutt));
				lblGithub.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				txtGithub.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				lblCPF.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfCPF.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfTelefone.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				lblEmail.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfEmail.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				lblSenha.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfSenha.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				lblNome.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				tfNome.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});

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

	public String getNome() {
		return tfNome.getText();
	}

	public String getSenha() {
		return tfSenha.getText();
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
	public ImageIcon getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(ImageIcon menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Image getScaledImage2() {
		return scaledImage2;
	}

	public void setScaledImage2(Image scaledImage2) {
		this.scaledImage2 = scaledImage2;
	}

	public ImageIcon getMenuResized() {
		return menuResized;
	}

	public void setMenuResized(ImageIcon menuResized) {
		this.menuResized = menuResized;
	}

	public ImageIcon getBarraIcon() {
		return barraIcon;
	}

	public void setBarraIcon(ImageIcon barraIcon) {
		this.barraIcon = barraIcon;
	}

	public Image getScaledImage3() {
		return scaledImage3;
	}

	public void setScaledImage3(Image scaledImage3) {
		this.scaledImage3 = scaledImage3;
	}

	public ImageIcon getBarraResized() {
		return barraResized;
	}

	public void setBarraResized(ImageIcon barraResized) {
		this.barraResized = barraResized;
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