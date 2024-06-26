package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.CreditCardAlreadyExistsException;
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
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_ALREADY_EXISTS_MESSAGE;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest implements InputBuilderCommons {
    
    @InjectMocks
    private CreditCardService service;

    @Mock
    private FluxService fluxService;

    @Mock
    private GenericFacadeDelegate facade;

    @Test
    void createCreditCard_WithValidData_ExecuteWithoutErros() throws Exception {
        when(fluxService.fluxCreateCreditCard()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> null).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.createCreditCard(buildNewBaseCreditCardInput())).doesNotThrowAnyException();
    }

    @Test
    void createCreditCard_WithClientNotExists_ReturnsException() throws Exception {
        when(fluxService.fluxCreateCreditCard()).thenReturn(facade);
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.createCreditCard(buildNewBaseCreditCardInput()))
                .isInstanceOf(ClientNotExistsException.class)
                .hasMessage(CLIENT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void createCreditCard_WithClientDeactivated_ReturnsException() throws Exception {
        when(fluxService.fluxCreateCreditCard()).thenReturn(facade);
        doThrow(new ClientNotExistsException(CLIENT_ALREADY_DEACTIVATED.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.createCreditCard(buildNewBaseCreditCardInput()))
                .isInstanceOf(ClientNotExistsException.class)
                .hasMessage(CLIENT_ALREADY_DEACTIVATED.getMessage());
    }

    @Test
    void createCreditCard_WithAccountNotExists_ReturnsException() throws Exception {
        when(fluxService.fluxCreateCreditCard()).thenReturn(facade);
        doThrow(new AccountNotExistsException(ACCOUNT_NOT_FOUND_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.createCreditCard(buildNewBaseCreditCardInput()))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage(ACCOUNT_NOT_FOUND_MESSAGE.getMessage());
    }

    @Test
    void createCreditCard_WithAccountDeactivated_ReturnsException() throws Exception {
        when(fluxService.fluxCreateCreditCard()).thenReturn(facade);
        doThrow(new AccountDeactivatedException(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.createCreditCard(buildNewBaseCreditCardInput()))
                .isInstanceOf(AccountDeactivatedException.class)
                .hasMessage(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage());
    }

    @Test
    void createCreditCard_WithAlreadyHasCreditCard_ReturnsException() throws Exception {
        when(fluxService.fluxCreateCreditCard()).thenReturn(facade);
        doThrow(new CreditCardAlreadyExistsException(CREDIT_CARD_ALREADY_EXISTS_MESSAGE.getMessage())).when(facade).exec(any(Source.class));

        assertThatCode(() -> service.createCreditCard(buildNewBaseCreditCardInput()))
                .isInstanceOf(CreditCardAlreadyExistsException.class)
                .hasMessage(CREDIT_CARD_ALREADY_EXISTS_MESSAGE.getMessage());
    }
}