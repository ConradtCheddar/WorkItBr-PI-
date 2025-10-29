package view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Usuario;
import net.miginfocom.swing.MigLayout;

public class VisContratado extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel Perfil;
	private JPanel PanelInfo;
	private JLabel lblNome;
	private JLabel lblGithub;
	private JLabel lblEmail;
	private JLabel lblTelefone;
	private JButton btnVoltar;
    private Image imagemSelecionada;
    private Image imagemOriginal;

	/**
	 * Create the panel.
	 */
	public VisContratado(Usuario u) {
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow]"));
		
		panel = new JPanel();
		add(panel, "cell 0 0 4 6,grow");
		panel.setLayout(new CardLayout(0, 0));
		
		// painel Perfil renderiza a foto do usuário quando disponível
		Perfil = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagemSelecionada != null) {
					g.drawImage(imagemSelecionada, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};
		panel.add(Perfil, "name_1709392782600");
		// Redimensiona a imagem quando o painel mudar de tamanho
		Perfil.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (imagemOriginal != null) {
					imagemSelecionada = imagemOriginal.getScaledInstance(Perfil.getWidth(), Perfil.getHeight(), Image.SCALE_SMOOTH);
					Perfil.repaint();
				}
			}
		});
		
		PanelInfo = new JPanel();
		add(PanelInfo, "cell 4 0 6 15,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow][grow][][grow][grow][][grow][grow][grow][grow][grow]"));
		
		lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblNome, "cell 0 0 9 2,grow");
		
		lblGithub = new JLabel("Github");
		lblGithub.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblGithub, "cell 0 2 9 3,grow");
		
		lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblEmail, "cell 0 5 9 3,grow");
		
		lblTelefone = new JLabel("Telefone");
		lblTelefone.setHorizontalAlignment(SwingConstants.CENTER);
		PanelInfo.add(lblTelefone, "cell 0 8 9 3,grow");
		
		btnVoltar = new JButton("Voltar");
		add(btnVoltar, "cell 0 16 11 1,alignx center");

		lblNome.setText(u.getUsuario());
		// Exibe github, com alternativa caso não esteja preenchido no banco
		String githubText = (u.getGithub() != null && !u.getGithub().isBlank()) ? u.getGithub() : "Não informado";
		lblGithub.setText(githubText);
		lblEmail.setText(u.getEmail());
		lblTelefone.setText(u.getTelefone());
		
		// Carrega a imagem do usuário caso exista caminho
		if (u != null && u.getCaminhoFoto() != null && !u.getCaminhoFoto().isBlank()) {
			try {
				ImageIcon imgIcon = new ImageIcon(u.getCaminhoFoto());
				imagemOriginal = imgIcon.getImage();
				// se o painel já tem tamanho, escala imediatamente, senão será escalada no componentResized
				int w = Perfil.getWidth() > 0 ? Perfil.getWidth() : 200;
				int h = Perfil.getHeight() > 0 ? Perfil.getHeight() : 200;
				imagemSelecionada = imagemOriginal.getScaledInstance(w, h, Image.SCALE_SMOOTH);
				Perfil.repaint();
			} catch (Exception ex) {
				// falha ao carregar imagem: apenas não exibe
				ex.printStackTrace();
			}
		}
		

	}
	
	public void voltar(ActionListener actionListener) {
		this.btnVoltar.addActionListener(actionListener);
	}

}