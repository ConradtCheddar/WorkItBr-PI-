CREATE Database if not exists WorkItBr_BD;

use WorkItBr_BD;

CREATE TABLE if not exists Contratado 
( 
 Github INT,  
 idLogin INT,  
 idContratado boolean PRIMARY KEY 
); 

CREATE TABLE if not exists Servico 
( 
 ID_servico INT PRIMARY KEY AUTO_INCREMENT,  
 Valor FLOAT NOT NULL,  
 Modalidade VARCHAR(50) NOT NULL,  
 Descricao VARCHAR(200) NOT NULL
);

CREATE TABLE if not exists Contratante 
( 
 id_contratante boolean PRIMARY KEY,  
 idLogin INT
); 

CREATE TABLE if not exists Login 
( 
 Email VARCHAR(25) NOT NULL DEFAULT '20',  
 Nome VARCHAR(30) NOT NULL,  
 CPF_CNPJ varchar(12) NOT NULL,  
 Telefone VARCHAR(12) NOT NULL, 
 Senha VARCHAR(50) NOT NULL, 
 idLogin INT PRIMARY KEY AUTO_INCREMENT  
); 

CREATE TABLE if not exists administrador 
( 
 id_admin boolean PRIMARY KEY,  
 idLogin INT 
); 

ALTER TABLE  Contratado ADD FOREIGN KEY(idLogin) REFERENCES Login (idLogin);
ALTER TABLE Contratante ADD FOREIGN KEY(idLogin) REFERENCES Login (idLogin);
ALTER TABLE administrador ADD FOREIGN KEY(idLogin) REFERENCES Login (idLogin);

INSERT INTO login (Nome, Email, CPF_CNPJ, Telefone, Senha, id_admin)
SELECT 'Admin', 'default', 'default', 'default', 'workitbr@321', true
WHERE NOT EXISTS (
SELECT 1 FROM login WHERE Nome = 'Admin');


select * from Login
