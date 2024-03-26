package br.net.silva.daniel.usecase;

import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.EditClientInput;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EditClientUseCaseTest {

    private EditClientUseCase editClientUseCase;
    @Mock
    private ApplicationBaseGateway<ClientOutput> baseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildClient()));
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(buildClient());

        var facotry = new GenericResponseMapper(Collections.emptyList());

        this.editClientUseCase = new EditClientUseCase(baseRepository, facotry);
    }

    @Test
    void shouldEditClientWithSuccess() throws GenericException {
        var editClientInput = new EditClientInput("22233344455", "Daniel", "22344445555");
        var source = new Source(EmptyOutput.INSTANCE, editClientInput);

        var response = editClientUseCase.exec(source);
        assertNotNull(response);
        assertEquals("Daniel", response.name());
        assertEquals("22344445555", response.telephone());

        verify(baseRepository, times(1)).findById(editClientInput);
        verify(baseRepository, times(1)).save(any(ClientOutput.class));
    }

    @Test
    void shouldGiveErrorClientNotExistsTryEditClient() {
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.empty());
        var editClientInput = new EditClientInput("22233344455", "Daniel", "22344445555");
        var source = new Source(EmptyOutput.INSTANCE, editClientInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> editClientUseCase.exec(source));
        assertEquals("Client not exists", exceptionResponse.getMessage());

        verify(baseRepository, times(1)).findById(editClientInput);
        verify(baseRepository, times(0)).save(any(ClientOutput.class));
    }

    @Test
    void shouldGiveErrorNullPointerTryEditClient() {
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(null);
        var editClientInput = new EditClientInput("22233344455", "Daniel", "22344445555");
        var source = new Source(EmptyOutput.INSTANCE, editClientInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> editClientUseCase.exec(source));
        assertEquals("Generic error", exceptionResponse.getMessage());

        verify(baseRepository, times(1)).findById(editClientInput);
        verify(baseRepository, times(1)).save(any(ClientOutput.class));
    }

    private ClientOutput buildClient() {
        var address = new AddressOutput("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new ClientOutput("1", "22233344455", "Daniel", "22344445555", true, address);
    }

}