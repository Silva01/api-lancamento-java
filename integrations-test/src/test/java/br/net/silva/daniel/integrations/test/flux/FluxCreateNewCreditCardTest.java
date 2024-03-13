package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.CreateNewCreditCardUseCase;
import br.net.silva.business.validations.AccountAlreadyExistsCreditCardValidation;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class FluxCreateNewCreditCardTest extends AbstractBuilder {

    private UseCase<AccountOutput> createNewCreditCardUseCase;

    private IValidations clientExistsValidate;
    private IValidations accountExistsValidate;
    private IValidations creditCardExistsInAccountValidate;

    @Mock
    private Repository<AccountOutput> findAccountByCpfAndAgencyAndAccountNumberRepository;

    @Mock
    private Repository<AccountOutput> saveAccountRepository;

    @Mock
    private Repository<Optional<ClientOutput>> findClientRepository;

    @Mock
    Repository<Optional<AccountOutput>> findAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountByCpfAndAgencyAndAccountNumberRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(true));
        when(saveAccountRepository.exec(any(Account.class))).thenReturn(buildMockAccountWithCreditCard(true, true));
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildMockClient(true)));
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));

        // Use Case
        createNewCreditCardUseCase = new CreateNewCreditCardUseCase(findAccountByCpfAndAgencyAndAccountNumberRepository, saveAccountRepository);

        // Validations
        clientExistsValidate = new ClientExistsValidate(new FindClientUseCase(findClientRepository, buildFactoryResponse()));
        accountExistsValidate = new AccountExistsValidate(findAccountRepository);
        creditCardExistsInAccountValidate = new AccountAlreadyExistsCreditCardValidation(findAccountRepository);
    }

    @Test
    void shouldCreateNewCreditCardForAccountThatNotHaveCreditCard() {
        var input = new CreateCreditCardInput("99988877766", 1, 45678);
        var source = new Source(EmptyOutput.INSTANCE, input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(createNewCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsInAccountValidate);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        assertDoesNotThrow(() -> facade.exec(source));

        verify(findAccountByCpfAndAgencyAndAccountNumberRepository, times(1)).exec(anyInt(), anyInt(), anyString());
        verify(saveAccountRepository, times(1)).exec(any(AccountOutput.class));
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(2)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorClientNotExistsWhenCreateNewCreditCard() {
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());
        var input = new CreateCreditCardInput("99988877766", 1, 45678);
        var source = new Source(EmptyOutput.INSTANCE, input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(createNewCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsInAccountValidate);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var response = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Client not exists in database", response.getMessage());

        verify(findAccountByCpfAndAgencyAndAccountNumberRepository, never()).exec(anyInt(), anyInt(), anyString());
        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, never()).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorAccountNotExistsWhenCreateNewCreditCard() {
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.empty());
        var input = new CreateCreditCardInput("99988877766", 1, 45678);
        var source = new Source(EmptyOutput.INSTANCE, input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(createNewCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsInAccountValidate);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var response = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Account not exists", response.getMessage());

        verify(findAccountByCpfAndAgencyAndAccountNumberRepository, never()).exec(anyInt(), anyInt(), anyString());
        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorCreditCardActivatedAlreadyExistsWhenCreateNewCreditCard() {
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccountWithCreditCard(true, true)));
        var input = new CreateCreditCardInput("99988877766", 1, 45678);
        var source = new Source(EmptyOutput.INSTANCE, input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(createNewCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsInAccountValidate);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var response = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("This account already have a credit card", response.getMessage());

        verify(findAccountByCpfAndAgencyAndAccountNumberRepository, never()).exec(anyInt(), anyInt(), anyString());
        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(2)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorCreditCardDeactivatedAlreadyExistsWhenCreateNewCreditCard() {
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccountWithCreditCard(true, false)));
        var input = new CreateCreditCardInput("99988877766", 1, 45678);
        var source = new Source(EmptyOutput.INSTANCE, input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(createNewCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsInAccountValidate);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var response = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("This account already have a credit card", response.getMessage());

        verify(findAccountByCpfAndAgencyAndAccountNumberRepository, never()).exec(anyInt(), anyInt(), anyString());
        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(2)).exec(anyInt(), anyInt(), anyString());
    }
}
