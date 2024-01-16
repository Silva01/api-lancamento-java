package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.value_object.Source;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FluxFindClientInfoTest extends AbstractBuilder {

    private UseCase findClientUseCase;

    private IValidations clientNotExistsValidate;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.ofNullable(buildMockClient()));

        findClientUseCase = new FindClientUseCase(findClientRepository);
        clientNotExistsValidate = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldFindClientWithSuccess() throws GenericException {
        Queue<UseCase> queueUseCase = new LinkedList<>();
        queueUseCase.add(findClientUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientNotExistsValidate);

        var facade = new GenericFacadeDelegate(queueUseCase, validations);

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "99988877766");

        var source = new Source(new HashMap<>(), inputMap);
        facade.exec(source);

        var client = (ClientDTO) source.map().get(TypeClientMapperEnum.CLIENT.name());
        assertNotNull(client);

        var buildClient = buildMockClient().build();

        assertEquals(buildClient, client);
    }

    @Test
    void shouldFindClientErrorClientNotExists() {
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());
        Queue<UseCase> queueUseCase = new LinkedList<>();
        queueUseCase.add(findClientUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientNotExistsValidate);

        var facade = new GenericFacadeDelegate(queueUseCase, validations);

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "99988877766");

        var source = new Source(new HashMap<>(), inputMap);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));

        assertEquals("Client not exists in database", exceptionResponse.getMessage());
    }

}
