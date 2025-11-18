package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import controller.Navegador;

public class ServicoDAO {

	static String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	static String Usuario = "workitbr";
	static String Senha = "1234";

	public ServicoDAO() {

	}
	
	

	public boolean cadastrarS(Servico s) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "INSERT INTO Servico (Nome_servico, Modalidade, Valor, Descricao, id_contratante) VALUES (?, ?, ?, ?, ?)";
			var stmt = conn.prepareStatement(sql);
			stmt.setString(1, s.getNome_Servico());
			stmt.setString(2, s.getModalidade());
			stmt.setDouble(3, s.getValor());
			stmt.setString(4, s.getDescricao());
			stmt.setInt(5, s.getContratante().getIdUsuario());
			stmt.executeUpdate();

			stmt.close();
			conn.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public ArrayList<Servico> buscarTodosServicosPorUsuario(Usuario u) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "SELECT * FROM Servico WHERE id_contratante = ?";
			var stmt = conn.prepareStatement(sql);

			stmt.setInt(1, u.getIdUsuario());

			var rs = stmt.executeQuery();

			ArrayList<Servico> listaServicos = new ArrayList<Servico>();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while (rs.next()) {
				if (u.getImagem64() != null && !u.getImagem64().isEmpty()) {
					try {
						usuarioDAO.decode64(u);
					} catch (Exception ex) {
					}
				}

				Servico s = new Servico(rs.getInt("ID_servico"), rs.getString("Nome_servico"), rs.getDouble("Valor"),
						rs.getString("Modalidade"), rs.getString("Descricao"), rs.getBytes("submicoes"),
						model.Status.valueOf(rs.getString("status")), u);
				s.setIdServico(rs.getInt("ID_servico"));
				s.setIdContratante(rs.getInt("id_contratante"));

				listaServicos.add(s);

			}

			rs.close();
			stmt.close();
			conn.close();
			return listaServicos;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List<Servico> listarServicos() {
		List<Servico> servicos = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);
			String sql = "SELECT * FROM Servico WHERE status = 'CADASTRADO'";
			var stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while (rs.next()) {
				int idContratante = rs.getInt("id_contratante");
				Usuario contratante = usuarioDAO.getUsuarioById(idContratante);
				if (contratante != null) {
					try {
						usuarioDAO.decode64(contratante);
					} catch (Exception ex) {
					}
				}
				Servico s = new Servico(rs.getInt("ID_servico"), rs.getString("Nome_servico"), rs.getDouble("Valor"),
						rs.getString("Modalidade"), rs.getString("Descricao"), rs.getBytes("submicoes"),
						model.Status.valueOf(rs.getString("status")), contratante);
				s.setIdServico(rs.getInt("ID_servico"));
				s.setIdContratante(idContratante);
				servicos.add(s);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return servicos;
	}

	public List<Servico> listarServicosAceitos(Navegador n) {
		List<Servico> servicos = new ArrayList<>();
		Usuario u = n.getCurrentUser();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);
			String sql = "SELECT * FROM Servico WHERE (status = 'ACEITO' || status = 'REABERTO') and id_contratado = ?";
			var stmt = conn.prepareStatement(sql);
			stmt.setInt(1, u.getIdUsuario());
			ResultSet rs = stmt.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while (rs.next()) {
				int idContratante = rs.getInt("id_contratante");
				Usuario contratante = usuarioDAO.getUsuarioById(idContratante);
				if (contratante != null) {
					try {
						usuarioDAO.decode64(contratante);
					} catch (Exception ex) {
					}
				}
				Servico s = new Servico(rs.getInt("ID_servico"), rs.getString("Nome_servico"), rs.getDouble("Valor"),
						rs.getString("Modalidade"), rs.getString("Descricao"), rs.getBytes("submicoes"),
						model.Status.valueOf(rs.getString("status")), contratante);
				s.setIdServico(rs.getInt("ID_servico"));
				s.setIdContratante(idContratante);
				s.setIdContratado(rs.getInt("id_contratado"));
				servicos.add(s);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return servicos;
	}

