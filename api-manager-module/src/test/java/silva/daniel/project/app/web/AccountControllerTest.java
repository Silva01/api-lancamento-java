package silva.daniel.project.app.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.domain.account.service.AccountService;
import silva.daniel.project.app.web.account.EditAgencyOfAccountPrepare;
import silva.daniel.project.app.web.account.annotations.EnableAccountPrepare;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("unit")
@EnableAccountPrepare
class AccountControllerTest implements RequestBuilderCommons {

    @Autowired
    private EditAgencyOfAccountPrepare editAgencyOfAccountPrepare;

    @MockBean
    private AccountService accountService;

    @Test
    void editAgencyOfAccount_WithValidData_ReturnsStatus200() throws Exception {
        editAgencyOfAccountPrepare.successPutAssert(buildBaseEditAgencyOfAccountRequest(), status().isOk());
    }
}