package silva.daniel.project.app.web;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.domain.transaction.request.TransactionBatchRequest;
import silva.daniel.project.app.domain.transaction.request.TransactionRequest;
import silva.daniel.project.app.domain.transaction.service.TransactionService;
import silva.daniel.project.app.web.account.annotations.EnableTransactionPrepare;
import silva.daniel.project.app.web.account.transaction.RegisterTransactionTestPrepare;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;

@ActiveProfiles("unit")
@EnableTransactionPrepare
class TransactionControllerTest implements RequestBuilderCommons {

    @Autowired
    private RegisterTransactionTestPrepare registerTransactionTestPrepare;

    @MockBean
    private TransactionService transactionService;

    @Test
    void registerTransaction_WithValidData_ReturnsStatus200() throws Exception {
        registerTransactionTestPrepare.successPostAssert(buildBaseTransactionDebitBatchRequest(), status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDataForRegisterTransaction")
    void registerTransaction_WithInvalidData_ReturnsSTatus406(TransactionBatchRequest request) throws Exception {
        registerTransactionTestPrepare.failurePostAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    private static Stream<Arguments> provideInvalidDataForRegisterTransaction() {
        return Stream.of(
                Arguments.of(new TransactionBatchRequest(
                        "",
                        1,
                        12345,
                        List.of(new TransactionRequest(
                                123,
                                "Compra no debito",
                                BigDecimal.valueOf(100),
                                1,
                                TransactionTypeEnum.DEBIT,
                                123L,
                                null,
                                null
                        )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                null,
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "999888777",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "9998887770088888888",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                null,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                -1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                null,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                0,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                -12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        null,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        0,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        -123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(-100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        null,
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        null,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        -1,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        0,
                                        TransactionTypeEnum.DEBIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        null,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        null,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        -123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.DEBIT,
                                        0L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.CREDIT,
                                        123L,
                                        null,
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.CREDIT,
                                        123L,
                                        "",
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.CREDIT,
                                        123L,
                                        "1234567890123456",
                                        null
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.CREDIT,
                                        123L,
                                        "1234567890123456",
                                        0
                                )))),
                Arguments.of(
                        new TransactionBatchRequest(
                                "99988877700",
                                1,
                                12345,
                                List.of(new TransactionRequest(
                                        123,
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.CREDIT,
                                        123L,
                                        "1234567890123456",
                                        -1
                                )))),
                Arguments.of(new TransactionBatchRequest(
                        "99988877700",
                        1,
                        12345,
                        Collections.emptyList()))
        );
    }
}
