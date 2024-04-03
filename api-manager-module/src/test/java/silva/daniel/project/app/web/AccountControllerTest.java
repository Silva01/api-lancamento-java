package silva.daniel.project.app.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;
import silva.daniel.project.app.domain.account.service.AccountService;
import silva.daniel.project.app.web.account.EditAgencyOfAccountPrepare;
import silva.daniel.project.app.web.account.annotations.EnableAccountPrepare;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;

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

    @ParameterizedTest
    @MethodSource("provideInvalidDataOfEditAgencyOfAccount")
    void editAgencyOfAccount_WithValidData_ReturnsStatus406(EditAgencyOfAccountRequest request) throws Exception {
        editAgencyOfAccountPrepare.failurePutAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    private static Stream<Arguments> provideInvalidDataOfEditAgencyOfAccount() {
        return Stream.of(
                Arguments.of(new EditAgencyOfAccountRequest(null, 123456, 1234, 1234)),
                Arguments.of(new EditAgencyOfAccountRequest("", 123456, 1234, 1234)),
                Arguments.of(new EditAgencyOfAccountRequest("999", 123456, 1234, 1234)),
                Arguments.of(new EditAgencyOfAccountRequest("9999999999999999", 123456, 1234, 1234)),
                Arguments.of(new EditAgencyOfAccountRequest("22233344455", null, 1234, 1234)),
                Arguments.of(new EditAgencyOfAccountRequest("22233344455", 1234, null, 1234)),
                Arguments.of(new EditAgencyOfAccountRequest("22233344455", 1234, 1234, null))
        );
    }
}