package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.DeactivateAccountUseCase;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FluxDeactivateAccountTest extends AbstractBuilder {

    private UseCase<AccountDTO> deactivateAccountUseCase;

    private UseCase<ClientDTO> findClientUseCase;

    private IValidations accountExistsValidation;

    private IValidations clientExistsValidation;

    @Mock
    private Repository<Account> deactivateAccountRepository;

    @Mock
    private Repository<Optional<Account>> findAccountRepository;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

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
