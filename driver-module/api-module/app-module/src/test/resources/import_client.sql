INSERT INTO finance.address (id, street, number, complement, neighborhood, state, city, zip_code) VALUES (1, 'Rua Teste', '123', 'Complemento', 'Bairro Teste', 'SP', 'São Paulo', '12345678');
INSERT INTO finance.client (aggregate_id, cpf, name, telephone, active, address_id) VALUES (1, '12345678901', 'Teste', '12345678901', true, 1);
