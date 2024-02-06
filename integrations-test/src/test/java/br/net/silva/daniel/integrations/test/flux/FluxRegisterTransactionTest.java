package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.RegisterTransactionUseCase;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.business.validations.DestinyAccountExistsValidate;
import br.net.silva.business.validations.TransactionIfCreditCardIsValidValidation;
import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FluxRegisterTransactionTest extends AbstractBuilder {

    private UseCase<EmptyOutput> registerTransactionUseCase;

    private IValidations clientExistsValidate;
    private IValidations accountExistsValidate;
    private IValidations accountDestinyExistsValidate;
    private IValidations transactionIfCreditCardIsValidValidation;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @Mock
    private Repository<Optional<Account>> findOptionalAccountRepository;

    @Mock
    private Repository<Optional<Account>> findDetinyAccountRepository;

    @Mock
    private Repository<Account> findAccountRepository;

    @Mock
    private Repository<Account> saveAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findClientRepository.exec(any())).thenReturn(Optional.of(buildMockClient(true)));
        when(findOptionalAccountRepository.exec(any(), any(), any())).thenReturn(Optional.of(buildMockAccountWithCreditCard(true, true)));
        when(findDetinyAccountRepository.exec(any(), any(), any())).thenReturn(Optional.of(buildMockAccount(true)));
        when(findAccountRepository.exec(any(), any(), any())).thenReturn(buildMockAccount(true));
        when(saveAccountRepository.exec(any())).thenReturn(buildMockAccount(true));

        // Use case
        registerTransactionUseCase = new RegisterTransactionUseCase(findAccountRepository, saveAccountRepository);
        var findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());

        // Validations
        clientExistsValidate = new ClientExistsValidate(findClientUseCase);
        accountExistsValidate = new AccountExistsValidate(findOptionalAccountRepository);
        accountDestinyExistsValidate = new DestinyAccountExistsValidate(findDetinyAccountRepository);
        transactionIfCreditCardIsValidValidation = new TransactionIfCreditCardIsValidValidation(findOptionalAccountRepository);
    }

    @Test
    void shouldRegisterTransactionsKindOfDebitWithSuccess() {
        var sourceAccount = new AccountInput(1, 1, "12345678901");
        var destinyAccount = new AccountInput(2, 1, "12345678901");
        var listOfTransactions = generateTransactionOnlyDebit(1);

        var batchTransactionInput = new BatchTransactionInput(sourceAccount, destinyAccount, listOfTransactions);
        var source = new Source(batchTransactionInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(registerTransactionUseCase);

        List<IValidations> validations = List.of(
                clientExistsValidate,
                accountExistsValidate,
                accountDestinyExistsValidate,
                transactionIfCreditCardIsValidValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        assertDoesNotThrow(() -> facade.exec(source));

        verify(findAccountRepository, times(2)).exec(any(), any(), any());
        verify(saveAccountRepository, times(2)).exec(any());
        verify(findOptionalAccountRepository, times(1)).exec(any(), any(), any());
        verify(findDetinyAccountRepository, times(1)).exec(any(), any(), any());
        verify(findClientRepository, times(1)).exec(any());
    }

    @Test
    void shouldErrorRegisterTransactionsClientNotExists() {
        when(findClientRepository.exec(any())).thenReturn(Optional.empty());
        var sourceAccount = new AccountInput(1, 1, "12345678901");
        var destinyAccount = new AccountInput(2, 1, "12345678901");
        var listOfTransactions = generateTransactionOnlyDebit(1);

        var batchTransactionInput = new BatchTransactionInput(sourceAccount, destinyAccount, listOfTransactions);
        var source = new Source(batchTransactionInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(registerTransactionUseCase);

        List<IValidations> validations = List.of(
                clientExistsValidate,
                accountExistsValidate,
                accountDestinyExistsValidate,
                transactionIfCreditCardIsValidValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var response = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Client not exists in database", response.getMessage());

        verify(findAccountRepository, never()).exec(any(), any(), any());
        verify(saveAccountRepository, never()).exec(any());
        verify(findOptionalAccountRepository, never()).exec(any(), any(), any());
        verify(findDetinyAccountRepository, never()).exec(any(), any(), any());
        verify(findClientRepository, times(1)).exec(any());
    }
}
