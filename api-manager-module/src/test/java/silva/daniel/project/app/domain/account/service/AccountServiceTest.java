package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.business.value_object.input.CreateNewAccountByCpfDTO;
import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.business.value_object.output.NewAccountResponse;
import br.net.silva.daniel.exception.ClientDeactivatedException;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.PasswordDivergentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import silva.daniel.project.app.commons.InputBuilderCommons;
import silva.daniel.project.app.service.FluxService;

import java.math.BigDecimal;

import static br.net.silva.business.enums.AccountStatusEnum.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_ACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_WITH_PASSWORD_DIFFERENT;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest implements InputBuilderCommons {

    @InjectMocks
    private AccountService service;

    @Mock
    private FluxService fluxService;

    @Mock
    private GenericFacadeDelegate facade;

    @Test
    void editAgencyOfAcount_withValidData_ReturnsSuccess() throws Exception {
        when(fluxService.fluxEditAgencyOfAccount()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service
                .editAgencyOfAccount(buildNewBaseChangeAgencyInput()))
                .doesNotThrowAnyException();
    }

    @Test
    void editAgencyOfAccount_WithClientNotExists_ReturnsException() throws Exception {
        when(fluxService.fluxEditAgencyOfAccount()).thenReturn(facade);
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.editAgencyOfAccount(buildNewBaseChangeAgencyInput()))
                .isInstanceOf(ClientNotExistsException.class)
                .hasMessage(CLIENT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void editAgencyOfAccount_WithAccountNotExists_ReturnsException() throws Exception {
        when(fluxService.fluxEditAgencyOfAccount()).thenReturn(facade);
        doThrow(new AccountNotExistsException(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.editAgencyOfAccount(buildNewBaseChangeAgencyInput()))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage(ACCOUNT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void editAgencyOfAccount_WithNewAgencyNumberAlreadyInOtherAccount_ReturnsException() throws Exception {
        when(fluxService.fluxEditAgencyOfAccount()).thenReturn(facade);
        doThrow(new AccountAlreadyExistsForNewAgencyException(ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.editAgencyOfAccount(buildNewBaseChangeAgencyInput()))
                .isInstanceOf(AccountAlreadyExistsForNewAgencyException.class)
                .hasMessage(ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE.getMessage());
    }

    @Test
    void getInformationAccount_WithCpfValid_ReturnsSuccessAndAccountData() throws Exception {
        when(fluxService.fluxGetAccountByCpf()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> {
            var source = ((Source) argumentsOnMock.getArgument(0));
            ((GetInformationAccountOutput) source.output()).setAccountNumber(123456);
            ((GetInformationAccountOutput) source.output()).setAgency(1234);
            ((GetInformationAccountOutput) source.output()).setStatus(ACTIVE.name());
            ((GetInformationAccountOutput) source.output()).setBalance(BigDecimal.valueOf(1000));
            ((GetInformationAccountOutput) source.output()).setHaveCreditCard(true);
            return null;
        }).when(facade).exec(any(Source.class));

        final var response = service.getAccountByCpf(new GetInformationAccountInput("12345678901"));
        assertThat(response).isNotNull();
        assertThat(response.getAccountNumber()).isEqualTo(123456);
        assertThat(response.getAgency()).isEqualTo(1234);
        assertThat(response.getStatus()).isEqualTo(ACTIVE.name());
        assertThat(response.getBalance()).isEqualTo(BigDecimal.valueOf(1000));
        assertThat(response.isHaveCreditCard()).isTrue();
    }

    @Test
    void getInformationAccount_WithCpfNotExists_ReturnsException() throws Exception {
        when(fluxService.fluxGetAccountByCpf()).thenReturn(facade);
        doThrow(new AccountNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.getAccountByCpf(new GetInformationAccountInput("12345678901")))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage(CLIENT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void getInformationAccount_WithAccountNotExists_ReturnsException() throws Exception {
        when(fluxService.fluxGetAccountByCpf()).thenReturn(facade);
        doThrow(new AccountNotExistsException(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.getAccountByCpf(new GetInformationAccountInput("12345678901")))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage(ACCOUNT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void getAllAccount_WithValidData_ReturnsAccounts() throws Exception {
        when(fluxService.fluxGetAllAccount()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.getAllAccountsByCpf(new GetInformationAccountInput("123")))
                .doesNotThrowAnyException();
    }

    @Test
    void activateAccount_WithValidData_ExecuteWithoutThrowsException() throws Exception {
        when(fluxService.fluxActivateAccount()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.activateAccount(new ActivateAccount(1, 2, "123444")))
                .doesNotThrowAnyException();
    }

    @Test
    void activateAccount_WithClientNotExists_ThrowsClientNotExistsException() throws Exception {
        when(fluxService.fluxActivateAccount()).thenReturn(facade);
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.activateAccount(new ActivateAccount(1, 2, "123444")))
                .isInstanceOf(ClientNotExistsException.class)
                .hasMessage(CLIENT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void activateAccount_WithClientDeactivated_ThrowsClientDeactivatedException() throws Exception {
        when(fluxService.fluxActivateAccount()).thenReturn(facade);
        doThrow(new ClientDeactivatedException(CLIENT_DEACTIVATED.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.activateAccount(new ActivateAccount(1, 2, "123444")))
                .isInstanceOf(ClientDeactivatedException.class)
                .hasMessage(CLIENT_DEACTIVATED.getMessage());
    }

    @Test
    void activateAccount_WithAccountNotExists_ThrowsAccountNotExistsException() throws Exception {
        when(fluxService.fluxActivateAccount()).thenReturn(facade);
        doThrow(new AccountNotExistsException(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.activateAccount(new ActivateAccount(1, 2, "123444")))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage(ACCOUNT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void activateAccount_WithAccountAlreadyActive_ThrowsAccountAlreadyActiveException() throws Exception {
        when(fluxService.fluxActivateAccount()).thenReturn(facade);
        doThrow(new AccountAlreadyActiveException(ACCOUNT_ALREADY_ACTIVATED_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.activateAccount(new ActivateAccount(1, 2, "123444")))
                .isInstanceOf(AccountAlreadyActiveException.class)
                .hasMessage(ACCOUNT_ALREADY_ACTIVATED_MESSAGE.getMessage());
    }

    @Test
    void deactivateAccount_WithValidData_ExecuteWithoutThrowsException() throws Exception {
        when(fluxService.fluxDeactivateAccount()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.deactivateAccount(new DeactivateAccount( "123444", 1, 2)))
                .doesNotThrowAnyException();
    }

    @Test
    void deactivateAccount_WithClientNotExists_ThrowsClientNotExistsException() throws Exception {
        when(fluxService.fluxDeactivateAccount()).thenReturn(facade);
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.deactivateAccount(new DeactivateAccount( "123444", 1, 2)))
                .isInstanceOf(ClientNotExistsException.class)
                .hasMessage(CLIENT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void deactivateAccount_WithAccountNotExists_ThrowsAccountNotExistsException() throws Exception {
        when(fluxService.fluxDeactivateAccount()).thenReturn(facade);
        doThrow(new AccountNotExistsException(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.deactivateAccount(new DeactivateAccount( "123444", 1, 2)))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage(ACCOUNT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void deactivateAccount_WithAccountDeactivated_ThrowsAccountDeactivatedException() throws Exception {
        when(fluxService.fluxDeactivateAccount()).thenReturn(facade);
        doThrow(new AccountDeactivatedException(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.deactivateAccount(new DeactivateAccount( "123444", 1, 2)))
                .isInstanceOf(AccountDeactivatedException.class)
                .hasMessage(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage());
    }

    @Test
    void deactivateAccount_WithClientDeactivated_ThrowsClientDeactivatedException() throws Exception {
        when(fluxService.fluxDeactivateAccount()).thenReturn(facade);
        doThrow(new ClientDeactivatedException(CLIENT_DEACTIVATED.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.deactivateAccount(new DeactivateAccount( "123444", 1, 2)))
                .isInstanceOf(ClientDeactivatedException.class)
                .hasMessage(CLIENT_DEACTIVATED.getMessage());
    }

    @Test
    void changePassword_WithValidData_ExecuteWithoutThrowsException() throws Exception {
        when(fluxService.fluxChangePassword()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.changePassword(new ChangePasswordDTO("123444", 1, 2, "123456", "123456")))
                .doesNotThrowAnyException();
    }

    @Test
    void changePassword_WithClientNotExists_ThrowsClientNotExistsException() throws Exception {
        when(fluxService.fluxChangePassword()).thenReturn(facade);
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.changePassword(new ChangePasswordDTO("123444", 1, 2, "123456", "123456")))
                .isInstanceOf(ClientNotExistsException.class)
                .hasMessage(CLIENT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void changePassword_WithClientDeactivated_ThrowsClientDeactivatedException() throws Exception {
        when(fluxService.fluxChangePassword()).thenReturn(facade);
        doThrow(new ClientDeactivatedException(CLIENT_ALREADY_DEACTIVATED.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.changePassword(new ChangePasswordDTO("123444", 1, 2, "123456", "123456")))
                .isInstanceOf(ClientDeactivatedException.class)
                .hasMessage(CLIENT_ALREADY_DEACTIVATED.getMessage());
    }

    @Test
    void changePassword_WithAccountNotFound_ThrowsAccountNotExistsException() throws Exception {
        when(fluxService.fluxChangePassword()).thenReturn(facade);
        doThrow(new AccountNotExistsException(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.changePassword(new ChangePasswordDTO("123444", 1, 2, "123456", "123456")))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage(ACCOUNT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void changePassword_WithAccountDeactivated_ThrowsAccountDeactivatedException() throws Exception {
        when(fluxService.fluxChangePassword()).thenReturn(facade);
        doThrow(new AccountDeactivatedException(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.changePassword(new ChangePasswordDTO("123444", 1, 2, "123456", "123456")))
                .isInstanceOf(AccountDeactivatedException.class)
                .hasMessage(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage());
    }

    @Test
    void changePassword_WithPasswordDifferentThatRegistered_ThrowsPasswordDivergentException() throws Exception {
        when(fluxService.fluxChangePassword()).thenReturn(facade);
        doThrow(new PasswordDivergentException(ACCOUNT_WITH_PASSWORD_DIFFERENT.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.changePassword(new ChangePasswordDTO("123444", 1, 2, "123456", "123456")))
                .isInstanceOf(PasswordDivergentException.class)
                .hasMessage(ACCOUNT_WITH_PASSWORD_DIFFERENT.getMessage());
    }

    @Test
    void createAccount_WithValidaData_ReturnsAgencyAndAccountNumberData() throws Exception {
        when(fluxService.fluxCreateNewAccount()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> {
            var source = ((Source) argumentsOnMock.getArgument(0));
            ((NewAccountResponse) source.output()).setAccountNumber(123456);
            ((NewAccountResponse) source.output()).setAgency(1234);
            return null;
        }).when(facade).exec(any(Source.class));

        final var response = service.createNewAccount(new CreateNewAccountByCpfDTO("12345678901", 1234, "123456"));
        assertThat(response).isNotNull();
        assertThat(response.getAccountNumber()).isEqualTo(123456);
        assertThat(response.getAgency()).isEqualTo(1234);
    }
}