package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.build.TransactionBuilder;
import br.net.silva.business.enums.AccountStatusEnum;
import br.net.silva.business.usecase.GetInformationAccountUseCase;
import br.net.silva.business.validations.AccountExistsAndActiveByCpfValidate;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;
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

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations accountExistsValidation;

    private IValidations clientExistsValidation;

    @Mock
    private Repository<AccountOutput> getInformationAccountByCpfRepository;

    @Mock
    private Repository<List<TransactionOutput>> transactionsRepository;

    @Mock
    private Repository<Optional<AccountOutput>> findAccountByCpfRepository;

    @Mock
    private Repository<Optional<ClientOutput>> findClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(transactionsRepository.exec(anyString(), anyInt())).thenReturn(buildListTransaction());
        when(findAccountByCpfRepository.exec(anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildMockClient(true)));
        when(getInformationAccountByCpfRepository.exec(anyString())).thenReturn(buildMockAccount(true));

        // Use Case
        getInformationAccountByCpfUseCase = new GetInformationAccountUseCase(getInformationAccountByCpfRepository, transactionsRepository, buildFactoryResponse());
//        findClientUseCase = new FindClientUseCase(findClientRepository, buildFactoryResponse());

        // Validations
        accountExistsValidation = new AccountExistsAndActiveByCpfValidate(findAccountByCpfRepository);
//        clientExistsValidation = new ClientExistsValidate(findClientUseCase);
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

        var mockAccount = AccountBuilder.buildFullAccountDto().createFrom(buildMockAccount(true));
        var mockTransaction = TransactionBuilder.buildFullTransactionListDto().createFrom(buildListTransaction());

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

        var mockAccount = AccountBuilder.buildFullAccountDto().createFrom(buildMockAccount(true));
        var mockTransaction = TransactionBuilder.buildFullTransactionListDto().createFrom(buildListTransaction());

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
