package br.net.silva.business.facade;

import br.net.silva.business.usecase.ActivateAccountUseCase;
import br.net.silva.business.validations.AccountExistsAndActiveValidate;
import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ActivateAccountFacadeTest {

    private ActivateAccountUseCase activateAccountUseCase;

    private IValidations accountExistsValidate;

    @Mock
    private Repository<AccountOutput> activateAccountRepository;

    @Mock
    private Repository<AccountOutput> findAccountRepository;

    @Mock
    private Repository<Optional<AccountOutput>> optionalFindAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.activateAccountUseCase = new ActivateAccountUseCase(activateAccountRepository, findAccountRepository);
        this.accountExistsValidate = new AccountExistsAndActiveValidate(optionalFindAccountRepository);
    }

    @Test
    void shouldActivateAccountWithSuccess() throws GenericException {
        when(activateAccountRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));
        when(optionalFindAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(false)));
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(false));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateAccountUseCase);

        List<IValidations> validationsList = List.of(accountExistsValidate);

        var facade = new GenericFacadeDelegate(useCases, validationsList);
        var dtoRequest = new ActivateAccount( 45678, 4321888, "99988877766");
        var source = new Source(EmptyOutput.INSTANCE, dtoRequest);

        facade.exec(source);

        assertNotNull(source.output());

        verify(activateAccountRepository).exec(any(AccountOutput.class));
    }

    @Test
    void shouldActivateAccountErrorWhenAccountNotExists() {
        when(activateAccountRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));
        when(optionalFindAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.empty());
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(false));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateAccountUseCase);

        List<IValidations> validationsList = List.of(accountExistsValidate);

        var facade = new GenericFacadeDelegate(useCases, validationsList);
        var dtoRequest = new ActivateAccount( 45678, 4321888, "99988877766");
        var source = new Source(EmptyOutput.INSTANCE, dtoRequest);

        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account not exists", exceptionResponse.getMessage());
    }

    @Test
    void shouldActivateAccountErrorWhenAccountIsActive() {
        when(activateAccountRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));
        when(optionalFindAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(false));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateAccountUseCase);

        List<IValidations> validationsList = List.of(accountExistsValidate);

        var facade = new GenericFacadeDelegate(useCases, validationsList);
        var dtoRequest = new ActivateAccount( 45678, 4321888, "99988877766");
        var source = new Source(EmptyOutput.INSTANCE, dtoRequest);

        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account already active", exceptionResponse.getMessage());
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }
}
