package br.net.silva.business.facade;

import br.net.silva.business.usecase.ActivateAccountUseCase;
import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivateAccountFacadeTest {

    private ActivateAccountUseCase activateAccountUseCase;

    @Mock
    private ApplicationBaseGateway<AccountOutput> baseGateway;

    @BeforeEach
    void setUp() {
        this.activateAccountUseCase = new ActivateAccountUseCase(baseGateway);
    }

    @Test
    void shouldActivateAccountWithSuccess() throws GenericException {
        when(baseGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount(true));
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(false)));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateAccountUseCase);

        var facade = new GenericFacadeDelegate(useCases);
        var dtoRequest = new ActivateAccount( 45678, 4321888, "99988877766");
        var source = new Source(EmptyOutput.INSTANCE, dtoRequest);

        facade.exec(source);

        assertNotNull(source.output());

        verify(baseGateway, times(1)).save(any(AccountOutput.class));
        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void shouldActivateAccountErrorWhenAccountNotExists() {
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.empty());

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateAccountUseCase);

        var facade = new GenericFacadeDelegate(useCases);
        var dtoRequest = new ActivateAccount( 45678, 4321888, "99988877766");
        var source = new Source(EmptyOutput.INSTANCE, dtoRequest);

        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account not exists", exceptionResponse.getMessage());

        verify(baseGateway, never()).save(any(AccountOutput.class));
        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void shouldActivateAccountErrorWhenAccountIsActive() {
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true)));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateAccountUseCase);

        var facade = new GenericFacadeDelegate(useCases);
        var dtoRequest = new ActivateAccount( 45678, 4321888, "99988877766");
        var source = new Source(EmptyOutput.INSTANCE, dtoRequest);

        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account already active", exceptionResponse.getMessage());

        verify(baseGateway, never()).save(any(AccountOutput.class));
        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }
}
