package br.net.silva.business.usecase;

import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReversalTransactionUseCaseTest extends AbstractAccountBuilder {

    @Mock
    private ApplicationBaseGateway<ReversalTransactionInput> baseGateway;

    private ReversalTransactionUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ReversalTransactionUseCase(baseGateway);
    }

    @Test
    void shouldReverseTransactionWithSuccess() {
        var input = new ReversalTransactionInput("00099988877", generateRandomIdTransaction(), generateIdempotencyId());
        when(baseGateway.save(any(ReversalTransactionInput.class))).thenReturn(input);
        var source = new Source(input);

        assertDoesNotThrow(() -> useCase.exec(source));

        verify(baseGateway, times(1)).save(any(ReversalTransactionInput.class));
    }

}