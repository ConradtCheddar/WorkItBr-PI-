package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class InicializadorDoBanco {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "WorkItBr_BD";
    private static final String ROOT_USER = "root";
    private static final String ROOT_PASSWORD = "admin"; // Altere para sua senha root
    
    public static void initializeDatabase() {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // Conecta ao MySQL como root para criar o banco
            conn = DriverManager.getConnection(DB_URL, ROOT_USER, ROOT_PASSWORD);
            stmt = conn.createStatement();
            
            // Cria o banco de dados
            System.out.println("Criando banco de dados...");
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            stmt.executeUpdate("USE " + DB_NAME);
            
            // Cria o usuário
            System.out.println("Criando usuário...");
            stmt.executeUpdate("CREATE USER IF NOT EXISTS 'workitbr'@'localhost' IDENTIFIED BY '1234'");
            stmt.executeUpdate("GRANT ALL PRIVILEGES ON " + DB_NAME + ".* TO 'workitbr'@'localhost'");
            stmt.executeUpdate("FLUSH PRIVILEGES");
            
            // Cria a tabela Usuarios
            System.out.println("Criando tabela Usuarios...");
            String createUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                "idUsuario INT PRIMARY KEY AUTO_INCREMENT, " +
                "Email VARCHAR(60) NOT NULL UNIQUE, " +
                "Nome_Usuario VARCHAR(60) NOT NULL, " +
                "CPF_CNPJ VARCHAR(60) NOT NULL UNIQUE, " +
                "Telefone VARCHAR(60) NOT NULL, " +
                "Senha VARCHAR(60) NOT NULL, " +
                "github VARCHAR(60), " +
                "isContratado BOOLEAN DEFAULT FALSE, " +
                "isAdmin BOOLEAN DEFAULT FALSE, " +
                "isContratante BOOLEAN DEFAULT FALSE, " +
                "imagem64 LONGTEXT" +
                ")";
            stmt.executeUpdate(createUsuarios);
            
            // Cria a tabela Servico
            System.out.println("Criando tabela Servico...");
            String createServico = "CREATE TABLE IF NOT EXISTS Servico (" +
                "ID_servico INT PRIMARY KEY AUTO_INCREMENT, " +
                "Nome_servico VARCHAR(50) NOT NULL, " +
                "Valor DOUBLE NOT NULL, " +
                "Modalidade VARCHAR(50) NOT NULL, " +
                "Descricao LONGTEXT NOT NULL, " +
                "submicoes LONGBLOB DEFAULT NULL, " +
                "extencao VARCHAR(10), " +
                "status ENUM('CADASTRADO', 'FINALIZADO', 'ACEITO', 'REABERTO') NOT NULL DEFAULT 'CADASTRADO', " +
                "id_contratante INT NOT NULL, " +
                "id_contratado INT DEFAULT NULL, " +
                "KEY idx_contratante (id_contratante), " +
                "KEY idx_contratado (id_contratado), " +
                "CONSTRAINT fk_contratante FOREIGN KEY (id_contratante) REFERENCES Usuarios(idUsuario) ON DELETE RESTRICT ON UPDATE CASCADE, " +
                "CONSTRAINT fk_contratado FOREIGN KEY (id_contratado) REFERENCES Usuarios(idUsuario) ON DELETE SET NULL ON UPDATE CASCADE" +
                ")";
            stmt.executeUpdate(createServico);
            
            // Insere dados iniciais
            System.out.println("Inserindo dados iniciais...");
            
            // Admin
            String insertAdmin = "INSERT INTO Usuarios (Nome_Usuario, Email, CPF_CNPJ, Telefone, Senha, isAdmin, imagem64) " +
                "SELECT 'Admin', 'admin@workit.com', '00000000000', '11999999999', '4321', TRUE, '' " +
                "WHERE NOT EXISTS (SELECT 1 FROM Usuarios WHERE Nome_Usuario = 'Admin')";
            stmt.executeUpdate(insertAdmin);
            
            // Serviço teste
            String insertServico = "INSERT INTO Servico (Nome_servico, Valor, Modalidade, Descricao, id_contratante, status) " +
                "SELECT 'teste1', 100, 'Remoto', 'Serviço ainda não aceito', 1, 'CADASTRADO' " +
                "FROM Usuarios " +
                "WHERE Nome_Usuario = 'Admin' " +
                "AND NOT EXISTS (SELECT 1 FROM Servico WHERE Nome_servico = 'teste1')";
            stmt.executeUpdate(insertServico);
            
            // Usuario 1 (Contratante)
            String insertUser1 = "INSERT INTO Usuarios (Nome_Usuario, Email, CPF_CNPJ, Telefone, Senha, github, isContratado, isAdmin, isContratante, imagem64) " +
                "SELECT '1', 'joao.contratante@workit.com', '12345678909', '11987654321', '1', NULL, FALSE, FALSE, TRUE, '' " +
                "WHERE NOT EXISTS (SELECT 1 FROM Usuarios WHERE Email = 'joao.contratante@workit.com')";
            stmt.executeUpdate(insertUser1);
            
            // Usuario 2 (Contratado)
            String insertUser2 = "INSERT INTO Usuarios (Nome_Usuario, Email, CPF_CNPJ, Telefone, Senha, github, isContratado, isAdmin, isContratante, imagem64) " +
                "SELECT '2', 'maria.dev@workit.com', '11144477735', '21988887777', '2', 'github.com/mariadev', TRUE, FALSE, FALSE, '' " +
                "WHERE NOT EXISTS (SELECT 1 FROM Usuarios WHERE Email = 'maria.dev@workit.com')";
            stmt.executeUpdate(insertUser2);
            
            System.out.println("Banco de dados inicializado com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
