package model;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Navegador;
import view.Primario;

public class UsuarioDAO {

	static String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	static String Usuario = "root";
	static String Senha = "root";
	Navegador n = new Navegador(null);
	public UsuarioDAO() {

	}

	public void cadastrarU(Usuario u, String senha2) {

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
						n.navegarPara("LOGIN");

						stmt.close();
						conn.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Senhas se diferem", "Erro", JOptionPane.ERROR_MESSAGE);
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
						n.navegarPara("LOGIN");

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
<<<<<<< HEAD:Telas_Final/src/model/UsuarioDAO.java
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "SELECT * FROM Login WHERE Nome = ? AND Senha = ?";
			var stmt = conn.prepareStatement(sql);

			stmt.setString(1, nome);
			stmt.setString(2, senha);

			var rs = stmt.executeQuery();

			if (rs.next()) {
				Usuario u = new Usuario(rs.getString("Email"), rs.getString("Nome"), rs.getString("CPF_CNPJ"),
						rs.getString("Telefone"), rs.getString("Senha"), rs.getBoolean("idContratado"),
						rs.getBoolean("idContratante"));
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

=======
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
	                rs.getBoolean("idContratante"),
	                rs.getString("caminhofoto"),
	                rs.getInt("idLogin")
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
	
	public Usuario configU(String nome) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(url, Usuario, Senha);

	        String sql = "SELECT * FROM Login WHERE Nome = ?";
	        var stmt = conn.prepareStatement(sql);

	        stmt.setString(1, nome);

	        var rs = stmt.executeQuery();

	        if (rs.next()) {
	            Usuario u = new Usuario(
	                rs.getString("Email"),
	                rs.getString("Nome"),
	                rs.getString("CPF_CNPJ"),
	                rs.getString("Telefone"),
	                rs.getString("Senha"),
	                rs.getBoolean("idContratado"),
	                rs.getBoolean("idContratante"),
	                rs.getString("caminhofoto"),
	                rs.getInt("idLogin")
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
	
	public void atualizarUsuario(Usuario u) {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(url, Usuario, Senha);

	        String sql = "UPDATE Login SET Email = ?, Nome = ?, CPF_CNPJ = ?, Telefone = ?, Senha = ?, caminhofoto = ? WHERE idLogin = ?";
	        var stmt = conn.prepareStatement(sql);
	        stmt.setString(1, u.getEmail());
	        stmt.setString(2, u.getUsuario());
	        stmt.setString(3, u.getCpfCnpj());
	        stmt.setString(4, u.getTelefone());
	        stmt.setString(5, u.getSenha());
	        stmt.setString(6, u. getCaminhoFoto());
	        stmt.setInt(7, u.getIdLogin());

	        int rowsUpdated = stmt.executeUpdate();

	        if (rowsUpdated > 0) {
	            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(null, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
	        }

	        stmt.close();
	        conn.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Erro ao atualizar dados.", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	
>>>>>>> tela_config_usuario:Telas_Final/src/telas_Final/UsuarioDAO.java
}
