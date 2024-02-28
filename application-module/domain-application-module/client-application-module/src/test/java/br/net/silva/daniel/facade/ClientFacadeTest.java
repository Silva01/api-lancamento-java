package br.net.silva.daniel.facade;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.ActivateClientUseCase;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.usecase.DeactivateClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.validation.ClientNotExistsValidate;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.*;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientFacadeTest {

    private IValidations clientExistsValidate;
    private IValidations clientNotExistsValidate;

    private UseCase<ClientOutput> createNewClientUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private UseCase<ClientOutput> activateClientUseCase;

    private DeactivateClientUseCase deactivateClientUseCase;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @Mock
    private Repository<Client> saveRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        var factory = new GenericResponseMapper(Collections.emptyList());
        this.findClientUseCase = new FindClientUseCase(findClientRepository, factory);
        this.createNewClientUseCase = new CreateNewClientUseCase(saveRepository, factory);
        this.clientExistsValidate = new ClientExistsValidate(findClientUseCase);
        this.clientNotExistsValidate = new ClientNotExistsValidate(findClientUseCase);
        this.deactivateClientUseCase = new DeactivateClientUseCase(findClientRepository, saveRepository, factory);
        this.activateClientUseCase = new ActivateClientUseCase(saveRepository);
    }

    @Test
    void mustCreateNewClientAndAccountWithSuccess() throws GenericException {

        var client = buildClient();

        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(client);
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);

        List<IValidations> validationsList = List.of(clientNotExistsValidate);
        var clientFacade = new GenericFacadeDelegate<>(useCases, validationsList);

        var clientRequestDto = new ClientRequestDTO("123", "99988877766", "Daniel", "6122223333", true, 222, new AddressRequestDTO("Rua 1", "123", "House", "Test", "DF", "Others", "12345-123"));
        var source = new Source(EmptyOutput.INSTANCE, clientRequestDto);

        clientFacade.exec(source);

        assertNotNull(source.output());
        verify(saveRepository, Mockito.times(1)).exec(Mockito.any(Client.class));
        verify(findClientRepository, Mockito.times(1)).exec(clientRequestDto.cpf());
    }

    @Test
    void mustErrorWhenClientExistsInDatabaseWhereCreateClient() {
        var client = buildClient();

        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(client);
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.of(client));

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);

        List<IValidations> validationsList = List.of(clientNotExistsValidate);
        var clientFacade = new GenericFacadeDelegate<>(useCases, validationsList);

        var findClientByCpf = new FindClientByCpf("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, findClientByCpf);

        var exception = assertThrows(GenericException.class, () -> clientFacade.exec(source));
        assertEquals("Client exists in database", exception.getMessage());
    }

    @Test
    void mustDeactivateClientWithSuccess() throws GenericException {
        var client = buildClient();
        client.deactivate();
        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(client);
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.of(client));

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(deactivateClientUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidate);
        var clientFacade = new GenericFacadeDelegate<>(useCases, validationsList);

        var deactivateClient = new DeactivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        clientFacade.exec(source);
        verify(saveRepository, Mockito.times(1)).exec(Mockito.any(Client.class));
        verify(findClientRepository, Mockito.times(2)).exec(deactivateClient.cpf());
    }

    @Test
    void mustDeactivateClientErrorClientNotExists() throws GenericException {
        var client = buildClient();
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(deactivateClientUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidate);
        var clientFacade = new GenericFacadeDelegate(useCases, validationsList);

        var deactivateClient = new DeactivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClient);

        var exceptionResponse = assertThrows(GenericException.class, () -> clientFacade.exec(source));
        assertEquals("Client not exists in database", exceptionResponse.getMessage());
    }

    @Test
    void mustActivateClientWithSuccess() throws GenericException {
        var client = buildClient();
        when(saveRepository.exec(Mockito.any(String.class))).thenReturn(client);
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.of(client));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateClientUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidate);
        var clientFacade = new GenericFacadeDelegate(useCases, validationsList);

        var activateClient = new ActivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, activateClient);
        clientFacade.exec(source);

        verify(saveRepository, Mockito.times(1)).exec(activateClient.cpf());
        verify(findClientRepository, Mockito.times(1)).exec(activateClient.cpf());
    }

    private Client buildClient() {
        var address = new Address("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new Client("abcd", "99988877766", "Daniel", "6122223333", true, address);
    }
}