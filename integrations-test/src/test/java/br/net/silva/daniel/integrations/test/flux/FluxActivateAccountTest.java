package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.ActivateAccountUseCase;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FluxActivateAccountTest extends AbstractBuilder {
    private UseCase<EmptyOutput> activateAccountUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations accountExistsValidation;

    private IValidations clientExistsValidation;

    @Mock
    private Repository<AccountOutput> activateAccountRepository;

    @Mock
    private Repository<Optional<AccountOutput>> findOptionalAccountRepository;

    @Mock
    private Repository<Optional<ClientOutput>> findClientRepository;

    @Mock
    private Repository<AccountOutput> findAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(activateAccountRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));
        when(findOptionalAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildMockClient(true)));
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(false));

        // Use Case
        activateAccountUseCase = new ActivateAccountUseCase(activateAccountRepository, findAccountRepository);
        findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());

        // Validations
        accountExistsValidation = new AccountExistsValidate(findOptionalAccountRepository);
        clientExistsValidation = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldActivateAccountWithSuccess() throws GenericException {
        var activateAccount = new ActivateAccount(1234, 123456, "12345678900");
        var source = new Source(EmptyOutput.INSTANCE, activateAccount);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(activateAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(queue, validations);
        facade.exec(source);
        verify(activateAccountRepository, times(1)).exec(any(AccountOutput.class));
    }

    @Test
    void shouldActivateAccountWithErrorClientNotExists() throws GenericException {
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());

        var activateAccount = new ActivateAccount(1234, 123456, "12345678900");
        var source = new Source(EmptyOutput.INSTANCE, activateAccount);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(activateAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(queue, validations);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Client not exists in database", exceptionResponse.getMessage());
    }

    @Test
    void shouldActivateAccountWithErrorAccountNotExists() throws GenericException {
        when(findOptionalAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.empty());

        var activateAccount = new ActivateAccount(1234, 123456, "12345678900");
        var source = new Source(EmptyOutput.INSTANCE, activateAccount);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(activateAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(queue, validations);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Account not exists", exceptionResponse.getMessage());
    }
}
