package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.TransactionNotExistsException;
import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.exception.ClientDeactivatedException;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import silva.daniel.project.app.commons.FailureMessageEnum;
import silva.daniel.project.app.service.FluxService;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.TRANSACTION_NOT_FOUND_MESSAGE;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService service;

    @Mock
    private FluxService fluxService;

    @Mock
    private GenericFacadeDelegate facade;

    @Test
    void registerTransaction_WithValidData_ProcessWithSuccess() throws GenericException {
        when(fluxService.fluxRegisterTransaction()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.registerTransaction(createMockBatchTransactionInput()))
                .doesNotThrowAnyException();
    }

    @Test
    void refundTransaction_WithValidData_ProcessWithSuccess() throws GenericException {
        when(fluxService.fluxRefundTransaction()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .doesNotThrowAnyException();
    }

    @Test
    void refundTransaction_WithClientNotExists_ReturnsClientNotExistsException() throws GenericException {
        when(fluxService.fluxRefundTransaction()).thenReturn(facade);
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(ClientNotExistsException.class)
                .hasMessage(CLIENT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void refundTransaction_WithClientDeactivated_ReturnsClientDeactivatedException() throws GenericException {
        when(fluxService.fluxRefundTransaction()).thenReturn(facade);
        doThrow(new ClientDeactivatedException(CLIENT_DEACTIVATED.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(ClientDeactivatedException.class)
                .hasMessage(CLIENT_DEACTIVATED.getMessage());
    }

    @Test
    void refundTransaction_WithAccountNotExists_ReturnsAccountNotExistsException() throws GenericException {
        when(fluxService.fluxRefundTransaction()).thenReturn(facade);
        doThrow(new AccountNotExistsException(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage(ACCOUNT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void refundTransaction_WithAccountDeactivated_ReturnsAccountDeactivatedException() throws GenericException {
        when(fluxService.fluxRefundTransaction()).thenReturn(facade);
        doThrow(new AccountNotExistsException(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage());
    }


    @Test
    void refundTransaction_WithTransactionNotExists_ReturnsNotExistsException() throws GenericException {
        when(fluxService.fluxRefundTransaction()).thenReturn(facade);
        doThrow(new TransactionNotExistsException(TRANSACTION_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(TransactionNotExistsException.class)
                .hasMessage(TRANSACTION_NOT_FOUND_MESSAGE.getMessage());
    }

    private static BatchTransactionInput createMockBatchTransactionInput() {
        return new BatchTransactionInput(
                new AccountInput(1234, 1, "99988877766"),
                new AccountInput(4321, 2, "99988877700"),
                List.of(
                        new TransactionInput(
                                1L,
                                "Test",
                                BigDecimal.valueOf(1000),
                                1,
                                TransactionTypeEnum.DEBIT,
                                1234L,
                                null,
                                null
                        )
                )
        );
    }

    private static ReversalTransactionInput createMockReversalTransactionInput() {
        return new ReversalTransactionInput("99988877766", 1L, 1234L);
    }
}