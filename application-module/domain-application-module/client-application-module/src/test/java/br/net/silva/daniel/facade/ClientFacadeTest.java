package br.net.silva.daniel.facade;

import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.ApplicationBaseRepository;
import br.net.silva.daniel.shared.application.repository.ParamRepository;
import br.net.silva.daniel.usecase.ActivateClientUseCase;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.usecase.DeactivateClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.validation.ClientNotExistsValidate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.*;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    private ApplicationBaseRepository<ClientOutput> baseRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        var factory = new GenericResponseMapper(Collections.emptyList());
        this.findClientUseCase = new FindClientUseCase(baseRepository, factory);
        this.createNewClientUseCase = new CreateNewClientUseCase(baseRepository, factory);
        this.clientExistsValidate = new ClientExistsValidate(findClientUseCase);
        this.clientNotExistsValidate = new ClientNotExistsValidate(findClientUseCase);
        this.deactivateClientUseCase = new DeactivateClientUseCase(baseRepository, factory);
        this.activateClientUseCase = new ActivateClientUseCase(baseRepository);
    }

    @Test
    void mustCreateNewClientAndAccountWithSuccess() throws GenericException {

        var client = buildClient(true);

        when(baseRepository.save(any(ClientOutput.class))).thenReturn(client);
        when(baseRepository.findById(any(ParamRepository.class))).thenReturn(Optional.empty());

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);

        List<IValidations> validationsList = List.of(clientNotExistsValidate);
        var clientFacade = new GenericFacadeDelegate<>(useCases, validationsList);

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
        when(baseRepository.findById(any(ParamRepository.class))).thenReturn(Optional.of(client));

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
        var client = buildClient(false);
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(client);
        when(baseRepository.findById(any(ParamRepository.class))).thenReturn(Optional.of(client));

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(deactivateClientUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidate);
        var clientFacade = new GenericFacadeDelegate<>(useCases, validationsList);

        var deactivateClient = new DeactivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        clientFacade.exec(source);
        verify(baseRepository, Mockito.times(1)).save(Mockito.any(ClientOutput.class));
        verify(baseRepository, Mockito.times(2)).findById(deactivateClient);
    }

    @Test
    void mustDeactivateClientErrorClientNotExists() throws GenericException {
        var client = buildClient(true);
        when(baseRepository.findById(any(ParamRepository.class))).thenReturn(Optional.empty());

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
        var client = buildClient(true);
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(client);
        when(baseRepository.findById(any(ParamRepository.class))).thenReturn(Optional.of(client));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateClientUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidate);
        var clientFacade = new GenericFacadeDelegate(useCases, validationsList);

        var activateClient = new ActivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, activateClient);
        clientFacade.exec(source);

        verify(baseRepository, Mockito.times(1)).save(Mockito.any(ClientOutput.class));
        verify(baseRepository, Mockito.times(2)).findById(activateClient);
    }

    private ClientOutput buildClient(boolean active) {
        var address = new AddressOutput("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new ClientOutput("abcd", "99988877766", "Daniel", "6122223333", active, address);
    }
}