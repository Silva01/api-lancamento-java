package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import silva.daniel.project.app.commons.InputBuilderCommons;
import silva.daniel.project.app.service.FluxService;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
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
}