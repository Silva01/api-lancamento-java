package br.net.silva.daniel.usecase;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.factory.GenericResponseFactory;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.FindClientByCpf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FindClientUseCaseTest {

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    private GenericResponseFactory factory;

    private FindClientUseCase findClientUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new GenericResponseFactory(Collections.emptyList());
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildClient()));
        this.findClientUseCase = new FindClientUseCase(findClientRepository, factory);
    }

    @Test
    void must_looking_for_client_in_database_with_success() throws ClientNotExistsException {
        var findClientByCpf = new FindClientByCpf("22233344455");
        var source = new Source(EmptyOutput.INSTANCE, findClientByCpf);
        var clientDto = findClientUseCase.exec(source);

        assertNotNull(source.output());
        assertNotNull(clientDto);
        verify(findClientRepository, times(1)).exec(findClientByCpf.cpf());
    }

    @Test
    void must_looking_for_client_in_database_what_not_exists() {
        var findClientByCpf = new FindClientByCpf("22233344455");
        var source = new Source(EmptyOutput.INSTANCE, findClientByCpf);
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(ClientNotExistsException.class, () -> findClientUseCase.exec(source));
    }

    private Client buildClient() {
        var address = new Address("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new Client("1", "22233344455", "Daniel", "22344445555", true, address);
    }

}