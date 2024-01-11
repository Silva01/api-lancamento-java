package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.AddressRequestDTO;
import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

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
        var address = new AddressRequestDTO("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        var clientDto = new ClientRequestDTO("1234", "00099988877", "Daniel", "61933334444", true, 1234, address);

        createNewClientUseCase.exec(clientDto);

        verify(saveRepository, times(1)).exec(any(Client.class));
    }

    @Test
    void testShouldCreateANewClientWithErrorExistsClient() throws ExistsClientRegistredException {
        // Arrange
        var address = new AddressRequestDTO("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        var clientDto = new ClientRequestDTO("1234", "00099988877", "Daniel", "61933334444", true, 1234, address);

        // Mock the repository
        when(saveRepository.exec(any())).thenThrow(new RuntimeException("Exists client"));

        // Act & Assert
        assertThrows(ExistsClientRegistredException.class, () -> {
            createNewClientUseCase.exec(clientDto);
        });

        // Verify that saveRepository.exec was called exactly once
        verify(saveRepository, times(1)).exec(any());
    }

    private Client createClient() {
        var address = new Address("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        return new Client("1234", "00099988877", "Daniel", "61933334444", true, address);
    }

}