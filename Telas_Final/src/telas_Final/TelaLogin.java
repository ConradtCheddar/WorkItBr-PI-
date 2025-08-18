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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class TelaLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtUsuario;
	private JPasswordField passwordField;

	String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	String Usuario = "root";
	String Senha = "admin";
	private JTextField txtUsurio;
	private JPasswordField passwordField_1;
	

	/**
	 * Create the panel.
	 * 
	 * @param frame
	 */
	public TelaLogin(Primario prim) {
		setPreferredSize(new Dimension(700, 500));
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new MigLayout("fill, insets 0", "[20px][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][20px]", "[35px][grow][grow][grow][grow][grow][grow][grow][grow][][grow][grow][grow][grow][grow][grow][grow][grow][][][][grow][grow][grow][grow][grow][][grow][][grow][grow][grow][grow][grow][grow][grow][35px]"));
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		add(panel, "flowx,cell 0 0 25 1,grow");
		panel.setLayout(new MigLayout("fill", "[center]", "[]"));

		JLabel lblNewLabel = new JLabel("WorkITBr");
		panel.add(lblNewLabel, "cell 0 0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		
		JLabel lblNewLabel_1 = new JLabel("Usuário");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1, "cell 10 9 5 2,grow");

		txtUsurio = new JTextField();
		txtUsurio.setForeground(Color.BLACK);
		txtUsurio.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsurio.setText("");
		add(txtUsurio, "cell 10 11 5 1,grow");
		txtUsurio.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Senha");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1_1, "cell 10 18 5 2,grow");

		passwordField_1 = new JPasswordField();
		passwordField_1.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField_1.setLayout(new MigLayout("fill, insets 0", "[grow]", "[grow]"));

		add(passwordField_1, "flowy,cell 10 20 5 2,grow");

//		textField = new JTextField();
//		add(textField, "cell 10 10 5 1,grow");
//		textField.setColumns(10);
//		
//		passwordField_1 = new JPasswordField();
//		add(passwordField_1, "cell 10 18 5 2,grow");

		JButton btnLogin = new JButton("Login");
		add(btnLogin, "cell 11 28 3 2,grow");
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
				lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				btnLogin.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
				lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
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

	}

}
