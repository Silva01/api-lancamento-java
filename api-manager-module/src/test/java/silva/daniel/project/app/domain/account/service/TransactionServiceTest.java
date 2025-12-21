package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.exception.AccountNotExistsExceptionDomain;
import br.net.silva.business.exception.ReversalTransactionAlreadyRefundedExceptionDomain;
import br.net.silva.business.exception.TransactionNotExistsExceptionDomain;
import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.exception.ClientDeactivatedExceptionDomain;
import br.net.silva.daniel.exception.ClientNotExistsExceptionDomain;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import static silva.daniel.project.app.commons.FailureMessageEnum.TRANSACTION_ALREADY_REFUNDED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.TRANSACTION_NOT_FOUND_MESSAGE;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService service;

    @Mock
    private TransactionFacade transactionFacade;

    @Mock
    private GenericFacadeDelegate facade;

    @Test
    void registerTransaction_WithValidData_ProcessWithSuccess() throws DomainGenericException {
        when(transactionFacade.registerTransaction()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.registerTransaction(createMockBatchTransactionInput()))
                .doesNotThrowAnyException();
    }

    @Test
    void refundTransaction_WithValidData_ProcessWithSuccess() throws DomainGenericException {
        when(transactionFacade.refundTransaction()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .doesNotThrowAnyException();
    }

    @Test
    void refundTransaction_WithClientNotExists_ReturnsClientNotExistsException() throws DomainGenericException {
        when(transactionFacade.refundTransaction()).thenReturn(facade);
        doThrow(new ClientNotExistsExceptionDomain(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(ClientNotExistsExceptionDomain.class)
                .hasMessage(CLIENT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void refundTransaction_WithClientDeactivated_ReturnsClientDeactivatedException() throws DomainGenericException {
        when(transactionFacade.refundTransaction()).thenReturn(facade);
        doThrow(new ClientDeactivatedExceptionDomain(CLIENT_DEACTIVATED.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(ClientDeactivatedExceptionDomain.class)
                .hasMessage(CLIENT_DEACTIVATED.getMessage());
    }

    @Test
    void refundTransaction_WithAccountNotExists_ReturnsAccountNotExistsException() throws DomainGenericException {
        when(transactionFacade.refundTransaction()).thenReturn(facade);
        doThrow(new AccountNotExistsExceptionDomain(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(AccountNotExistsExceptionDomain.class)
                .hasMessage(ACCOUNT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void refundTransaction_WithAccountDeactivated_ReturnsAccountDeactivatedException() throws DomainGenericException {
        when(transactionFacade.refundTransaction()).thenReturn(facade);
        doThrow(new AccountNotExistsExceptionDomain(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(AccountNotExistsExceptionDomain.class)
                .hasMessage(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage());
    }

    @Test
    void refundTransaction_WithTransactionNotExists_ReturnsTransactionNotExistsException() throws DomainGenericException {
        when(transactionFacade.refundTransaction()).thenReturn(facade);
        doThrow(new TransactionNotExistsExceptionDomain(TRANSACTION_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(TransactionNotExistsExceptionDomain.class)
                .hasMessage(TRANSACTION_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void refundTransaction_WithTransactionAlreadyRefunded_ReturnsReversalTransactionAlreadyRefundedException() throws DomainGenericException {
        when(transactionFacade.refundTransaction()).thenReturn(facade);
        doThrow(new ReversalTransactionAlreadyRefundedExceptionDomain(TRANSACTION_ALREADY_REFUNDED_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.refundTransaction(createMockReversalTransactionInput()))
                .isInstanceOf(ReversalTransactionAlreadyRefundedExceptionDomain.class)
                .hasMessage(TRANSACTION_ALREADY_REFUNDED_MESSAGE.getMessage());
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