
CREATE DATABASE IF NOT EXISTS techchallenge;
USE techchallenge;


CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    documento VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_email (email),
    INDEX idx_documento (documento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SHOW TABLES;
DESCRIBE usuarios;

-- Criar banco de dados para o Konga
CREATE DATABASE IF NOT EXISTS konga CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Conceder permissões ao usuário para acessar o banco konga
GRANT ALL PRIVILEGES ON konga.* TO 'admin'@'%';
FLUSH PRIVILEGES;
