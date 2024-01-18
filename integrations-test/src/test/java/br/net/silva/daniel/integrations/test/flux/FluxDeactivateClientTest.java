package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.DeactivateAccountUseCase;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.usecase.DeactivateClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FluxDeactivateClientTest extends AbstractBuilder {

    private UseCase deactivateClientUseCase;

    private UseCase deactivateAccountUseCase;

    private UseCase findClientUseCase;

    private IValidations clientExistsValidation;

    @Mock
    private Repository<Account> deactivateAccountRepository;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @Mock
    private Repository<Client> saveRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(deactivateAccountRepository.exec(anyString())).thenReturn(buildMockAccount(true));
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.of(buildMockClient(true)));
        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(buildMockClient(false));

        // Use Cases
        this.deactivateAccountUseCase = new DeactivateAccountUseCase(deactivateAccountRepository);
        this.deactivateClientUseCase = new DeactivateClientUseCase(findClientRepository, saveRepository);
        this.findClientUseCase = new FindClientUseCase(findClientRepository);

        // Validations
        this.clientExistsValidation = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldDeactivateClientWithSuccess() throws GenericException {
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678900");

        var source = new Source(new HashMap<>(), inputMap);

        Queue<UseCase> queue = new LinkedList<>();
        queue.add(deactivateClientUseCase);
        queue.add(deactivateAccountUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidation);
        var facade = new GenericFacadeDelegate<>(queue, validationsList);

        facade.exec(source);

        var processedDto = (ClientDTO) source.map().get(TypeClientMapperEnum.CLIENT.name());
        assertionClient(buildMockClient(false).build(), processedDto);
    }

    @Test
    void shouldErrorDeactivateClientClientNotExists() {
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678900");

        var source = new Source(new HashMap<>(), inputMap);

        Queue<UseCase> queue = new LinkedList<>();
        queue.add(deactivateClientUseCase);
        queue.add(deactivateAccountUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidation);
        var facade = new GenericFacadeDelegate<>(queue, validationsList);

        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        Assertions.assertEquals("Client not exists in database", exceptionResponse.getMessage());
    }
}
