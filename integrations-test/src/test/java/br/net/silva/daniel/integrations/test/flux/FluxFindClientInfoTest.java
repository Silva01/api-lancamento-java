package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.FindClientByCpf;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FluxFindClientInfoTest extends AbstractBuilder {

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations clientNotExistsValidate;

    @Mock
    private Repository<Optional<ClientOutput>> findClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.ofNullable(buildMockClient(true)));

//        findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());
//        clientNotExistsValidate = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldFindClientWithSuccess() throws GenericException {
        Queue<UseCase<?>> queueUseCase = new LinkedList<>();
        queueUseCase.add(findClientUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientNotExistsValidate);

        var findCLientByCpf = new FindClientByCpf("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, findCLientByCpf);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        facade.exec(source);

        verify(findClientRepository, Mockito.times(2)).exec(Mockito.anyString());
        verify(findClientRepository, Mockito.times(2)).exec(Mockito.anyString());
    }

    @Test
    void shouldFindClientErrorClientNotExists() {
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());
        Queue<UseCase> queueUseCase = new LinkedList<>();
        queueUseCase.add(findClientUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientNotExistsValidate);

        var facade = new GenericFacadeDelegate(queueUseCase, validations);

        var findCLientByCpf = new FindClientByCpf("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, findCLientByCpf);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));

        assertEquals("Client not exists in database", exceptionResponse.getMessage());
    }

}
