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

## Glossário

- [Termo 1]: [Definição]
- [Termo 2]: [Definição]

## Referências

- [Liste quaisquer documentos, padrões ou fontes de referência usados na criação destas User Stories]
