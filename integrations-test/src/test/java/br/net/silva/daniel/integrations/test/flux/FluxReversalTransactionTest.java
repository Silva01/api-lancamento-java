package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.exception.TransactionNotExistsException;
import br.net.silva.business.usecase.ReversalTransactionUseCase;
import br.net.silva.business.validations.TransactionExistsValidate;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class FluxReversalTransactionTest extends AbstractBuilder {

    private UseCase<EmptyOutput> fluxReversalTransactionUseCase;

    private IValidations transactionExistsValidation;

    @Mock
    private Repository<TransactionOutput> findTransactionByIdAndIdempotencyIdRepository;

    @Mock
    private Repository<AccountOutput> findAccountByAccountNumberRepository;

    @Mock
    private Repository<Optional<TransactionOutput>> transactionRepository;

    @Mock
    private Repository<AccountOutput> saveAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        var transactionMock = generateTransaction();
        when(transactionRepository.exec(anyLong(), anyLong())).thenReturn(Optional.of(transactionMock));
        when(findTransactionByIdAndIdempotencyIdRepository.exec(anyLong(), anyLong())).thenReturn(transactionMock);
        when(findAccountByAccountNumberRepository.exec(anyInt())).thenReturn(buildMockAccount(true));
        when(saveAccountRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));

        // Use Cases
        fluxReversalTransactionUseCase = new ReversalTransactionUseCase(findTransactionByIdAndIdempotencyIdRepository, findAccountByAccountNumberRepository, saveAccountRepository);

        // Validations
        transactionExistsValidation = new TransactionExistsValidate(transactionRepository);
    }

    @Test
    void shouldReversalTransactionWithSuccess() {
        final var input = new ReversalTransactionInput(generateRandomIdTransaction(), generateIdempotencyId());
        final var source = new Source(input);

        final Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(fluxReversalTransactionUseCase);

        final List<IValidations> validations = List.of(transactionExistsValidation);

        final var facade = new GenericFacadeDelegate<>(useCases, validations);

        assertDoesNotThrow(() -> facade.exec(source));

        verify(transactionRepository, times(1)).exec(input.id(), input.idempotencyId());
        verify(findTransactionByIdAndIdempotencyIdRepository, times(1)).exec(input.id(), input.idempotencyId());
        verify(findAccountByAccountNumberRepository, times(1)).exec(anyInt());
        verify(saveAccountRepository, times(1)).exec(any(AccountOutput.class));
    }

    @Test
    void shouldReversalTransactionErrorTransactionNotExists() {
        when(transactionRepository.exec(anyLong(), anyLong())).thenReturn(Optional.empty());
        final var input = new ReversalTransactionInput(generateRandomIdTransaction(), generateIdempotencyId());
        final var source = new Source(input);

        final Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(fluxReversalTransactionUseCase);

        final List<IValidations> validations = List.of(transactionExistsValidation);

        final var facade = new GenericFacadeDelegate<>(useCases, validations);

        var transactionResponse = assertThrows(TransactionNotExistsException.class, () -> facade.exec(source));
        assertEquals("Transaction not found", transactionResponse.getMessage());

        verify(transactionRepository, times(1)).exec(input.id(), input.idempotencyId());
        verify(findTransactionByIdAndIdempotencyIdRepository, never()).exec(input.id(), input.idempotencyId());
        verify(findAccountByAccountNumberRepository, never()).exec(anyInt());
        verify(saveAccountRepository, never()).exec(any(Account.class));
    }
}
