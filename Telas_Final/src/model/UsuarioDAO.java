package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Base64;

public class UsuarioDAO {

	static String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	static String Usuario = "workitbr";
	static String Senha = "1234";

	public UsuarioDAO() {

	}
	
	public Usuario code64(Usuario u) throws FileNotFoundException, IOException {
		String imagem64 = u.getImagem64();
		String caminho = u.getCaminhoFoto();
		if (caminho == null || caminho.isEmpty()) {
			throw new FileNotFoundException("Caminho da foto não informado para usuário id=" + u.getIdUsuario());
		}
		File fotoFile = new File(caminho);
		if (!fotoFile.exists() || !fotoFile.isFile()) {
			throw new FileNotFoundException("Arquivo de foto não encontrado: " + caminho);
		}
		byte[] fotoContent = new byte[(int) fotoFile.length()];
		try(FileInputStream fis = new FileInputStream(fotoFile)) {
			fis.read(fotoContent);
			}
			imagem64 = Base64.getEncoder().encodeToString(fotoContent);
			u.setImagem64(imagem64);
			return u;
	}
	
	public Usuario decode64(Usuario u) {
		String imagem64 = u.getImagem64();
		if (imagem64 == null || imagem64.isEmpty()) {
			return u;
		}
		byte[] fotoContent = Base64.getDecoder().decode(imagem64);
		String caminhofoto = System.getProperty("user.dir") + "/src/imagens/FotoPerfil_" + u.getIdUsuario() + ".jpg";
		try (FileOutputStream fos = new FileOutputStream(caminhofoto)) {
			fos.write(fotoContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		u.setCaminhoFoto(caminhofoto);
		return u;
		}

	public boolean cadastrarU(Usuario u, String senha2) throws campoVazioException{

		if (u.isContratado() == true) {
			if (u.getEmail().isEmpty() || u.getUsuario().isEmpty() || u.getCpfCnpj().isEmpty()
					|| u.getTelefone().isEmpty() || u.getSenha().isEmpty() || senha2.isEmpty()) {
				throw new campoVazioException("Preencha todos os campos");
				return false;
			} else {
				if (u.getSenha().equals(senha2)) {
				
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, Usuario, Senha);

						String sql = "INSERT INTO Usuarios (Email, Nome_Usuario, CPF_CNPJ, Telefone, Senha, isContratado) VALUES (?, ?, ?, ?, ?, ?)";
						var stmt = conn.prepareStatement(sql);
						stmt.setString(1, u.getEmail());
						stmt.setString(2, u.getUsuario());
						stmt.setString(3, u.getCpfCnpj());
						stmt.setString(4, u.getTelefone());
						stmt.setString(5, u.getSenha());
						stmt.setBoolean(6, u.isContratado());

						stmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!", "Sucesso!",
								JOptionPane.PLAIN_MESSAGE);

						stmt.close();
						conn.close();
						return true;
					
				} else {
					throw new SenhaException("Senhas se diferem!");
					
				}
			}
		} else if (u.isContratante() == true) {
			if (u.getEmail().isEmpty() || u.getUsuario().isEmpty() || u.getCpfCnpj().isEmpty()
					|| u.getTelefone().isEmpty() || u.getSenha().isEmpty() || senha2.isEmpty()) {
				throw campoVazioException("Preencha todos os campos");
				return false;
			} else {
				if (u.getSenha().equals(senha2)) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, Usuario, Senha);

						String sql = "INSERT INTO Usuarios (Email, Nome_Usuario, CPF_CNPJ, Telefone, Senha, isContratante) VALUES (?, ?, ?, ?, ?, ?)";
						var stmt = conn.prepareStatement(sql);
						stmt.setString(1, u.getEmail());
						stmt.setString(2, u.getUsuario());
						stmt.setString(3, u.getCpfCnpj());
						stmt.setString(4, u.getTelefone());
						stmt.setString(5, u.getSenha());
						stmt.setBoolean(6, u.isContratante());

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
					throw new SenhaException("Senhas se diferem!");
					return false;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public Usuario login(String nome, char[] senha) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "SELECT * FROM Usuarios WHERE Nome_Usuario = ? AND Senha = ?";
			var stmt = conn.prepareStatement(sql);

			stmt.setString(1, nome);
			stmt.setString(2, new String(senha));

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
				u.setImagem64(rs.getString("imagem64"));
				u.setIdUsuario(rs.getInt("idUsuario"));
				
				// Decodifica a imagem Base64 para um arquivo local
				if (u.getImagem64() != null && !u.getImagem64().isEmpty()) {
					u = decode64(u);
				}
				
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
	            u.setImagem64(rs.getString("imagem64"));
	            u.setIdUsuario(rs.getInt("idUsuario"));
	            
	            // Decodifica a imagem Base64 para um arquivo local
	            if (u.getImagem64() != null && !u.getImagem64().isEmpty()) {
	                u = decode64(u);
	            }
	            
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

	        String sql = "UPDATE Usuarios SET Email = ?, Nome_Usuario = ?, CPF_CNPJ = ?, Telefone = ?, Senha = ?, github = ?, imagem64 = ? WHERE idUsuario = ?";
	        var stmt = conn.prepareStatement(sql);
	        stmt.setString(1, u.getEmail());
	        stmt.setString(2, u.getUsuario());
	        stmt.setString(3, u.getCpfCnpj());
	        stmt.setString(4, u.getTelefone());
	        stmt.setString(5, u.getSenha());
	        stmt.setString(6, u.getGithub());
	        stmt.setString(7, u.getImagem64());
	        stmt.setInt(8, u.getIdUsuario());

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
                u.setImagem64(rs.getString("imagem64"));
                u.setIdUsuario(rs.getInt("idUsuario"));
                
                // Decodifica a imagem Base64 para um arquivo local
                if (u.getImagem64() != null && !u.getImagem64().isEmpty()) {
                    u = decode64(u);
                }
                
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