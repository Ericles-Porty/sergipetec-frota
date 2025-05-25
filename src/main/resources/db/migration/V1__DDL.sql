CREATE TABLE Veiculo (
    id BIGSERIAL PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    fabricante VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('CARRO', 'MOTO'))
);

CREATE TABLE Carro (
    veiculo_id INT PRIMARY KEY,
    quantidade_portas INT NOT NULL,
    tipo_combustivel VARCHAR(10) NOT NULL CHECK (tipo_combustivel IN ('GASOLINA', 'ETANOL', 'DIESEL', 'FLEX')),
    FOREIGN KEY (veiculo_id) REFERENCES Veiculo(id) ON DELETE CASCADE
);

CREATE TABLE Moto (
    veiculo_id INT PRIMARY KEY,
    cilindrada INT NOT NULL,
    FOREIGN KEY (veiculo_id) REFERENCES Veiculo(id) ON DELETE CASCADE
);
