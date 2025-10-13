CREATE DATABASE IF NOT EXISTS WorkItBr_BD;
USE WorkItBr_BD;

CREATE TABLE IF NOT EXISTS Usuarios (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    Email VARCHAR(60) NOT NULL UNIQUE,
    Nome_Usuario VARCHAR(60) NOT NULL,
    CPF_CNPJ VARCHAR(60) NOT NULL UNIQUE,
    Telefone VARCHAR(60) NOT NULL,
    Senha VARCHAR(60) NOT NULL,
    github VARCHAR(60),
    isContratado BOOLEAN,
    isAdmin BOOLEAN,
    isContratante BOOLEAN,
    caminhofoto VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Servico (
    ID_servico INT PRIMARY KEY AUTO_INCREMENT,
    Nome_servico VARCHAR(25) NOT NULL,
    Valor INT NOT NULL,
    Modalidade VARCHAR(50) NOT NULL,
    Descricao LONGTEXT NOT NULL,
    Aceito BOOLEAN DEFAULT FALSE,
    id_contratante INT NOT NULL,
    id_contratado INT DEFAULT NULL,
    KEY idx_contratante (id_contratante),
    KEY idx_contratado (id_contratado),
    CONSTRAINT fk_contratante FOREIGN KEY (id_contratante) REFERENCES Usuarios(idUsuario) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_contratado FOREIGN KEY (id_contratado) REFERENCES Usuarios(idUsuario) ON DELETE SET NULL ON UPDATE CASCADE
);

INSERT INTO Login (Nome_Usuario, Email, CPF_CNPJ, Telefone, Senha, isAdmin, isContratante, isContratado)
SELECT 'Admin', 'admin@workit.com', '00000000000', '11999999999', 'workitbr@321', TRUE, TRUE, TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM Usuarios WHERE Nome_Usuario = 'Admin'
);

INSERT INTO Servico (Nome_servico, Valor, Modalidade, Descricao, Aceito, id_contratante)
SELECT 'teste1', 100, 'Remoto', 'Serviço ainda não aceito', FALSE, idLogin
FROM Usuarios
WHERE Nome_Usuario = 'Admin'
AND NOT EXISTS (
    SELECT 1 FROM Servico WHERE Nome_servico = 'teste1'
);

INSERT INTO Servico (Nome_servico, Valor, Modalidade, Descricao, Aceito, id_contratante)
SELECT 'teste2', 200, 'Presencial', 'Serviço aceito por um contratado', TRUE, idLogin
FROM Usuarios
WHERE Nome_Usuario = 'Admin'
AND NOT EXISTS (
    SELECT 1 FROM Servico WHERE Nome_servico = 'teste2'
);

SELECT * FROM Usuarios;
SELECT * FROM Servico;