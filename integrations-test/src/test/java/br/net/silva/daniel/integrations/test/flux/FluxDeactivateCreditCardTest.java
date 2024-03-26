package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.usecase.DeactivateCreditCardUseCase;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.business.validations.CreditCardNumberExistsValidate;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FluxDeactivateCreditCardTest extends AbstractBuilder {

    private UseCase<EmptyOutput> deactivateCreditCardUseCase;

    private IValidations clientExistsValidate;
    private IValidations accountExistsValidate;
    private IValidations creditCardExistsValidate;

    @Mock
    private Repository<Optional<ClientOutput>> findClientRepository;

    @Mock
    private Repository<Optional<AccountOutput>> findAccountRepository;

    @Mock
    private Repository<AccountOutput> findAccountByCpfAndAccountNumberAndAgencyRepository;

    @Mock
    private Repository<AccountOutput> saveAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findClientRepository.exec(anyString())).thenReturn(Optional.of(buildMockClient(true)));
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        when(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccountWithCreditCard(true, true));
        when(saveAccountRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));

        // Use Case
        deactivateCreditCardUseCase = new DeactivateCreditCardUseCase(findAccountByCpfAndAccountNumberAndAgencyRepository, saveAccountRepository);

        // Validations
//        clientExistsValidate = new ClientExistsValidate(new FindClientUseCase(findClientRepository, buildFactoryResponse()));
        accountExistsValidate = new AccountExistsValidate(findAccountRepository);
        creditCardExistsValidate = new CreditCardNumberExistsValidate(findAccountByCpfAndAccountNumberAndAgencyRepository);
    }

    @Test
    void shouldDeactivateCreditCardWithSuccess() {
        var input = new DeactivateCreditCardInput("99988877766", 1, 1234, "5400639532571841");
        var source = new Source(input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(deactivateCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsValidate);
        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);

        assertDoesNotThrow(() -> facade.exec(source));

        verify(saveAccountRepository, times(1)).exec(any(AccountOutput.class));
        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(2)).exec(anyInt(), anyInt(), anyString());
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorClientNotExistsAtDeactivateCreditCard() {
        when(findClientRepository.exec(anyString())).thenReturn(Optional.empty());
        var input = new DeactivateCreditCardInput("99988877766", 1, 1234, "5400639532571841");
        var source = new Source(input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(deactivateCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsValidate);
        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);

        var responseException = assertThrows(GenericException.class, () -> facade.exec(source));
        Assertions.assertEquals("Client not exists in database", responseException.getMessage());

        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, never()).exec(anyInt(), anyInt(), anyString());
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, never()).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorAccountNotExistsAtDeactivateCreditCard() {
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.empty());
        var input = new DeactivateCreditCardInput("99988877766", 1, 1234, "5400639532571841");
        var source = new Source(input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(deactivateCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsValidate);
        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);

        var responseException = assertThrows(GenericException.class, () -> facade.exec(source));
        Assertions.assertEquals("Account not exists", responseException.getMessage());

        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, never()).exec(anyInt(), anyInt(), anyString());
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorCreditCardNotExistsDeactivateCreditCard() {
        when(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(true));
        var input = new DeactivateCreditCardInput("99988877766", 1, 1234, "5400639532571841");
        var source = new Source(input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(deactivateCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsValidate);
        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);

        var exception = assertThrows(CreditCardNotExistsException.class, () -> facade.exec(source));
        Assertions.assertEquals("Credit card not exists in the account", exception.getMessage());

        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(anyInt(), anyInt(), anyString());
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorCreditCardNumberDifferentDeactivateCreditCard() {
        when(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccountWithCreditCard(true, true));
        var input = new DeactivateCreditCardInput("99988877766", 1, 1234, "5400639532599999");
        var source = new Source(input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(deactivateCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsValidate);
        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);

        var exception = assertThrows(CreditCardNumberDifferentException.class, () -> facade.exec(source));
        Assertions.assertEquals("Credit Card number is different at register in account", exception.getMessage());

        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(anyInt(), anyInt(), anyString());
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldErrorCreditCardAlreadyDeactivateDeactivateCreditCard() {
        when(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccountWithCreditCard(true, false));
        var input = new DeactivateCreditCardInput("99988877766", 1, 1234, "5400639532571841");
        var source = new Source(input);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(deactivateCreditCardUseCase);

        List<IValidations> validations = List.of(clientExistsValidate, accountExistsValidate, creditCardExistsValidate);
        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);

        var exception = assertThrows(CreditCardNotExistsException.class, () -> facade.exec(source));
        Assertions.assertEquals("Credit card deactivated in the account", exception.getMessage());

        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(anyInt(), anyInt(), anyString());
        verify(findClientRepository, times(1)).exec(anyString());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }



}
