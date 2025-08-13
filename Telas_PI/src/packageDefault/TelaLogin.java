package packageDefault;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JButton btnLogin;

	/**
	 * Create the panel.
	 */
	public TelaLogin(Seila janela) {
		
		seila = janela;
		
		setBorder(new EmptyBorder(0, 0, 0, 0));
		
		setLayout(new MigLayout("", "[grow][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][grow]", "[grow][][][][][][][][][][][][][][][][][][][][][grow]"));
		
		JLabel lblNewLabel = new JLabel("Teste");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, "cell 8 6 16 2,grow");
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Container contentPane = seila.getContentPane();
				contentPane.remove(TelaLogin.this);
				
						
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	  String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";  
	  String Usuario = "root";
	  String Senha = "admin";
	  private JPasswordField passwordField;
	public TelaLogin() {
		setTitle("WorkITBr");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1169, 788);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("fill, insets 0", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[35px][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][][][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		contentPane.add(panel, "cell 0 0 27 2,grow");
		panel.setLayout(new MigLayout("fill", "[center]", "[]"));
		
		
		
		lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(lblNewLabel_1, "cell 8 5 2 2");
		
		txtUsuario = new JTextField();
		contentPane.add(txtUsuario, "cell 8 7 11 3,grow");
		txtUsuario.setColumns(10);
		
		lblNewLabel_2 = new JLabel("Senha");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(lblNewLabel_2, "cell 8 14 1 2");
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String usuario = txtUsuario.getText();
		        String senha = new String(passwordField.getPassword());

		        try {
		        	
		            Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection conn = DriverManager.getConnection(url, Usuario, Senha);

		            
		            String sql = "SELECT * FROM Login WHERE Nome = ? AND Senha = ?";
		            var stmt = conn.prepareStatement(sql);

		           
		            stmt.setString(1, usuario);
		            stmt.setString(2, senha);

		            var rs = stmt.executeQuery();

		            if (rs.next()) {
		                System.out.println(" Login realizado com sucesso");
		              
		            } else {
		                System.out.println(" Usuário ou senha incorretos");
		            }

		            rs.close();
		            stmt.close();
		            conn.close();
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		
		passwordField = new JPasswordField();
		contentPane.add(passwordField, "cell 8 16 11 3,grow");
		contentPane.add(btnLogin, "cell 11 23 4 6,growx");
		
		panel.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(ComponentEvent e) {
		        int panelHeight = panel.getHeight();
		        int fontSize = Math.max(35, panelHeight / 3);
		        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
		    }
		});
		
		

		
		  
		
	}

}
