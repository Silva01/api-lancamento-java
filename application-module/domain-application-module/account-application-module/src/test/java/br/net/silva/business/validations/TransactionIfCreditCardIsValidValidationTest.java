package br.net.silva.business.validations;

import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionIfCreditCardIsValidValidationTest extends AbstractAccountBuilder {

    private TransactionIfCreditCardIsValidValidation transactionIfCreditCardIsValidValidation;

    @Mock
    private Repository<Optional<Account>> findAccountByCpfAndAccountNumberAndAgencyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(true))));
        transactionIfCreditCardIsValidValidation = new TransactionIfCreditCardIsValidValidation(findAccountByCpfAndAccountNumberAndAgencyRepository);
    }

    @Test
    void shouldValidateTransactionIfCreditCardWithSuccess() {
        var sourceAccount = new AccountInput(1, 45678, "978534");
        var destinyAccount = new AccountInput(2, 99999, "978534");

        var transaction1 = buildMockInputTransaction(TransactionTypeEnum.CREDIT, "99988877766", 45678);
        var transaction2 = buildMockInputTransaction(TransactionTypeEnum.CREDIT, "99988877766", 45678);
        var transaction3 = buildMockInputTransaction(TransactionTypeEnum.DEBIT, null, null);

        var input = new BatchTransactionInput(sourceAccount, destinyAccount, List.of(transaction1, transaction2, transaction3));

        var source = new Source(input);

        assertDoesNotThrow(() -> transactionIfCreditCardIsValidValidation.validate(source));

        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldValidateTransactionOnlyDebitWithSuccess() {
        var sourceAccount = new AccountInput(1, 45678, "978534");
        var destinyAccount = new AccountInput(2, 99999, "978534");

        var transaction3 = buildMockInputTransaction(TransactionTypeEnum.DEBIT, null, null);

        var input = new BatchTransactionInput(sourceAccount, destinyAccount, List.of(transaction3));

        var source = new Source(input);

        assertDoesNotThrow(() -> transactionIfCreditCardIsValidValidation.validate(source));

        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorAtValidateTransactionCreditCardNumberDifferent() {
        var sourceAccount = new AccountInput(1, 45678, "978534");
        var destinyAccount = new AccountInput(2, 99999, "978534");

        var transaction1 = buildMockInputTransaction(TransactionTypeEnum.CREDIT, "99988877788", 45678);
        var transaction2 = buildMockInputTransaction(TransactionTypeEnum.CREDIT, "99988877766", 45678);
        var transaction3 = buildMockInputTransaction(TransactionTypeEnum.DEBIT, null, null);

        var input = new BatchTransactionInput(sourceAccount, destinyAccount, List.of(transaction1, transaction2, transaction3));

        var source = new Source(input);

        var responseException = assertThrows(GenericException.class, () -> transactionIfCreditCardIsValidValidation.validate(source));
        assertEquals("Credit card number or cvv is different at register in account", responseException.getMessage());

        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorAtValidateTransactionCreditCardDeactivated() {
        when(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(false))));
        var sourceAccount = new AccountInput(1, 45678, "978534");
        var destinyAccount = new AccountInput(2, 99999, "978534");

        var transaction1 = buildMockInputTransaction(TransactionTypeEnum.CREDIT, "99988877766", 45678);
        var transaction2 = buildMockInputTransaction(TransactionTypeEnum.CREDIT, "99988877766", 45678);
        var transaction3 = buildMockInputTransaction(TransactionTypeEnum.DEBIT, null, null);

        var input = new BatchTransactionInput(sourceAccount, destinyAccount, List.of(transaction1, transaction2, transaction3));

        var source = new Source(input);

        var responseException = assertThrows(GenericException.class, () -> transactionIfCreditCardIsValidValidation.validate(source));
        assertEquals("Credit card deactivated in the account", responseException.getMessage());

        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorAtValidateTransactionCvvIsDifferent() {
        var sourceAccount = new AccountInput(1, 45678, "978534");
        var destinyAccount = new AccountInput(2, 99999, "978534");

        var transaction1 = buildMockInputTransaction(TransactionTypeEnum.CREDIT, "99988877766", 45699);
        var transaction2 = buildMockInputTransaction(TransactionTypeEnum.CREDIT, "99988877766", 45678);
        var transaction3 = buildMockInputTransaction(TransactionTypeEnum.DEBIT, null, null);

        var input = new BatchTransactionInput(sourceAccount, destinyAccount, List.of(transaction1, transaction2, transaction3));

        var source = new Source(input);

        var responseException = assertThrows(GenericException.class, () -> transactionIfCreditCardIsValidValidation.validate(source));
        assertEquals("Credit card number or cvv is different at register in account", responseException.getMessage());

        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

}