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
	public ServicoDAO() {

	}

	public boolean cadastrarS(Servico s) {
		
		
		if (s.getNome_Servico().isEmpty() || s.getModalidade().isEmpty() || Double.toString(s.getValor()).isEmpty() || s.getDescricao().isEmpty()) {
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

	public List<Servico> listarServicos() {
	    List<Servico> servicos = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(url, Usuario, Senha);
	        String sql = "SELECT * FROM Servico WHERE Aceito = false";
	        var stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Servico s = new Servico(
	                rs.getString("Nome_servico"),
	                rs.getDouble("Valor"),
	                rs.getString("Modalidade"),
	                rs.getString("Descricao"),
	                rs.getBoolean("Aceito"),
	                null
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
	
	public List<Servico> listarServicosAceitos() {
	    List<Servico> servicos = new ArrayList<>();
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(url, Usuario, Senha);
	        String sql = "SELECT * FROM Servico WHERE Aceito = true";
	        var stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Servico s = new Servico(
	                rs.getString("Nome_servico"),
	                rs.getDouble("Valor"),
	                rs.getString("Modalidade"),
	                rs.getString("Descricao"),
	                rs.getBoolean("Aceito"),
	                null
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

	public void atualizarServico(Servico u) {
	    try {
	    	System.out.println(u.getIdServico());
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection(url, Usuario, Senha);

	        String sql = "UPDATE Servico SET Nome_servico = ?, Modalidade = ?, Valor = ?, Descricao = ?, Aceito = ? WHERE ID_servico = ?";
	        var stmt = conn.prepareStatement(sql);
	        stmt.setString(1, u.getNome_Servico());
	        stmt.setString(2, u.getModalidade());
	        stmt.setDouble(3, u.getValor());
	        stmt.setString(4, u.getDescricao());
	        stmt.setBoolean(5, true);
	        stmt.setInt(6, u.getIdServico());

	        int rowsUpdated = stmt.executeUpdate();

	        if (rowsUpdated > 0) {
	            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Sucesso", JOptionPane.PLAIN_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(null, "Servico não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
	        }

	        stmt.close();
	        conn.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Erro ao atualizar dados.", "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	}

}