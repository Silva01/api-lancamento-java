# User Stories

###### Sumario

- [Introdução](#introdução)
- [User Story: Criar Cliente](#user-story-criar-cliente)
- [User Story: Criar Conta Corrente](#user-story-criar-conta-corrente)
- [User Story: Definir Senha](#user-story-definir-senha)
- [User Story: Desativar Cliente](#user-story-desativar-cliente)
- [User Story: Desativar Conta](#user-story-desativar-conta)
- [User Story: Reativar Cliente](#user-story-reativar-cliente)
- [User Story: Reativar Conta Corrente](#user-story-reativar-conta-corrente)
- [User Story: Listar contas correntes do cliente](#user-story-listar-contas-correntes-do-cliente)
- [User Story: Obter Informação da Conta Corrente Ativa](#user-story-obter-informação-da-conta-corrente-ativa)
- [User Story: Obter Informação do cliente ativo](#user-story-obter-informação-do-cliente-ativo)
- [User Story: Editar informações do Cliente](#user-story-editar-informações-do-cliente)
- [User Story: Editar endereço do cliente](#user-story-editar-endereço-do-cliente)
- [User Story: Alterar Agencia da Conta Corrente](#user-story-alterar-agencia-da-conta-corrente)
- [User Story: Solicitar cartão de credito](#user-story-solicitar-cartão-de-credito)
- [User Story: Desativar cartão de credito](#user-story-desativar-cartão-de-credito)
- [User Story: Enviar Transações](#user-story-enviar-transações)
- [User Story: Estornar Transação](#user-story-estornar-transação)
- [Glossário](#glossário)
- [Referências](#referências)

## Introdução

Este documento descreve várias User Stories para Projeto de Transações. As User Stories são usadas para definir requisitos de software de uma maneira centrada no usuário e simplificada. Cada User Story representa uma funcionalidade do sistema do ponto de vista do usuário.

## User Story: Criar Cliente

- **Nome**: Como um cliente em potencial, desejo me cadastrar para obter uma conta e realizar transações de compras.

- **Descrição**: Para permitir que os clientes realizem transações de compras, é essencial que eles se cadastrem no sistema e tenham uma conta corrente associada.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer um processo de cadastro que inclui a coleta de informações necessárias, esses campos são:
     
     - Nome do Cliente (Obrigatório)
     - CPF (Obrigatório)
     - Telefone (Obrigatório)
     - Agência
     - Endereço (Obrigatório)
       - Rua (Obrigatório)
       - Numero (Obrigatório)
       - Complemento (Opcional)
       - Estado (Obrigatório)
       - Cidade (Obrigatório)
       - Cep (Obrigatório)
  
  2. Após o cadastro, o sistema deve criar automaticamente uma conta corrente para o cliente.
  
  3. O cliente deve ter a opção de solicitar a emissão de um cartão de crédito no momento do cadastro.
  
  4. API irá devolver os seguintes dados:
     
     - Agência
     - Numero da Conta Corrente
     - Senha Provisória

## User Story: Criar Conta Corrente

- **Nome**: Como Cliente, desejo abrir uma nova conta corrente.

- **Descrição**: Os clientes têm a opção de criar novas contas correntes, desde que não possuam uma conta corrente ativa.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita aos clientes enviar as seguintes informações necessárias para a abertura da conta:
     
     - CPF do Cliente (Obrigatório)
     - Agência (Obrigatório)
     - Senha da Conta (Obrigatório)
  
  2. Antes de processar a solicitação, o sistema deve realizar uma validação para verificar se o cliente já possui uma conta corrente ativa e válida. Se uma conta ativa for encontrada, o processo de abertura não pode ser concluído.
  
  3. O cliente deve ter a opção de indicar se deseja receber um cartão de crédito associado à nova conta corrente.
  
  4. Após a abertura da nova conta, o cliente receberá como resposta os seguintes campos:
     
     - Agência
     - Numero da Conta Corrente
     - Senha Provisória

## User Story: Definir Senha

- **Nome**: Como Cliente, desejo trocar a senha provisória por minha própria senha.

- **Descrição**: Os clientes têm a opção de substituir a senha provisória por uma senha definitiva.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita aos clientes realizar a troca de senha, com os seguintes campos obrigatórios:
     
     - Número da Agência
     - Número da Conta
     - Senha Atual
     - Nova Senha
  
  2. Antes de processar a solicitação, o sistema deve verificar se a conta está ativa. Se a conta estiver desativada, o processo de troca de senha deve ser interrompido.
  
  3. O sistema deve validar se a senha atual fornecida pelo cliente corresponde à senha atual da conta. Se as senhas não coincidirem, o processo de troca de senha deve ser encerrado.

## User Story: Desativar Cliente

- **Nome**: Como Cliente, desejo me desativar do sistema.

- **Descrição**: Os cliente tem a opção de se desativar e não utilizar os serviços de transações.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita aos clientes desativar seu cadastro na plataforma:
     
     - CPF
  
  2. Antes de processar a solicitação, o sistema valida se o usuário existe, caso não exista o processo de desativação do usuário deve ser encerrado.
  
  3. Sistema deve validar se o cliente já está desabilitado, caso esteja o sistema deve encerrar o processo.
  
  4. Ao desabilitar o cliente, sua conta ativa deve ser desabilitada de forma automatica.

## User Story: Desativar Conta

- **Nome**: Como Cliente, desejo desativar minha conta e não receber mais transações.

- **Descrição**: Os clientes têm a opção de desativar suas contas, o que os impedirá de usar os serviços de transações.

- **Critérios de Aceitação**:
  
  1. O sistema deve disponibilizar uma API que permita aos clientes desativarem suas contas na plataforma, fornecendo os seguintes campos obrigatórios:
     
     - Agência
     - Conta
  
  2. Antes de processar a solicitação, o sistema deve validar se a conta do cliente solicitante realmente existe. Se a conta não for encontrada, o processo de desativação deve ser encerrado.
  
  3. O sistema deve verificar se a conta informada já está desabilitada. Se a conta já estiver desativada, o processo de desativação deve ser encerrado.
  
  4. Após a desativação da conta, a API deve fornecer uma confirmação da operação bem-sucedida.

## User Story: Reativar Cliente

- **Nome**: Como Cliente, desejo reativar meu cadastro para retomar o uso dos serviços de transações.

- **Descrição**: Os clientes têm a opção de reativar seus cadastros para permitir o uso da plataforma novamente.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita aos clientes reativarem seus cadastros na plataforma, fornecendo o seguinte campo obrigatório:
     
     - CPF
  
  2. Antes de processar a solicitação, o sistema deve validar se o cliente com o CPF fornecido existe no sistema. Se o cliente não for encontrado, o processo de reativação deve ser encerrado.
  
  3. O sistema deve verificar se o cliente já está ativado. Se o cliente já estiver ativado, o processo de reativação deve ser encerrado.
  
  4. Ao reativar o cadastro do cliente, a última conta habilitada associada ao CPF do cliente deve ser reativada.

## User Story: Reativar Conta Corrente

- **Nome**: Como Cliente, desejo reativar minha conta corrente para retomar o uso dos serviços de transações.

- **Descrição**: Os clientes têm a opção de reativar sua conta corrente para permitir o processamento de transações novamente.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita aos clientes reativarem sua conta corrente, os seguintes campos devem ser enviados:
     
     - Agencia
     - Conta
  
  2. Antes de processar a solicitação, o sistema deve validar se a conta pertence ao cliente solicitante, caso não pertença o processo deve ser encerrado.
  
  3. O sistema deve verificar se a conta está habilitada, caso esteja o processo deve ser encerrado.
  
  4. API deve devolver uma confirmação informando que a conta solicitada está habilitada.

## User Story: Listar contas correntes do cliente

- **Nome**: Como Cliente, desejo saber quais contas correntes estão vinculadas a mim.

- **Descrição**: Os clientes tem a opção de listar todas as suas contas.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita aos clientes obterem todas as contas correntes vinculadas a seu CPF, os seguintes campos precisam ser enviados:
     
     - CPF
  
  2. Antes de processar a solicitação, o sistema deve validar se o CPF existe no sistema. Caso não exista o processo deve encerrar o processo.
  
  3. API deve devolver uma lista com todas as contas do usuário com os seguintes campos:
     
     - Agencia
     - Conta
     - Status

## User Story: Obter Informação da Conta Corrente Ativa

- **Nome**: Como Cliente, desejo saber as informações da minha conta, tal como saldo, cartões de credito, status, etc.

- **Descrição**: Os clientes tem a opção de consultar as informações de sua conta corrente ativa.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita aos clientes consultar todas as informações sobre a conta corrente ativa:
     
     - CPF
  
  2. Antes de processar a solicitação, o sistema deve validar se o CPF existe no sistema. Caso não exista o processo deve encerrar o processo.
  
  3. API deve devolver informações da conta corrente ativa, os seguintes campos são devolvidos pela API:
     
     - Agencia
     - Conta
     - Status
     - Saldo
     - Cartão de credito
     - 10 ultimas transações paginadas

## User Story: Obter Informação do cliente ativo

- **Nome**: Como Cliente, desejo recuperar as informações que foram cadastrada por mim.

- **Descrição**: Os clientes tem a opção de consultar as informações de seu cadastro.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita aos clientes consultar todas as informações sobre seu cadastro, é preciso informar os seguintes campos:
     
     - CPF
  
  2. Antes de processar a solicitação, o sistema deve validar se o CPF existe no sistema. Caso não exista o processo deve encerrar o processo.
  
  3. API deve devolver informações do cadastro do cliente, os seguintes campos são devolvidos pela API:
     
     - Nome do Cliente 
     - CPF 
     - Telefone 
     - Endereço 
       - Rua 
       - Numero 
       - Complemento 
       - Estado 
       - Cidade 
       - Cep 

## User Story: Editar informações do Cliente

- **Nome**: Como Cliente, desejo editar meus dados caso necessário.

- **Descrição**: Os clientes tem a opção editar seus dados caso necessário.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita o cliente editar seus dados, os seguintes campos precisam ser enviados:
     
     - Nome do Cliente
     - CPF
     - Telefone
  
  2. Antes de processar a solicitação, o sistema deve validar se o CPF existe no sistema. Caso não exista o processo deve encerrar o processo.
  
  3. API deve devolver a confirmação de que o processo foi concluído sem erros, retornando assim o status 200.

## User Story: Editar endereço do cliente

- **Nome**: Como Cliente, desejo editar meus dados de endereço caso necessário.

- **Descrição**: Os clientes tem a opção editar seus dados de endereço caso necessário.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita o cliente editar seus dados, os seguintes campos precisam ser enviados:
     
     - CPF
     - Rua
     - Numero
     - Complemento
     - Estado
     - Cidade
     - Cep
  
  2. Antes de processar a solicitação, o sistema deve validar se o CPF existe no sistema. Caso não exista o processo deve encerrar o processo.
  
  3. API deve devolver a confirmação de que o processo foi concluído sem erros, retornando assim o status 200.

## User Story: Alterar Agencia da Conta Corrente

- **Nome**: Como Cliente, desejo mudar a agencia da minha conta corrente caso necessário.

- **Descrição**: Os clientes tem a opção alterar a agencia de suas contas correntes.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita o cliente editar seus dados, os seguintes campos precisam ser enviados:
     
     - CPF
     - Conta
     - Agencia Antiga
     - Agencia Nova
  
  2. Antes de processar a solicitação, o sistema deve validar se o CPF existe no sistema. Caso não exista o processo deve encerrar o processo.
  
  3. Sistema deve validar se a conta a ser alterada é uma conta existente ou ativa do cliente solicitante. Caso não seja valida o processo deve ser encerrado.
  
  4. Sistema deve validar se a agencia para qual o cliente deseja mudar a conta corrente existe. Caso não exista o sistema deve encerrar o processo.
  
  5. Sistema deve validar se já não existe um conta com o mesmo numero na agencia nova. Caso exista o processo deve ser encerrado
  
  6. API deve devolver a confirmação de que o processo foi concluído sem erros, retornando assim o status 200.

## User Story: Solicitar cartão de credito

- **Nome**: Como Cliente, desejo solicitar um cartão de credito para realizar minhas compras.

- **Descrição**: Os clientes tem a opção de solicitar um cartão de credito via API no qual podem realizar transações financeiras.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita o cliente solicitar um cartão de credito, as seguintes informações precisam ser enviadas:
     
     - CPF
     - Conta
     - Agencia
  
  2. Antes de processar a solicitação, o sistema deve validar se o CPF existe no sistema. Caso não exista o processo deve encerrar o processo.
  
  3. Sistema deve validar se a conta informada é uma conta existente ou ativa do cliente solicitante. Caso não seja valida o processo deve ser encerrado.
  
  4. Sistema deve validar se a agencia na qual pertence a conta corrente existe. Caso não exista o sistema deve encerrar o processo.
  
  5. Sistema deve validar se o cliente que solicitou já não possui um cartão vinculado, caso tenha o processo deve ser encerrado.
  
  6. Sistema gera um cartão e vincula a conta corrente.
  
  7. API deve devolver a confirmação de que o processo foi concluído sem erros, retornando assim o status 200.

## User Story: Desativar cartão de credito

- **Nome**: Como Cliente, desejo desativar meu cartão para não usa-lo.

- **Descrição**: Os clientes tem a opção de solicitar a desativação do cartão de credito caso necessário.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita o cliente solicitar a desativação de um cartão de credito, as seguintes informações precisam ser enviadas:
     
     - CPF
     - Conta
     - Agencia
     - Numero do Cartão
  
  2. Antes de processar a solicitação, o sistema deve validar se o CPF existe no sistema. Caso não exista o processo deve encerrar o processo.
  
  3. Sistema deve validar se a conta informada é uma conta existente ou ativa do cliente solicitante. Caso não seja valida o processo deve ser encerrado.
  
  4. Sistema deve validar se a agencia na qual pertence a conta corrente existe. Caso não exista o sistema deve encerrar o processo.
  
  5. Sistema precisa validar se o cartão de credito existe e se de fato pertence as informações fornecidas, caso contrário o processo deve ser encerrado
  
  6. Sistema desativa o cartão de credito.
  
  7. API deve devolver a confirmação de que o processo foi concluído sem erros, retornando assim o status 200.

## User Story: Enviar Transações

- **Nome**: Como Cliente, desejo fazer comprar e registrar em minha conta.

- **Descrição**: Os clientes tem a opção de usar o saldo da conta ou cartão de credito para realizarem compras.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita o cliente enviar as informações de compras, os seguintes campos devem ser enviados:
     
     - Id
     - Descrição
     - Preco
     - Quantidade
     - Tipo
     - Conta Origem
     - Conta Destino
     - IdempotencyId
     - Numero do cartão de credito (apenas para compras no credito)
     - Segredo do cartão (apenas para compras no credito)
  
  2. Antes de processar a solicitação, O sistema deve validar a transação, caso a transação seja inválida, o processo deve ser encerrado.
  
  3. O sistema deve validar se a conta de origem é valida, caso não seja o processo deve ser encerrado.
  
  4. O Sistema deve somar o valor total das transações separando por tipo e validando se existe saldo para processamento. Se algum deles não tiver saldo, o processo deve ser encerrado.
  
  5. Se todas as validações forem válidas, o sistema deve registrar as transações na conta de origem e atualizar o saldo da conta.
  
  6. O Sistema deve retornar uma resposta de sucesso caso a transação seja processada com sucesso, deve retornar um status HTTP 200.

## User Story: Estornar Transação

- **Nome**: Como Cliente, desejo estornar uma compra feita de maneira errada.

- **Descrição**: Os clientes tem a opção de estornar transações realizadas.

- **Critérios de Aceitação**:
  
  1. O sistema deve fornecer uma API que permita o cliente enviar as informações de compras, os seguintes campos devem ser enviados:
     
     - Id
     - IdempotencyId
  
  2. Antes de processar a solicitação, O sistema deve validar se o ID e o IdempotencyID existem, se não existirem o sistema deve encerrar o processo.
  
  3. Caso encontre a transação, o sistema deve registrar a transação de estorno com valor negativo e com o Tipo estorno e deve devolver o saldo para conta.
  
  4. O Sistema deve retornar uma resposta de sucesso caso a transação seja processada com sucesso, deve retornar um status HTTP 200.

## Glossário

- [Termo 1]: [Definição]
- [Termo 2]: [Definição]

## Referências

- [Liste quaisquer documentos, padrões ou fontes de referência usados na criação destas User Stories]
