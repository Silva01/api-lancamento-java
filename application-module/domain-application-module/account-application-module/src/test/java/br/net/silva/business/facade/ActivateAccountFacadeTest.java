package br.net.silva.business.facade;

import br.net.silva.business.dto.FindAccountDTO;
import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.usecase.ActivateAccountUseCase;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.ConverterUtils;
import br.net.silva.daniel.shared.business.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ActivateAccountFacadeTest {

    private ActivateAccountUseCase activateAccountUseCase;

    private IValidations accountExistsValidate;

    @Mock
    private Repository<Account> activateAccountRepository;

    @Mock
    private Repository<Account> findAccountRepository;

    @Mock
    private Repository<Optional<Account>> optionalFindAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.activateAccountUseCase = new ActivateAccountUseCase(activateAccountRepository, findAccountRepository);
        this.accountExistsValidate = new AccountExistsValidate(optionalFindAccountRepository);
    }

    @Test
    void shouldActivateAccountWithSuccess() throws GenericException {
        when(activateAccountRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));
        when(optionalFindAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(false)));
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(false));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateAccountUseCase);

        List<IValidations> validationsList = List.of(accountExistsValidate);

        var facade = new GenericFacadeDelegate(useCases, validationsList);
        var dtoRequest = new FindAccountDTO("99988877766", 45678, 4321888, null);
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(dtoRequest)));

        facade.exec(source);

        assertNotNull(source);

        var dtoResponse = (AccountDTO) source.map().get(TypeAccountMapperEnum.ACCOUNT.name());

        assertNotNull(dtoResponse);
        assertTrue(dtoResponse.active());
    }

    @Test
    void shouldActivateAccountErrorWhenAccountNotExists() {
        when(activateAccountRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));
        when(optionalFindAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.empty());
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(false));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateAccountUseCase);

        List<IValidations> validationsList = List.of(accountExistsValidate);

        var facade = new GenericFacadeDelegate(useCases, validationsList);
        var dtoRequest = new FindAccountDTO("99988877766", 45678, 4321888, null);
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(dtoRequest)));

        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account not exists", exceptionResponse.getMessage());
    }

    @Test
    void shouldActivateAccountErrorWhenAccountIsActive() {
        when(activateAccountRepository.exec(any(Account.class))).thenReturn(buildMockAccount(true));
        when(optionalFindAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(false));

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(activateAccountUseCase);

        List<IValidations> validationsList = List.of(accountExistsValidate);

        var facade = new GenericFacadeDelegate(useCases, validationsList);
        var dtoRequest = new FindAccountDTO("99988877766", 45678, 4321888, null);
        var source = new Source(new HashMap<>(), ConverterUtils.convertJsonToInputMap(ConverterUtils.convertObjectToJson(dtoRequest)));

        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account already active", exceptionResponse.getMessage());
    }

    private Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }
}
