package silva.daniel.project.app.web;

import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.business.value_object.input.CreateNewAccountByCpfDTO;
import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.daniel.exception.ClientDeactivatedException;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.business.exception.PasswordDivergentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.domain.account.request.ActivateAccountRequest;
import silva.daniel.project.app.domain.account.request.ChangePasswordRequest;
import silva.daniel.project.app.domain.account.request.DeactivateAccountRequest;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;
import silva.daniel.project.app.domain.account.request.NewAccountRequest;
import silva.daniel.project.app.domain.account.service.AccountService;
import silva.daniel.project.app.web.account.ActivateAccountTestPrepare;
import silva.daniel.project.app.web.account.ChangePasswordForAccountTestPrepare;
import silva.daniel.project.app.web.account.CreateAccountTestPrepare;
import silva.daniel.project.app.web.account.DeactivateAccountTestPrepare;
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
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_ACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_WITH_PASSWORD_DIFFERENT;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_ACCOUNT_ACTIVE;
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

    @Autowired
    private DeactivateAccountTestPrepare deactivateAccountTestPrepare;

    @Autowired
    private ChangePasswordForAccountTestPrepare changePasswordForAccountTestPrepare;

    @Autowired
    private CreateAccountTestPrepare createAccountTestPrepare;

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

    @Test
    void activateAccount_WithAccountAlreadyActivated_ReturnsStatus409() throws Exception {
        doThrow(new AccountAlreadyActiveException("Account already active")).when(accountService).activateAccount(any(ActivateAccount.class));
        activateAccountTestPrepare.failurePostAssert(buildBaseActivateAccount(), ACCOUNT_ALREADY_ACTIVATED_MESSAGE, status().isConflict());
    }

    @Test
    void deactivateAccount_WithValidData_ReturnsStatus200() throws Exception {
        deactivateAccountTestPrepare.successPostAssert(buildBaseDeactivateAccount(), status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDataOfDeactivateAccount")
    void deactivateAccount_WithInvalidData_ReturnsStatus406(DeactivateAccountRequest request) throws Exception {
        deactivateAccountTestPrepare.failurePostAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void deactivateAccount_WithClientNotExists_ReturnsStatus404() throws Exception {
        doThrow(new ClientNotExistsException("Client not Found")).when(accountService).deactivateAccount(any(DeactivateAccount.class));
        deactivateAccountTestPrepare.failurePostAssert(buildBaseDeactivateAccount(), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void deactivateAccount_WithClientDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new ClientDeactivatedException("Client is Deactivated")).when(accountService).deactivateAccount(any(DeactivateAccount.class));
        deactivateAccountTestPrepare.failurePostAssert(buildBaseDeactivateAccount(), CLIENT_DEACTIVATED, status().isConflict());
    }

    @Test
    void deactivateAccount_WithAccountNotExists_ReturnsStatus404() throws Exception {
        doThrow(new AccountNotExistsException("Account not Found")).when(accountService).deactivateAccount(any(DeactivateAccount.class));
        deactivateAccountTestPrepare.failurePostAssert(buildBaseDeactivateAccount(), ACCOUNT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void deactivateAccount_WithAlreadyAccountDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new AccountDeactivatedException("Account is Deactivated")).when(accountService).deactivateAccount(any(DeactivateAccount.class));
        deactivateAccountTestPrepare.failurePostAssert(buildBaseDeactivateAccount(), ACCOUNT_ALREADY_DEACTIVATED_MESSAGE, status().isConflict());
    }

    @Test
    void changePassword_WithValidData_ReturnsStatus200() throws Exception {
        changePasswordForAccountTestPrepare.successPutAssert(buildBaseCreateNewPasswordForAccount(), status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDataOfChangePassword")
    void changePassword_WithInvalidData_ReturnsStatus406(ChangePasswordRequest request) throws Exception {
        changePasswordForAccountTestPrepare.failurePutAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void changePassword_WithClientNotExists_ReturnsStatus404() throws Exception {
        doThrow(new ClientNotExistsException("Client not Found")).when(accountService).changePassword(any(ChangePasswordDTO.class));
        changePasswordForAccountTestPrepare.failurePutAssert(buildBaseCreateNewPasswordForAccount(), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void changePassword_WithClientDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new ClientDeactivatedException("Client is Deactivated")).when(accountService).changePassword(any(ChangePasswordDTO.class));
        changePasswordForAccountTestPrepare.failurePutAssert(buildBaseCreateNewPasswordForAccount(), CLIENT_DEACTIVATED, status().isConflict());
    }

    @Test
    void changePassword_WithAccountNotExists_ReturnsStatus404() throws Exception {
        doThrow(new AccountNotExistsException("Account not Found")).when(accountService).changePassword(any(ChangePasswordDTO.class));
        changePasswordForAccountTestPrepare.failurePutAssert(buildBaseCreateNewPasswordForAccount(), ACCOUNT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void changePassword_WithAccountDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new AccountDeactivatedException("Account is Deactivated")).when(accountService).changePassword(any(ChangePasswordDTO.class));
        changePasswordForAccountTestPrepare.failurePutAssert(buildBaseCreateNewPasswordForAccount(), ACCOUNT_ALREADY_DEACTIVATED_MESSAGE, status().isConflict());
    }

    @Test
    void changePassword_WithPasswordDifferentThatRegistered_ReturnsStatus400() throws Exception {
        doThrow(new PasswordDivergentException("Password is different")).when(accountService).changePassword(any(ChangePasswordDTO.class));
        changePasswordForAccountTestPrepare.failurePutAssert(buildBaseCreateNewPasswordForAccount(), ACCOUNT_WITH_PASSWORD_DIFFERENT, status().isBadRequest());
    }

    @Test
    void createAccount_WithValidData_ReturnsAgencyAndAccountNumberThatNewAccount() throws Exception {
        when(accountService.createNewAccount(any(CreateNewAccountByCpfDTO.class))).thenReturn(createAccountTestPrepare.buildNewAccountResponse());
        createAccountTestPrepare.successPostAssert(buildBaseNewAccountRequest(),
                                                   status().isCreated(),
                                                   jsonPath("$.agency").value(1),
                                                   jsonPath("$.accountNumber").value(1234));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDataOfCreateAccount")
    void createAccount_WithInvalidData_ReturnsStatus406(NewAccountRequest request) throws Exception {
        createAccountTestPrepare.failurePostAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void createAccount_WithClientNotExists_ReturnsStatus404() throws Exception {
        when(accountService.createNewAccount(any(CreateNewAccountByCpfDTO.class))).thenThrow(new ClientNotExistsException("Client not Found"));
        createAccountTestPrepare.failurePostAssert(buildBaseNewAccountRequest(), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void createAccount_WithClientDeactivated_ReturnsStatus409() throws Exception {
        when(accountService.createNewAccount(any(CreateNewAccountByCpfDTO.class))).thenThrow(new ClientDeactivatedException("Client is Deactivated"));
        createAccountTestPrepare.failurePostAssert(buildBaseNewAccountRequest(), CLIENT_DEACTIVATED, status().isConflict());
    }

    @Test
    void createAccount_WithClientWithAccountActive_ReturnsStatus409() throws Exception {
        when(accountService.createNewAccount(any(CreateNewAccountByCpfDTO.class))).thenThrow(new AccountAlreadyActiveException("Account already exists for the CPF informed"));
        createAccountTestPrepare.failurePostAssert(buildBaseNewAccountRequest(), CLIENT_ALREADY_ACCOUNT_ACTIVE, status().isConflict());
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

    private static Stream<Arguments> provideInvalidDataOfDeactivateAccount() {
        return Stream.of(
                Arguments.of(new DeactivateAccountRequest(null, 123456, 1234)),
                Arguments.of(new DeactivateAccountRequest("", 123456, 1234)),
                Arguments.of(new DeactivateAccountRequest("999", 123456, 1234)),
                Arguments.of(new DeactivateAccountRequest("9999999999999999", 123456, 1234)),
                Arguments.of(new DeactivateAccountRequest("22233344455", null, 1234)),
                Arguments.of(new DeactivateAccountRequest("22233344455", 0, 1234)),
                Arguments.of(new DeactivateAccountRequest("22233344455", 123456, null)),
                Arguments.of(new DeactivateAccountRequest("22233344455", 123456, 0))
        );
    }

    private static Stream<Arguments> provideInvalidDataOfChangePassword() {
        return Stream.of(
                Arguments.of(new ChangePasswordRequest(null, 123456, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(0, 123456, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(-1, 123456, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, null, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 0, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, -233, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, null, "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "999", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "9999999999999", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", null, "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "12345", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "123456", null)),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "123456", "")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "123456", "12345"))
        );
    }

    private static Stream<Arguments> provideInvalidDataOfCreateAccount() {
        return Stream.of(
                Arguments.of(new NewAccountRequest(null, 123456, "123444")),
                Arguments.of(new NewAccountRequest("", 123456, "123444")),
                Arguments.of(new NewAccountRequest("999", 123456, "123444")),
                Arguments.of(new NewAccountRequest("9999999999999999", 123456, "123444")),
                Arguments.of(new NewAccountRequest("22233344455", null, "123444")),
                Arguments.of(new NewAccountRequest("22233344455", 0, "123444")),
                Arguments.of(new NewAccountRequest("22233344455", 123456, null)),
                Arguments.of(new NewAccountRequest("22233344455", 123456, ""))
        );
    }
}