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
	private JLabel lblFoto; // mostra a foto do contratante
	private JPanel panelBotoes;

	public VisServico(Servico s) {
		setLayout(new MigLayout("", "[grow][grow 170]", "[grow][grow 130][grow 10]"));
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Contratante"));
		add(panel, "cell 0 0,grow");
		panel.setLayout(new CardLayout(0, 0));
		
		panelPerfil = new JPanel();
		// configurar painel de perfil com espaço para foto
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
		taTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		taTitulo.setBackground(panelInfo.getBackground());
		taTitulo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		panelInfo.add(taTitulo, "cell 0 0,grow");
		
		taModalidade = new JTextArea("Modalidade");
		taModalidade.setEditable(false);
		taModalidade.setFocusable(false);
		taModalidade.setLineWrap(true);
		taModalidade.setWrapStyleWord(true);
		taModalidade.setRows(1);
		taModalidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taModalidade.setBackground(panelInfo.getBackground());
		taModalidade.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		panelInfo.add(taModalidade, "cell 0 1,grow");
		
		taPreco = new JTextArea("Preço");
		taPreco.setEditable(false);
		taPreco.setFocusable(false);
		taPreco.setLineWrap(true);
		taPreco.setWrapStyleWord(true);
		taPreco.setRows(1);
		taPreco.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
		taDesc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		taDesc.setBackground(panelDesc.getBackground());
		taDesc.setText(s.getDescricao());
		
		JScrollPane scrollPane = new JScrollPane(taDesc);
		scrollPane.setBorder(null);
		panelDesc.add(scrollPane, "cell 0 0,grow");

		taTitulo.setText(s.getNome_Servico());
		taModalidade.setText(s.getModalidade());
		taPreco.setText(String.format("R$ %.2f", s.getValor()));
		
		// Carrega a foto do contratante (se disponível). Se o objeto Usuario não estiver presente,
		// tenta recuperar pelo idContratante via UsuarioDAO.
		Usuario u = s.getContratante();
		if (u == null && s.getIdContratante() != 0) {
			UsuarioDAO udao = new UsuarioDAO();
			u = udao.getUsuarioById(s.getIdContratante());
		}
		ImageIcon foto = loadUserImage(u, 150, 150);
		lblFoto.setIcon(foto);
		
		panelBotoes = new JPanel();
		add(panelBotoes, "cell 0 2 2 1,growx");
		
		btnAceitar = new JButton("Aceitar");
		btnAceitar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelBotoes.add(btnAceitar);
		
		// Aplicar FontScaler padronizado
		FontScaler.addAutoResize(this,
			new Object[] {taTitulo, FontSize.SUBTITULO},
			new Object[] {taModalidade, FontSize.TEXTO},
			new Object[] {taPreco, FontSize.TEXTO},
			new Object[] {taDesc, FontSize.TEXTO},
			new Object[] {btnAceitar, FontSize.BOTAO}
		);
	}
	
	/**
	 * Carrega e escala a imagem do usuário. Se não houver imagem válida, usa um placeholder
	 */
	private ImageIcon loadUserImage(Usuario u, int width, int height) {
		try {
			String caminho = null;
			if (u != null) caminho = u.getCaminhoFoto();
			// Tentar caminho informado
			if (caminho != null && !caminho.trim().isEmpty()) {
				File f = new File(caminho);
				if (f.exists()) {
					Image img = ImageIO.read(f);
					Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					return new ImageIcon(scaled);
				}
				// tentar recurso no classpath
				URL res = getClass().getResource(caminho.startsWith("/") ? caminho : "/" + caminho);
				if (res != null) {
					Image img = ImageIO.read(res);
					Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					return new ImageIcon(scaled);
				}
			}
			// alternativa: usar imagem padrão do projeto (imagens/clickable_icon.png ou Casa.png)
			URL fallback = getClass().getResource("/imagens/clickable_icon.png");
			if (fallback == null) fallback = getClass().getResource("/imagens/Casa.png");
			if (fallback != null) {
				Image img = ImageIO.read(fallback);
				Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaled);
			}
			// última tentativa: arquivo relativo
			File alt = new File("imagens/clickable_icon.png");
			if (alt.exists()) {
				Image img = ImageIO.read(alt);
				Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaled);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// se tudo falhar, retorna um ícone vazio
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		return new ImageIcon(bi);
	}
	
	public void aceitar(ActionListener actionListener) {
		this.btnAceitar.addActionListener(actionListener);
	}

}