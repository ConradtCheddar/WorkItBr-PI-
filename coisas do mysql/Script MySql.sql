CREATE Database if not exists WorkItBr_BD;

use WorkItBr_BD;

CREATE TABLE if not exists Servico 
( 
 ID_servico INT PRIMARY KEY AUTO_INCREMENT, 
 Nome_servico varchar(25) not null,
 Valor varchar(10) NOT NULL,  
 Modalidade VARCHAR(50) NOT NULL,  
 Descricao longtext NOT NULL,
 Aceito boolean
);

CREATE TABLE if not exists Login 
( 
 Email VARCHAR(60) NOT NULL DEFAULT '20',  
 Nome_Usuario VARCHAR(60) NOT NULL,  
 CPF_CNPJ varchar(60) NOT NULL,  
 Telefone VARCHAR(60) NOT NULL, 
 Senha VARCHAR(60) NOT NULL, 
 github VARCHAR(60),
 idLogin INT PRIMARY KEY AUTO_INCREMENT, 
 idContratado boolean,
 idAdmin boolean,
 idContratante boolean
); 

INSERT INTO login (Nome_Usuario, Email, CPF_CNPJ, Telefone, Senha, idAdmin)
SELECT 'Admin', 'default', 'default', 'default', 'workitbr@321', true
WHERE NOT EXISTS (
SELECT 1 FROM login WHERE Nome_Usuario = 'Admin');


select * from Login;
select * from Servico;





