package telas_Final;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.ComponentOrientation;

public class TelaConfigUser extends JPanel {

	private static final long serialVersionUID = 1L;
	ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
	Image scaledImage2 = menuIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon menuResized = new ImageIcon(scaledImage2);

	ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
	Image scaledImage3 = barraIcon.getImage().getScaledInstance(24, 10, Image.SCALE_SMOOTH);
	ImageIcon barraResized = new ImageIcon(scaledImage3);

	Usuario u;

	private JTextField tfPesquisar;
	private JPanel foto;
	private JTextField tfNome;
	private JTextField tfSenha;
	private JTextField tfEmail;
	private JTextField tfTelefone;
	private JTextField tfCPF;

	/**
	 * Create the panel.
	 * 
	 * @param usuario
	 */

	public TelaConfigUser(Primario prim) {
		setPreferredSize(new Dimension(900, 700));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0",
				"[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]",
				"[35px][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 102, 204));
		add(panel, "flowx,cell 0 0 41 1,grow");
		panel.setLayout(new MigLayout("fill", "[][][][][][][][][][][][][]", "[]"));

		JLabel lblBarra = new JLabel(barraResized);
		lblBarra.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBarra.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				prim.mostrarTela(prim.TEMP_PANEL);
			}
		});
		panel.add(lblBarra, "cell 12 0,grow");
		lblBarra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JLabel lblMenu = new JLabel(menuResized);
		lblMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				prim.mostrarTela(prim.TEMP_PANEL);
			}
		});
		lblMenu.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblMenu, "cell 0 0,grow");
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "flowx,cell 6 0,grow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNewLabel_1, "cell 2 2");

		JPanel fundo = new JPanel();
		fundo.setPreferredSize(new Dimension(100, 100));
		fundo.setBackground(Color.DARK_GRAY);
		add(fundo, "cell 12 2 8 9,grow");
		fundo.setLayout(null);

		foto = new JPanel();
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

		UsuarioDAO dao = new UsuarioDAO();

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

		JButton btnNewButton = new JButton("Alterar Dados");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		add(btnNewButton, "cell 15 13 1 2,grow");
		btnNewButton.putClientProperty("JComponent.roundRect", true);

		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ImageIcon menuIcon = new ImageIcon(getClass().getResource("/imagens/Casa.png"));
				Image img = menuIcon.getImage();
				Image scaled = img.getScaledInstance(panel.getWidth() / 40, panel.getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
				ImageIcon barraIcon = new ImageIcon(getClass().getResource("/imagens/MenuBarra.png"));
				Image imgbarra = barraIcon.getImage();
				Image scaledBarra = imgbarra.getScaledInstance(panel.getWidth() / 40, panel.getHeight() * 2 / 4,
						Image.SCALE_SMOOTH);
				lblMenu.setIcon(new ImageIcon(scaled));
				lblBarra.setIcon(new ImageIcon(scaledBarra));
			}
		});

	}

	public void mostrarDados(Usuario usuario) {
		this.u = usuario;
		tfTelefone.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, usuario.getTelefone());
		tfCPF.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, usuario.getCpfCnpj());
		tfEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, usuario.getEmail());
		tfSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, usuario.getSenha());
		tfNome.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, usuario.getUsuario());

	}
}
