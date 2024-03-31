-- Address of clients
INSERT INTO address (id, street, number, complement, neighborhood, state, city, zip_code) VALUES (1, 'Rua Teste', '123', 'Complemento', 'Bairro Teste', 'SP', 'São Paulo', '12345678');
INSERT INTO address (id, street, number, complement, neighborhood, state, city, zip_code) VALUES (2, 'Rua Teste', '123', 'Complemento', 'Bairro Teste', 'SP', 'São Paulo', '12345678');
INSERT INTO address (id, street, number, complement, neighborhood, state, city, zip_code) VALUES (3, 'Rua Teste', '123', 'Complemento', 'Bairro Teste', 'SP', 'São Paulo', '12345678');
INSERT INTO address (id, street, number, complement, neighborhood, state, city, zip_code) VALUES (4, 'Rua Teste', '123', 'Complemento', 'Bairro Teste', 'SP', 'São Paulo', '12345678');

-- Clients
INSERT INTO client (aggregate_id, cpf, name, telephone, active, address_id) VALUES (1, '12345678901', 'Teste', '12345678901', true, 1);
INSERT INTO client (aggregate_id, cpf, name, telephone, active, address_id) VALUES (2, '12345678903', 'Teste', '12345678901', false, 2);
INSERT INTO client (aggregate_id, cpf, name, telephone, active, address_id) VALUES (3, '12345678904', 'Teste', '12345678901', true, 3);
INSERT INTO client (aggregate_id, cpf, name, telephone, active, address_id) VALUES (3, '12345678910', 'Teste With Credit Card', '12345678901', true, 4);

-- Account of clients
INSERT INTO account (number, bank_agency_number, balance, password, active, cpf) VALUES (1234, 1, 3000, MD5('123456'), true, '12345678901');
INSERT INTO account (number, bank_agency_number, balance, password, active, cpf) VALUES (1235, 1, 3000, MD5('123456'), false, '12345678903');
INSERT INTO account (number, bank_agency_number, balance, password, active, cpf) VALUES (1236, 1, 3000, MD5('123456'), false, '12345678904');
INSERT INTO account (number, bank_agency_number, balance, password, active, cpf, credit_card_number) VALUES (1236, 1, 3000, MD5('123456'), false, '12345678910', '1234567890123456');

-- Credit card of clients
INSERT INTO credit_card (number, cvv, flag, balance, expiration_date, active) VALUES ('1234567890123456', 123, 3000, '2022-12-01', true);