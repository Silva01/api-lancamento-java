package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.ChangePasswordAccountUseCase;
import br.net.silva.business.usecase.FindAccountUseCase;
import br.net.silva.business.validations.PasswordAndExistsAccountValidate;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FluxChangePasswordAccountTest extends AbstractBuilder {

    private UseCase<AccountDTO> changePasswordAccountUseCase;

    private UseCase<AccountDTO> findAccountUseCase;

    private UseCase<ClientDTO> findClientUseCase;

    private IValidations clientExistsValidate;

    private IValidations passwordAndExistsAccountValidate;

    @Mock
    private Repository<Account> updatePasswordRepository;

    @Mock
    private Repository<Optional<Account>> findAccountRepository;

    @Mock
    private Repository<Optional<Client>> findCLientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountRepository.exec(anyInt(), anyInt())).thenReturn(Optional.ofNullable(buildMockAccount(true)));
        when(findCLientRepository.exec(anyString())).thenReturn(Optional.ofNullable(buildMockClient(true)));

        //Use Cases
        findAccountUseCase = new FindAccountUseCase(findAccountRepository, buildFactoryResponse());
        changePasswordAccountUseCase = new ChangePasswordAccountUseCase(findAccountUseCase, updatePasswordRepository);
        findClientUseCase = new FindClientUseCase(findCLientRepository, buildFactoryResponse());

        //Validations
        clientExistsValidate = new ClientExistsValidate(findClientUseCase);
        passwordAndExistsAccountValidate = new PasswordAndExistsAccountValidate(findAccountUseCase);
    }

    @Test
    void shouldChangePasswordAccountWithSuccess() throws GenericException {
        when(updatePasswordRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));

        var changePassord = new ChangePasswordDTO("12345678901", 1234, 1, "978534", "1234567");
        var source = new Source(EmptyOutput.INSTANCE, changePassord);

        Queue<UseCase<?>> queueUseCase = new LinkedList<>();
        queueUseCase.add(changePasswordAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(passwordAndExistsAccountValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        facade.exec(source);

        verify(updatePasswordRepository, times(1)).exec(any(Account.class));
        verify(findAccountRepository, times(2)).exec(anyInt(), anyInt());
        verify(findCLientRepository, times(1)).exec(anyString());
    }

    @Test
    void shouldChangePasswordAccountWithErrorPasswordIsDifferent() {
        when(updatePasswordRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));

        var changePassord = new ChangePasswordDTO("12345678901", 1234, 1, "978501", "1234567");
        var source = new Source(EmptyOutput.INSTANCE, changePassord);

        Queue<UseCase<?>> queueUseCase = new LinkedList<>();
        queueUseCase.add(changePasswordAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(passwordAndExistsAccountValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        var exceptionResponde = assertThrows(IllegalArgumentException.class, () -> facade.exec(source));
        Assertions.assertEquals("Password is different", exceptionResponde.getMessage());
    }

    @Test
    void shouldChangePasswordAccountWithErrorPasswordHasRepeatNumbers() {
        when(updatePasswordRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));

        var changePassord = new ChangePasswordDTO("12345678901", 1234, 1, "978599", "1234567");
        var source = new Source(EmptyOutput.INSTANCE, changePassord);

        Queue<UseCase> queueUseCase = new LinkedList<>();
        queueUseCase.add(changePasswordAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(passwordAndExistsAccountValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        var exceptionResponde = assertThrows(IllegalArgumentException.class, () -> facade.exec(source));
        Assertions.assertEquals("Password cannot have repeated numbers", exceptionResponde.getMessage());
    }

    @Test
    void shouldChangePasswordAccountWithErrorPasswordHasThanSixNumbers() {
        when(updatePasswordRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));

        var changePassord = new ChangePasswordDTO("12345678901", 1234, 1, "1234", "1234567");
        var source = new Source(EmptyOutput.INSTANCE, changePassord);

        Queue<UseCase> queueUseCase = new LinkedList<>();
        queueUseCase.add(changePasswordAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(passwordAndExistsAccountValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        var exceptionResponde = assertThrows(IllegalArgumentException.class, () -> facade.exec(source));
        Assertions.assertEquals("Password must be greater than 6", exceptionResponde.getMessage());
    }

    @Test
    void shouldChangePasswordAccountWithErrorCpfNotExists() {
        when(updatePasswordRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));
        when(findCLientRepository.exec(anyString())).thenReturn(Optional.empty());

        var changePassord = new ChangePasswordDTO("12345678901", 1234, 1, "978534", "1234567");
        var source = new Source(EmptyOutput.INSTANCE, changePassord);

        Queue<UseCase> queueUseCase = new LinkedList<>();
        queueUseCase.add(changePasswordAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(passwordAndExistsAccountValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        var exceptionResponde = assertThrows(GenericException.class, () -> facade.exec(source));
        Assertions.assertEquals("Client not exists in database", exceptionResponde.getMessage());
    }

    @Test
    void shouldChangePasswordAccountWithErrorAccountDeactivatedOrNotExists() {
        when(updatePasswordRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));

        // Repository representa uma busca de conta ativada, como não tem nenhuma conta ativada, retorna vazio
        when(findAccountRepository.exec(anyInt(), anyInt())).thenReturn(Optional.empty());

        var changePassord = new ChangePasswordDTO("12345678901", 1234, 1, "978534", "1234567");
        var source = new Source(EmptyOutput.INSTANCE, changePassord);

        Queue<UseCase> queueUseCase = new LinkedList<>();
        queueUseCase.add(changePasswordAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(passwordAndExistsAccountValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        var exceptionResponde = assertThrows(GenericException.class, () -> facade.exec(source));
        Assertions.assertEquals("Account not found", exceptionResponde.getMessage());
    }
}
