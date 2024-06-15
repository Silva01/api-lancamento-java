package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.service;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component.AccountActiveValidator;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component.AccountBalanceValidator;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component.AccountExistsValidator;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component.ValidationHandler;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.enuns.ResponseStatus;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.AccountKey;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.net.silva.daniel.enuns.TransactionTypeEnum.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionRegisterServiceTest {

    private TransactionRegisterService service;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        final var validations = List.of(new AccountExistsValidator(), new AccountActiveValidator(), new AccountBalanceValidator());
        final var validationHandler = new ValidationHandler(validations);
        service = new TransactionRegisterService(accountRepository, validationHandler);
    }

    @Test
    void registerTransaction_WithValidData_RegisterWithSuccess() {
        // Arrange
        when(accountRepository.findByAccountNumberAndAgencyAndCpf(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(generateMockAccount(true, BigDecimal.ONE)));
        final var message = createMockMessageRequest(generateMockTransactions());

        // Act
        final var response = service.registerTransaction(message);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ResponseStatus.SUCCESS);
        assertThat(response.sourceAccountNumber()).isEqualTo(message.sourceAccount().accountNumber());
        assertThat(response.sourceAccountAgency()).isEqualTo(message.sourceAccount().agency());
        assertThat(response.destinyAccountNumber()).isEqualTo(message.destinyAccount().accountNumber());
        assertThat(response.destinyAccountAgency()).isEqualTo(message.destinyAccount().agency());
        assertThat(response.transactions()).hasSize(1);
        assertThat(response.transactions().get(0).id()).isEqualTo(1L);
        assertThat(response.transactions().get(0).idempotency()).isEqualTo(123L);
    }

    @Test
    void registerTransaction_WithSourceAccountNotExists_ThrowsAccountNotExistsException() {
        when(accountRepository.findByAccountNumberAndAgencyAndCpf(anyInt(), anyInt(), ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        final var message = createMockMessageRequest(generateMockTransactions());

        final var sut = service.registerTransaction(message);
        assertThat(sut).isNotNull();
        assertThat(sut.status()).isEqualTo(ResponseStatus.ERROR);
        assertThat(sut.message()).isEqualTo("Account 123 and agency 1: Account not found");
    }

    @Test
    void registerTransaction_WithDestinyAccountNotExists_ThrowsAccountNotExistsException() {
        when(accountRepository.findByAccountNumberAndAgencyAndCpf(anyInt(), anyInt(), ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(generateMockAccount(true, BigDecimal.ONE)))
                .thenReturn(Optional.empty());

        final var message = createMockMessageRequest(generateMockTransactions());

        final var sut = service.registerTransaction(message);
        assertThat(sut).isNotNull();
        assertThat(sut.status()).isEqualTo(ResponseStatus.ERROR);
        assertThat(sut.message()).isEqualTo("Account 456 and agency 2: Account not found");
    }

    @Test
    void registerTransaction_WithSourceAccountDeactivated_ThrowsAccountDeactivateException() {
        when(accountRepository.findByAccountNumberAndAgencyAndCpf(anyInt(), anyInt(), ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(generateMockAccount(false, BigDecimal.ONE)));
        final var message = createMockMessageRequest(generateMockTransactions());

        final var sut = service.registerTransaction(message);
        assertThat(sut).isNotNull();
        assertThat(sut.status()).isEqualTo(ResponseStatus.ERROR);
        assertThat(sut.message()).isEqualTo("Account 123 and agency 1: Account is not active");
    }

    @Test
    void registerTransaction_WithDestinyAccountDeactivate_ThrowsAccountDeactivatedException() {
        when(accountRepository.findByAccountNumberAndAgencyAndCpf(anyInt(), anyInt(), ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(generateMockAccount(true, BigDecimal.ONE)))
                .thenReturn(Optional.of(generateMockAccount(false, BigDecimal.ONE)));

        final var message = createMockMessageRequest(generateMockTransactions());

        final var sut = service.registerTransaction(message);
        assertThat(sut).isNotNull();
        assertThat(sut.status()).isEqualTo(ResponseStatus.ERROR);
        assertThat(sut.message()).isEqualTo("Account 456 and agency 2: Account is not active");
    }

    @Test
    void registerTransaction_WithSourceAccountNotHasBalance_ThrowsAccountNotHasBalanceException() {
        when(accountRepository.findByAccountNumberAndAgencyAndCpf(anyInt(), anyInt(), ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(generateMockAccount(true, BigDecimal.ZERO)));

        final var message = createMockMessageRequest(generateMockTransactions());

        final var sut = service.registerTransaction(message);
        assertThat(sut).isNotNull();
        assertThat(sut.status()).isEqualTo(ResponseStatus.ERROR);
        assertThat(sut.message()).isEqualTo("Account 123 and agency 1: Insufficient balance");
    }

    @Test
    void registerTransaction_WithTransactionsDuplicated_ThrowsTransactionDuplicateException() {
        when(accountRepository.findByAccountNumberAndAgencyAndCpf(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(generateMockAccount(true, BigDecimal.valueOf(1000))));
        final var message = createMockMessageRequest(generateMockDuplicatedTransactions());

        final var sut = service.registerTransaction(message);
        assertThat(sut).isNotNull();
        assertThat(sut.status()).isEqualTo(ResponseStatus.ERROR);
        assertThat(sut.message()).isEqualTo("Transaction has 2 or more equals transactions.");
    }

    private BatchTransactionInput createMockMessageRequest(List<TransactionInput> transactions) {
        final var sourceAccount = new AccountInput(123, 1, "55544433322");
        final var destinyAccount = new AccountInput(456, 2, "55544433300");
        return new BatchTransactionInput(sourceAccount, destinyAccount, transactions);
    }

    private List<TransactionInput> generateMockTransactions() {
        return List.of(
                new TransactionInput(1L, "Test", BigDecimal.ONE, 1, DEBIT, 123L, null, null)
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
