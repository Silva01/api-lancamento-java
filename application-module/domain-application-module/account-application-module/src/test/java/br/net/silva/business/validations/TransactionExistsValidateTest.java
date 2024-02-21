package br.net.silva.business.validations;

import br.net.silva.business.exception.TransactionNotExistsException;
import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TransactionExistsValidateTest extends AbstractAccountBuilder {

    private TransactionExistsValidate transactionExistsValidate;

    @Mock
    private Repository<Optional<Transaction>> transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(transactionRepository.exec(anyLong(), anyLong())).thenReturn(Optional.of(buildMockTransaction(TransactionTypeEnum.DEBIT)));

        transactionExistsValidate = new TransactionExistsValidate(transactionRepository);
    }

    @Test
    void shouldValidateIfTransactionExistsWithSuccess() {
        var input = new ReversalTransactionInput(generateRandomIdTransaction(), generateIdempotencyId());
        var source = new Source(input);

        assertDoesNotThrow(() -> transactionExistsValidate.validate(source));
        verify(transactionRepository, times(1)).exec(input.id(), input.idempotencyId());
    }

    @Test
    void shouldErrorAtValidateIfTransactionExistsTransactionNotFound() {
        when(transactionRepository.exec(anyLong(), anyLong())).thenReturn(Optional.empty());
        var input = new ReversalTransactionInput(generateRandomIdTransaction(), generateIdempotencyId());
        var source = new Source(input);

        var exceptionResponse = assertThrows(TransactionNotExistsException.class, () -> transactionExistsValidate.validate(source));
        assertEquals("Transaction not found", exceptionResponse.getMessage());

        verify(transactionRepository, times(1)).exec(input.id(), input.idempotencyId());
    }

}