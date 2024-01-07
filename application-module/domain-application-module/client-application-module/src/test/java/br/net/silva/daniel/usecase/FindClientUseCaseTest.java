package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FindClientUseCaseTest {

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    private FindClientUseCase findClientUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildClient()));
        this.findClientUseCase = new FindClientUseCase(findClientRepository);
    }

    @Test
    void must_looking_for_client_in_database_with_success() throws ClientNotExistsException {
        var request = new ClientRequestDTO(null, "22233344455", null, null, false, null, null);
        var client = findClientUseCase.exec(request).build();

        assertNotNull(client);
        assertEquals(client.cpf(), request.cpf());
        assertEquals("Daniel", client.name());
        assertEquals("1", client.id());
        assertEquals("22344445555", client.telephone());
        assertTrue(client.active());
        assertEquals("Rua 1", client.address().street());
        assertEquals("1234", client.address().number());
        assertEquals("nao tem", client.address().complement());
        assertEquals("Bairro 1", client.address().neighborhood());
        assertEquals("Cidade 1", client.address().city());
        assertEquals("Estado 1", client.address().state());
        assertEquals("11111111", client.address().zipCode());

        verify(findClientRepository, times(1)).exec(request.cpf());
    }

    @Test
    void must_looking_for_client_in_database_what_not_exists() {
        var request = new ClientRequestDTO(null, "22233344455", null, null, false, null, null);
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(ClientNotExistsException.class, () -> findClientUseCase.exec(request));
    }

    private Client buildClient() {
        var address = new Address("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new Client("1", "22233344455", "Daniel", "22344445555", true, address);
    }

}