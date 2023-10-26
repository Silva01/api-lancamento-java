# Modelagem do Desafio de transações

### Objetivos

Principal objetivo é atender os pontos abaixo

- [ ] Sistemas distribuídos concorrentes  
- [ ] Rastreabilidade  
- [ ] Idempotência  
- [ ] Tolerância a falhas  
- [ ] Padrões Arquiteturais  
- [ ] Concorrência

### Modelagem dos Pacotes

![](./gift/modules-transaction-application.gif)

----

#### Core Module

![](./gift/core-module-representation.gif)

Aqui está o módulo responsável por armazenar os módulos de regras de negócio separados por domínios. Dentro do Core Module, temos dois módulos principais para regras de negócio.

----

##### Shared Business Module

Aqui se encontram as interfaces que serão compartilhadas entre todos os módulos de regras de negócio, como, por exemplo, interfaces de validação e interfaces de marcação, entre outras.

----

##### Domain Business Module

Aqui, integramos outros módulos que representam os Bounded Contexts do domínio da aplicação desenvolvida. Cada Bounded Context é um módulo independente. Dentro deste módulo, teremos dois módulos (Bounded Contexts): o Módulo do Cliente (Client Module) e o Módulo de Conta Corrente (Current Account Module).

---

#### Application Module

![](./gift/application-module-representation.gif)

Aqui é onde encontramos os módulos que representam os casos de uso da aplicação. Esta é a área em que a camada de drivers consome funcionalidades. É importante lembrar que essa camada pode se comunicar diretamente com a camada de regras de negócio. No entanto, toda comunicação direcionada a este módulo deve ser realizada por meio de portas (Ports), conforme recomendado pela arquitetura hexagonal.([Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)).

---

##### Shared Application Module

Aqui, encontramos classes e interfaces que são compartilhadas por todos os módulos que representam os casos de uso. É importante ressaltar que este módulo é destinado exclusivamente para uso interno ao Módulo de Application.

---

##### Domain Application Module

Aqui, temos os módulos que contêm os casos de uso da aplicação. É nesta área que os drivers se comunicam. É importante destacar que, de acordo com a arquitetura hexagonal, toda comunicação com um caso de uso é realizada por meio de portas (Ports).

---

#### Adapter Module

![](/Users/silva01/workspace/api%20de%20lancamentos/documents/gift/adapter-module-representation.gif)

Aqui é onde encontramos as interfaces e abstrações para adaptadores. Quando um caso de uso precisa utilizar um banco de dados, uma fila de mensageria, entre outros recursos, é utilizado um adaptador. Da mesma forma que o acesso dos casos de uso é realizado por meio de portas (Ports), os Adaptadores (Adapters) são usados pelos casos de uso para acessar recursos ou aplicações externas.

---

#### Driver Module

![](/Users/silva01/workspace/api%20de%20lancamentos/documents/gift/drive-module-representation.gif)

Este é o nível em que podemos criar nossas APIs, jobs, listeners e persistência. Este módulo permite a criação desses drivers usando frameworks distintos. É a camada de infraestrutura que fornece recursos para que os usuários acessem a aplicação.

### Modelagem da API de Lançamentos

![](./images/domain-model.png)

### Requisitos de regras de Negócio

##### Bounded Context de Cliente

![](/Users/silva01/workspace/api%20de%20lancamentos/documents/images/model-aggregate-client-address.png)

Representa um cliente cadastrado e válido em nossa aplicação, dessa forma permitindo que esse cliente consiga realizar transações financeiras.

###### Regras

- **<u>Cliente</u>**
  
  - Cliente só poderá ter apenas um unico endereço
  
  - Quando um cliente é criado, automaticamente é vinculado uma conta corrente, não pode existir um cliente sem uma conta corrente vinculada.
  
  - Cliente precisa ter um CPF
  
  - Cliente Precisa ter um nome, não é permitido deixar vazio ou Nulo
  
  - Cliente precisa ter um telefone para contato, não é permitido vazio, nulo ou preencher com letras.
  
  - Por padrão um cliente quando criado ele é ativado automaticamente.
  
  - Não pode editar um cliente desativado
  
  - Não é permitido criar cliente sem endereço
  
  - Os campos disponiveis para edição são:
    
    - Name
    
    - Telephone
    
    - Address

- **<u>Address</u>**
  
  - Endereço só pode referenciar apenas um unico Cliente
  
  - Endereço não permite valores vazio ou null nos seguintes campos:
    
    - Street
    
    - Number
    
    - Neighborhood
    
    - State
    
    - City
    
    - Zip Code

---

##### Bounded Context de Conta Corrente

![](/Users/silva01/workspace/api%20de%20lancamentos/documents/images/modeling-aggregate-current-account.png)

Representa uma conta bancária onde o cliente poderá realizar transações de debito e credito. Também podemos vincular o cliente a um cartão de credito.

###### Regras

- **<u>Account</u>**
  
  - O numero da conta possui 6 digitos e é gerado automaticamente
  
  - Não pode existir uma conta corrente sem um cliente vinculado
  
  - O número da conta não pode existir dentro de uma mesma agência para clientes diferentes
  
  - A agência é informada manualmente no momento da criação da conta para um cliente, não é permitido numeros negativos, zero ou nulos.
  
  - O Saldo da Conta Corrente inicialmente é zero (0)
  
  - Senha possui apenas 6 digitos, é apenas numero, não pode haver números repetidos, não pode ser 0, não pode ser Nulo e não pode ser negativo.
  
  - Quando uma conta é criada, seu status é ativo de forma automatica.
  
  - Não pode registrar transações se a conta não tiver saldo ou for desativada.
  
  - Quando criada, não possui cartão de crédito vinculado.
  
  - Não é obrigatorio ter cartão de credito vinculado, o vinculo é opcional e pode ser solicitado a qualquer momento.
  
  - Não pode processar comprar caso a validade do cartão esteja vencida

