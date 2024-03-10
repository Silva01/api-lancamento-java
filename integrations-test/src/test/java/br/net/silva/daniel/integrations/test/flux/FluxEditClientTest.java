package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.usecase.EditClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.EditClientInput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FluxEditClientTest extends AbstractBuilder {

    private UseCase<ClientOutput> editClientUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations clientExistsValidation;

    @Mock
    private Repository<ClientOutput> saveRepository;

    @Mock
    private Repository<Optional<ClientOutput>> findRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findRepository.exec(anyString())).thenReturn(Optional.of(buildMockClient(true)));
        when(saveRepository.exec(any())).thenReturn(buildMockClient(true));

        // Use Case
        editClientUseCase = new EditClientUseCase(findRepository, saveRepository, buildFactoryResponse());
        findClientUseCase = new FindClientUseCase(findRepository, buildFactoryResponse());

        // Validation
        clientExistsValidation = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldEditClientWithSuccess() {
        var editClientInput = new EditClientInput("22233344455", "Daniel Silva", "11999999999");
        var source = new Source(EmptyOutput.INSTANCE, editClientInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(editClientUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        assertDoesNotThrow(() -> facade.exec(source));

        verify(findRepository, times(2)).exec(anyString());
        verify(saveRepository, times(1)).exec(any());
    }

    @Test
    void shouldGiveErrorClientNotExistsEditClient() {
        when(findRepository.exec(anyString())).thenReturn(Optional.empty());
        var editClientInput = new EditClientInput("22233344455", "Daniel Silva", "11999999999");
        var source = new Source(EmptyOutput.INSTANCE, editClientInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(editClientUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var responseException = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Client not exists in database", responseException.getMessage());

        verify(findRepository, times(1)).exec(anyString());
        verify(saveRepository, times(0)).exec(any());
    }

    @Test
    void shouldGiveErrorNullPointerEditClient() {
        when(saveRepository.exec(any())).thenReturn(null);
        var editClientInput = new EditClientInput("22233344455", "Daniel Silva", "11999999999");
        var source = new Source(EmptyOutput.INSTANCE, editClientInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(editClientUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var responseException = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Generic error", responseException.getMessage());

        verify(findRepository, times(2)).exec(anyString());
        verify(saveRepository, times(1)).exec(any());
    }
}
