package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.repository.SaveRepository;
import br.net.silva.daniel.value_object.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;


class CreateNewClientUseCaseTest {

    @InjectMocks
    private SaveRepository<Client> saveRepository = mock(SaveRepository.class);

    private CreateNewClientUseCase createNewClientUseCase;

    @BeforeEach
    protected void setUp() {
        when(saveRepository.save(any(Client.class))).thenReturn(createClient());
        createNewClientUseCase = new CreateNewClientUseCase(saveRepository);
    }

    @Test
    void testShouldCreateANewClientWithSuccess() throws Exception {
        var address = new AddressDTO("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        var clientDto = new ClientDTO("1234", "00099988877", "Daniel", "61933334444", true, address);

        createNewClientUseCase.exec(clientDto);

        verify(saveRepository, times(1)).save(any(Client.class));
    }

    private Client createClient() {
        var address = new Address("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        return new Client("1234", "00099988877", "Daniel", "61933334444", true, address);
    }

}