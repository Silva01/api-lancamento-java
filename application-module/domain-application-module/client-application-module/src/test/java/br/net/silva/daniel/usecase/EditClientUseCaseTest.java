package br.net.silva.daniel.usecase;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.EditClientInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EditClientUseCaseTest {

    private EditClientUseCase editClientUseCase;

    @Mock
    private Repository<Optional<Client>> findRepository;

    @Mock
    private Repository<Client> saveRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findRepository.exec(anyString())).thenReturn(Optional.of(buildClient()));
        when(saveRepository.exec(any(Client.class))).thenReturn(buildClient());

        var facotry = new GenericResponseMapper(Collections.emptyList());

        this.editClientUseCase = new EditClientUseCase(findRepository, saveRepository, facotry);
    }

    @Test
    void shouldEditClientWithSuccess() throws GenericException {
        var editClientInput = new EditClientInput("22233344455", "Daniel", "22344445555");
        var source = new Source(EmptyOutput.INSTANCE, editClientInput);

        var response = editClientUseCase.exec(source);
        assertNotNull(response);
        assertEquals("Daniel", response.name());
        assertEquals("22344445555", response.telephone());

        verify(findRepository, times(1)).exec(editClientInput.cpf());
        verify(saveRepository, times(1)).exec(any(Client.class));
    }

    @Test
    void shouldGiveErrorClientNotExistsTryEditClient() {
        when(findRepository.exec(anyString())).thenReturn(Optional.empty());
        var editClientInput = new EditClientInput("22233344455", "Daniel", "22344445555");
        var source = new Source(EmptyOutput.INSTANCE, editClientInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> editClientUseCase.exec(source));
        assertEquals("Client not exists", exceptionResponse.getMessage());

        verify(findRepository, times(1)).exec(editClientInput.cpf());
        verify(saveRepository, times(0)).exec(any(Client.class));
    }

    @Test
    void shouldGiveErrorNullPointerTryEditClient() {
        when(saveRepository.exec(any(Client.class))).thenReturn(null);
        var editClientInput = new EditClientInput("22233344455", "Daniel", "22344445555");
        var source = new Source(EmptyOutput.INSTANCE, editClientInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> editClientUseCase.exec(source));
        assertEquals("Generic error", exceptionResponse.getMessage());

        verify(findRepository, times(1)).exec(editClientInput.cpf());
        verify(saveRepository, times(1)).exec(any(Client.class));
    }

    private Client buildClient() {
        var address = new Address("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new Client("1", "22233344455", "Daniel", "22344445555", true, address);
    }

}