
CREATE Database if not exists WorkItBr_BD;

use WorkItBr_BD;

CREATE TABLE if not exists Servico 
( 
 ID_servico INT PRIMARY KEY AUTO_INCREMENT, 
 Nome_servico varchar(25) not null,
 Valor int NOT NULL,  
 Modalidade VARCHAR(50) NOT NULL,  
 Descricao longtext NOT NULL,
 Aceito boolean,
 `id_contratado` int DEFAULT NULL,
  `id_contratante` int NOT NULL,
    PRIMARY KEY (`ID_servico`),
  KEY `fk_idContratante_idx` (`id_contratante`),
  KEY `fk_idContratado_idx` (`id_contratado`),
  CONSTRAINT `fk_idContratado_login` FOREIGN KEY (`id_contratado`) REFERENCES `login` (`idLogin`),
  CONSTRAINT `fk_idContratante_login` FOREIGN KEY (`id_contratante`) REFERENCES `login` (`idLogin`)

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
 idContratante boolean,
 caminhofoto VARCHAR(255)
); 

INSERT INTO login (Nome_Usuario, Email, CPF_CNPJ, Telefone, Senha, idAdmin)
SELECT 'Admin', 'default', 'default', 'default', 'workitbr@321', true
WHERE NOT EXISTS (
SELECT 1 FROM login WHERE Nome_Usuario = 'Admin');

INSERT INTO servico (Nome_servico, Valor, Modalidade, Descricao, Aceito)
SELECT 'teste1', '100', 'default', 'default', false
WHERE NOT EXISTS (
SELECT 1 FROM servico  WHERE Nome_servico = 'teste1');

INSERT INTO servico (Nome_servico, Valor, Modalidade, Descricao, Aceito)
SELECT 'teste2', '200', 'default', 'default', false
WHERE NOT EXISTS (
SELECT 1 FROM servico  WHERE Nome_servico = 'teste2');

select * from Login;

select * from servico;
