package br.net.silva.daniel.usecase;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class CreateNewClientUseCaseTest {

    @InjectMocks
    private Repository<Client> saveRepository = mock(Repository.class);

    private CreateNewClientUseCase createNewClientUseCase;

    @BeforeEach
    protected void setUp() {
        when(saveRepository.exec(any(Client.class))).thenReturn(createClient());
        createNewClientUseCase = new CreateNewClientUseCase(saveRepository, new GenericResponseMapper(Collections.emptyList()));
    }

    @Test
    void testShouldCreateANewClientWithSuccess() throws Exception {
        var newRequestClient = new ClientRequestDTO(
                "1234",
                "00099988877",
                "Daniel",
                "61933334444",
                true,
                1234,
                new AddressRequestDTO("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556")
        );

        var source = new Source(EmptyOutput.INSTANCE, newRequestClient);

        createNewClientUseCase.exec(source);

        verify(saveRepository, times(1)).exec(any(Client.class));
    }

    @Test
    void testShouldCreateANewClientWithErrorExistsClient() throws ExistsClientRegistredException {
        // Arrange
        var newRequestClient = new ClientRequestDTO(
                "1234",
                "00099988877",
                "Daniel",
                "61933334444",
                true,
                1234,
                new AddressRequestDTO("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556")
        );

        var source = new Source(EmptyOutput.INSTANCE, newRequestClient);


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