package silva.daniel.project.app.web;

import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.daniel.exception.ClientNotExistsException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
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

    @Test
    void editAgencyOfAccount_WithClientNotExists_ReturnsStatus404() throws Exception {
        doThrow(new ClientNotExistsException("Client not Found")).when(accountService).editAgencyOfAccount(any(ChangeAgencyInput.class));
        editAgencyOfAccountPrepare.failurePutAssert(buildBaseEditAgencyOfAccountRequest(), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void editAgencyOfAccount_WithAccountNotExists_ReturnsStatus404() throws Exception {
        doThrow(new AccountNotExistsException("Account not Found")).when(accountService).editAgencyOfAccount(any(ChangeAgencyInput.class));
        editAgencyOfAccountPrepare.failurePutAssert(buildBaseEditAgencyOfAccountRequest(), ACCOUNT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void editAgencyOfAccount_WithAlreadyAccountWithNewAgency_ReturnsStatus409() throws Exception {
        doThrow(new AccountAlreadyExistsForNewAgencyException(ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE.getMessage())).when(accountService).editAgencyOfAccount(any(ChangeAgencyInput.class));
        editAgencyOfAccountPrepare.failurePutAssert(buildBaseEditAgencyOfAccountRequest(), ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE, status().isConflict());
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