
CREATE Database if not exists WorkItBr_BD;

use WorkItBr_BD;

CREATE TABLE if not exists Servico 
( 
 ID_servico INT PRIMARY KEY AUTO_INCREMENT,  
 Valor FLOAT NOT NULL,  
 Modalidade VARCHAR(50) NOT NULL,  
 Descricao VARCHAR(200) NOT NULL
);

CREATE TABLE if not exists Login 
( 
 Email VARCHAR(60) NOT NULL DEFAULT '20',  
 Nome VARCHAR(60) NOT NULL,  
 CPF_CNPJ varchar(60) NOT NULL,  
 Telefone VARCHAR(60) NOT NULL, 
 Senha VARCHAR(60) NOT NULL, 
 github VARCHAR(60),
 idLogin INT PRIMARY KEY AUTO_INCREMENT, 
 idContratado boolean,
 idAdmin boolean,
 idContratante boolean
); 

INSERT INTO login (Nome, Email, CPF_CNPJ, Telefone, Senha, idAdmin)
SELECT 'Admin', 'default', 'default', 'default', 'workitbr@321', true
WHERE NOT EXISTS (
SELECT 1 FROM login WHERE Nome = 'Admin');
ALTER TABLE Login ADD COLUMN caminhofoto VARCHAR(255);

select * from Login
