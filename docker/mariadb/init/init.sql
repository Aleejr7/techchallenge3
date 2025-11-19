
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

CREATE TABLE IF NOT EXISTS consulta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_medico BIGINT NOT NULL,
    id_paciente BIGINT NOT NULL,
    descricao VARCHAR(500),
    dia_hora_consulta DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    motivo_consulta VARCHAR(500),
    PRIMARY KEY (id),
    INDEX idx_medico (id_medico),
    INDEX idx_paciente (id_paciente),
    INDEX idx_data (dia_hora_consulta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SHOW TABLES;
DESCRIBE usuarios;
DESCRIBE consulta;
