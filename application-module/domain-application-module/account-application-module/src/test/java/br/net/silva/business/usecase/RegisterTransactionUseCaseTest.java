package br.net.silva.business.usecase;

import br.net.silva.business.exception.TransactionDuplicateException;
import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
final class RegisterTransactionUseCaseTest {

    private RegisterTransactionUseCase useCase;

    @Mock
    private SaveApplicationBaseGateway<BatchTransactionInput> saveTransactionRepository;

    @BeforeEach
    void setUp() {
        useCase = new RegisterTransactionUseCase(saveTransactionRepository);
    }

    @Test
    void registerTransaction_WithValidData_RegisterWithSuccess() {
        final var batchInput = buildBatchTransactionMock(List.of(buildTransactionDebitMock()));
        final var source = Source.of(batchInput);
        assertThatCode(() -> useCase.exec(source)).doesNotThrowAnyException();

        verify(saveTransactionRepository, times(1)).save(any());
    }

    @Test
    void registerTransaction_WithDuplicatedTransaction_ReturnsException() {
        final var batchInput = buildBatchTransactionMock(List.of(buildTransactionDebitMock(), buildTransactionDebitMock()));
        final var source = Source.of(batchInput);
        assertThatThrownBy(() -> useCase.exec(source))
                .isInstanceOf(TransactionDuplicateException.class)
                .hasMessage("Transaction has 2 or more equals transactions.");

        verify(saveTransactionRepository, never()).save(any(BatchTransactionInput.class));
    }

    private static BatchTransactionInput buildBatchTransactionMock(List<TransactionInput> transactions) {
        final var sourceAccount = new AccountInput(1, 45678, "978534");
        final var destinyAccount = new AccountInput(2, 45699, "978500");
        return new BatchTransactionInput(
                sourceAccount,
                destinyAccount,
                transactions
        );
    }

    private static TransactionInput buildTransactionDebitMock() {
        return new TransactionInput(1L, "Compra de teste", BigDecimal.valueOf(100), 1, TransactionTypeEnum.DEBIT, 123L, null, null);
    }
}