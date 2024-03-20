package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.usecase.ActivateClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.ActivateClient;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FluxActivateClientTest extends AbstractBuilder {

    private UseCase<ClientOutput> activeClientUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations clientExistsValidation;

    @Mock
    private ApplicationBaseGateway<ClientOutput> baseGateway;

    @Mock
    private Repository<ClientOutput> activateClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockClient(false)));
//        when(activateClientRepository.exec(anyString())).thenReturn(buildMockClient(true));

        // Factory
        var factory = new GenericResponseMapper(Collections.emptyList());

        // Use Cases
        this.activeClientUseCase = new ActivateClientUseCase(baseGateway);
        this.findClientUseCase = new FindClientUseCase(baseGateway, buildFactoryResponse());

        // Validations
//        this.clientExistsValidation = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldActivateClientWithSuccess() throws GenericException {
        var activateClient = new ActivateClient("12345678900");
        var source = new Source(EmptyOutput.INSTANCE, activateClient);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(activeClientUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidation);
        var facade = new GenericFacadeDelegate<>(queue, validationsList);

        facade.exec(source);

        verify(baseGateway, Mockito.times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void shouldErrorActivateClientClientNotExists() {
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.empty());
        var activateClient = new ActivateClient("12345678900");
        var source = new Source(EmptyOutput.INSTANCE, activateClient);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(activeClientUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidation);
        var facade = new GenericFacadeDelegate<>(queue, validationsList);

        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        Assertions.assertEquals("Client not exists in database", exceptionResponse.getMessage());
    }
}
