package silva.daniel.project.app.web;

import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.daniel.exception.ClientDeactivatedException;
import br.net.silva.daniel.exception.ClientNotExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.domain.account.request.ActivateAccountRequest;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;
import silva.daniel.project.app.domain.account.service.AccountService;
import silva.daniel.project.app.web.account.ActivateAccountTestPrepare;
import silva.daniel.project.app.web.account.EditAgencyOfAccountPrepare;
import silva.daniel.project.app.web.account.GetAccountListTestPrepare;
import silva.daniel.project.app.web.account.GetInformationAccountTestPrepare;
import silva.daniel.project.app.web.account.annotations.EnableAccountPrepare;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;
import static silva.daniel.project.app.commons.MatcherCommons.ConditionalMatcher.or;
import static silva.daniel.project.app.commons.MatcherCommons.DuplicateMatcher.hasNotDuplicate;

@ActiveProfiles("unit")
@EnableAccountPrepare
class AccountControllerTest implements RequestBuilderCommons {

    @Autowired
    private EditAgencyOfAccountPrepare editAgencyOfAccountPrepare;

    @Autowired
    private GetInformationAccountTestPrepare getInformationAccountTestPrepare;

    @Autowired
    private GetAccountListTestPrepare getAccountListTestPrepare;

    @Autowired
    private ActivateAccountTestPrepare activateAccountTestPrepare;

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

    @Test
    void getInformationAccount_WithValidData_ReturnsStatus200AndAccountData() throws Exception {
        when(accountService.getAccountByCpf(any(GetInformationAccountInput.class))).thenReturn(getInformationAccountTestPrepare.buildResponseMockAccountInformation());
        getInformationAccountTestPrepare.successGetAssert(new Object[]{"12345678901"},
                                                          status().isOk(),
                                                          jsonPath("$.accountNumber").value(1234),
                                                          jsonPath("$.agency").value(1),
                                                          jsonPath("$.balance").value(1000.0),
                                                          jsonPath("$.status").value("ACTIVE"),
                                                          jsonPath("$.transactions[0].id").value(1L),
                                                          jsonPath("$.transactions[0].description").value("test"),
                                                          jsonPath("$.transactions[0].price").value(BigDecimal.valueOf(100)),
                                                          jsonPath("$.transactions[0].quantity").value(1),
                                                          jsonPath("$.transactions[0].type").value("DEBIT"),
                                                          jsonPath("$.transactions[0].originAccountNumber").value(1234),
                                                          jsonPath("$.transactions[0].destinationAccountNumber").value(5678),
                                                          jsonPath("$.transactions[0].idempotencyId").value(123L),
                                                          jsonPath("$.transactions[0].creditCardNumber").isEmpty(),
                                                          jsonPath("$.transactions[0].creditCardCvv").isEmpty()
        );
    }

    @Test
    void getInformationAccount_WithCpfNotExists_ReturnsStatus404() throws Exception {
        doThrow(new ClientNotExistsException("Client not Found")).when(accountService).getAccountByCpf(any(GetInformationAccountInput.class));
        getInformationAccountTestPrepare.failureGetAssert(new Object[]{"1234"}, CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void getInformationAccount_WithAccountNotExists_ReturnsStatus404() throws Exception {
        doThrow(new AccountNotExistsException("Account not Found")).when(accountService).getAccountByCpf(any(GetInformationAccountInput.class));
        getInformationAccountTestPrepare.failureGetAssert(new Object[]{"1234"}, ACCOUNT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void getAccountList_WithValidData_ReturnsAllAccounts() throws Exception {
        when(accountService.getAllAccountsByCpf(new GetInformationAccountInput("12345678901"))).thenReturn(getAccountListTestPrepare.buildAccounts());
        getAccountListTestPrepare.successGetAssert(new Object[]{"12345678901"},
                                                   status().isOk(),
                                                   jsonPath("$.accounts", hasSize(4)),
                                                   jsonPath("$.accounts[*].number").value(hasNotDuplicate()),
                                                   jsonPath("$.accounts[?(@.active == true)]").value(or(hasSize(0), hasSize(1)))
        );
    }

    @Test
    void activateAccount_WithValidData_ReturnsSuccess() throws Exception {
        activateAccountTestPrepare.successPostAssert(buildBaseActivateAccount(), status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDataOfActivateAccount")
    void activateAccount_WithInvalidData_ReturnsStatus406(ActivateAccountRequest request) throws Exception {
        activateAccountTestPrepare.failurePostAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void activateAccount_WithClientNotExists_ReturnsStatus404() throws Exception {
        doThrow(new ClientNotExistsException("Client not Found")).when(accountService).activateAccount(any(ActivateAccount.class));
        activateAccountTestPrepare.failurePostAssert(buildBaseActivateAccount(), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void activateAccount_WithClientDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new ClientDeactivatedException("Client is Deactivated")).when(accountService).activateAccount(any(ActivateAccount.class));
        activateAccountTestPrepare.failurePostAssert(buildBaseActivateAccount(), CLIENT_DEACTIVATED, status().isConflict());
    }

    @Test
    void activateAccount_WithAccountNotExists_ReturnsStatus404() throws Exception {
        doThrow(new AccountNotExistsException("Account not Found")).when(accountService).activateAccount(any(ActivateAccount.class));
        activateAccountTestPrepare.failurePostAssert(buildBaseActivateAccount(), ACCOUNT_NOT_FOUND_MESSAGE, status().isNotFound());
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

    private static Stream<Arguments> provideInvalidDataOfActivateAccount() {
        return Stream.of(
                Arguments.of(new ActivateAccountRequest(null, 1, 1234)),
                Arguments.of(new ActivateAccountRequest("", 1, 1234)),
                Arguments.of(new ActivateAccountRequest("999", 1, 1234)),
                Arguments.of(new ActivateAccountRequest("9999999999999999", 1, 1234)),
                Arguments.of(new ActivateAccountRequest("22233344455", null, 1234)),
                Arguments.of(new ActivateAccountRequest("22233344455", 1, null)),
                Arguments.of(new ActivateAccountRequest("22233344455", 1, -2)),
                Arguments.of(new ActivateAccountRequest("22233344455", -1, 123))
        );
    }
}