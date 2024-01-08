package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DeactivateClientUseCaseTest {

    private DeactivateClientUseCase deactivateClientUseCase;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @Mock
    private Repository<Client> saveRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deactivateClientUseCase = new DeactivateClientUseCase(findClientRepository, saveRepository);
    }

    @Test
    void mustDeactivateClientWithSuccess() throws GenericException {
        when(findClientRepository.exec(Mockito.any(String.class))).thenReturn(Optional.of(buildClient(true)));
        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(buildClient(false));

        var requestClient = new ClientRequestDTO(null, "99988877766", null, null, false, null, null);
        var clientDto = deactivateClientUseCase.exec(requestClient).build();

        assertFalse(clientDto.active());

        Mockito.verify(findClientRepository, Mockito.times(1)).exec(requestClient.cpf());
        Mockito.verify(saveRepository, Mockito.times(1)).exec(Mockito.any(Client.class));
    }

    @Test
    void mustErrorClientNotExistsWhenTryDeactivateClient() {
        when(findClientRepository.exec(Mockito.any(String.class))).thenReturn(Optional.empty());
        var requestClient = new ClientRequestDTO(null, "99988877766", null, null, false, null, null);
        var exceptionReponse = assertThrows(GenericException.class, () -> deactivateClientUseCase.exec(requestClient));

        assertEquals("Client not exists in database", exceptionReponse.getMessage());
    }

    private Client buildClient(boolean status) {
        var address = new Address("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new Client("abcd", "99988877766", "Daniel", "6122223333", status, address);
    }

}