package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientNotExistsValidate;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class FluxCreateNewClientTest extends AbstractBuilder {

    private UseCase<AccountOutput> createNewAccountByCpfUseCase;

    private UseCase<ClientDTO> createNewClientUseCase;

    private UseCase<ClientDTO> findClientUseCase;

    private IValidations clientNotExistsValidate;

    @Mock
    private Repository<Client> saveClientRepository;

    @Mock
    private Repository<Boolean> findIsExistsPeerCPFRepository;

    @Mock
    private Repository<Account> saveAccountRepository;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createNewAccountByCpfUseCase = new CreateNewAccountByCpfUseCase(findIsExistsPeerCPFRepository, saveAccountRepository, buildFactoryResponse());
        createNewClientUseCase = new CreateNewClientUseCase(saveClientRepository, buildFactoryResponse());
        findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());
        clientNotExistsValidate = new ClientNotExistsValidate(findClientUseCase);
    }

    @Test
    void shouldCreateNewClientWithSuccess() throws GenericException {
        when(saveClientRepository.exec(Mockito.any(Client.class))).thenReturn(buildMockClient(true));
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());
        when(saveAccountRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount(true));
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(false);

        var createNewClientInput = new ClientRequestDTO(
                null,
                "99988877700",
                "Daniel",
                "6122223333",
                true,
                45678,
                new AddressRequestDTO(
                        "Rua 1",
                        "123",
                        "Teste",
                        "Bairro 1",
                        "DF",
                        "Brasilia",
                        "44444-555"
                )
        );

        var source = new Source(new NewAccountByNewClientResponseSuccess(), createNewClientInput);

        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(clientNotExistsValidate);

        var facade = new GenericFacadeDelegate(useCases, validationsList);

        facade.exec(source);

        var response = (NewAccountByNewClientResponseSuccess) source.output();
        assertNotNull(response);
        assertEquals(createNewClientInput.agency(), response.getAgency());
        assertNotNull(response.getAccountNumber());
        assertNotNull(response.getProvisionalPassword());

        verify(saveClientRepository, times(1)).exec(any(Client.class));
        verify(findClientRepository, times(1)).exec(anyString());
        verify(saveAccountRepository, times(1)).exec(any(Account.class));
        verify(findIsExistsPeerCPFRepository, times(1)).exec(anyString());
    }
}
