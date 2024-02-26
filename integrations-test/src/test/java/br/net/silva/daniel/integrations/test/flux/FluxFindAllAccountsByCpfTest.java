package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.FindAllAccountsByCpfUseCase;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.FindClientByCpf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FluxFindAllAccountsByCpfTest extends AbstractBuilder {

    private UseCase<List<AccountOutput>> findAllAccountsByCpfUseCase;

    private UseCase<ClientDTO> findClientUseCase;

    private IValidations clientExistsValidate;

    @Mock
    private Repository<List<Account>> findAllAccountsByCpfRepository;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAllAccountsByCpfRepository.exec(anyString())).thenReturn(buildMockListAccount());
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildMockClient(true)));

        // Use Case
        findAllAccountsByCpfUseCase = new FindAllAccountsByCpfUseCase(findAllAccountsByCpfRepository, buildFactoryResponse());
        findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());

        // Validation
        clientExistsValidate = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldGetListAllAccountsByCpfWithSuccess() throws GenericException {
        var findClientByCpf = new FindClientByCpf("99988877766");
        var source = new Source(new AccountsByCpfResponseDto(), findClientByCpf);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(findAllAccountsByCpfUseCase);

        List<IValidations> validations = List.of(clientExistsValidate);

        var facade = new GenericFacadeDelegate<>(queue, validations);
        facade.exec(source);

        var response = (AccountsByCpfResponseDto) source.output();
        assertNotNull(response);

        var mockListAccount = buildMockListAccount().stream().map(Account::build).toList();
        assertEquals(3, response.getAccounts().size());
        assertEquals(mockListAccount, response.getAccounts());
    }

    @Test
    void shouldGetListEmptyAccountsByCpf() throws GenericException {
        when(findAllAccountsByCpfRepository.exec(anyString())).thenReturn(Collections.emptyList());
        var findClientByCpf = new FindClientByCpf("99988877766");
        var source = new Source(new AccountsByCpfResponseDto(), findClientByCpf);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(findAllAccountsByCpfUseCase);

        List<IValidations> validations = List.of(clientExistsValidate);

        var facade = new GenericFacadeDelegate<>(queue, validations);
        facade.exec(source);

        var response = (AccountsByCpfResponseDto) source.output();
        assertNotNull(response);
        assertTrue(response.getAccounts().isEmpty());
    }

    @Test
    void shouldGetListAllAccountsByCpfWithErrorCLientNotExists() throws GenericException {
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());
        var findClientByCpf = new FindClientByCpf("99988877766");
        var source = new Source(new AccountsByCpfResponseDto(), findClientByCpf);

        Queue<UseCase<?>> queue = new LinkedList<>();
        queue.add(findAllAccountsByCpfUseCase);

        List<IValidations> validations = List.of(clientExistsValidate);

        var facade = new GenericFacadeDelegate<>(queue, validations);
        var responseException = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Client not exists in database", responseException.getMessage());
    }
}
