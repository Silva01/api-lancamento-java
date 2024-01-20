package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.validations.AccountNotExistsByAgencyAndCPFValidate;
import br.net.silva.business.value_object.input.CreateNewAccountByCpfDTO;
import br.net.silva.business.value_object.output.NewAccountResponse;
import br.net.silva.daniel.dto.AccountDTO;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FluxCreateNewAccountTest extends AbstractBuilder {

    private UseCase<AccountDTO> createNewAccountByCpfUseCase;

    private UseCase<ClientDTO> findClientUseCase;

    private IValidations accountNotExistsValidate;

    private IValidations clientExistsValidate;

    @Mock
    private Repository<Boolean> findAccountIsExistsPeerCPFRepository;

    @Mock
    private Repository<Account> saveAccountRepository;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @Mock
    private Repository<Optional<Account>> findAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findClientRepository.exec(anyString())).thenReturn(Optional.ofNullable(buildMockClient(true)));
        when(findAccountIsExistsPeerCPFRepository.exec(anyString())).thenReturn(false);
        when(saveAccountRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));
        when(findAccountRepository.exec(anyInt(), anyString())).thenReturn(Optional.empty());

        createNewAccountByCpfUseCase = new CreateNewAccountByCpfUseCase(findAccountIsExistsPeerCPFRepository, saveAccountRepository, buildFactoryResponse());
        findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());
        clientExistsValidate = new ClientExistsValidate(findClientUseCase);
        accountNotExistsValidate = new AccountNotExistsByAgencyAndCPFValidate(findAccountRepository);
    }

    @Test
    void shouldCreateANewAccountWithSuccess() throws GenericException {
        var createNewAccount = new CreateNewAccountByCpfDTO("99988877766", 45678, "123456");
        var source = new Source(new NewAccountResponse(), createNewAccount);

        Queue<UseCase<?>> queueUseCase = new LinkedList<>();
        queueUseCase.add(createNewAccountByCpfUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(accountNotExistsValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        facade.exec(source);

        var response = (NewAccountResponse) source.output();
        assertNotNull(response);
        assertEquals(createNewAccount.agency(), response.getAgency());
        assertNotNull(response.getAccountNumber());

        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountIsExistsPeerCPFRepository, times(1)).exec(anyString());
        verify(saveAccountRepository, times(1)).exec(any(Account.class));
    }

    @Test
    void shouldCreateANewAccountWithErrorAccountExists() throws GenericException {
        when(findAccountRepository.exec(anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        var createNewAccount = new CreateNewAccountByCpfDTO("99988877766", 45678, "123456");
        var source = new Source(new NewAccountResponse(), createNewAccount);

        Queue<UseCase<?>> queueUseCase = new LinkedList<>();
        queueUseCase.add(createNewAccountByCpfUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(accountNotExistsValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account already active", exceptionResponse.getMessage());
    }

}
