CREATE Database WorkItBr_BD;

use WorkItBr_BD;

CREATE TABLE Contratado 
( 
 Github INT,  
 idLogin INT,  
 idContratado INT PRIMARY KEY AUTO_INCREMENT 
); 

CREATE TABLE Servico 
( 
 ID_servico INT PRIMARY KEY AUTO_INCREMENT,  
 Valor FLOAT NOT NULL,  
 Modalidade VARCHAR(50) NOT NULL,  
 Descricao VARCHAR(200) NOT NULL
);

CREATE TABLE Contratante 
( 
 id_contratante INT PRIMARY KEY,  
 idLogin INT
); 

CREATE TABLE Login 
( 
 Email VARCHAR(25) NOT NULL DEFAULT '20',  
 Nome VARCHAR(30) NOT NULL,  
 CPF_CNPJ INT NOT NULL,  
 Telefone VARCHAR(12) NOT NULL, 
 Senha VARCHAR(50) NOT NULL, 
 idLogin INT PRIMARY KEY AUTO_INCREMENT  
); 

CREATE TABLE administrador 
( 
 id_admin INT PRIMARY KEY,  
 idLogin INT 
); 

ALTER TABLE Contratado ADD FOREIGN KEY(idLogin) REFERENCES Login (idLogin);
ALTER TABLE Contratante ADD FOREIGN KEY(idLogin) REFERENCES Login (idLogin);
ALTER TABLE administrador ADD FOREIGN KEY(idLogin) REFERENCES Login (idLogin);
