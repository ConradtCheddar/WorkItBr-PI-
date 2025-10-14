package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ServicoDAO {

	static String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	static String Usuario = "root";
	static String Senha = "admin";
	//static String Senha = "aluno";
	public ServicoDAO() {

	}

	public boolean cadastrarS(Servico s) {
		if (s.getNome_Servico().isEmpty() || s.getModalidade().isEmpty() || s.getValor()==0 || s.getDescricao().isEmpty()) {
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
				JOptionPane.showMessageDialog(null, "Serviço cadastrado com sucesso!", "Sucesso!", JOptionPane.PLAIN_MESSAGE);
				stmt.close();
				conn.close();
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}
	}
	
	public ArrayList<Servico> buscarTodosServicosPorUsuario(Usuario u){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, Usuario, Senha);

			String sql = "SELECT * FROM Servico WHERE id_contratante = ?";
			var stmt = conn.prepareStatement(sql);

			stmt.setInt(1, u.getIdUsuario());
			

			var rs = stmt.executeQuery();

			ArrayList<Servico> listaServicos = new ArrayList<Servico>();
			while (rs.next()) {
				Servico s = new Servico(
					rs.getString("Nome_servico"),
					rs.getDouble("Valor"),
					rs.getString("Modalidade"),
					rs.getString("Descricao"),
					rs.getBoolean("Aceito"),
					u
				);
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
	        String sql = "SELECT * FROM Servico";
	        var stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Servico s = new Servico(
	                rs.getString("Nome_servico"),
	                rs.getDouble("Valor"),
	                rs.getString("Modalidade"),
	                rs.getString("Descricao"),
	                rs.getBoolean("Aceito"),
	                null // contratante não carregado aqui
	            );
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

	// dentro de model.ServicoDAO
	public boolean atualizarServicoPorId(int idServico, Servico s) {
	    String sql = "UPDATE Servicos SET Nome_servico = ?, Valor = ?, Modalidade = ?, Descricao = ?, Aceito = ? WHERE id_Servico = ?";
	    Connection conn = null;
	    java.sql.PreparedStatement stmt = null;
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(url, Usuario, Senha);
	        conn.setAutoCommit(false); // usar transação

	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, s.getNome_Servico());
	        if (s.getValor() != null) {
	            stmt.setDouble(2, s.getValor());
	        } else {
	            stmt.setNull(2, java.sql.Types.DOUBLE);
	        }
	        stmt.setString(3, s.getModalidade());
	        stmt.setString(4, s.getDescricao());
	        // supondo que Aceito esteja representado como 1/0 ou true/false no DB
	        if (s.getAceito() != null) {
	            stmt.setBoolean(5, s.getAceito());
	        } else {
	            stmt.setNull(5, java.sql.Types.BOOLEAN);
	        }
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


}