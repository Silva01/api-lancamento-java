package br.net.silva.business.usecase;

import br.net.silva.business.exception.TransactionNotExistsException;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindTransactionUseCaseTest {

    @Mock
    private ApplicationBaseGateway<TransactionOutput> baseGateway;

    private FindTransactionUseCase findTransactionUseCase;

    @BeforeEach
    void setUp() {
        findTransactionUseCase = new FindTransactionUseCase(baseGateway);
    }

    @Test
    void findTransaction_WithById_ReturnsTransaction() throws GenericException {
        final var reversal = new ReversalTransactionInput("1234567890", 1L, 2L);
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockTransaction()));

        final var sut = findTransactionUseCase.exec(Source.of(reversal));
        assertThat(sut).isNotNull();
        assertThat(sut.id()).isEqualTo(buildMockTransaction().id());
        assertThat(sut.description()).isEqualTo(buildMockTransaction().description());
        assertThat(sut.price()).isEqualTo(buildMockTransaction().price());
        assertThat(sut.quantity()).isEqualTo(buildMockTransaction().quantity());
        assertThat(sut.type()).isEqualTo(buildMockTransaction().type());
        assertThat(sut.originAccountNumber()).isEqualTo(buildMockTransaction().originAccountNumber());
        assertThat(sut.destinationAccountNumber()).isEqualTo(buildMockTransaction().destinationAccountNumber());
        assertThat(sut.idempotencyId()).isEqualTo(buildMockTransaction().idempotencyId());
        assertThat(sut.creditCardNumber()).isEqualTo(buildMockTransaction().creditCardNumber());
        assertThat(sut.creditCardCvv()).isEqualTo(buildMockTransaction().creditCardCvv());
    }

    @Test
    void findTransaction_WithTransactionNotExists_ReturnsTransactionNotExistsException() {
        final var reversal = new ReversalTransactionInput("1234567890", 1L, 2L);
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> findTransactionUseCase.exec(Source.of(reversal)))
                .isInstanceOf(TransactionNotExistsException.class)
                .hasMessage("Transaction not found");
    }

    private static TransactionOutput buildMockTransaction() {
        return new TransactionOutput(
                1L,
                "test",
                BigDecimal.valueOf(100),
                1,
                TransactionTypeEnum.CREDIT,
                333,
                222,
                4444L,
                "00000",
                321);
    }
}
