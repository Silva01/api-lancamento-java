package br.net.silva.daniel.facade;

import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.usecase.ActivateClientUseCase;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.usecase.DeactivateClientUseCase;
import br.net.silva.daniel.value_object.input.*;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientFacadeProcessorTest {

    private UseCase<ClientOutput> createNewClientUseCase;

    private UseCase<ClientOutput> activateClientUseCase;

    private DeactivateClientUseCase deactivateClientUseCase;

    @Mock
    private ApplicationBaseGateway<ClientOutput> baseRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        var factory = new GenericResponseMapper(Collections.emptyList());
        this.createNewClientUseCase = new CreateNewClientUseCase(baseRepository, factory);
        this.deactivateClientUseCase = new DeactivateClientUseCase(baseRepository, factory);
        this.activateClientUseCase = new ActivateClientUseCase(baseRepository);
    }

    @Test
    void mustCreateNewClientAndAccountWithSuccess() throws GenericException {

        var client = buildClient(true);

        when(baseRepository.save(any(ClientOutput.class))).thenReturn(client);
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.empty());

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);

        var clientFacade = new GenericFacadeDelegate(useCases);

        var clientRequestDto = new ClientRequestDTO("123", "99988877766", "Daniel", "6122223333", true, 222, new AddressRequestDTO("Rua 1", "123", "House", "Test", "DF", "Others", "12345-123"));
        var source = new Source(EmptyOutput.INSTANCE, clientRequestDto);

        clientFacade.exec(source);

        assertNotNull(source.output());
        verify(baseRepository, Mockito.times(1)).save(Mockito.any(ClientOutput.class));
        verify(baseRepository, Mockito.times(1)).findById(clientRequestDto);
    }

    @Test
    void mustErrorWhenClientExistsInDatabaseWhereCreateClient() {
        var client = buildClient(true);

        when(baseRepository.save(any(ClientOutput.class))).thenReturn(client);
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(client));

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);

        var clientFacade = new GenericFacadeDelegate(useCases);

        var findClientByCpf = new FindClientByCpf("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, findClientByCpf);

        var exception = assertThrows(GenericException.class, () -> clientFacade.exec(source));
        assertEquals("Client already exists in database", exception.getMessage());
    }

    @Test
    void mustDeactivateClientWithSuccess() throws GenericException {
        var client = buildClient(true);
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(client);
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(client));

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(deactivateClientUseCase);

        var clientFacade = new GenericFacadeDelegate(useCases);

        var deactivateClient = new DeactivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        clientFacade.exec(source);
        verify(baseRepository, Mockito.times(1)).save(Mockito.any(ClientOutput.class));
        verify(baseRepository, Mockito.times(1)).findById(deactivateClient);
    }

    @Test
    void mustDeactivateClientErrorClientNotExists() throws GenericException {
        var client = buildClient(true);
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.empty());

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(deactivateClientUseCase);

        var clientFacade = new GenericFacadeDelegate(useCases);

        var deactivateClient = new DeactivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClient);

        var exceptionResponse = assertThrows(GenericException.class, () -> clientFacade.exec(source));
        assertEquals("Client not exists in database", exceptionResponse.getMessage());
    }

    @Test
    void mustActivateClientWithSuccess() throws GenericException {
        var client = buildClient(false);
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(client);
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(client));

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(activateClientUseCase);

        var clientFacade = new GenericFacadeDelegate(useCases);

        var activateClient = new ActivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, activateClient);
        clientFacade.exec(source);

        verify(baseRepository, Mockito.times(1)).save(Mockito.any(ClientOutput.class));
        verify(baseRepository, Mockito.times(1)).findById(activateClient);
    }

    private ClientOutput buildClient(boolean active) {
        var address = new AddressOutput("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new ClientOutput("abcd", "99988877766", "Daniel", "6122223333", active, address);
    }
}