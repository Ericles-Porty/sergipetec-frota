-- Veículos
INSERT INTO veiculo (id, modelo, fabricante, ano, preco, tipo) VALUES (1, 'Civic', 'Honda', 2022, 95000.00, 'carro');
INSERT INTO carro (veiculo_id, quantidade_portas, tipo_combustivel) VALUES (1, 4, 'flex');

INSERT INTO veiculo (id, modelo, fabricante, ano, preco, tipo) VALUES (2, 'Corolla', 'Toyota', 2021, 90000.00, 'carro');
INSERT INTO carro (veiculo_id, quantidade_portas, tipo_combustivel) VALUES (2, 4, 'gasolina');

INSERT INTO veiculo (id, modelo, fabricante, ano, preco, tipo) VALUES (3, 'Ninja ZX-6R', 'Kawasaki', 2023, 45000.00, 'moto');
INSERT INTO moto (veiculo_id, cilindrada) VALUES (3, 636);

INSERT INTO veiculo (id, modelo, fabricante, ano, preco, tipo) VALUES (4, 'CB 500F', 'Honda', 2020, 28000.00, 'moto');
INSERT INTO moto (veiculo_id, cilindrada) VALUES (4, 471);

INSERT INTO veiculo (id, modelo, fabricante, ano, preco, tipo) VALUES (5, 'Fusca', 'Volkswagen', 1970, 30000.00, 'carro');
INSERT INTO carro (veiculo_id, quantidade_portas, tipo_combustivel) VALUES (5, 2, 'gasolina');

INSERT INTO veiculo (id, modelo, fabricante, ano, preco, tipo) VALUES (6, 'MT-03', 'Yamaha', 2021, 24000.00, 'moto');
INSERT INTO moto (veiculo_id, cilindrada) VALUES (6, 321);

INSERT INTO veiculo (id, modelo, fabricante, ano, preco, tipo) VALUES (7, 'MT-07', 'Yamaha', 2024, 50000.00, 'moto');
INSERT INTO moto (veiculo_id, cilindrada) VALUES (7, 689);

INSERT INTO veiculo (id, modelo, fabricante, ano, preco, tipo) VALUES (8, 'F-250', 'Ford', 2023, 200000.00, 'carro');
INSERT INTO carro (veiculo_id, quantidade_portas, tipo_combustivel) VALUES (8, 4, 'diesel');

INSERT INTO veiculo (id, modelo, fabricante, ano, preco, tipo) VALUES (9, 'Onix', 'Chevrolet', 2022, 70000.00, 'carro');
INSERT INTO carro (veiculo_id, quantidade_portas, tipo_combustivel) VALUES (9, 4, 'etanol');
