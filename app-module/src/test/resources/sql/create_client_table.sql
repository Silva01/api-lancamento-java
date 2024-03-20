create table IF NOT EXISTS address
(
    id           bigint auto_increment
        primary key,
    city         varchar(255) NOT NULL,
    complement   varchar(255) null,
    neighborhood varchar(255) NOT NULL,
    number       varchar(255) NOT NULL,
    state        varchar(255) NOT NULL,
    street       varchar(255) NOT NULL,
    zip_code     varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS client (
    cpf VARCHAR(255) PRIMARY KEY,
    aggregate_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    telephone VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    address_id BIGINT NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address (id)
    );


