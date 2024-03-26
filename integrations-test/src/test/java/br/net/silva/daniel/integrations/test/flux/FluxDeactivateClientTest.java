package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.DeactivateClient;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FluxDeactivateClientTest extends AbstractBuilder {

    private UseCase<ClientOutput> deactivateClientUseCase;

    private UseCase<AccountOutput> deactivateAccountUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations clientExistsValidation;

    @Mock
    private Repository<AccountOutput> deactivateAccountRepository;

    @Mock
    private Repository<Optional<ClientOutput>> findClientRepository;

    @Mock
    private Repository<ClientOutput> saveRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(deactivateAccountRepository.exec(anyString())).thenReturn(buildMockAccount(true));
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.of(buildMockClient(true)));
        when(saveRepository.exec(Mockito.any(Client.class))).thenReturn(buildMockClient(false));

        // Use Cases
//        this.deactivateAccountUseCase = new DeactivateAccountUseCase(deactivateAccountRepository);
//        this.deactivateClientUseCase = new DeactivateClientUseCase(findClientRepository, saveRepository, buildFactoryResponse());
//        this.findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());

        // Validations
//        this.clientExistsValidation = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldDeactivateClientWithSuccess() throws GenericException {
        var deactivateClientInput = new DeactivateClient("12345678900");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClientInput);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(deactivateClientUseCase);
        queue.add(deactivateAccountUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidation);
        var facade = new GenericFacadeDelegate<>(queue, validationsList);

        facade.exec(source);

        verify(saveRepository, Mockito.times(1)).exec(Mockito.any(Client.class));
        verify(deactivateAccountRepository, Mockito.times(1)).exec(Mockito.anyString());
        verify(findClientRepository, Mockito.times(2)).exec(Mockito.anyString());
    }

    @Test
    void shouldErrorDeactivateClientClientNotExists() {
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());
        var deactivateClientInput = new DeactivateClient("12345678900");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClientInput);

        Queue<UseCase> queue = new LinkedList<>();
        queue.add(deactivateClientUseCase);
        queue.add(deactivateAccountUseCase);

        List<IValidations> validationsList = List.of(clientExistsValidation);
        var facade = new GenericFacadeDelegate<>(queue, validationsList);

        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        Assertions.assertEquals("Client not exists in database", exceptionResponse.getMessage());
    }
}
