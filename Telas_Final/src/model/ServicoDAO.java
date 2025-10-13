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
	static String Senha = "aluno";
	public ServicoDAO() {

	}

	public boolean cadastrarS(Servico s) {
		if (s.getNome_Servico().isEmpty() || s.getModalidade().isEmpty() || s.getValor().isEmpty() || s.getDescricao().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, Usuario, Senha);

				String sql = "INSERT INTO Servico (Nome_servico, Modalidade, Valor, Descricao, id_contratante) VALUES (?, ?, ?, ?, ?)";
				var stmt = conn.prepareStatement(sql);
				stmt.setString(1, s.getNome_Servico());
				stmt.setString(2, s.getValor());
				stmt.setString(3, s.getModalidade());
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
	        String sql = "SELECT * FROM Servico";
	        var stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Servico s = new Servico(
	                rs.getString("Nome_servico"),
	                rs.getString("Valor"),
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

//	public Usuario login(String nome, String senha) {
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection conn = DriverManager.getConnection(url, Usuario, Senha);
//
//			String sql = "SELECT * FROM Login WHERE Nome = ? AND Senha = ?";
//			var stmt = conn.prepareStatement(sql);
//
//			stmt.setString(1, nome);
//			stmt.setString(2, senha);
//
//			var rs = stmt.executeQuery();
//
//			if (rs.next()) {
//				Usuario u = new Usuario(rs.getString("Email"), rs.getString("Nome"), rs.getString("CPF_CNPJ"),
//						rs.getString("Telefone"), rs.getString("Senha"), rs.getBoolean("idContratado"),
//						rs.getBoolean("idContratante"));
//				rs.close();
//				stmt.close();
//				conn.close();
//				return u;
//			}
//
//			rs.close();
//			stmt.close();
//			conn.close();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return null;
//	}

}