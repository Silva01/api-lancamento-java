package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure;


import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.commons.Fixture;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.enuns.ResponseStatus;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.AccountKey;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.repository.AccountRepository;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.service.TransactionRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static br.net.silva.daniel.enuns.TransactionTypeEnum.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RegisterTransactionTest {


    private TransactionRegisterService service;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Fixture fixture;

    @BeforeEach
    void setUp() {
        service = new TransactionRegisterService(accountRepository);
        fixture = new Fixture(entityManager);
    }

    @Test
    @DisplayName("Register transaction with valid data and return success and register transaction in database")
    void registerTransaction_WithValidData_ReturnsSuccessAndRegisterTransactionInDatabase() {
        final var message = createMockMessageRequest(generateMockTransactions());
        final var response = service.registerTransaction(message);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ResponseStatus.SUCCESS);

        final var account = entityManager
                .find(Account.class, new AccountKey(message.sourceAccount().accountNumber(), message.sourceAccount().agency()));

        assertThat(account).isNotNull();
        assertThat(account.getBalance()).isEqualTo(BigDecimal.valueOf(2800));

        final var destiny = entityManager
                .find(Account.class, new AccountKey(message.destinyAccount().accountNumber(), message.destinyAccount().agency()));

        assertThat(destiny).isNotNull();
        assertThat(destiny.getBalance()).isEqualTo(BigDecimal.valueOf(3200));
    }

    private BatchTransactionInput createMockMessageRequest(List<TransactionInput> transactions) {
        final var sourceAccount = fixture.createAccount();
        final var destinyAccount = fixture.createAccount();
        return new BatchTransactionInput(sourceAccount, destinyAccount, transactions);
    }

    private List<TransactionInput> generateMockTransactions() {
        return List.of(
                new TransactionInput(1L, "Test", BigDecimal.valueOf(200), 1, DEBIT, 123L, null, null)
        );
    }

    private List<TransactionInput> generateMockDuplicatedTransactions() {
        return List.of(
                new TransactionInput(1L, "Test", BigDecimal.ONE, 1, DEBIT, 123L, null, null),
                new TransactionInput(1L, "Test", BigDecimal.ONE, 1, DEBIT, 123L, null, null)
        );
    }

    private Account generateMockAccount(boolean active, BigDecimal balance) {
        return new Account(
                new AccountKey(1, 1234),
                balance,
                "Test",
                active,
                "55544433322",
                null,
                null,
                LocalDateTime.now());
    }
}
