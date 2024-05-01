package silva.daniel.project.app.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.domain.transaction.service.TransactionService;
import silva.daniel.project.app.web.account.annotations.EnableTransactionPrepare;
import silva.daniel.project.app.web.account.transaction.RegisterTransactionTestPrepare;

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
        registerTransactionTestPrepare.successPostAssert(buildBaseTransactionDebitBatchRequest(), status().isOk());
    }
}
