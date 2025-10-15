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

import model.Usuario;
import net.miginfocom.swing.MigLayout;

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

	/**
	 * Create the panel.
	 * 
	 * @param usuario
	 */

	public TelaConfigUser() {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0",
				"[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]",
				"[35px][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][35px]"));

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNewLabel_1, "cell 2 2");

		JPanel fundo = new JPanel();
		fundo.setPreferredSize(new Dimension(100, 100));
		fundo.setBackground(Color.DARK_GRAY);
		add(fundo, "cell 12 2 8 9,grow");
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
		foto.setBackground(new Color(0, 255, 0));
		foto.setBounds(10, 11, 349, 281);
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
		add(tfNome, "cell 2 3 5 1,growx");
		tfNome.setColumns(10);
		tfNome.putClientProperty("JComponent.roundRect", true);

		JLabel lblNewLabel_1_2 = new JLabel("Senha");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNewLabel_1_2, "cell 2 4");

		tfSenha = new JTextField();
		add(tfSenha, "cell 2 5 5 1,growx");
		tfSenha.setColumns(10);
		tfSenha.putClientProperty("JComponent.roundRect", true);

		JLabel lblNewLabel_1_3 = new JLabel("Email");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNewLabel_1_3, "cell 2 6");

		tfEmail = new JTextField();
		add(tfEmail, "cell 2 7 5 1,growx");
		tfEmail.setColumns(10);
		tfEmail.putClientProperty("JComponent.roundRect", true);

		JLabel lblNewLabel_1_4 = new JLabel("Telefone");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNewLabel_1_4, "cell 2 8");

		tfTelefone = new JTextField();
		add(tfTelefone, "cell 2 9 5 1,growx");
		tfTelefone.setColumns(10);
		tfTelefone.putClientProperty("JComponent.roundRect", true);

		JLabel lblNewLabel_1_5 = new JLabel("CPF");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNewLabel_1_5, "cell 2 10");

		tfCPF = new JTextField();
		add(tfCPF, "cell 2 11 5 1,growx");
		tfCPF.setColumns(10);
		tfCPF.putClientProperty("JComponent.roundRect", true);

		btnAlterarImagem = new JButton("Alterar Imagem");
		add(btnAlterarImagem, "cell 15 11,growx");
		btnAlterarDados = new JButton("Alterar Dados");
		btnAlterarDados.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(btnAlterarDados, "cell 15 14,grow");
		btnAlterarDados.putClientProperty("JComponent.roundRect", true);

	}

	// Set all fields from Usuario (for controller to call)
	public void setUserData(Usuario usuario) {
		tfNome.setText(usuario.getUsuario());
		tfSenha.setText(usuario.getSenha());
		tfEmail.setText(usuario.getEmail());
		tfTelefone.setText(usuario.getTelefone());
		tfCPF.setText(usuario.getCpfCnpj());
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

	// Getters for all fields (for controller to read user input)
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

	// Register listeners for buttons
	public void addAlterarDadosListener(ActionListener l) {
		btnAlterarDados.addActionListener(l);
	}

	public void addAlterarImagemListener(ActionListener l) {
		btnAlterarImagem.addActionListener(l);
	}

	// For image selection, expose a method to open file chooser and return path
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