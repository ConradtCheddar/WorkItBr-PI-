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

	public VisContratado(Usuario u) {
		setLayout(new MigLayout("fill", "[grow][grow][grow]", "[grow][grow][grow]"));

		panel = new JPanel();
		TitledBorder titledBorder = new TitledBorder(new LineBorder(Color.GRAY, 1), "Foto do Perfil");
		titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));
		panel.setBorder(titledBorder);
		add(panel, "cell 0 0 1 2,grow");
		panel.setLayout(new CardLayout(0, 0));

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

		FontScaler.addResizeCallback(Perfil, () -> {
			if (imagemOriginal != null) {
				imagemSelecionada = imagemOriginal.getScaledInstance(Perfil.getWidth(), Perfil.getHeight(),
						Image.SCALE_SMOOTH);
				Perfil.repaint();
			}
		});

		PanelInfo = new JPanel();
		TitledBorder titledBorder2 = new TitledBorder(new LineBorder(Color.GRAY, 1), "Informações do Contratado");
		titledBorder2.setTitleFont(new Font("Arial", Font.BOLD, 14));
		PanelInfo.setBorder(titledBorder2);
		add(PanelInfo, "cell 1 0 2 2,grow");
		PanelInfo.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow][grow]"));

		taNome = new JTextArea("Nome");
		taNome.setEditable(false);
		taNome.setFocusable(false);
		//taNome.setLineWrap(true);
		taNome.setWrapStyleWord(true);
		taNome.setRows(1);
		taNome.setBackground(PanelInfo.getBackground());
		taNome.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taNome, "cell 0 0,grow");

		taGithub = new JTextArea("Github");
		taGithub.setEditable(false);
		taGithub.setFocusable(false);
		//taGithub.setLineWrap(true);
		taGithub.setWrapStyleWord(true);
		taGithub.setRows(1);
		taGithub.setBackground(PanelInfo.getBackground());
		taGithub.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taGithub, "cell 0 1,grow");

		taEmail = new JTextArea("Email");
		taEmail.setEditable(false);
		taEmail.setFocusable(false);
		//taEmail.setLineWrap(true);
		taEmail.setWrapStyleWord(true);
		taEmail.setRows(1);
		taEmail.setBackground(PanelInfo.getBackground());
		taEmail.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taEmail, "cell 0 2,grow");

		taTelefone = new JTextArea("Telefone");
		taTelefone.setEditable(false);
		taTelefone.setFocusable(false);
		//taTelefone.setLineWrap(true);
		taTelefone.setWrapStyleWord(true);
		taTelefone.setRows(1);
		taTelefone.setBackground(PanelInfo.getBackground());
		taTelefone.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		PanelInfo.add(taTelefone, "cell 0 3,grow");

		taNome.setText(u.getUsuario());
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

		FontScaler.addAutoResize(this, new Object[] { taNome, FontSize.SUBTITULO },
				new Object[] { taGithub, FontSize.TEXTO }, new Object[] { taEmail, FontSize.TEXTO },
				new Object[] { taTelefone, FontSize.TEXTO }, new Object[] { btnVoltar, FontSize.BOTAO });

		if (u != null && u.getCaminhoFoto() != null && !u.getCaminhoFoto().isBlank()) {
			try {
				ImageIcon imgIcon = new ImageIcon(u.getCaminhoFoto());
				imagemOriginal = imgIcon.getImage();
				int w = Perfil.getWidth() > 0 ? Perfil.getWidth() : 200;
				int h = Perfil.getHeight() > 0 ? Perfil.getHeight() : 200;
				imagemSelecionada = imagemOriginal.getScaledInstance(w, h, Image.SCALE_SMOOTH);
				Perfil.repaint();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void voltar(ActionListener actionListener) {
		this.btnVoltar.addActionListener(actionListener);
	}
}