package br.net.silva.daniel.usecase;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.DeactivateClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class DeactivateClientUseCaseTest {

    private DeactivateClientUseCase deactivateClientUseCase;

    private GenericResponseMapper facotry;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @Mock
    private Repository<Client> saveRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        facotry = new GenericResponseMapper(Collections.emptyList());
        deactivateClientUseCase = new DeactivateClientUseCase(findClientRepository, saveRepository, facotry);
    }

    @Test
    void mustDeactivateClientWithSuccess() throws GenericException {
        when(findClientRepository.exec(Mockito.any(String.class))).thenReturn(Optional.of(buildClient(true)));
        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(buildClient(false));

        var deactivateClient = new DeactivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        deactivateClientUseCase.exec(source);
        Mockito.verify(findClientRepository, Mockito.times(1)).exec(deactivateClient.cpf());
        Mockito.verify(saveRepository, Mockito.times(1)).exec(Mockito.any(Client.class));
    }

    @Test
    void mustErrorClientNotExistsWhenTryDeactivateClient() {
        when(findClientRepository.exec(Mockito.any(String.class))).thenReturn(Optional.empty());
        var deactivateClient = new DeactivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        var exceptionReponse = assertThrows(GenericException.class, () -> deactivateClientUseCase.exec(source));

        assertEquals("Client not exists in database", exceptionReponse.getMessage());
    }

    private Client buildClient(boolean status) {
        var address = new Address("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new Client("abcd", "99988877766", "Daniel", "6122223333", status, address);
    }

}