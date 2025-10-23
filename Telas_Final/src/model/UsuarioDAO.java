package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Base64;

import javax.swing.JOptionPane;

public class UsuarioDAO {

	static String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	static String Usuario = "workitbr";
	static String Senha = "1234";

	public UsuarioDAO() {

	}
	
	public Usuario code64(Usuario u) throws FileNotFoundException, IOException {
		// Trocar CaminhoFoto por imagem64
		String imagem64 = u.getImagem64();
		File fotoFile = new File(u.getCaminhoFoto());
		byte[] fotoContent = new byte[(int) fotoFile.length()];
		try(FileInputStream fis = new FileInputStream(fotoFile)) {
			fis.read(fotoContent);
			}
			imagem64 = Base64.getEncoder().encodeToString(fotoContent);
			u.setImagem64(imagem64);
			return u;
	}
	
	public Usuario decode64(Usuario u) {
		// Trocar CaminhoFoto por imagem64
		String imagem64 = u.getImagem64();
		if (imagem64 == null || imagem64.isEmpty()) {
			// Não há imagem para decodificar
			return u;
		}
		byte[] fotoContent = Base64.getDecoder().decode(imagem64);
		String caminhofoto = System.getProperty("user.dir") + "/src/imagens/fotoPerfil.jpg";
		try (FileOutputStream fos = new FileOutputStream(caminhofoto)) {
			fos.write(fotoContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		u.setCaminhoFoto(caminhofoto);
		return u;
		}


	public boolean cadastrarU(Usuario u, String senha2) {

		if (u.isContratado() == true) {
			if (u.getEmail().isEmpty() || u.getUsuario().isEmpty() || u.getCpfCnpj().isEmpty()
					|| u.getTelefone().isEmpty() || u.getSenha().isEmpty() || senha2.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
				return false;
			} else {
				if (u.getSenha().equals(senha2)) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, Usuario, Senha);

						String sql = "INSERT INTO Usuarios (Email, Nome_Usuario, CPF_CNPJ, Telefone, Senha, isContratado) VALUES (?, ?, ?, ?, ?, ?)";
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

						stmt.close();
						conn.close();
						return true;
					} catch (Exception ex) {
						ex.printStackTrace();
						return false;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Senhas se diferem", "Erro", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		} else if (u.isContratante() == true) {
			if (u.getEmail().isEmpty() || u.getUsuario().isEmpty() || u.getCpfCnpj().isEmpty()
					|| u.getTelefone().isEmpty() || u.getSenha().isEmpty() || senha2.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
				return false;
			} else {
				if (u.getSenha().equals(senha2)) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, Usuario, Senha);

						String sql = "INSERT INTO Usuarios (Email, Nome_Usuario, CPF_CNPJ, Telefone, Senha, isContratante) VALUES (?, ?, ?, ?, ?, ?)";
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

						stmt.close();
						conn.close();
						return true;
					} catch (Exception ex) {
						ex.printStackTrace();
						return false;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Senha Incoreta", "Erro", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public Usuario login(String nome, String senha) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "SELECT * FROM Usuarios WHERE Nome_Usuario = ? AND Senha = ?";
			var stmt = conn.prepareStatement(sql);

			stmt.setString(1, nome);
			stmt.setString(2, senha);

			var rs = stmt.executeQuery();

			if (rs.next()) {
				Usuario u = new Usuario(
						rs.getString("Email"),
						rs.getString("Nome_Usuario"),
						rs.getString("CPF_CNPJ"),
						rs.getString("Telefone"),
						rs.getString("Senha"),
						rs.getString("github"),
						rs.getBoolean("isContratado"),
						rs.getBoolean("isContratante"),
						rs.getBoolean("isAdmin")
				);
				// Substituir rs.getString("CaminhoFoto") por rs.getString("imagem64")
				u.setImagem64(rs.getString("imagem64"));
				u.setIdUsuario(rs.getInt("idUsuario"));
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

	        String sql = "SELECT * FROM Usuarios WHERE Nome_Usuario = ?";
	        var stmt = conn.prepareStatement(sql);

	        stmt.setString(1, nome);

	        var rs = stmt.executeQuery();

	        if (rs.next()) {
	            Usuario u = new Usuario(
	            		rs.getString("Email"),
						rs.getString("Nome_Usuario"),
						rs.getString("CPF_CNPJ"),
						rs.getString("Telefone"),
						rs.getString("Senha"),
						rs.getString("github"),
						rs.getBoolean("isContratado"),
						rs.getBoolean("isContratante"),
						rs.getBoolean("isAdmin")
	            );
	            // Substituir rs.getString("CaminhoFoto") por rs.getString("imagem64")
	            u.setImagem64(rs.getString("imagem64"));
	            u.setIdUsuario(rs.getInt("idUsuario"));
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

	        String sql = "UPDATE Usuarios SET Email = ?, Nome_Usuario = ?, CPF_CNPJ = ?, Telefone = ?, Senha = ?, github =? WHERE idUsuario = ?";
	        var stmt = conn.prepareStatement(sql);
	        stmt.setString(1, u.getEmail());
	        stmt.setString(2, u.getUsuario());
	        stmt.setString(3, u.getCpfCnpj());
	        stmt.setString(4, u.getTelefone());
	        stmt.setString(5, u.getSenha());
	        stmt.setString(6, u.getGithub());
	        stmt.setInt(7, u.getIdUsuario());

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
	
	// Busca um usuário pelo id (usado para visualizar dados do contratado/contratante)
    public Usuario getUsuarioById(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, Usuario, Senha);

            String sql = "SELECT * FROM Usuarios WHERE idUsuario = ?";
            var stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            var rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("Email"),
                        rs.getString("Nome_Usuario"),
                        rs.getString("CPF_CNPJ"),
                        rs.getString("Telefone"),
                        rs.getString("Senha"),
                        rs.getString("github"),
                        rs.getBoolean("isContratado"),
                        rs.getBoolean("isContratante"),
                        rs.getBoolean("isAdmin")
                );
                // Substituir rs.getString("CaminhoFoto") por rs.getString("imagem64")
                u.setImagem64(rs.getString("imagem64"));
                u.setIdUsuario(rs.getInt("idUsuario"));
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