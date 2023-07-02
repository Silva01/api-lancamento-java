CREATE TABLE account (
                         id VARCHAR(255) PRIMARY KEY,
                         name VARCHAR(255),
                         account_number BIGINT,
                         credit_balance DECIMAL(10, 2),
                         balance DECIMAL(10, 2)
);

INSERT INTO account (id, name, account_number, credit_balance, balance) VALUES ('ksjdgjkqsdgh-kjsdkhd', 'John', 4321111, 5000, 3000);