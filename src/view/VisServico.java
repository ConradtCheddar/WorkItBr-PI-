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
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.awt.image.BufferedImage;
import model.Usuario;
import model.UsuarioDAO;

import javax.imageio.ImageIO;

public class VisServico extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JPanel panel;
	JPanel Perfil;
	JPanel PanelInfo;
	JPanel PanelDesc;
	JLabel lblTitulo;
	JLabel lblModalidade;
	JLabel lblPreco;
	JButton btnAceitar;
	private JLabel tpDesc;
    private JLabel lblFoto; // mostra a foto do contratante

	/**
	 * Create the panel.
	 */
	public VisServico(Servico s) {
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow]"));
		
		panel = new JPanel();
		add(panel, "cell 0 0 4 6,grow");
		panel.setLayout(new CardLayout(0, 0));
		
		Perfil = new JPanel();
		// configurar painel de perfil com espaço para foto
		Perfil.setLayout(new MigLayout("", "[grow]", "[grow]"));
		panel.add(Perfil, "name_1709392782600");
		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		Perfil.add(lblFoto, "cell 0 0,alignx center,aligny center");
		
		PanelInfo = new JPanel();
		add(PanelInfo, "cell 4 0 6 7,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][][grow][grow][][grow][grow][grow][grow][grow]"));
		
		lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblTitulo, "cell 0 0 9 2,grow");
		
		lblModalidade = new JLabel("Modalidade");
		lblModalidade.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblModalidade, "cell 0 2 9 3,grow");
		
		lblPreco = new JLabel("Preco");
		lblPreco.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblPreco, "cell 0 5 9 3,grow");
		
		PanelDesc = new JPanel();
		add(PanelDesc, "cell 0 8 10 8,grow");
		PanelDesc.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		tpDesc = new JLabel("");
		PanelDesc.add(tpDesc, "cell 0 0");
		
		btnAceitar = new JButton("Aceitar");
		add(btnAceitar, "cell 0 16 11 1,alignx center");

		lblTitulo.setText(s.getNome_Servico());
		lblModalidade.setText(s.getModalidade());
		lblPreco.setText(Double.toString(s.getValor()));
		tpDesc.setText(s.getDescricao());
		

		// Carrega a foto do contratante (se disponível). Se o objeto Usuario não estiver presente,
		// tenta recuperar pelo idContratante via UsuarioDAO.
		Usuario u = s.getContratante();
		if (u == null && s.getIdContratante() != 0) {
			UsuarioDAO udao = new UsuarioDAO();
			u = udao.getUsuarioById(s.getIdContratante());
		}
		ImageIcon foto = loadUserImage(u, 150, 150);
		lblFoto.setIcon(foto);
		

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
			// fallback: usar imagem padrão do projeto (imagens/clickable_icon.png ou Casa.png)
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