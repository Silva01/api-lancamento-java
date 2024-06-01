package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.service;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.enuns.ResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static br.net.silva.daniel.enuns.TransactionTypeEnum.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TransactionRegisterServiceTest {

    private TransactionRegisterService service;

    @BeforeEach
    void setUp() {
        service = new TransactionRegisterService();
    }

    @Test
    void registerTransaction_WithValidData_RegisterWithSuccess() {
        // Arrange
        final var sourceAccount = new AccountInput(123, 1, "55544433322");
        final var destinyAccount = new AccountInput(456, 2, "55544433300");
        final var message = new BatchTransactionInput(sourceAccount, destinyAccount, generateMockTransactions());

        // Act
        final var response = service.registerTransaction(message);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ResponseStatus.SUCCESS);
        assertThat(response.sourceAccountNumber()).isEqualTo(sourceAccount.accountNumber());
        assertThat(response.sourceAccountAgency()).isEqualTo(sourceAccount.agency());
        assertThat(response.destinyAccountNumber()).isEqualTo(destinyAccount.accountNumber());
        assertThat(response.destinyAccountAgency()).isEqualTo(destinyAccount.agency());
        assertThat(response.transactions()).hasSize(1);
        assertThat(response.transactions().get(0).id()).isEqualTo(1L);
        assertThat(response.transactions().get(0).idempotency()).isEqualTo(123L);
    }

    private List<TransactionInput> generateMockTransactions() {
        return List.of(
                new TransactionInput(1L, "Test", BigDecimal.ONE, 1, DEBIT, 123L, null, null)
        );
    }
}
