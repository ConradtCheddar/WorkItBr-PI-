package packageDefault;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JFrame;
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

public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JButton btnLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
		
		JLabel lblNewLabel = new JLabel("WorkITBr");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setForeground(Color.WHITE);
		panel.add(lblNewLabel, "cell 0 0, grow, push");
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		contentPane.add(panel_1, "cell 2 6 2 1");
		
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
