package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.validations.AccountNotExistsByAgencyAndCPFValidate;
import br.net.silva.business.value_object.input.CreateNewAccountByCpfDTO;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.NewAccountResponse;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FluxCreateNewAccountTest extends AbstractBuilder {

    private UseCase<AccountOutput> createNewAccountByCpfUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations accountNotExistsValidate;

    private IValidations clientExistsValidate;

    @Mock
    private ApplicationBaseGateway<ClientOutput> clientBaseGateway;

    @Mock
    private ApplicationBaseGateway<AccountOutput> accountBaseGateway;

    @Mock
    private Repository<Optional<AccountOutput>> findAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clientBaseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.ofNullable(buildMockClient(true)));
        when(accountBaseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.empty());
        when(accountBaseGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount(true));
        when(findAccountRepository.exec(anyInt(), anyString())).thenReturn(Optional.empty());

        createNewAccountByCpfUseCase = new CreateNewAccountByCpfUseCase(accountBaseGateway, buildFactoryResponse());
        findClientUseCase = new FindClientUseCase(clientBaseGateway, buildFactoryResponse());
//        clientExistsValidate = new ClientExistsValidate(findClientUseCase);
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

        verify(clientBaseGateway, times(1)).findById(any(ParamGateway.class));
        verify(accountBaseGateway, times(1)).findById(any(ParamGateway.class));
        verify(accountBaseGateway, times(1)).save(any(AccountOutput.class));
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
