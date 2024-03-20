package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.ChangeAgencyUseCase;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.business.validations.AccountWithNewAgencyAlreadyExistsValidate;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FluxChangeAgencyAccountTest extends AbstractBuilder {

    private UseCase<EmptyOutput> changeAgencyAccountUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations clientExistsValidation;

    private IValidations accountExistsValidation;

    private IValidations accountWithNewAgencyAlreadyExistsValidation;

    @Mock
    private Repository<AccountOutput> findAccountByCpfAndAccountNumberRepository;

    @Mock
    private Repository<AccountOutput> saveAccountRepository;

    @Mock
    private ApplicationBaseGateway<ClientOutput> baseGateway;

    @Mock
    private Repository<Optional<AccountOutput>> findAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountByCpfAndAccountNumberRepository.exec(anyString(), anyInt(), anyInt())).thenReturn(buildMockAccount(true));
        when(saveAccountRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockClient(true)));
        when(findAccountRepository.exec(anyInt(), anyInt())).thenReturn(Optional.empty());
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));

        // Use Case
        changeAgencyAccountUseCase = new ChangeAgencyUseCase(findAccountByCpfAndAccountNumberRepository, saveAccountRepository);
        findClientUseCase = new FindClientUseCase(baseGateway, buildFactoryResponse());

        // Validations
//        clientExistsValidation = new ClientExistsValidate(findClientUseCase);
        accountExistsValidation = new AccountExistsValidate(findAccountRepository);
        accountWithNewAgencyAlreadyExistsValidation = new AccountWithNewAgencyAlreadyExistsValidate(findAccountRepository);
    }

    @Test
    void shouldChangeAgencyWithSuccess() {
        var changeAgencyInput = new ChangeAgencyInput("12345678900", 1, 45678, 55555);
        var source = new Source(EmptyOutput.INSTANCE, changeAgencyInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(changeAgencyAccountUseCase);

        var validations = List.of(clientExistsValidation, accountExistsValidation, accountWithNewAgencyAlreadyExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        assertDoesNotThrow(() -> facade.exec(source));

        verify(findAccountByCpfAndAccountNumberRepository, times(1)).exec(changeAgencyInput.cpf(), changeAgencyInput.accountNumber(), changeAgencyInput.agency());
        verify(saveAccountRepository, times(2)).exec(any(AccountOutput.class));
        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
    }

    @Test
    void shouldChangeAgencyErrorClientNotExists() {
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.empty());
        var changeAgencyInput = new ChangeAgencyInput("12345678900", 1, 45678, 55555);
        var source = new Source(EmptyOutput.INSTANCE, changeAgencyInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(changeAgencyAccountUseCase);

        var validations = List.of(clientExistsValidation, accountExistsValidation, accountWithNewAgencyAlreadyExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var responseError = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Client not exists in database", responseError.getMessage());
        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void shouldChangeAgencyWithErrorAccountNotExists() {
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.empty());
        var changeAgencyInput = new ChangeAgencyInput("12345678900", 1, 45678, 55555);
        var source = new Source(EmptyOutput.INSTANCE, changeAgencyInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(changeAgencyAccountUseCase);

        var validations = List.of(clientExistsValidation, accountExistsValidation, accountWithNewAgencyAlreadyExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var responseError = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Account not exists", responseError.getMessage());

        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
        verify(findAccountByCpfAndAccountNumberRepository, never()).exec(changeAgencyInput.cpf(), changeAgencyInput.accountNumber(), changeAgencyInput.agency());
        verify(saveAccountRepository, never()).exec(any(Account.class));
        verify(findAccountRepository, never()).exec(anyInt(), anyInt());
    }

    @Test
    void shouldChangeAgencyWithErrorAccountExistsWithAccountNumberAndNewAgencyNumber() {
        when(findAccountRepository.exec(anyInt(), anyInt())).thenReturn(Optional.of(buildMockAccount(true)));
        var changeAgencyInput = new ChangeAgencyInput("12345678900", 1, 45678, 55555);
        var source = new Source(EmptyOutput.INSTANCE, changeAgencyInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(changeAgencyAccountUseCase);

        var validations = List.of(clientExistsValidation, accountExistsValidation, accountWithNewAgencyAlreadyExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var responseError = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Account with new agency already exists", responseError.getMessage());

        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt());
        verify(findAccountByCpfAndAccountNumberRepository, never()).exec(changeAgencyInput.cpf(), changeAgencyInput.accountNumber(), changeAgencyInput.agency());
        verify(saveAccountRepository, never()).exec(any(Account.class));
    }

    @Test
    void shouldChangeAgencyWithErrorGenericError() {
        when(findAccountByCpfAndAccountNumberRepository.exec(anyString(), anyInt(), anyInt())).thenReturn(null);
        var changeAgencyInput = new ChangeAgencyInput("12345678900", 1, 45678, 55555);
        var source = new Source(EmptyOutput.INSTANCE, changeAgencyInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(changeAgencyAccountUseCase);

        var validations = List.of(clientExistsValidation, accountExistsValidation, accountWithNewAgencyAlreadyExistsValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var responseError = assertThrows(GenericException.class, () -> facade.exec(source));
        assertEquals("Generic error", responseError.getMessage());

        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt(), anyString());
        verify(findAccountRepository, times(1)).exec(anyInt(), anyInt());
        verify(findAccountByCpfAndAccountNumberRepository, times(1)).exec(changeAgencyInput.cpf(), changeAgencyInput.accountNumber(), changeAgencyInput.agency());
        verify(saveAccountRepository, never()).exec(any(Account.class));
    }
}
