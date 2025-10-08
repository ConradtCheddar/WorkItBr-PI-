package model;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Navegador;
import view.Primario;

public class ServicoDAO {

	static String url = "jdbc:mysql://localhost:3306/WorkItBr_BD";
	static String Usuario = "root";
	static String Senha = "admin";
	Navegador n = new Navegador(null);
	public ServicoDAO() {

	}

	public void cadastrarS(Servico s) {

			if (s.getNome_Servico().isEmpty() || s.getModalidade().isEmpty() || s.getValor().isEmpty() || s.getDescricao().isEmpty()) {

				JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);

			} else {

					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conn = DriverManager.getConnection(url, Usuario, Senha);

						String sql = "INSERT INTO Servico (Nome_servico, Valor, Modalidade, Descricao) VALUES (?, ?, ?, ?)";
						var stmt = conn.prepareStatement(sql);
						
						stmt.setString(1, s.getNome_Servico()); // Nome do serviço
						stmt.setString(2, s.getValor()); // Valor
						stmt.setString(3, s.getModalidade()); // Modalidade
						stmt.setString(4, s.getDescricao()); // Descrição
						
						stmt.executeUpdate();
						JOptionPane.showMessageDialog(null, "Serviço cadastrado com sucesso!", "Sucesso!",
								JOptionPane.PLAIN_MESSAGE);
						n.navegarPara("LOGIN");

						stmt.close();
						conn.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
			}

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
