package br.net.silva.business.usecase;

import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RegisterTransactionUseCaseTest extends AbstractAccountBuilder {

    private RegisterTransactionUseCase useCase;

    @Mock
    private Repository<AccountOutput> findAccountRepository;

    @Mock
    private Repository<AccountOutput> saveAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(true, null));
        doAnswer(invocation -> invocation.getArguments()[0]).when(saveAccountRepository).exec(any(Account.class));

        useCase = new RegisterTransactionUseCase(findAccountRepository, saveAccountRepository);
    }

    @Test
    void shouldRegisterDebitTransactionWithSuccess() {
        var sourceAccount = new AccountInput(1, 45678, "978534");
        var destinyAccount = new AccountInput(2, 99999, "00099988877");
        var transactionInput = new TransactionInput(generateRandomNumberLong(4), "Compra de teste", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, generateIdempotencyId(), null, null);

        var input = new BatchTransactionInput(sourceAccount, destinyAccount, List.of(transactionInput));
        var source = new Source(input);

        assertDoesNotThrow(() -> useCase.exec(source));

        verify(findAccountRepository, times(2)).exec(anyInt(), anyInt(), anyString());
        verify(saveAccountRepository, times(2)).exec(any(AccountOutput.class));
    }

    @Test
    void shouldRegisterDebitAndCreditTransactionWithSuccess() {
        var sourceAccount = new AccountInput(1, 45678, "978534");
        var destinyAccount = new AccountInput(2, 99999, "00099988877");
        var transactionDebit = new TransactionInput(generateRandomNumberLong(4), "Compra de teste", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, generateIdempotencyId(), null, null);
        var transactionCredit = new TransactionInput(generateRandomNumberLong(4), "Compra de teste", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.CREDIT, generateIdempotencyId(), "12345", 123);

        var input = new BatchTransactionInput(sourceAccount, destinyAccount, List.of(transactionDebit, transactionCredit));
        var source = new Source(input);

        assertDoesNotThrow(() -> useCase.exec(source));

        verify(findAccountRepository, times(2)).exec(anyInt(), anyInt(), anyString());
        verify(saveAccountRepository, times(2)).exec(any(AccountOutput.class));
    }

    @Test
    void shouldErrorAtRegisterDebitTransactionBalanceIsInsufficient() {
        var sourceAccount = new AccountInput(1, 45678, "978534");
        var destinyAccount = new AccountInput(2, 99999, "00099988877");
        var transactionInput = new TransactionInput(generateRandomNumberLong(4), "Compra de teste", BigDecimal.valueOf(3000), 1, TransactionTypeEnum.DEBIT, generateIdempotencyId(), null, null);

        var input = new BatchTransactionInput(sourceAccount, destinyAccount, List.of(transactionInput));
        var source = new Source(input);

        var responseException = assertThrows(IllegalArgumentException.class, () -> useCase.exec(source));
        assertEquals("Balance is insufficient", responseException.getMessage());

        verify(findAccountRepository, times(2)).exec(anyInt(), anyInt(), anyString());
        verify(saveAccountRepository, never()).exec(any(Account.class));
    }
}