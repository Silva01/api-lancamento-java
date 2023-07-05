# API de Lançamento de Transações Financeiras

Este projeto é uma API desenvolvida utilizando Java 8 e Spring Boot 2.5.5. A API é responsável por lidar com lançamentos de transações financeiras.

## Começando

Clone o repositório e execute `mvn install` para instalar as dependências do projeto.

## Rotas da API

- **Conta**
    - `POST /account`: cria uma nova conta. O corpo da solicitação deve ser um objeto JSON `CreateAccountDTO`.
    - `GET /account/balance/{accountNumber}`: retorna o saldo da conta com o número fornecido.

- **Transação**
    - `POST /transaction`: faz o lançamento de uma transação. O corpo da solicitação deve ser um objeto JSON `TransactionRequestDTO`.

## Modelos de Dados

- `CreateAccountDTO`: um objeto que contém o nome do titular da conta.
- `CreatedAccountNumberDTO`: um objeto que contém o número da conta recém-criada.
- `BalanceDTO`: um objeto que contém o saldo da conta.
- `TransactionRequestDTO`: um objeto que contém o número da conta e as transações a serem lançadas.
- `TransactionDTO`: um objeto que contém a data, descrição, ID, tipo e valor de uma transação.

## Documentação API

Para uma descrição mais detalhada de cada rota, incluindo parâmetros de consulta, corpo da solicitação e resposta, visite a documentação Swagger UI na URL `http://localhost:8080/swagger-ui/` após iniciar o servidor.

## Executando testes

Execute `mvn test` para rodar os testes unitários.

## Informação de Contato

Para qualquer dúvida ou problema, por favor, entre em contato conosco em `seu-email@email.com`.
