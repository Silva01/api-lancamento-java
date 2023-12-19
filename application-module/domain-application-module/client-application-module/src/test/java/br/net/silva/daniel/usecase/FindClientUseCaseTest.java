package br.net.silva.daniel.usecase;

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
        var cpf = "22233344455";
        var client = findClientUseCase.exec(cpf);

        assertNotNull(client);
        assertEquals(client.cpf(), cpf);
        assertEquals(client.name(), "Daniel");
        assertEquals(client.id(), "1");
        assertEquals(client.telephone(), "22344445555");
        assertTrue(client.active());
        assertEquals(client.address().street(), "Rua 1");
        assertEquals(client.address().number(), "1234");
        assertEquals(client.address().complement(), "nao tem");
        assertEquals(client.address().neighborhood(), "Bairro 1");
        assertEquals(client.address().city(), "Cidade 1");
        assertEquals(client.address().state(), "Estado 1");
        assertEquals(client.address().zipCode(), "11111111");

        verify(findClientRepository, times(1)).exec(cpf);
    }

    @Test
    void must_looking_for_client_in_database_what_not_exists() {
        var cpf = "22233344455";
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(ClientNotExistsException.class, () -> findClientUseCase.exec(cpf));
    }

    private Client buildClient() {
        var address = new Address("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new Client("1", "22233344455", "Daniel", "22344445555", true, address);
    }

}