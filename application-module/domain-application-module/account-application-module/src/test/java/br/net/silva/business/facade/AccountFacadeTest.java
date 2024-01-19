package br.net.silva.business.facade;

import br.net.silva.business.exception.AccountExistsForCPFInformatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.usecase.*;
import br.net.silva.business.validations.PasswordAndExistsAccountValidate;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.business.value_object.input.CreateNewAccountByCpfDTO;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountFacadeTest {

    private UseCase<AccountDTO> createNewAccountByCpfUseCase;

    private UseCase<AccountDTO> changePasswordAccountUseCase;

    private UseCase<AccountDTO> deactivateAccountUseCase;

    private UseCase<AccountDTO> findAccountByCpfUseCase;

    private IValidations passwordAndExistsAccountValidate;

    @Mock
    private Repository<Boolean> findIsExistsPeerCPFRepository;

    @Mock
    private Repository<Account> saveRepository;

    @Mock
    private Repository<Optional<Account>> findAccountRepository;

    @Mock
    private Repository<Account> deactivateAccountRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        createNewAccountByCpfUseCase = new CreateNewAccountByCpfUseCase(findIsExistsPeerCPFRepository, saveRepository);
        var findAccountByCpfUseCase = new FindAccountByCpfUseCase(findAccountRepository);
        passwordAndExistsAccountValidate = new PasswordAndExistsAccountValidate(findAccountByCpfUseCase);
        changePasswordAccountUseCase = new ChangePasswordAccountUseCase(new FindAccountUseCase(findAccountRepository), saveRepository);
        this.deactivateAccountUseCase = new DeactivateAccountUseCase(deactivateAccountRepository);
    }

    @Test
    void mustCreateNewAccountByCpf() throws GenericException {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(false);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyString())).thenReturn(Optional.of(buildMockAccount()));
        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(EmptyOutput.INSTANCE, createNewAccountByCpfDTO); //TODO: precisa substituir o EmptyOutput por um DTO de resposta

        accountFacade.exec(source);
        assertNotNull(source.output());
    }

    @Test
    void mustErrorCreateNewAccountByCpfAccountNotExists() {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(true);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyString())).thenReturn(Optional.of(buildMockAccount()));
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(EmptyOutput.INSTANCE, createNewAccountByCpfDTO); //TODO: precisa substituir o EmptyOutput por um DTO de resposta

        var exceptionResponse = assertThrows(AccountExistsForCPFInformatedException.class, () -> accountFacade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Exists account active for CPF informated", exceptionResponse.getMessage());
    }

    @Test
    void mustErrorCreateNewAccountByCpfAccountNotExistsValidation() {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(true);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyString())).thenReturn(Optional.empty());
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(EmptyOutput.INSTANCE, createNewAccountByCpfDTO); //TODO: precisa substituir o EmptyOutput por um DTO de resposta

        var exceptionResponse = assertThrows(AccountNotExistsException.class, () -> accountFacade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account not found", exceptionResponse.getMessage());
    }

    @Test
    void mustErrorCreateNewAccountByCpfPasswordInvalid() {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(true);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyString())).thenReturn(Optional.of(buildMockAccount()));
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "123456");
        var source = new Source(EmptyOutput.INSTANCE, createNewAccountByCpfDTO); //TODO: precisa substituir o EmptyOutput por um DTO de resposta

        var exceptionResponse = assertThrows(IllegalArgumentException.class, () -> accountFacade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Password is different", exceptionResponse.getMessage());
    }

    @Test
    void mustChangePasswordWithSuccess() throws GenericException {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(false);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyString())).thenReturn(Optional.of(buildMockAccount()));
        when(findAccountRepository.exec(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.of(buildMockAccount()));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(changePasswordAccountUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("99988877766", 45678, 1, "978534", "123456");
        var source = new Source(EmptyOutput.INSTANCE, changePasswordDTO); //TODO: precisa substituir o EmptyOutput por um DTO de resposta
        accountFacade.exec(source);

        assertNotNull(source.output());
    }

    @Test
    void mustDeactivateAccountWithSuccess() throws GenericException {
        when(deactivateAccountRepository.exec(Mockito.any(String.class))).thenReturn(buildMockAccount());

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(deactivateAccountUseCase);

        List<IValidations> validationsList = Collections.emptyList();

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("99988877766", 45678, 1, "978534", "123456");
        var source = new Source(EmptyOutput.INSTANCE, changePasswordDTO); //TODO: precisa substituir o EmptyOutput por um DTO de resposta

        accountFacade.exec(source);
        assertNotNull(source.output());
    }

    private Account buildMockAccount() {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), true, "99988877766", null, Collections.emptyList());
    }
}