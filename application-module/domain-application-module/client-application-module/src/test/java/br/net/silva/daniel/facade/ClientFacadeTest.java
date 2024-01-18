package br.net.silva.daniel.facade;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.ToClientMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.ActivateClientUseCase;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.usecase.DeactivateClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.utils.ConverterUtils;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.validation.ClientNotExistsValidate;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.shared.business.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ClientFacadeTest {

    private IValidations clientExistsValidate;
    private IValidations clientNotExistsValidate;

    private UseCase createNewClientUseCase;

    private UseCase findClientUseCase;

    private UseCase activateClientUseCase;

    private ToClientMapper mapper;

    private DeactivateClientUseCase deactivateClientUseCase;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @Mock
    private Repository<Client> saveRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        this.createNewClientUseCase = new CreateNewClientUseCase(saveRepository);
        this.findClientUseCase = new FindClientUseCase(findClientRepository);
        this.clientExistsValidate = new ClientExistsValidate(findClientUseCase);
        this.clientNotExistsValidate = new ClientNotExistsValidate(findClientUseCase);
        this.deactivateClientUseCase = new DeactivateClientUseCase(findClientRepository, saveRepository);
        this.activateClientUseCase = new ActivateClientUseCase(saveRepository);
        this.mapper = ToClientMapper.INSTANCE;
    }

    @Test
    void mustCreateNewClientAndAccountWithSuccess() throws GenericException {

        var client = buildClient();

        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(client);
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);

        List<IValidations> validationsList = List.of(clientNotExistsValidate);
        var clientFacade = new GenericFacadeDelegate(useCases, validationsList);

        Map<String, String> map = generateInputMap();

        var source = new Source(new HashMap<>(), map);

        clientFacade.exec(source);

        var resultProcess = (ClientDTO) source.map().get(TypeClientMapperEnum.CLIENT.name());
        var clientDTO = client.build();

        assertNotNull(resultProcess);
        assertEquals(clientDTO.id(), resultProcess.id());
        assertEquals(clientDTO.cpf(), resultProcess.cpf());
        assertEquals(clientDTO.name(), resultProcess.name());
        assertEquals(clientDTO.telephone(), resultProcess.telephone());
        assertEquals(clientDTO.active(), resultProcess.active());
        assertEquals(clientDTO.address().street(), resultProcess.address().street());
        assertEquals(clientDTO.address().number(), resultProcess.address().number());
        assertEquals(clientDTO.address().complement(), resultProcess.address().complement());
        assertEquals(clientDTO.address().neighborhood(), resultProcess.address().neighborhood());
        assertEquals(clientDTO.address().city(), resultProcess.address().city());
        assertEquals(clientDTO.address().state(), resultProcess.address().state());
        assertEquals(clientDTO.address().zipCode(), resultProcess.address().zipCode());
    }

    @Test
    void mustErrorWhenClientExistsInDatabaseWhereCreateClient() {
        var client = buildClient();

        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(client);
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.of(client));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);

        List<IValidations> validationsList = List.of(clientNotExistsValidate);
        var clientFacade = new GenericFacadeDelegate(useCases, validationsList);

        var source = new Source(new HashMap<>(), generateInputMap());

        var exception = assertThrows(GenericException.class, () -> clientFacade.exec(source));
        assertEquals("Client exists in database", exception.getMessage());
    }

    @Test
    void mustDeactivateClientWithSuccess() throws GenericException {
        var client = buildClient();
        client.deactivate();
        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(client);
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.of(client));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(deactivateClientUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidate);
        var clientFacade = new GenericFacadeDelegate(useCases, validationsList);

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "99988877766");
        var source = new Source(new HashMap<>(), inputMap);
        clientFacade.exec(source);
        var resultProcess = (ClientDTO) source.map().get(TypeClientMapperEnum.CLIENT.name());
        var clientDTO = client.build();

        assertNotNull(resultProcess);
        assertEquals(clientDTO.id(), resultProcess.id());
        assertEquals(clientDTO.cpf(), resultProcess.cpf());
        assertEquals(clientDTO.name(), resultProcess.name());
        assertEquals(clientDTO.telephone(), resultProcess.telephone());
        assertFalse(resultProcess.active());
        assertEquals(clientDTO.address().street(), resultProcess.address().street());
        assertEquals(clientDTO.address().number(), resultProcess.address().number());
        assertEquals(clientDTO.address().complement(), resultProcess.address().complement());
        assertEquals(clientDTO.address().neighborhood(), resultProcess.address().neighborhood());
        assertEquals(clientDTO.address().city(), resultProcess.address().city());
        assertEquals(clientDTO.address().state(), resultProcess.address().state());
        assertEquals(clientDTO.address().zipCode(), resultProcess.address().zipCode());
    }

    @Test
    void mustDeactivateClientErrorClientNotExists() throws GenericException {
        var client = buildClient();
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(deactivateClientUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidate);
        var clientFacade = new GenericFacadeDelegate(useCases, validationsList);

        var request = new ClientRequestDTO(null, "99988877766", null, null, false, null, null);
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(request)));

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

        var request = new ClientRequestDTO(null, "99988877766", null, null, false, null, null);
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "99988877766");
        var source = new Source(new HashMap<>(), inputMap);
        clientFacade.exec(source);
        var resultProcess = (ClientDTO) source.map().get(TypeClientMapperEnum.CLIENT.name());
        var clientDTO = client.build();

        assertNotNull(resultProcess);
        assertEquals(clientDTO.id(), resultProcess.id());
        assertEquals(clientDTO.cpf(), resultProcess.cpf());
        assertEquals(clientDTO.name(), resultProcess.name());
        assertEquals(clientDTO.telephone(), resultProcess.telephone());
        assertTrue(resultProcess.active());
        assertEquals(clientDTO.address().street(), resultProcess.address().street());
        assertEquals(clientDTO.address().number(), resultProcess.address().number());
        assertEquals(clientDTO.address().complement(), resultProcess.address().complement());
        assertEquals(clientDTO.address().neighborhood(), resultProcess.address().neighborhood());
        assertEquals(clientDTO.address().city(), resultProcess.address().city());
        assertEquals(clientDTO.address().state(), resultProcess.address().state());
        assertEquals(clientDTO.address().zipCode(), resultProcess.address().zipCode());
    }

    private Client buildClient() {
        var address = new Address("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new Client("abcd", "99988877766", "Daniel", "6122223333", true, address);
    }

    private static Map<String, String> generateInputMap() {
        Map<String, String> map = new HashMap<>();
        map.put("cpf", "99988877766");
        map.put("name", "Daniel");
        map.put("telephone", "6122223333");
        map.put("active", "true");
        map.put("agency", "1234");
        map.put("street", "Rua 1");
        map.put("number", "Bairro 1");
        map.put("complement", "Cidade 1");
        map.put("neighborhood", "Flores");
        map.put("state", "DF");
        map.put("city", "Brasilia");
        map.put("zipCode", "44444-555");
        return map;
    }
}