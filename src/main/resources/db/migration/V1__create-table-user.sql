CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL,  -- Aqui, assumimos que o enum 'RoleName' é um tipo string com valores curtos.
    updated_date DATETIME NOT NULL,  -- Ajustado para armazenar a data e hora.
    address VARCHAR(255)
);
