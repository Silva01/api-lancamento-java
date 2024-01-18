package br.net.silva.daniel.usecase;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class CreateNewClientUseCaseTest {

    @InjectMocks
    private Repository<Client> saveRepository = mock(Repository.class);

    private CreateNewClientUseCase createNewClientUseCase;

    @BeforeEach
    protected void setUp() {
        when(saveRepository.exec(any(Client.class))).thenReturn(createClient());
        createNewClientUseCase = new CreateNewClientUseCase(saveRepository);
    }

    @Test
    void testShouldCreateANewClientWithSuccess() throws Exception {
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("id", "1234");
        inputMap.put("cpf", "00099988877");
        inputMap.put("name", "Daniel");
        inputMap.put("telephone", "61933334444");
        inputMap.put("active", "true");
        inputMap.put("agency", "1234");
        inputMap.put("street", "Street 1");
        inputMap.put("number", "1234");
        inputMap.put("complement", "Brazil");
        inputMap.put("neighborhood", "Brasilia");
        inputMap.put("state", "DF");
        inputMap.put("city", "Brasilia");
        inputMap.put("zipCode", "12344-556");

        var source = new Source(new HashMap<>(), inputMap);

        createNewClientUseCase.exec(source);

        verify(saveRepository, times(1)).exec(any(Client.class));
    }

    @Test
    void testShouldCreateANewClientWithErrorExistsClient() throws ExistsClientRegistredException {
        // Arrange
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("id", "1234");
        inputMap.put("cpf", "00099988877");
        inputMap.put("name", "Daniel");
        inputMap.put("telephone", "61933334444");
        inputMap.put("active", "true");
        inputMap.put("agency", "1234");
        inputMap.put("street", "Street 1");
        inputMap.put("number", "1234");
        inputMap.put("complement", "Brazil");
        inputMap.put("neighborhood", "Brasilia");
        inputMap.put("state", "DF");
        inputMap.put("city", "Brasilia");
        inputMap.put("zipCode", "12344-556");

        var source = new Source(new HashMap<>(), inputMap);


        // Mock the repository
        when(saveRepository.exec(any())).thenThrow(new RuntimeException("Exists client"));

        // Act & Assert
        assertThrows(ExistsClientRegistredException.class, () -> {
            createNewClientUseCase.exec(source);
        });

        // Verify that saveRepository.exec was called exactly once
        verify(saveRepository, times(1)).exec(any());
    }

    private Client createClient() {
        var address = new Address("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        return new Client("1234", "00099988877", "Daniel", "61933334444", true, address);
    }

}