	public List<Servico> listarServicosFinalizados(Navegador n, boolean comoContratado) {

	    List<Servico> servicos = new ArrayList<>();
	    Usuario u = n.getCurrentUser();

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(url, Usuario, Senha);
	        String coluna = comoContratado ? "id_contratado" : "id_contratante";
	        String sql = "SELECT * FROM Servico WHERE status = 'FINALIZADO' AND " + coluna + " = ?";
	        var stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, u.getIdUsuario());
	        System.out.println("Executando SQL: " + sql + " com ID_usuario = " + u.getIdUsuario());
	        ResultSet rs = stmt.executeQuery();
	        UsuarioDAO usuarioDAO = new UsuarioDAO();
	        while (rs.next()) {
	            int idContratante = rs.getInt("id_contratante");
	            Usuario contratante = usuarioDAO.getUsuarioById(idContratante);
	            if (contratante != null) {
	                try {
	                    usuarioDAO.decode64(contratante);
	                } catch (Exception ignore) {}
	            }
	            Servico s = new Servico(
		                rs.getInt("ID_servico"),
		                rs.getString("Nome_servico"),
		                rs.getDouble("Valor"),
		                rs.getString("Modalidade"),
		                rs.getString("Descricao"),
		   //esse null e problema
		                null, model.Status.valueOf(rs.getString("status")),
		                contratante
		               
	            );
	            s.setIdServico(rs.getInt("ID_servico"));
	            s.setIdContratante(idContratante);
	            s.setIdContratado(rs.getInt("id_contratado"));
	            servicos.add(s);
	        }
	        rs.close();
	        stmt.close();
	        conn.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }

	    return servicos;
	}


	public Servico buscarServicoPorId(int idServico) {
		Servico servico = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);
			String sql = "SELECT * FROM Servico WHERE ID_servico = ?";
			var stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idServico);
			var rs = stmt.executeQuery();
			if (rs.next()) {
				int idContratante = rs.getInt("id_contratante");
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario contratante = usuarioDAO.getUsuarioById(idContratante);
				if (contratante != null) {
					try {
						usuarioDAO.decode64(contratante);
					} catch (Exception ex) {
					}
				}
				servico = new Servico(rs.getInt("ID_servico"), rs.getString("Nome_servico"), rs.getDouble("Valor"),
						rs.getString("Modalidade"), rs.getString("Descricao"), rs.getBytes("submicoes"),
						model.Status.valueOf(rs.getString("status")), contratante);
				servico.setIdServico(rs.getInt("ID_servico"));
				servico.setIdContratante(idContratante);
				servico.setIdContratado(rs.getInt("id_contratado"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return servico;
	}

	public void finalizarServico(Servico s) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "UPDATE Servico SET Nome_servico = ?, Modalidade = ?, Valor = ?, Descricao = ?, status = ?, id_contratado = ? WHERE ID_servico = ?";
			var stmt = conn.prepareStatement(sql);
			stmt.setString(1, s.getNome_Servico());
			stmt.setString(2, s.getModalidade());
			stmt.setDouble(3, s.getValor());
			stmt.setString(4, s.getDescricao());
			stmt.setString(5, model.Status.FINALIZADO.toString());
			stmt.setInt(6, s.getIdContratado());
			stmt.setInt(7, s.getIdServico());

			int rowsUpdated = stmt.executeUpdate();

			if (rowsUpdated > 0) {

			} else {
				
			}

			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}
	
	public void ReabrirServico(Servico s) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "UPDATE Servico SET Nome_servico = ?, Modalidade = ?, Valor = ?, Descricao = ?, status = ?, id_contratado = ? WHERE ID_servico = ?";
			var stmt = conn.prepareStatement(sql);
			stmt.setString(1, s.getNome_Servico());
			stmt.setString(2, s.getModalidade());
			stmt.setDouble(3, s.getValor());
			stmt.setString(4, s.getDescricao());
			stmt.setString(5, model.Status.REABERTO.toString());
			stmt.setInt(6, s.getIdContratado());
			stmt.setInt(7, s.getIdServico());

			int rowsUpdated = stmt.executeUpdate();

			if (rowsUpdated > 0) {

			} else {
				
			}

			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	public void aceitarServico(Servico s) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "UPDATE Servico SET Nome_servico = ?, Modalidade = ?, Valor = ?, Descricao = ?, status = ?, id_contratado = ? WHERE ID_servico = ?";
			var stmt = conn.prepareStatement(sql);
			stmt.setString(1, s.getNome_Servico());
			stmt.setString(2, s.getModalidade());
			stmt.setDouble(3, s.getValor());
			stmt.setString(4, s.getDescricao());
			stmt.setString(5, model.Status.ACEITO.toString());
			stmt.setInt(6, s.getIdContratado());
			stmt.setInt(7, s.getIdServico());

			int rowsUpdated = stmt.executeUpdate();

			if (rowsUpdated > 0) {

			} else {

			}

			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	public Servico buscarServicoPorNome(String nome) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "SELECT * FROM Servico WHERE Nome_servico = ?";
			var stmt = conn.prepareStatement(sql);

			stmt.setString(1, nome);

			var rs = stmt.executeQuery();

			if (rs.next()) {
				Servico u = new Servico(rs.getInt("ID_servico"), rs.getString("Nome_servico"), rs.getDouble("Valor"),
						rs.getString("Modalidade"), rs.getString("Descricao"), rs.getBytes("submicoes"),
						model.Status.valueOf(rs.getString("status")), null);
				u.setIdServico(rs.getInt("ID_servico"));
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

	public boolean atualizarServicoPorId(int idServico, Servico s) {
		Servico atual = buscarServicoPorId(idServico);
		if (atual == null)
			return false;
		String nome = (s.getNome_Servico() != null && !s.getNome_Servico().isEmpty()) ? s.getNome_Servico()
				: atual.getNome_Servico();
		Double valor = (s.getValor() != null) ? s.getValor() : atual.getValor();
		String modalidade = (s.getModalidade() != null && !s.getModalidade().isEmpty()) ? s.getModalidade()
				: atual.getModalidade();
		String descricao = (s.getDescricao() != null && !s.getDescricao().isEmpty()) ? s.getDescricao()
				: atual.getDescricao();
		model.Status status = (s.getStatus() != null) ? s.getStatus() : atual.getStatus();
		if (descricao == null)
			descricao = "";
		String sql = "UPDATE Servico SET Nome_servico = ?, Valor = ?, Modalidade = ?, Descricao = ?, status = ? WHERE ID_servico = ?";
		Connection conn = null;
		java.sql.PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, Usuario, Senha);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, nome);
			stmt.setDouble(2, valor);
			stmt.setString(3, modalidade);
			stmt.setString(4, descricao);
			stmt.setString(5, status.toString());
			stmt.setInt(6, idServico);
			int rowsUpdated = stmt.executeUpdate();
			conn.commit();
			return rowsUpdated > 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} finally {

		}
	}

	public boolean deletarServico(int id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "Delete from Servico where ID_servico = ?";
			var stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();

			stmt.close();
			conn.close();
			return rowsAffected > 0;
		} catch (Exception ex) {
			ex.printStackTrace();

			return false;
		}
	}

	public void salvarArquivoServico(int idServico, byte[] arquivo) {
		Connection conn = null;
		java.sql.PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, Usuario, Senha);
			String sql = "UPDATE Servico SET submicoes = ? WHERE ID_servico = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setBytes(1, arquivo);
			stmt.setInt(2, idServico);
			stmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				/* ignore */ }
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				/* ignore */ }
		}
	}
	
	public void salvarArquivoLocal(Servico s) throws IOException {
		byte[] arquivoByte = s.getArquivo();
		String caminho = System.getProperty("user.dir") + "/src/submicoes/sub_servico_N"+s.getIdServico()+".java";
		Path caminhoArquivo = Paths.get(caminho);
		
		FileOutputStream arquivo = new FileOutputStream(caminho);
		arquivo.write(arquivoByte);
		s.setCaminho(caminhoArquivo);
	}

}