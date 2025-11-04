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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;

import model.Usuario;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import util.FontScaler;
import util.FontScaler.FontSize;

public class VisContratado extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel Perfil;
	private JPanel PanelInfo;
	private JTextArea taNome;
	private JTextArea taGithub;
	private JTextArea taEmail;
	private JTextArea taTelefone;
	private JButton btnVoltar;
    private Image imagemSelecionada;
    private Image imagemOriginal;

	/**
	 * Create the panel.
	 */
    public VisContratado(Usuario u) {
		setLayout(new MigLayout("fill, insets 20 20 20 20, gap 20", "[grow][grow][grow]", "[grow][grow][grow]"));
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Perfil"));
		add(panel, "cell 0 0 1 2,grow");
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
		panel.add(Perfil, "name_12377154952900");
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
		PanelInfo.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Contratado"));
		add(PanelInfo, "cell 1 0 2 2,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow][grow]"));
		
		taNome = new JTextArea("Nome");
		taNome.setEditable(false);
		taNome.setFocusable(false);
		taNome.setLineWrap(true);
		taNome.setWrapStyleWord(true);
		taNome.setRows(1);
		taNome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		taNome.setBackground(PanelInfo.getBackground());
		taNome.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taNome, "cell 0 0,grow");
		
		taGithub = new JTextArea("Github");
		taGithub.setEditable(false);
		taGithub.setFocusable(false);
		taGithub.setLineWrap(true);
		taGithub.setWrapStyleWord(true);
		taGithub.setRows(1);
		taGithub.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taGithub.setBackground(PanelInfo.getBackground());
		taGithub.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taGithub, "cell 0 1,grow");
		
		taEmail = new JTextArea("Email");
		taEmail.setEditable(false);
		taEmail.setFocusable(false);
		taEmail.setLineWrap(true);
		taEmail.setWrapStyleWord(true);
		taEmail.setRows(1);
		taEmail.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taEmail.setBackground(PanelInfo.getBackground());
		taEmail.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taEmail, "cell 0 2,grow");
		
		taTelefone = new JTextArea("Telefone");
		taTelefone.setEditable(false);
		taTelefone.setFocusable(false);
		taTelefone.setLineWrap(true);
		taTelefone.setWrapStyleWord(true);
		taTelefone.setRows(1);
		taTelefone.setFont(new Font("Tahoma", Font.PLAIN, 16));
		taTelefone.setBackground(PanelInfo.getBackground());
		taTelefone.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taTelefone, "cell 0 3,grow");

		taNome.setText(u.getUsuario());
		// Exibe github, com alternativa caso não esteja preenchido no banco
		String githubText = (u.getGithub() != null && !u.getGithub().isBlank()) ? u.getGithub() : "Não informado";
		taGithub.setText(githubText);
		taEmail.setText(u.getEmail());
		taTelefone.setText(u.getTelefone());
		
		btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnVoltar, "cell 0 2 3 1,alignx center,aligny center");
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		// Aplicar FontScaler padronizado
		FontScaler.addAutoResize(this,
			new Object[] {taNome, FontSize.SUBTITULO},
			new Object[] {taGithub, FontSize.TEXTO},
			new Object[] {taEmail, FontSize.TEXTO},
			new Object[] {taTelefone, FontSize.TEXTO},
			new Object[] {btnVoltar, FontSize.BOTAO}
		);
		
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