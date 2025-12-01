package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CriarBanco {

    private static final String[] POSSIVEIS_SENHAS = { 
        "",      // root sem senha
        " ",     // algumas instalacoes aceitam espaco
        "root", 
        "admin", 
        "1234" 
    };

    private static final String URL_ROOT = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String URL_DB = "jdbc:mysql://localhost:3306/WorkItBr_BD?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static void criarBanco() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // PASSO 1: Conectar como root
            Connection conn = tentarConectar();
            if (conn == null) {
                System.err.println("❌ NAO FOI POSSIVEL CONECTAR COMO ROOT");
                System.err.println("⚠️  Usuario pode estar configurado com 'auth_socket'.");
                System.err.println("Solucao: mudar root para autenticacao por senha:");
                System.err.println("  ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '';");
                return;
            }

            System.out.println("🔌 Conectado ao MySQL como root.");
            Statement st = conn.createStatement();
            
            ResultSet rs = null;

            // PASSO 2: Criar banco de dados
            System.out.println("▶ Criando banco WorkItBr_BD...");
            st.execute("CREATE DATABASE IF NOT EXISTS WorkItBr_BD");
            System.out.println("✅ Banco criado!");

            // PASSO 3: Criar usuário workitbr (se não existir)
            try {
                System.out.println("▶ Criando usuario workitbr...");
                st.execute("CREATE USER IF NOT EXISTS 'workitbr'@'localhost' IDENTIFIED BY '1234'");
                System.out.println("✅ Usuario criado!");
            } catch (Exception e) {
                System.out.println("ℹ️  Usuario workitbr ja existe, pulando...");
            }

            // PASSO 4: Dar permissões
            System.out.println("▶ Configurando permissoes...");
            st.execute("GRANT ALL PRIVILEGES ON WorkItBr_BD.* TO 'workitbr'@'localhost'");
            st.execute("FLUSH PRIVILEGES");
            System.out.println("✅ Permissoes configuradas!");

            conn.close();

            // PASSO 5: Conectar ao banco criado e criar tabelas
            System.out.println("▶ Conectando ao banco WorkItBr_BD...");
            Connection connDB = DriverManager.getConnection(URL_DB, "workitbr", "1234");
            Statement stDB = connDB.createStatement();

            // CRIAR TABELA USUARIOS
            System.out.println("▶ Criando tabela Usuarios...");
            stDB.execute("""
                CREATE TABLE IF NOT EXISTS Usuarios (
                    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
                    Email VARCHAR(60) NOT NULL UNIQUE,
                    Nome_Usuario VARCHAR(60) NOT NULL,
                    CPF_CNPJ VARCHAR(60) NOT NULL UNIQUE,
                    Telefone VARCHAR(60) NOT NULL,
                    Senha VARCHAR(60) NOT NULL,
                    github VARCHAR(60),
                    isContratado BOOLEAN DEFAULT FALSE,
                    isAdmin BOOLEAN DEFAULT FALSE,
                    isContratante BOOLEAN DEFAULT FALSE,
                    imagem64 LONGTEXT
                )
            """);

            // CRIAR TABELA SERVICO
            System.out.println("▶ Criando tabela Servico...");
            stDB.execute("""
                CREATE TABLE IF NOT EXISTS Servico (
                    ID_servico INT PRIMARY KEY AUTO_INCREMENT,
                    Nome_servico VARCHAR(50) NOT NULL,
                    Valor DOUBLE NOT NULL,
                    Modalidade VARCHAR(50) NOT NULL,
                    Descricao LONGTEXT NOT NULL,
                    submicoes LONGBLOB DEFAULT NULL,
                    extencao VARCHAR(10),
                    status ENUM('CADASTRADO', 'FINALIZADO', 'ACEITO', 'REABERTO') NOT NULL DEFAULT 'CADASTRADO',
                    id_contratante INT NOT NULL,
                    id_contratado INT DEFAULT NULL,
                    KEY idx_contratante (id_contratante),
                    KEY idx_contratado (id_contratado),
                    CONSTRAINT fk_contratante FOREIGN KEY (id_contratante) REFERENCES Usuarios(idUsuario) ON DELETE RESTRICT ON UPDATE CASCADE,
                    CONSTRAINT fk_contratado FOREIGN KEY (id_contratado) REFERENCES Usuarios(idUsuario) ON DELETE SET NULL ON UPDATE CASCADE
                )
            """);

            // INSERIR DADOS INICIAIS
            System.out.println("▶ Inserindo dados iniciais...");
            
            // Admin
            String insertAdmin = "INSERT INTO Usuarios (Nome_Usuario, Email, CPF_CNPJ, Telefone, Senha, isAdmin, imagem64) " +
                    "SELECT 'Admin', 'admin@workit.com', '00000000000', '11999999999', '4321', TRUE, '' " +
                    "WHERE NOT EXISTS (SELECT 1 FROM Usuarios WHERE Nome_Usuario = 'Admin')";
                stDB.executeUpdate(insertAdmin);
                
                // Serviço teste
                String insertServico = "INSERT INTO Servico (Nome_servico, Valor, Modalidade, Descricao, id_contratante, status) " +
                    "SELECT 'teste1', 100, 'Remoto', 'Serviço ainda não aceito', 1, 'CADASTRADO' " +
                    "FROM Usuarios " +
                    "WHERE Nome_Usuario = 'Admin' " +
                    "AND NOT EXISTS (SELECT 1 FROM Servico WHERE Nome_servico = 'teste1')";
                stDB.executeUpdate(insertServico);
                
                // Usuario 1 (Contratante)
                String insertUser1 = "INSERT INTO Usuarios (Nome_Usuario, Email, CPF_CNPJ, Telefone, Senha, github, isContratado, isAdmin, isContratante, imagem64) " +
                    "SELECT '1', 'joao.contratante@workit.com', '12345678909', '11987654321', '1', NULL, FALSE, FALSE, TRUE, '' " +
                    "WHERE NOT EXISTS (SELECT 1 FROM Usuarios WHERE Email = 'joao.contratante@workit.com')";
                stDB.executeUpdate(insertUser1);
                
                // Usuario 2 (Contratado)
                String insertUser2 = "INSERT INTO Usuarios (Nome_Usuario, Email, CPF_CNPJ, Telefone, Senha, github, isContratado, isAdmin, isContratante, imagem64) " +
                    "SELECT '2', 'maria.dev@workit.com', '11144477735', '21988887777', '2', 'github.com/mariadev', TRUE, FALSE, FALSE, '' " +
                    "WHERE NOT EXISTS (SELECT 1 FROM Usuarios WHERE Email = 'maria.dev@workit.com')";
                stDB.executeUpdate(insertUser2);

            connDB.close();
            System.out.println("✅ Banco criado e configurado com sucesso!");

        } catch (Exception e) {
            System.err.println("❌ Erro ao criar banco:");
            e.printStackTrace();
        }
    }

    private static Connection tentarConectar() {
        for (String senha : POSSIVEIS_SENHAS) {
            try {
                System.out.println("🔑 Tentando conectar com senha: [" + (senha.isBlank() ? "(vazia)" : senha) + "]");
                return DriverManager.getConnection(URL_ROOT, "root", senha);
            } catch (Exception ignored) {
                // Continua tentando outras senhas
            }
        }
        return null;
    }
}