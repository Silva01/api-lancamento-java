INSERT INTO address (id, street, number, complement, neighborhood, state, city, zip_code) VALUES (1, 'Rua Teste', '123', 'Complemento', 'Bairro Teste', 'SP', 'São Paulo', '12345678');
INSERT INTO address (id, street, number, complement, neighborhood, state, city, zip_code) VALUES (2, 'Rua Teste', '123', 'Complemento', 'Bairro Teste', 'SP', 'São Paulo', '12345678');
INSERT INTO client (aggregate_id, cpf, name, telephone, active, address_id) VALUES (1, '12345678901', 'Teste', '12345678901', true, 1);
INSERT INTO client (aggregate_id, cpf, name, telephone, active, address_id) VALUES (2, '12345678903', 'Teste', '12345678901', false, 2);
INSERT INTO account (number, bank_agency_number, balance, password, active, cpf) VALUES (1234, 1, 3000, MD5('123456'), true, '12345678901');
INSERT INTO account (number, bank_agency_number, balance, password, active, cpf) VALUES (1235, 1, 3000, MD5('123456'), false, '12345678903');