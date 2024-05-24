package silva.daniel.project.app.domain.account.gateway;

import br.net.silva.business.value_object.input.ReversalTransactionInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import silva.daniel.project.app.domain.account.repository.TransactionRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionGatewayTest {

    private TransactionGateway transactionGateway;

    @Mock
    private TransactionRepository repository;

    @BeforeEach
    void setUp() {
        transactionGateway = new TransactionGateway(repository);
    }

    @Test
    void findTransaction_WithTransactionNotFound_ReturnsOptionalEmpty() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        final var param = new ReversalTransactionInput("33344455566", 1L, 2L);
        final var sut = transactionGateway.findById(param);
        assertThat(sut).isEmpty();

        verify(repository, times(1)).findById(param.id());
    }
}