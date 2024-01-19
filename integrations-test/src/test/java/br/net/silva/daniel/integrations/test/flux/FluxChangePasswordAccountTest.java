package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.usecase.ChangePasswordAccountUseCase;
import br.net.silva.business.usecase.FindAccountUseCase;
import br.net.silva.business.validations.PasswordAndExistsAccountValidate;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class FluxChangePasswordAccountTest extends AbstractBuilder {

    private UseCase changePasswordAccountUseCase;

    private UseCase findAccountUseCase;

    private UseCase findClientUseCase;

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
        when(findCLientRepository.exec(anyString())).thenReturn(Optional.ofNullable(buildMockClient()));

        //Use Cases
        findAccountUseCase = new FindAccountUseCase(findAccountRepository, null);
        changePasswordAccountUseCase = new ChangePasswordAccountUseCase(findAccountUseCase, updatePasswordRepository);
        findClientUseCase = new FindClientUseCase(findCLientRepository);

        //Validations
        clientExistsValidate = new ClientExistsValidate(findClientUseCase);
        passwordAndExistsAccountValidate = new PasswordAndExistsAccountValidate(findAccountUseCase);
    }

    @Test
    void shouldChangePasswordAccountWithSuccess() throws GenericException {
        when(updatePasswordRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678901");
        inputMap.put("agency", "1234");
        inputMap.put("account", "1");
        inputMap.put("password", "978534");
        inputMap.put("newPassword", "1234567");

        var source = new Source(new HashMap<>(), inputMap);

        Queue<UseCase> queueUseCase = new LinkedList<>();
        queueUseCase.add(changePasswordAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(passwordAndExistsAccountValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        facade.exec(source);

        var responseDto = (AccountDTO) source.map().get(TypeAccountMapperEnum.ACCOUNT.name());
        assertionAccount(buildMockAccount(true).build(), responseDto);
    }

    @Test
    void shouldChangePasswordAccountWithErrorPasswordIsDifferent() {
        when(updatePasswordRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678901");
        inputMap.put("agency", "1234");
        inputMap.put("account", "1");
        inputMap.put("password", "123490");
        inputMap.put("newPassword", "1234567");

        var source = new Source(new HashMap<>(), inputMap);

        Queue<UseCase> queueUseCase = new LinkedList<>();
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

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678901");
        inputMap.put("agency", "1234");
        inputMap.put("account", "1");
        inputMap.put("password", "123499");
        inputMap.put("newPassword", "1234567");

        var source = new Source(new HashMap<>(), inputMap);

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

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678901");
        inputMap.put("agency", "1234");
        inputMap.put("account", "1");
        inputMap.put("password", "1234");
        inputMap.put("newPassword", "1234567");

        var source = new Source(new HashMap<>(), inputMap);

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

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678901");
        inputMap.put("agency", "1234");
        inputMap.put("account", "1");
        inputMap.put("password", "978534");
        inputMap.put("newPassword", "1234567");

        var source = new Source(new HashMap<>(), inputMap);

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

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("cpf", "12345678901");
        inputMap.put("agency", "1234");
        inputMap.put("account", "1");
        inputMap.put("password", "978534");
        inputMap.put("newPassword", "1234567");

        var source = new Source(new HashMap<>(), inputMap);

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
