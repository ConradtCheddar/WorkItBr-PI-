package view;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.Servico;

import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.awt.image.BufferedImage;
import model.Usuario;
import model.UsuarioDAO;

import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import util.FontScaler;
import util.FontScaler.FontSize;

public class VisServico extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JPanel panelPerfil;
	private JPanel panelInfo;
	private JPanel panelDesc;
	private JTextArea taTitulo;
	private JTextArea taModalidade;
	private JTextArea taPreco;
	private JButton btnAceitar;
	private JTextArea taDesc;
	private JLabel lblFoto;
	private JPanel panelBotoes;
	private Image imagemOriginal;

	public VisServico(Servico s) {
		setLayout(new MigLayout("", "[grow][grow 170]", "[grow][grow 130][grow 10]"));

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Contratante"));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new CardLayout(0, 0));

		panelPerfil = new JPanel();
		panelPerfil.setLayout(new MigLayout("", "[grow]", "[grow]"));
		panel.add(panelPerfil, "name_1709392782600");
		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		panelPerfil.add(lblFoto, "cell 0 0,alignx center,aligny center");

		panelInfo = new JPanel();
		panelInfo.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Serviço"));
		add(panelInfo, "cell 1 0,grow");
		panelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));

		taTitulo = new JTextArea("Título");
		taTitulo.setEditable(false);
		taTitulo.setFocusable(false);
		taTitulo.setLineWrap(true);
		taTitulo.setWrapStyleWord(true);
		taTitulo.setRows(1);
		taTitulo.setBackground(panelInfo.getBackground());
		taTitulo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		panelInfo.add(taTitulo, "cell 0 0,grow");

		taModalidade = new JTextArea("Modalidade");
		taModalidade.setEditable(false);
		taModalidade.setFocusable(false);
		taModalidade.setLineWrap(true);
		taModalidade.setWrapStyleWord(true);
		taModalidade.setRows(1);
		taModalidade.setBackground(panelInfo.getBackground());
		taModalidade.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		panelInfo.add(taModalidade, "cell 0 1,grow");

		taPreco = new JTextArea("Preço");
		taPreco.setEditable(false);
		taPreco.setFocusable(false);
		taPreco.setLineWrap(true);
		taPreco.setWrapStyleWord(true);
		taPreco.setRows(1);
		taPreco.setBackground(panelInfo.getBackground());
		taPreco.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		panelInfo.add(taPreco, "cell 0 2,grow");

		panelDesc = new JPanel();
		panelDesc.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Descrição"));
		add(panelDesc, "cell 0 1 2 1,grow");
		panelDesc.setLayout(new MigLayout("", "[grow]", "[grow]"));

		taDesc = new JTextArea();
		taDesc.setEditable(false);
		taDesc.setFocusable(false);
		taDesc.setLineWrap(true);
		taDesc.setWrapStyleWord(true);
		taDesc.setBackground(panelDesc.getBackground());
		taDesc.setText(s.getDescricao());

		JScrollPane scrollPane = new JScrollPane(taDesc);
		scrollPane.setBorder(null);
		panelDesc.add(scrollPane, "cell 0 0,grow");

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
		FontScaler.addResizeCallback(panelPerfil, () -> {
			if (imagemOriginal != null) {
				updateImageSize();
			}
		});

		panelBotoes = new JPanel();
		add(panelBotoes, "cell 0 2 2 1,growx");

		btnAceitar = new JButton("Aceitar");
		panelBotoes.add(btnAceitar);

		FontScaler.addAutoResize(this, new Object[] { taTitulo, FontSize.SUBTITULO },
				new Object[] { taModalidade, FontSize.TEXTO }, new Object[] { taPreco, FontSize.TEXTO },
				new Object[] { taDesc, FontSize.TEXTO }, new Object[] { btnAceitar, FontSize.BOTAO });
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
		if (imagemOriginal == null || panelPerfil.getWidth() <= 0 || panelPerfil.getHeight() <= 0) {
			return;
		}

		int panelWidth = panelPerfil.getWidth();
		int panelHeight = panelPerfil.getHeight();
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

	public void aceitar(ActionListener actionListener) {
		this.btnAceitar.addActionListener(actionListener);
	}
}