package view;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.prompt.PromptSupport;

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
import java.awt.event.ActionEvent;

public class VisServicoAndamento extends JPanel {

	private static final long serialVersionUID = 1L;

	JPanel panel;
	JPanel Perfil;
	JPanel PanelInfo;
	JPanel PanelDesc;
	JLabel lblTitulo;
	JLabel lblModalidade;
	JLabel lblPreco;
	private JLabel tpDesc;
	private JLabel lblFoto;
	private JButton btnFinalizar;

	public VisServicoAndamento(Servico s) {
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow]", "[grow]10[grow][grow][grow]"));

		Perfil = new JPanel();
		Perfil.setLayout(new MigLayout("", "[grow]", "[grow]"));
		add(Perfil, "cell 0 0,grow");
		lblFoto = new JLabel();
		lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
		Perfil.add(lblFoto, "cell 0 0");

		PanelInfo = new JPanel();
		add(PanelInfo, "cell 1 0 5 1,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));

		lblTitulo = new JLabel("Titulo");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblTitulo, "cell 0 0,grow");

		lblModalidade = new JLabel("Modalidade");
		lblModalidade.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblModalidade, "cell 0 1,grow");

		lblPreco = new JLabel("Preco");
		lblPreco.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblPreco, "cell 0 2, grow");

		lblTitulo.setText(s.getNome_Servico());
		lblModalidade.setText(s.getModalidade());
		lblPreco.setText(Double.toString(s.getValor()));

		// Carrega a foto do contratante (se disponível)
		Usuario u = s.getContratante();
		if (u == null && s.getIdContratante() != 0) {
			UsuarioDAO udao = new UsuarioDAO();
			u = udao.getUsuarioById(s.getIdContratante());
		}
		ImageIcon foto = loadUserImage(u, 150, 150);
		lblFoto.setIcon(foto);

		PanelDesc = new JPanel();
		add(PanelDesc, "cell 0 1 6 2,grow");
		PanelDesc.setLayout(new MigLayout("", "[grow]", "[grow]"));

		tpDesc = new JLabel("");
		PanelDesc.add(tpDesc, "cell 0 0");
		tpDesc.setText(s.getDescricao());

		btnFinalizar = new JButton("Finalizar trabalho");
		add(btnFinalizar, "cell 0 3,alignx center");
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 37);
				int fontSize2 = Math.max(15, panelHeight / 23);
				Font italicPlaceholderFont = new Font("Tahoma", Font.PLAIN, fontSize);
				btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, fontSize2));
				
			}
		});

	}

	private ImageIcon loadUserImage(Usuario u, int width, int height) {
		try {
			String caminho = null;
			if (u != null)
				caminho = u.getCaminhoFoto();
			if (caminho != null && !caminho.trim().isEmpty()) {
				File f = new File(caminho);
				if (f.exists()) {
					Image img = ImageIO.read(f);
					Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					return new ImageIcon(scaled);
				}
				URL res = getClass().getResource(caminho.startsWith("/") ? caminho : "/" + caminho);
				if (res != null) {
					Image img = ImageIO.read(res);
					Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
					return new ImageIcon(scaled);
				}
			}
			// alternativa: usar imagem padrão do projeto (imagens/clickable_icon.png ou Casa.png)
			URL fallback = getClass().getResource("/imagens/clickable_icon.png");
			if (fallback == null)
				fallback = getClass().getResource("/imagens/Casa.png");
			if (fallback != null) {
				Image img = ImageIO.read(fallback);
				Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaled);
			}
			File alt = new File("imagens/clickable_icon.png");
			if (alt.exists()) {
				Image img = ImageIO.read(alt);
				Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaled);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		return new ImageIcon(bi);
	}
	
	public void finalizar(ActionListener actionlistener) {
		this.btnFinalizar.addActionListener(actionlistener);
	}

}