package silva.daniel.project.app.web;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import org.junit.jupiter.api.Test;
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
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("unit")
@EnableTransactionPrepare
class TransactionControllerTest implements RequestBuilderCommons {

    @Autowired
    private RegisterTransactionTestPrepare registerTransactionTestPrepare;

    @MockBean
    private TransactionService transactionService;

    @Test
    void registerTransaction_WithValidData_ReturnsStatus200() throws Exception {
        final var request = new TransactionBatchRequest(
                "99988877700",
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
                ))
        );

        registerTransactionTestPrepare.successPostAssert(request, status().isOk());
    }
}
