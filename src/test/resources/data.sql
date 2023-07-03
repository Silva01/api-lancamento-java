CREATE TABLE account (
                         id VARCHAR(255) PRIMARY KEY,
                         name VARCHAR(255),
                         account_number BIGINT,
                         credit_balance DECIMAL(10, 2),
                         balance DECIMAL(10, 2)
);

INSERT INTO account (account_number, id, name, credit_balance, balance) VALUES (4321111, 'ksjdgjkqsdgh-kjsdkhd', 'John', 5000, 3000);