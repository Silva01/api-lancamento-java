package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.DeactivateAccountUseCase;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FluxDeactivateAccountTest extends AbstractBuilder {

    private UseCase<AccountOutput> deactivateAccountUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations accountExistsValidation;

    private IValidations clientExistsValidation;

    @Mock
    private Repository<AccountOutput> deactivateAccountRepository;

    @Mock
    private Repository<Optional<AccountOutput>> findAccountRepository;

    @Mock
    private Repository<Optional<ClientOutput>> findClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(deactivateAccountRepository.exec(anyString())).thenReturn(buildMockAccount(false));
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildMockClient(true)));

        // Use Case
        deactivateAccountUseCase = new DeactivateAccountUseCase(deactivateAccountRepository);
        findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());

        // Validations
        accountExistsValidation = new AccountExistsValidate(findAccountRepository);
        clientExistsValidation = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldDeactivateAccountWithSuccess() throws GenericException {

        var deactivateAccountInput = new DeactivateAccount("12345678900", 1, 1234);
        var source = new Source(EmptyOutput.INSTANCE, deactivateAccountInput);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(deactivateAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(queue, validations);
        facade.exec(source);

        verify(deactivateAccountRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
        verify(findClientRepository, times(1)).exec(anyString());
    }
}
