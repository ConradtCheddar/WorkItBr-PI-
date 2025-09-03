package telas_Final;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UsuarioDAO {

	static String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	static String Usuario = "root";
	static String Senha = "admin";

	public UsuarioDAO() {

	}

	public void cadastrar(Usuario u, String senha2) {

		if (u.isContratado() == true) {

			if (u.getEmail().isEmpty() || u.getUsuario().isEmpty() || u.getCpfCnpj().isEmpty()
					|| u.getTelefone().isEmpty() || u.getSenha().isEmpty() || senha2.isEmpty()) {

				JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);

			} else {

				if (u.getSenha().equals(senha2)) {

					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, Usuario, Senha);

						String sql = "INSERT INTO Login (Email, Nome, CPF_CNPJ, Telefone, Senha, idContratado) VALUES (?, ?, ?, ?, ?, ?)";
						var stmt = conn.prepareStatement(sql);
						stmt.setString(1, u.getEmail()); // Email
						stmt.setString(2, u.getUsuario()); // Nome
						stmt.setString(3, u.getCpfCnpj()); // CPF/CNPJ
						stmt.setString(4, u.getTelefone()); // Telefone
						stmt.setString(5, u.getSenha()); // Senha
						stmt.setBoolean(6, u.isContratado()); // contratado

						stmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "Sucesso!",
								JOptionPane.PLAIN_MESSAGE);
						Primario.mostrarTela(Primario.LOGIN_PANEL);

						stmt.close();
						conn.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Senha Incoreta", "Erro", JOptionPane.ERROR_MESSAGE);
				}
				;
			}

		} else if (u.isContratante() == true) {

			if (u.getEmail().isEmpty() || u.getUsuario().isEmpty() || u.getCpfCnpj().isEmpty()
					|| u.getTelefone().isEmpty() || u.getSenha().isEmpty() || senha2.isEmpty()) {

				JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);

			} else {

				if (u.getSenha().equals(senha2)) {

					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, Usuario, Senha);

						String sql = "INSERT INTO Login (Email, Nome, CPF_CNPJ, Telefone, Senha, idContratante) VALUES (?, ?, ?, ?, ?, ?)";
						var stmt = conn.prepareStatement(sql);
						stmt.setString(1, u.getEmail()); // Email
						stmt.setString(2, u.getUsuario()); // Nome
						stmt.setString(3, u.getCpfCnpj()); // CPF/CNPJ
						stmt.setString(4, u.getTelefone()); // Telefone
						stmt.setString(5, u.getSenha()); // Senha
						stmt.setBoolean(6, u.isContratante()); // contratante

						stmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "Sucesso!",
								JOptionPane.PLAIN_MESSAGE);
						Primario.mostrarTela(Primario.LOGIN_PANEL);

						stmt.close();
						conn.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Senha Incoreta", "Erro", JOptionPane.ERROR_MESSAGE);
				}
				;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public Usuario login(String nome, String senha) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(url, Usuario, Senha);

	        String sql = "SELECT * FROM Login WHERE Nome = ? AND Senha = ?";
	        var stmt = conn.prepareStatement(sql);

	        stmt.setString(1, nome);
	        stmt.setString(2, senha);

	        var rs = stmt.executeQuery();

	        if (rs.next()) {
	            Usuario u = new Usuario(
	                rs.getString("Email"),
	                rs.getString("Nome"),
	                rs.getString("CPF_CNPJ"),
	                rs.getString("Telefone"),
	                rs.getString("Senha"),
	                rs.getBoolean("idContratado"),
	                rs.getBoolean("idContratante")
	            );
	            rs.close();
	            stmt.close();
	            conn.close();
	            return u;
	        }

	        rs.close();
	        stmt.close();
	        conn.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}
	
	
	
	
}
