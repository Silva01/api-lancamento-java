package silva.daniel.project.app.commons;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import org.junit.jupiter.params.provider.Arguments;
import silva.daniel.project.app.domain.account.request.AccountTransactionRequest;
import silva.daniel.project.app.domain.account.request.TransactionBatchRequest;
import silva.daniel.project.app.domain.account.request.TransactionRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public final class TransactionRequestBuilder {

    public static Stream<Arguments> provideInvalidDataForRegisterTransaction() {
        return Stream.of(
                Arguments.of(new TransactionBatchRequest(
                        new AccountTransactionRequest("",
                                                      1,
                                                      12345),
                        new AccountTransactionRequest("99900099900",
                                                      1,
                                                      12345),
                        List.of(new TransactionRequest(
                                "Compra no debito",
                                BigDecimal.valueOf(100),
                                1,
                                TransactionTypeEnum.DEBIT,
                                123L,
                                null,
                                null
                        )))),
                Arguments.of(new TransactionBatchRequest(
                        new AccountTransactionRequest("99900099900",
                                                      1,
                                                      12345),
                        new AccountTransactionRequest("",
                                                      1,
                                                      12345),
                        List.of(new TransactionRequest(
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
                                new AccountTransactionRequest(null,
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest(null,
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("999888777",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("999888777",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("9998887770088888888",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("9998887770088888888",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              null,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99988877700",
                                                              null,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              -1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99988877700",
                                                              -1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              null),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              null),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              0),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              0),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              -12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              -12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                null,
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
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
                                new AccountTransactionRequest("99988877700",
                                                              1,
                                                              12345),
                                new AccountTransactionRequest("99900099900",
                                                              1,
                                                              12345),
                                List.of(new TransactionRequest(
                                        "Compra no debito",
                                        BigDecimal.valueOf(100),
                                        1,
                                        TransactionTypeEnum.CREDIT,
                                        123L,
                                        "1234567890123456",
                                        -1
                                )))),
                Arguments.of(new TransactionBatchRequest(
                        new AccountTransactionRequest("99988877700",
                                                      1,
                                                      12345),
                        new AccountTransactionRequest("99900099900",
                                                      1,
                                                      12345),
                        Collections.emptyList()))
        );
    }
}
