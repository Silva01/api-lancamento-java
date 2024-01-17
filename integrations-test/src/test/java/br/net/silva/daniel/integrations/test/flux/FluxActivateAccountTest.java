package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.usecase.ActivateAccountUseCase;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.value_object.Source;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class FluxActivateAccountTest extends AbstractBuilder {

    private UseCase activateAccountUseCase;

    private UseCase findClientUseCase;

    private IValidations accountExistsValidation;

    private IValidations clientExistsValidation;

    @Mock
    private Repository<Account> activateAccountRepository;

    @Mock
    private Repository<Optional<Account>> findOptionalAccountRepository;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @Mock
    private Repository<Account> findAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(activateAccountRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));
        when(findOptionalAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildMockClient(true)));
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(false));

        // Use Case
        activateAccountUseCase = new ActivateAccountUseCase(activateAccountRepository, findAccountRepository);
        findClientUseCase = new FindClientUseCase(findClientRepository);

        // Validations
        accountExistsValidation = new AccountExistsValidate(findOptionalAccountRepository);
        clientExistsValidation = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldActivateAccountWithSuccess() throws GenericException {

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678900");
        inputMap.put("account", "123456");
        inputMap.put("agency", "1234");

        var source = new Source(new HashMap<>(), inputMap);

        Queue<UseCase> queue = new LinkedList<>();
        queue.add(activateAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(queue, validations);
        facade.exec(source);

        var processedDto = (AccountDTO) source.map().get(TypeAccountMapperEnum.ACCOUNT.name());
        assertionAccount(buildMockAccount(true).build(), processedDto);
    }

    @Test
    void shouldActivateAccountWithErrorClientNotExists() throws GenericException {
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678900");
        inputMap.put("account", "123456");
        inputMap.put("agency", "1234");

        var source = new Source(new HashMap<>(), inputMap);

        Queue<UseCase> queue = new LinkedList<>();
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

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678900");
        inputMap.put("account", "123456");
        inputMap.put("agency", "1234");

        var source = new Source(new HashMap<>(), inputMap);

        Queue<UseCase> queue = new LinkedList<>();
        queue.add(activateAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(queue, validations);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Account not exists", exceptionResponse.getMessage());
    }
}
