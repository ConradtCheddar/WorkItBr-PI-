package telas_Final;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import net.miginfocom.swing.MigLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtUsuario;
	private JPasswordField passwordField;

	String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	String Usuario = "root";
	String Senha = "admin";

	/**
	 * Create the panel.
	 * 
	 * @param frame
	 */
	public TelaLogin(Primario prim) {
		setPreferredSize(new Dimension(700, 500));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0",
				"[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]",
				"[35px][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][][][][grow][grow][grow][grow][grow][][grow][][grow][][][grow][grow][grow][grow][grow][grow][][35px]"));

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		add(panel, "flowx,cell 0 0 25 1,grow");
		panel.setLayout(new MigLayout("fill", "[center]", "[]"));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);

		txtUsuario = new JTextField();
		txtUsuario.setForeground(Color.BLACK);
		txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		add(txtUsuario, "cell 10 11 5 1,grow");
		txtUsuario.setColumns(10);
		PromptSupport.setPrompt("Usuario", txtUsuario);
		PromptSupport.setForeground(new Color(100, 100, 100), txtUsuario);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setLayout(new MigLayout("fill, insets 0", "[grow]", "[grow]"));
		PromptSupport.setPrompt("Senha", passwordField);
		PromptSupport.setForeground(new Color(100, 100, 100), passwordField);

		add(passwordField, "flowy,cell 10 20 5 2,grow");

		JButton btnLogin = new JButton("Login");
		btnLogin.setFocusTraversalPolicyProvider(true);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(0, 0, 255));
		add(btnLogin, "cell 11 28 3 2,grow");

		JLabel lblNewLabel_2 = new JLabel("Ainda não tem um login?");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblNewLabel_2, "cell 12 30,grow");

		JLabel lblNewLabel_3 = new JLabel("Cadastre-se");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				prim.mostrarTela(prim.CAD1_PANEL);
			}
		});

		prim.requestFocusInWindow();

		lblNewLabel_3.setForeground(new Color(51, 102, 255));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 8));
		add(lblNewLabel_3, "cell 13 30,growx");

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usuario = txtUsuario.getText()
						;
				String senha = passwordField.getText();
				prim.mostrarTela(prim.TEMP_PANEL);
				try {

					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection conn = DriverManager.getConnection(url, Usuario, Senha);

					String sql = "SELECT * FROM Login WHERE Nome = ? AND Senha = ?";
					var stmt = conn.prepareStatement(sql);

					stmt.setString(1, usuario);
				stmt.setString(2, senha);

				var rs = stmt.executeQuery();

				if (rs.next()) {
	               int idlogin = rs.getInt("idlogin");
	               if(idlogin == 1) {
	            	   prim.mostrarTela(Primario.ADM_PANEL);
	               }
	                } else {
	                    JOptionPane.showMessageDialog(null, "erro", "erro",JOptionPane.ERROR_MESSAGE);
	                    
	                }
	
				rs.close();
				stmt.close();
			  conn.close();
				} catch (Exception ex) {
		          ex.printStackTrace();
			}
			}
		});

		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 17);
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int panelHeight = getHeight();
				int fontSize = Math.max(15, panelHeight / 37);
				Font italicPlaceholderFont = new Font("Tahoma", Font.PLAIN, fontSize);
				// lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				btnLogin.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				// lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				PromptSupport.setPrompt("Senha", passwordField);
				PromptSupport.setPrompt("Usuario", txtUsuario);
				txtUsuario.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				passwordField.setFont(italicPlaceholderFont);
			}
		});

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);
			System.out.println("Conexão estabelecida com sucesso!");
			Statement stmt = conn.createStatement();
			stmt.execute("SELECT * FROM Login");
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		setFocusable(true);
		requestFocusInWindow();
		SwingUtilities.invokeLater(() -> {
			btnLogin.requestFocusInWindow();
		});

	}

}
