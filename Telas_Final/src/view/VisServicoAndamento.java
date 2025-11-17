package view;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;

import model.Servico;
import model.Usuario;
import model.UsuarioDAO;
import net.miginfocom.swing.MigLayout;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import util.FontScaler;
import util.FontScaler.FontSize;

public class VisServicoAndamento extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panel, Perfil, PanelInfo, PanelDesc;
	private JLabel lblTitulo, lblModalidade, lblPreco, lblFoto, lblNome_Arquivo;
	private JButton btnFinalizar, btnArquivos;
	private JFileChooser fileChooser;
	private File ArquivoSelecionado;
	private JTextArea taTitulo;
	private JTextArea taModalidade;
	private JTextArea taPreco;
	private JTextArea tpDesc;
	private Image imagemOriginal;

	public VisServicoAndamento(Servico s) {
		setLayout(new MigLayout("", "[grow][grow 170]", "[grow][grow 130][grow 10][grow 10]"));

		Perfil = new JPanel();
		Perfil.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Contratante"));
		Perfil.setLayout(new MigLayout("", "[grow]", "[grow]"));
		add(Perfil, "cell 0 0,grow");

		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		Perfil.add(lblFoto, "cell 0 0,alignx center,aligny center");

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
		taTitulo.setBackground(PanelInfo.getBackground());
		taTitulo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taTitulo, "cell 0 0,grow");

		taModalidade = new JTextArea("Modalidade");
		taModalidade.setEditable(false);
		taModalidade.setFocusable(false);
		taModalidade.setLineWrap(true);
		taModalidade.setWrapStyleWord(true);
		taModalidade.setRows(1);
		taModalidade.setBackground(PanelInfo.getBackground());
		taModalidade.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taModalidade, "cell 0 1,grow");

		taPreco = new JTextArea("Preco");
		taPreco.setEditable(false);
		taPreco.setFocusable(false);
		taPreco.setLineWrap(true);
		taPreco.setWrapStyleWord(true);
		taPreco.setRows(1);
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
		tpDesc.setBackground(PanelDesc.getBackground());
		tpDesc.setText(s.getDescricao());

		JScrollPane scrollPane = new JScrollPane(tpDesc);
		scrollPane.setBorder(null);
		PanelDesc.add(scrollPane, "cell 0 0,grow");

		lblNome_Arquivo = new JLabel("");
		add(lblNome_Arquivo, "cell 0 3 2 1,alignx center");

		taTitulo.setText(s.getNome_Servico());
		taModalidade.setText(s.getModalidade());
		taPreco.setText(String.format("R$ %.2f", s.getValor()));

		Usuario u = s.getContratante();
		if (u == null && s.getIdContratante() != 0) {
			UsuarioDAO udao = new UsuarioDAO();
			u = udao.getUsuarioById(s.getIdContratante());
		}
		loadUserImage(u);
		
		// Adicionar callback para redimensionar a imagem quando o painel mudar de tamanho
		FontScaler.addResizeCallback(Perfil, () -> {
			if (imagemOriginal != null) {
				updateImageSize();
			}
		});

		btnArquivos = new JButton("Adicionar arquivos");
		add(btnArquivos, "flowx,cell 0 2 2 1,alignx left, gap 100");

		btnFinalizar = new JButton("Finalizar trabalho");
		add(btnFinalizar, "cell 1 2,alignx right, gap 0 100");

		FontScaler.addAutoResize(this, new Object[] { taTitulo, FontSize.SUBTITULO },
				new Object[] { taModalidade, FontSize.TEXTO }, new Object[] { taPreco, FontSize.TEXTO },
				new Object[] { tpDesc, FontSize.TEXTO }, new Object[] { btnFinalizar, FontSize.BOTAO },
				new Object[] { btnArquivos, FontSize.BOTAO }, new Object[] { lblNome_Arquivo, FontSize.TEXTO });
	}

	private void loadUserImage(Usuario u) {
		try {
			String caminho = null;
			if (u != null)
				caminho = u.getCaminhoFoto();
			if (caminho != null && !caminho.trim().isEmpty()) {
				File f = new File(caminho);
				if (f.exists()) {
					imagemOriginal = ImageIO.read(f);
					updateImageSize();
					return;
				}
				URL res = getClass().getResource(caminho.startsWith("/") ? caminho : "/" + caminho);
				if (res != null) {
					imagemOriginal = ImageIO.read(res);
					updateImageSize();
					return;
				}
			}
			URL fallback = getClass().getResource("/imagens/clickable_icon.png");
			if (fallback == null)
				fallback = getClass().getResource("/imagens/Casa.png");
			if (fallback != null) {
				imagemOriginal = ImageIO.read(fallback);
				updateImageSize();
				return;
			}
			File alt = new File("imagens/clickable_icon.png");
			if (alt.exists()) {
				imagemOriginal = ImageIO.read(alt);
				updateImageSize();
				return;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// Criar imagem padrão se nada funcionar
		imagemOriginal = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		updateImageSize();
	}

	/**
	 * Atualiza o tamanho da imagem mantendo a proporção (aspect ratio)
	 */
	private void updateImageSize() {
		if (imagemOriginal == null || Perfil.getWidth() <= 0 || Perfil.getHeight() <= 0) {
			return;
		}

		int panelWidth = Perfil.getWidth();
		int panelHeight = Perfil.getHeight();
		int imgWidth = imagemOriginal.getWidth(null);
		int imgHeight = imagemOriginal.getHeight(null);

		// Calcular proporção mantendo aspect ratio
		double scaleWidth = (double) panelWidth / imgWidth;
		double scaleHeight = (double) panelHeight / imgHeight;
		double scale = Math.min(scaleWidth, scaleHeight) * 0.9; // 90% do tamanho disponível

		int scaledWidth = (int) (imgWidth * scale);
		int scaledHeight = (int) (imgHeight * scale);

		Image scaledImage = imagemOriginal.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
		lblFoto.setIcon(new ImageIcon(scaledImage));
	}

	public String selecionarArquivo(Servico s) {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			String caminho = selectedFile.getAbsolutePath();
			File file = new File(selectedFile.getAbsolutePath());
			lblNome_Arquivo.setText(file.getName());
			s.setCaminhoArquivo(caminho);
			return caminho;
		}
		return null;
	}

	/**
	 * Adiciona ActionListener ao botão finalizar
	 * 
	 * @param actionlistener
	 */
	public void finalizar(ActionListener actionlistener) {
		this.btnFinalizar.addActionListener(actionlistener);
	}

	/**
	 * Adiciona ActionListener ao botão adicionar arquivos
	 * 
	 * @param actionlistener
	 */
	public void Adicionar(ActionListener actionlistener) {
		this.btnArquivos.addActionListener(actionlistener);
	}

}