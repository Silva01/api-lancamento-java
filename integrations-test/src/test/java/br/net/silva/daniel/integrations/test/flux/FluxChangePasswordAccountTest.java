package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.business.usecase.ChangePasswordAccountUseCase;
import br.net.silva.business.usecase.FindAccountUseCase;
import br.net.silva.business.validations.PasswordAndExistsAccountValidate;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.business.value_object.output.AccountOutput;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class FluxChangePasswordAccountTest extends AbstractBuilder {

    private UseCase<EmptyOutput> changePasswordAccountUseCase;

    private UseCase<AccountOutput> findAccountUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations clientExistsValidate;

    private IValidations passwordAndExistsAccountValidate;

    @Mock
    private Repository<AccountOutput> updatePasswordRepository;

    @Mock
    private Repository<Optional<AccountOutput>> findAccountRepository;

    @Mock
    private ApplicationBaseGateway<ClientOutput> baseGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountRepository.exec(anyInt(), anyInt())).thenReturn(Optional.ofNullable(buildMockAccount(true)));
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.ofNullable(buildMockClient(true)));

        //Use Cases
//        findAccountUseCase = new FindAccountUseCase(findAccountRepository, buildFactoryResponse());
        changePasswordAccountUseCase = new ChangePasswordAccountUseCase(findAccountUseCase, updatePasswordRepository);
        findClientUseCase = new FindClientUseCase(baseGateway, buildFactoryResponse());

        //Validations
//        clientExistsValidate = new ClientExistsValidate(findClientUseCase);
        passwordAndExistsAccountValidate = new PasswordAndExistsAccountValidate(findAccountUseCase);
    }

    @Test
    void shouldChangePasswordAccountWithSuccess() throws GenericException {
        when(updatePasswordRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));

        var changePassord = new ChangePasswordDTO("12345678901", 1234, 1, "978534", "1234567");
        var source = new Source(EmptyOutput.INSTANCE, changePassord);

        Queue<UseCase<?>> queueUseCase = new LinkedList<>();
        queueUseCase.add(changePasswordAccountUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistsValidate);
        validations.add(passwordAndExistsAccountValidate);

        var facade = new GenericFacadeDelegate<>(queueUseCase, validations);
        facade.exec(source);

        verify(updatePasswordRepository, times(1)).exec(any(AccountOutput.class));
        verify(findAccountRepository, times(2)).exec(anyInt(), anyInt());
        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void shouldChangePasswordAccountWithErrorPasswordIsDifferent() {
        when(updatePasswordRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));

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
        when(updatePasswordRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));

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
        when(updatePasswordRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));

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
        when(updatePasswordRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.empty());

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
        when(updatePasswordRepository.exec(any(AccountOutput.class))).thenReturn(buildMockAccount(true));

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
