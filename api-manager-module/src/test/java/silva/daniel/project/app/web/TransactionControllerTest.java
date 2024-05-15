package silva.daniel.project.app.web;

import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.ReversalTransactionAlreadyRefundedException;
import br.net.silva.business.exception.ReversalTransactionNotFoundException;
import br.net.silva.business.exception.TransactionDuplicateException;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.daniel.exception.ClientDeactivatedException;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.business.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.domain.account.request.RefundRequest;
import silva.daniel.project.app.domain.account.request.TransactionBatchRequest;
import silva.daniel.project.app.domain.account.service.TransactionService;
import silva.daniel.project.app.web.account.annotations.EnableTransactionPrepare;
import silva.daniel.project.app.web.account.transaction.RefundTransactionTestPrepare;
import silva.daniel.project.app.web.account.transaction.RegisterTransactionTestPrepare;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.QUEUE_ERROR_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.TRANSACTION_ALREADY_REFUNDED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.TRANSACTION_DUPLICATE_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.TRANSACTION_NOT_FOUND_MESSAGE;

@ActiveProfiles("unit")
@EnableTransactionPrepare
class TransactionControllerTest implements RequestBuilderCommons {

    @Autowired
    private RegisterTransactionTestPrepare registerTransactionTestPrepare;

    @Autowired
    private RefundTransactionTestPrepare refundTransactionTestPrepare;

    @MockBean
    private TransactionService transactionService;

    @Test
    void registerTransaction_WithValidData_ReturnsStatus200() throws Exception {
        registerTransactionTestPrepare.successPostAssert(buildBaseTransactionDebitBatchRequest(), status().isOk());
    }

    @ParameterizedTest
    @MethodSource("silva.daniel.project.app.commons.TransactionRequestBuilder#provideInvalidDataForRegisterTransaction")
    void registerTransaction_WithInvalidData_ReturnsStatus406(TransactionBatchRequest request) throws Exception {
        registerTransactionTestPrepare.failurePostAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void registerTransaction_WithClientNotExists_ReturnsStatus404() throws Exception {
        doThrow(new ClientNotExistsException("Client Not Found")).when(transactionService).registerTransaction(any(BatchTransactionInput.class));
        registerTransactionTestPrepare.failurePostAssert(buildBaseTransactionDebitBatchRequest(), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void registerTransaction_WithClientDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new ClientDeactivatedException(CLIENT_DEACTIVATED.getMessage())).when(transactionService).registerTransaction(any(BatchTransactionInput.class));
        registerTransactionTestPrepare.failurePostAssert(buildBaseTransactionDebitBatchRequest(), CLIENT_DEACTIVATED, status().isConflict());
    }

    @Test
    void registerTransaction_WithAccountNotExists_ReturnsStatus404() throws Exception {
        doThrow(new AccountNotExistsException(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(transactionService).registerTransaction(any(BatchTransactionInput.class));
        registerTransactionTestPrepare.failurePostAssert(buildBaseTransactionDebitBatchRequest(), ACCOUNT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void registerTransaction_WithAccountDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new AccountDeactivatedException(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage())).when(transactionService).registerTransaction(any(BatchTransactionInput.class));
        registerTransactionTestPrepare.failurePostAssert(buildBaseTransactionDebitBatchRequest(), ACCOUNT_ALREADY_DEACTIVATED_MESSAGE, status().isConflict());
    }

    @Test
    void registerTransaction_WithDuplicationTransaction_ReturnsStatus400() throws Exception {
        doThrow(new TransactionDuplicateException(TRANSACTION_DUPLICATE_MESSAGE.getMessage())).when(transactionService).registerTransaction(any(BatchTransactionInput.class));
        registerTransactionTestPrepare.failurePostAssert(buildBaseTransactionDebitBatchRequest(), TRANSACTION_DUPLICATE_MESSAGE, status().isBadRequest());
    }

    @Test
    void registerTransaction_WithFailRegisterInQueue_ReturnsStatus500() throws Exception {
        doThrow(new GenericException(QUEUE_ERROR_MESSAGE.getMessage())).when(transactionService).registerTransaction(any(BatchTransactionInput.class));
        registerTransactionTestPrepare.failurePostAssert(buildBaseTransactionDebitBatchRequest(), QUEUE_ERROR_MESSAGE, status().isInternalServerError());
    }

    @Test
    void refundTransaction_WithValidData_ReturnsStatus200() throws Exception {
        refundTransactionTestPrepare.successPostAssert(buildBaseTransactionRefundBatchRequest(), status().isOk());
    }

    @ParameterizedTest
    @MethodSource("silva.daniel.project.app.commons.TransactionRequestBuilder#provideInvalidDataForRefundTransaction")
    void refundTransaction_WithInvalidData_ReturnsStatus406(RefundRequest request) throws Exception {
        refundTransactionTestPrepare.failurePostAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void refundTransaction_WithClientNotExists_ReturnsStatus404() throws Exception {
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(transactionService).refundTransaction(any(ReversalTransactionInput.class));
        refundTransactionTestPrepare.failurePostAssert(buildBaseTransactionRefundBatchRequest(), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void refundTransaction_WithClientDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new ClientDeactivatedException(CLIENT_DEACTIVATED.getMessage())).when(transactionService).refundTransaction(any(ReversalTransactionInput.class));
        refundTransactionTestPrepare.failurePostAssert(buildBaseTransactionRefundBatchRequest(), CLIENT_DEACTIVATED, status().isConflict());
    }

    @Test
    void refundTransaction_WithAccountNotExists_ReturnsStatus404() throws Exception {
        doThrow(new AccountNotExistsException(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(transactionService).refundTransaction(any(ReversalTransactionInput.class));
        refundTransactionTestPrepare.failurePostAssert(buildBaseTransactionRefundBatchRequest(), ACCOUNT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void refundTransaction_WithAccountDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new AccountDeactivatedException(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage())).when(transactionService).refundTransaction(any(ReversalTransactionInput.class));
        refundTransactionTestPrepare.failurePostAssert(buildBaseTransactionRefundBatchRequest(), ACCOUNT_ALREADY_DEACTIVATED_MESSAGE, status().isConflict());
    }

    @Test
    void refundTransaction_WithTransactionNotExists_ReturnsStatus404() throws Exception {
        doThrow(new ReversalTransactionNotFoundException(TRANSACTION_NOT_FOUND_MESSAGE.getMessage())).when(transactionService).refundTransaction(any(ReversalTransactionInput.class));
        refundTransactionTestPrepare.failurePostAssert(buildBaseTransactionRefundBatchRequest(), TRANSACTION_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void refundTransaction_WithTransactionAlreadyRefunded_ReturnsStatus409() throws Exception {
        doThrow(new ReversalTransactionAlreadyRefundedException(TRANSACTION_ALREADY_REFUNDED_MESSAGE.getMessage())).when(transactionService).refundTransaction(any(ReversalTransactionInput.class));
        refundTransactionTestPrepare.failurePostAssert(buildBaseTransactionRefundBatchRequest(), TRANSACTION_ALREADY_REFUNDED_MESSAGE, status().isConflict());
    }
}
