# User Stories

###### Sumario

[Criar Cliente](#user-story-criar-cliente)

[Criar Conta Corrente](#user-story-criar-conta-corrente)

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

## Glossário

- [Termo 1]: [Definição]
- [Termo 2]: [Definição]

## Referências

- [Liste quaisquer documentos, padrões ou fontes de referência usados na criação destas User Stories]
