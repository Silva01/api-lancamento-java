package br.net.silva.daniel.facade;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ClientNotExistsException;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FindClientFacadeTest {

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    private FindClientFacade findClientFacade;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        findClientFacade = new FindClientFacade(findClientRepository);
    }

    @Test
    void must_find_client_by_cpf() throws GenericException {
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildClient()));
        var clientDTO = findClientFacade.execute("22233344455");

        assertNotNull(clientDTO);
        assertEquals("22233344455", clientDTO.cpf());
        assertEquals("Daniel", clientDTO.name());
        assertEquals("1", clientDTO.id());
        assertEquals("22344445555", clientDTO.telephone());
        assertTrue(clientDTO.active());
        assertEquals("Rua 1", clientDTO.address().street());
        assertEquals("1234", clientDTO.address().number());
        assertEquals("nao tem", clientDTO.address().complement());
        assertEquals("Bairro 1", clientDTO.address().neighborhood());
        assertEquals("Cidade 1", clientDTO.address().city());
        assertEquals("Estado 1", clientDTO.address().state());
        assertEquals("11111111", clientDTO.address().zipCode());

        Mockito.verify(findClientRepository, Mockito.times(1)).exec(anyString());
    }

    @Test
    void must_not_find_client_by_cpf() {
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());
        assertThrows(ClientNotExistsException.class, () -> findClientFacade.execute("22233344455"));
    }

    private Client buildClient() {
        var address = new Address("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new Client("1", "22233344455", "Daniel", "22344445555", true, address);
    }
}