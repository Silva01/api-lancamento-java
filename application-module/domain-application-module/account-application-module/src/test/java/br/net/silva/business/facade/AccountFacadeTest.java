package br.net.silva.business.facade;

import br.net.silva.business.dto.ChangePasswordDTO;
import br.net.silva.business.dto.CreateNewAccountByCpfDTO;
import br.net.silva.business.exception.AccountExistsForCPFInformatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.mapper.MapToAccountMapper;
import br.net.silva.business.usecase.ChangePasswordAccountUseCase;
import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.usecase.DeactivateAccountUseCase;
import br.net.silva.business.usecase.FindAccountUseCase;
import br.net.silva.business.validations.PasswordAndExistsAccountValidate;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.ConverterUtils;
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

    private UseCase createNewAccountByCpfUseCase;

    private UseCase changePasswordAccountUseCase;

    private UseCase deactivateAccountUseCase;

    private IValidations passwordAndExistsAccountValidate;

    private MapToAccountMapper mapper;

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
        UseCase findAccountUseCase = new FindAccountUseCase(findAccountRepository);
        passwordAndExistsAccountValidate = new PasswordAndExistsAccountValidate(findAccountUseCase);
        changePasswordAccountUseCase = new ChangePasswordAccountUseCase(new FindAccountUseCase(findAccountRepository), saveRepository);
        this.deactivateAccountUseCase = new DeactivateAccountUseCase(deactivateAccountRepository);
        this.mapper = MapToAccountMapper.INSTANCE;
    }

    @Test
    void mustCreateNewAccountByCpf() throws GenericException {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(false);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.of(buildMockAccount()));
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(createNewAccountByCpfDTO)));

        accountFacade.exec(source);

        AccountDTO accountDTO = mapper.mapToAccountDTO(source);
        assertNotNull(accountDTO);
    }

    @Test
    void mustErrorCreateNewAccountByCpfAccountNotExists() {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(true);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.of(buildMockAccount()));
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(createNewAccountByCpfDTO)));

        var exceptionResponse = assertThrows(AccountExistsForCPFInformatedException.class, () -> accountFacade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Exists account active for CPF informated", exceptionResponse.getMessage());
    }

    @Test
    void mustErrorCreateNewAccountByCpfAccountNotExistsValidation() {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(true);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.empty());
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(createNewAccountByCpfDTO)));

        var exceptionResponse = assertThrows(AccountNotExistsException.class, () -> accountFacade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account not found", exceptionResponse.getMessage());
    }

    @Test
    void mustErrorCreateNewAccountByCpfPasswordInvalid() {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(true);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.of(buildMockAccount()));
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "123456");
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(createNewAccountByCpfDTO)));

        var exceptionResponse = assertThrows(IllegalArgumentException.class, () -> accountFacade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Password is different", exceptionResponse.getMessage());
    }

    @Test
    void mustChangePasswordWithSuccess() throws GenericException {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(false);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.of(buildMockAccount()));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(changePasswordAccountUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("99988877766", 45678, 1, "978534", "123456");
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(changePasswordDTO)));
        accountFacade.exec(source);

        AccountDTO accountDTO = mapper.mapToAccountDTO(source);
        assertNotNull(accountDTO);
    }

    @Test
    void mustDeactivateAccountWithSuccess() throws GenericException {
        when(deactivateAccountRepository.exec(Mockito.any(String.class))).thenReturn(buildMockAccount());

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(deactivateAccountUseCase);

        List<IValidations> validationsList = Collections.emptyList();

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("99988877766", 45678, 1, "978534", "123456");
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(changePasswordDTO)));

        accountFacade.exec(source);

        AccountDTO accountDTO = mapper.mapToAccountDTO(source);
        assertNotNull(accountDTO);
    }

    private Account buildMockAccount() {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), true, "99988877766", null, Collections.emptyList());
    }
}