---

- **<u>Credit Card</u>**
  - Numero do cartão é unico, não pode haver o mesmo numero para clientes diferentes
  - Numero do cartão é gerado automaticamente, seguir [regras de geração de cartão](https://gizmodo.uol.com.br/como-sao-criados-os-numeros-de-cartao-de-credito/)
  - Um cliente só poderá ter apenas um cartão de credito.
  - Cartão de Credito só pode ter vinculo com apenas um cliente
  - Saldo inicial do cartão de credito é R$ 1000,00
  - O Cartão precisa ter um conta vinculada.
  - Não pode vincular um cartão a uma conta desativada.
  - O cartão é desativado automaticamente se a conta for desativada.

---

- **<u>Transaction</u>**
  
  - Pode ser existir diversas transações para uma mesma conta
  
  - As transações precisam ser separadas por tipo (*DEBIT* or *CREDIT*) e processadas de forma paralela
  
  - Se ocorrer um erro no processamento de um dos tipos, toda a transação não pode ser processada.
  
  - Não pode existir transações com valores Nulos ou com zeros
  
  - Não pode existir transações com quantidade 0 ou nulo
  
  - O tipo precisa ser ***DEBIT*** ou ***CREDIT***
  
  - Para compras do tipo ***DEBIT*** é necessário realizar as seguintes regras:
    
    - Conta de origem não pode ser vazia, nulo, negativa ou zero, precisa ter o valor da conta na qual fez a transação.
    
    - Conta de destino não pode ser vazia, nulo, negativa ou zero.
  
  - Para compras do tipo ***CREDIT*** é necessário realizar as seguintes regras:
    
    - Numero do cartão de credito não pode ser vazio, nulo, negativo ou zero
    
    - A secret Key não pode ser vazia, nula, negativa ou zero
  
  - IdempotencyID é um uuid gerado pela origem que precisa existir, caso não seja enviado a transação não pode ser processada.

---

### User Stories

[Ver Documento de User Story Fase 1](./documento-caso-de-uso.md)

### Arquitetura da Solução

[Ver Documento de Arquitetura Fase 1](./ARCHITECTURE.md)

### Fluxo do Transaction Business

Usuário envia os dados para lançamento, o dado é enviado no seguinte JSON

```json
{
    "accountNumber": 123,
    "bankNumber": 1234,
    "transactions": [
      "description": "abc",
      "price": 1.0,
      "transactionType": "CREDIT"
    ]
}
```

Um `json` que suporta para cada conta, diversas transações, essas transações precisam ser separadas pois o processamento de cada transação possui regras especificas.

Transaction Business precisa validar os dados de conta que são obrigatorios, caso um deles não seja informado, uma `exception` precisa ser lançada e o processo encerrado.

Também será necessario validar o `price` pois não é permitido o envio de valores negativos, nulos ou zerados.

Atualmente é permitido apenas os tipo `CREDIT` e `DEBIT` , qualquer valor diferente deve lançar uma `exception`

Os campos `description`  é opcional e aceita valores vazios caso seja enviados.

Após validação o Transaction Business precisa separar as transações em blocos de credito e blocos de debito.

Após separar os dados, eles são enviados para a camada de `Account Business`

### Fluxo do Account Business

Nesse fluxo a camada consulta no banco os dados as informações referente a conta.

Ao obter as informações inicia-se o processamento das transações, sempre do mais critico para o menos critico, ou seja, do `DEBIT` para o `CREDIT` 

O processamento inicia validando se o saldo é maior ou igual a soma das transações do tipo em processamento, comparar saldo de debito com a soma das transações de debito e Saldo de credito com soma das transações de credito.

O lançamento só poderá ocorrer caso seja possível registrar todas as transações, caso uma delas falhe por falta de saldo, toda a transação precisa ser cancelada e o saldo retornado.

O fluxo irá seguir a seguinte ordem:

1. Grava as transações de Debito

2. Grava as transações de Credito

3. Atualiza o saldo da Conta

Se qualquer erro ocorrer em qualquer das etapas, toda a transação deve ser cancelada imadiatamente.

### Fluxo de Consulta de Saldo

Neste fluxo a camada consulta os dados de saldo da conta, nessa consulta é necessário informar o bankNumber e o accoutNumber para que seja possível recuperar os saldos de credito e debito.

### Fluxo para Criação de Conta

Neste fluxo para se criar uma conta será necessário informar os dados de cliente, essa informação é informada manualmente, outra informação é em qual agencia o usuário deseja criar sua conta. O número da conta é gerado de forma automatica e não se pode repetir.

### Rastreabilidade

Deve ser possível rastrear todo o processamento dos dados, pra esse caso usa-se Logs, também pode ser usado uma forma de rastreabilidade usando o Prometheus e rastrear os processos de erros e sucessos.

##### Ferramentas para rastreabilidade

- Logs

- Prometheus

### Concorrência

O sistema deve evitar a corrida por dados e deve ser garantido a consistência entre os dados. lembrando que o processamento precisa se de forma paralela, deve-se usar Thread Safe.

##### Ferramentas para concorrência

- Threads (Pesquisar)

### Idempotência

O sistema deve ser Idempotente e garantir que não é possível gerar transações duplicatas na base.

### Tolerância a Falhas

Sistema deve ser Tolerânte a falhas e se recuperar de falhas graves.
