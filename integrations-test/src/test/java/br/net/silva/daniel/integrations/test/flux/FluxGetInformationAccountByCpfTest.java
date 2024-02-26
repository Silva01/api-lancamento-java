package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.enums.AccountStatusEnum;
import br.net.silva.business.usecase.GetInformationAccountUseCase;
import br.net.silva.business.validations.AccountExistsAndActiveByCpfValidate;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.entity.Transaction;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FluxGetInformationAccountByCpfTest extends AbstractBuilder {

    private UseCase<AccountOutput> getInformationAccountByCpfUseCase;

    private UseCase<ClientDTO> findClientUseCase;

    private IValidations accountExistsValidation;

    private IValidations clientExistsValidation;

    @Mock
    private Repository<Account> getInformationAccountByCpfRepository;

    @Mock
    private Repository<List<Transaction>> transactionsRepository;

    @Mock
    private Repository<Optional<Account>> findAccountByCpfRepository;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(transactionsRepository.exec(anyString(), anyInt())).thenReturn(buildListTransaction());
        when(findAccountByCpfRepository.exec(anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildMockClient(true)));
        when(getInformationAccountByCpfRepository.exec(anyString())).thenReturn(buildMockAccount(true));

        // Use Case
        getInformationAccountByCpfUseCase = new GetInformationAccountUseCase(getInformationAccountByCpfRepository, transactionsRepository, buildFactoryResponse());
        findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());

        // Validations
        accountExistsValidation = new AccountExistsAndActiveByCpfValidate(findAccountByCpfRepository);
        clientExistsValidation = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldValidInformationAccountWithSuccess() throws GenericException {
        var getInformationAccountInput = new GetInformationAccountInput("12345678900");
        var source = new Source(new GetInformationAccountOutput(), getInformationAccountInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(getInformationAccountByCpfUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        facade.exec(source);

        var output = (GetInformationAccountOutput) source.output();

        var mockAccount = buildMockAccount(true).build();
        var mockTransaction = buildListTransaction().stream().map(Transaction::build).toList();

        assertEquals(mockAccount.number(), output.getAccountNumber());
        assertEquals(mockAccount.agency(), output.getAgency());
        assertEquals(mockAccount.balance(), output.getBalance());
        assertEquals(Objects.nonNull(mockAccount.creditCard()), output.isHaveCreditCard());
        assertEquals(AccountStatusEnum.ACTIVE, output.getStatus());
        assertEquals(mockTransaction, output.getTransactions());
    }

    @Test
    void shouldValidInformationAccountWithoutTransactions() throws GenericException {
        when(transactionsRepository.exec(anyString(), anyInt())).thenReturn(Collections.emptyList());
        var getInformationAccountInput = new GetInformationAccountInput("12345678900");
        var source = new Source(new GetInformationAccountOutput(), getInformationAccountInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(getInformationAccountByCpfUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        facade.exec(source);

        var output = (GetInformationAccountOutput) source.output();

        var mockAccount = buildMockAccount(true).build();
        var mockTransaction = buildListTransaction().stream().map(Transaction::build).toList();

        assertEquals(mockAccount.number(), output.getAccountNumber());
        assertEquals(mockAccount.agency(), output.getAgency());
        assertEquals(mockAccount.balance(), output.getBalance());
        assertEquals(Objects.nonNull(mockAccount.creditCard()), output.isHaveCreditCard());
        assertEquals(AccountStatusEnum.ACTIVE, output.getStatus());
        assertTrue(output.getTransactions().isEmpty());
    }

    @Test
    void shouldErrorInformationAccountWhyAccountNotExists() {
        when(findAccountByCpfRepository.exec(anyString())).thenReturn(Optional.empty());
        var getInformationAccountInput = new GetInformationAccountInput("12345678900");
        var source = new Source(new GetInformationAccountOutput(), getInformationAccountInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(getInformationAccountByCpfUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var responseException = assertThrows(GenericException.class, () -> facade.exec(source));

        assertEquals("Account not exists", responseException.getMessage());
    }

    @Test
    void shouldErrorInformationAccountWhyClientNotExists() {
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());
        var getInformationAccountInput = new GetInformationAccountInput("12345678900");
        var source = new Source(new GetInformationAccountOutput(), getInformationAccountInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(getInformationAccountByCpfUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidation);
        validations.add(accountExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var responseException = assertThrows(GenericException.class, () -> facade.exec(source));

        assertEquals("Client not exists in database", responseException.getMessage());
    }
}
