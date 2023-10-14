# Documento de Casos de Uso

## Visão Geral

Este documento descreve os casos de uso para o Projeto de Transações Financeiras. Os casos de uso são representações das interações entre atores (usuários) e o sistema. Este documento serve como um guia para entender como o sistema funcionará do ponto de vista do usuário.

## Tabela de Conteúdo

- [1. Casos de Uso do Modulo de Cliente](#1-casos-de-uso-do-modulo-de-cliente)
  - [1.1 Caso de Uso 1: Criar Cliente](#11-caso-de-uso-1-criar-cliente)
    - [1.1.1 Descrição](#111-descrição)
    - [1.1.2 Ator Principal](#112-ator-principal)
    - [1.1.3 Pré-condições](#113-pré-condições)
    - [1.1.4 Fluxo Principal](#114-fluxo-principal)
    - [1.1.5 Fluxo de Erro: Cliente Inválido](#115-fluxo-de-erro-cliente-inválido)
    - [1.1.6 Fluxo de Erro: Conta Inválida](#116-fluxo-de-erro-conta-inválida)
    - [1.1.7 Pós-condições](#117-pós-condições)
- [2. Glossário](#2-glossário)
- [3. Referências](#3-referências)

## 1. Casos de Uso do Modulo de Cliente

### 1.1 Caso de Uso 1: Criar Cliente

#### 1.1.1 Descrição

O proposito desde caso de uso é permitir o cliente consiga de se cadastrar e gerar um conta corrrente em seu nome. Quando o Cliente é cadastrado pela primeira vez, uma Conta correnta é vinculada a ele de forma automatica.

#### 1.1.2 Ator Principal

![[Nome e descrição do ator principal envolvido neste caso de uso]](/Users/silva01/workspace/api%20de%20lancamentos/documents/images/use-case-create-client.png)

#### 1.1.3 Pré-condições

É necessário informar os dados de usuário junto com o endereço e a informação da Agência

#### 1.1.4 Fluxo Principal

1. Cliente envia seus dados para cadastro no sistema de Transações
2. Os dados do cliente são validados com base nas Regras de Negocio
3. Os dados estando validos o usuário é persistido
4. Após ser persistido, um conta corrente é criada e vinculado a seu registro
5. É devolvido as informações da nova conta criada



#### 1.1.5 Fluxo de Erro : Cliente inválido

1. Cliente Envia seus dados para cadastro no sistema de Transações

2. Os dados do cliente não passa na validação

3. É devolvido para o solicitante erro 400 com a mensagem do campo que não passou na validação



#### 1.1.6 Fluxo de Erro : Conta inválida

1. Cliente envia seus dados para cadastro no sistema de Transações

2. Os dados do cliente são validados com base nas Regras de Negocio

3. Os dados estando corretos validos o usuário é persistido.

4. Após ser persistido, uma tentativa de criação da conta corrente é realizada.

5. A criação da conta sofre erros

6. É realizado uma transação de rollback na criação do usuário

7. É devolvido para o solicitante erro 400 com a mensagem do campo que não passou pela validação.

#### 1.1.7 Pós-condições

Deve Existir um cliente válido cadastrado e vinculado a uma conta corrente ativa e válida.

## 2. Glossário

- RollBack: Transação reversa no banco de dados, exclui um registro que acabou de ser criado. 

## 3. Referências

- [Liste quaisquer documentos, padrões ou fontes de referência usados na criação deste documento]
