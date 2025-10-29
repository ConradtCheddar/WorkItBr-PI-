package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ServicoDAO {

	static String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	static String Usuario = "workitbr";
	static String Senha = "1234";
	public ServicoDAO() {

	}

	public boolean cadastrarS(Servico s) {

		if (s.getNome_Servico().isEmpty() || s.getModalidade().isEmpty() || Double.toString(s.getValor()).isEmpty()
				|| s.getDescricao().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
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
				JOptionPane.showMessageDialog(null, "Serviço cadastrado com sucesso!", "Sucesso!",
						JOptionPane.PLAIN_MESSAGE);
				stmt.close();
				conn.close();
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
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
				// Carregar dados completos do contratante (já temos o objeto u, mas vamos garantir que tem foto)
				if (u.getImagem64() != null && !u.getImagem64().isEmpty()) {
					try {
						usuarioDAO.decode64(u);
					} catch (Exception ex) {
						// Se falhar ao decodificar a foto, continua sem ela
					}
				}
				Servico s = new Servico(rs.getInt("ID_servico"), rs.getString("Nome_servico"), rs.getDouble("Valor"),
						rs.getString("Modalidade"), rs.getString("Descricao"), rs.getBoolean("Aceito"), u);
				// preencher ids relacionados para uso posterior
				s.setIdServico(rs.getInt("ID_servico"));
				s.setIdContratante(rs.getInt("id_contratante"));
				// pode ser 0 ou NULL no banco -> checar
				try { s.setIdContratado(rs.getInt("id_contratado")); } catch (Exception ex) { /* ignore */ }
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
			String sql = "SELECT * FROM Servico WHERE Aceito = false";
			var stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while (rs.next()) {
				// Carregar dados completos do contratante incluindo foto
				int idContratante = rs.getInt("id_contratante");
				Usuario contratante = usuarioDAO.getUsuarioById(idContratante);
				if (contratante != null) {
					try {
						usuarioDAO.decode64(contratante);
					} catch (Exception ex) {
						// Se falhar ao decodificar a foto, continua sem ela
					}
				}
				Servico s = new Servico(rs.getString("Nome_servico"), rs.getDouble("Valor"), rs.getString("Modalidade"),
						rs.getString("Descricao"), rs.getBoolean("Aceito"), contratante);
				// preencher id do servico e contratante se existirem
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

	public List<Servico> listarServicosAceitos() {
		List<Servico> servicos = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);
			String sql = "SELECT * FROM Servico WHERE Aceito = true";
			var stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			while (rs.next()) {
				// Carregar dados completos do contratante incluindo foto
				int idContratante = rs.getInt("id_contratante");
				Usuario contratante = usuarioDAO.getUsuarioById(idContratante);
				if (contratante != null) {
					try {
						usuarioDAO.decode64(contratante);
					} catch (Exception ex) {
						// Se falhar ao decodificar a foto, continua sem ela
					}
				}
				Servico s = new Servico(rs.getString("Nome_servico"), rs.getDouble("Valor"), rs.getString("Modalidade"),
						rs.getString("Descricao"), rs.getBoolean("Aceito"), contratante);
				// preencher ids para permitir visualizacao do contratado
				s.setIdServico(rs.getInt("ID_servico"));
				s.setIdContratante(idContratante);
				// id_contratado pode ser nulo -> rs.getInt retorna 0 se nulo
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
				// Carregar dados completos do contratante incluindo foto
				int idContratante = rs.getInt("id_contratante");
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				Usuario contratante = usuarioDAO.getUsuarioById(idContratante);
				if (contratante != null) {
					try {
						usuarioDAO.decode64(contratante);
					} catch (Exception ex) {
						// Se falhar ao decodificar a foto, continua sem ela
					}
				}
				servico = new Servico(rs.getInt("ID_servico"), rs.getString("Nome_servico"), rs.getDouble("Valor"),
						rs.getString("Modalidade"), rs.getString("Descricao"), rs.getBoolean("Aceito"), contratante
				);
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

	public void aceitarServico(Servico u) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "UPDATE Servico SET Nome_servico = ?, Modalidade = ?, Valor = ?, Descricao = ?, Aceito = ?, id_contratado = ? WHERE ID_servico = ?";
			var stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getNome_Servico());
			stmt.setString(2, u.getModalidade());
			stmt.setDouble(3, u.getValor());
			stmt.setString(4, u.getDescricao());
			stmt.setBoolean(5, true);
			stmt.setInt(6, u.getIdContratado());
			stmt.setInt(7, u.getIdServico());

			int rowsUpdated = stmt.executeUpdate();

			if (rowsUpdated > 0) {
				JOptionPane.showMessageDialog(null, "Servico aceito", "Sucesso",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Servico não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
			}

			stmt.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao aceitar servico", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Servico configID(String nome) {
		 try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "SELECT * FROM Servico WHERE Nome_servico = ?";
			var stmt = conn.prepareStatement(sql);

			stmt.setString(1, nome);

			var rs = stmt.executeQuery();

			if (rs.next()) {
				Servico u = new Servico(
							rs.getString("Nome_servico"),
							rs.getDouble("Valor"),
							rs.getString("Modalidade"),
							rs.getString("Descricao"),
							rs.getBoolean("Aceito"),
							null
						);
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
        // Buscar valores atuais do serviço
        Servico atual = buscarServicoPorId(idServico);
        if (atual == null) return false;
        // Usar o novo valor se não for nulo/vazio, senão manter o atual
        String nome = (s.getNome_Servico() != null && !s.getNome_Servico().isEmpty()) ? s.getNome_Servico() : atual.getNome_Servico();
        Double valor = (s.getValor() != null) ? s.getValor() : atual.getValor();
        String modalidade = (s.getModalidade() != null && !s.getModalidade().isEmpty()) ? s.getModalidade() : atual.getModalidade();
        String descricao = (s.getDescricao() != null && !s.getDescricao().isEmpty()) ? s.getDescricao() : atual.getDescricao();
        Boolean aceito = (s.getAceito() != null) ? s.getAceito() : atual.getAceito();
        // Nunca permitir descricao nula
        if (descricao == null) descricao = "";
        String sql = "UPDATE Servico SET Nome_servico = ?, Valor = ?, Modalidade = ?, Descricao = ?, Aceito = ? WHERE ID_servico = ?";
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
            stmt.setBoolean(5, aceito);
            stmt.setInt(6, idServico);
            int rowsUpdated = stmt.executeUpdate();
            conn.commit();
            return rowsUpdated > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (Exception e) { e.printStackTrace(); }
            return false;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) { /* ignore */ }
            try { if (conn != null) conn.close(); } catch (Exception e) { /* ignore */ }
        }
    }
    
    public void deletarServico(int id) {
     try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, Usuario, Senha);
		
		String sql = "Delete from Servico where ID_servico = ?";
		var stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		int rowsAffected = stmt.executeUpdate();
            // opcional: informar no log ou UI; não usar System.out em produção
            // rowsAffected disponibilizado para chamadas futuras se necessário
		
		stmt.close();
		conn.close();
		} catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Erro ao deletar dados.", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
        
    
    }

}