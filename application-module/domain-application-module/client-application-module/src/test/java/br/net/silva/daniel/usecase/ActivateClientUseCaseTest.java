package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateClientByDtoFactory;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.value_object.Source;
import br.net.silva.daniel.value_object.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ActivateClientUseCaseTest {

    private ActivateClientUseCase activateClientUseCase;

    private CreateClientByDtoFactory factory;

    @Mock
    private Repository<Client> activateClientRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activateClientUseCase = new ActivateClientUseCase(activateClientRepository);
        this.factory = new CreateClientByDtoFactory();
    }

    @Test
    void mustActivateClientWithSucess() throws GenericException {
        when(activateClientRepository.exec(Mockito.anyString())).thenReturn(createClient());
        var dto = new ClientRequestDTO("1234", "00099988877", "Daniel", "61933334444", true, 1234, null);
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("id", "1234");
        inputMap.put("cpf", "00099988877");
        inputMap.put("name", "Daniel");
        inputMap.put("telephone", "61933334444");
        inputMap.put("active", "true");
        inputMap.put("agency", "1234");
        var source = new Source(new HashMap<>(), inputMap);
        activateClientUseCase.exec(source);

        var mockResponse = factory.create((ClientDTO) source.map().get(TypeClientMapperEnum.CLIENT.name()));
        assertNotNull(mockResponse);
        assertTrue(mockResponse.build().active());
    }

    private Client createClient() {
        var address = new Address("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        return new Client("1234", "00099988877", "Daniel", "61933334444", true, address);
    }

}