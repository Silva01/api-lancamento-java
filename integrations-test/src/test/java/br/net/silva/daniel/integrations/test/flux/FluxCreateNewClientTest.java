package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.AddressRequestDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;
import br.net.silva.daniel.shared.business.value_object.Source;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientNotExistsValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FluxCreateNewClientTest extends AbstractBuilder {

    private UseCase createNewAccountByCpfUseCase;

    private UseCase createNewClientUseCase;

    private UseCase findClientUseCase;

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
        createNewAccountByCpfUseCase = new CreateNewAccountByCpfUseCase(findIsExistsPeerCPFRepository, saveAccountRepository);
        createNewClientUseCase = new CreateNewClientUseCase(saveClientRepository);
        findClientUseCase = new FindClientUseCase(findClientRepository);
        clientNotExistsValidate = new ClientNotExistsValidate(findClientUseCase);
    }

    @Test
    void shouldCreateNewClientWithSuccess() throws GenericException {
        when(saveClientRepository.exec(Mockito.any(Client.class))).thenReturn(buildMockClient());
        when(findClientRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());
        when(saveAccountRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount(true));
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(false);

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "99988877700");
        inputMap.put("name", "Daniel");
        inputMap.put("telephone", "6122223333");
        inputMap.put("active", "true");
        inputMap.put("agency", "1");
        inputMap.put("street", "Rua 1");
        inputMap.put("number", "123");
        inputMap.put("complement", "Teste");
        inputMap.put("neighborhood", "Bairro 1");
        inputMap.put("state", "DF");
        inputMap.put("city", "Brasilia");
        inputMap.put("zipCode", "44444-555");

        var source = new Source(new HashMap<>(), inputMap);

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(clientNotExistsValidate);

        var facade = new GenericFacadeDelegate(useCases, validationsList);

        facade.exec(source);
        assertFalse(source.map().isEmpty());

        var accountDto = (AccountDTO) source.map().get(TypeAccountMapperEnum.ACCOUNT.name());
        var assertAccountDTO = buildMockAccount(true).build();
        assertNotNull(accountDto);
        assertEquals(assertAccountDTO.number(), accountDto.number());
        assertEquals(assertAccountDTO.agency(), accountDto.agency());
        assertEquals(assertAccountDTO.balance(), accountDto.balance());
        assertEquals(assertAccountDTO.active(), accountDto.active());
        assertEquals(assertAccountDTO.password(), accountDto.password());
        assertEquals(assertAccountDTO.cpf(), accountDto.cpf());
        assertEquals(assertAccountDTO.transactions().size(), accountDto.transactions().size());
    }
}
