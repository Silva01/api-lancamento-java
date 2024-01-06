package br.net.silva.business.facade;

import br.net.silva.business.dto.CreateNewAccountByCpfDTO;
import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.usecase.FindAccountUseCase;
import br.net.silva.business.validations.PasswordAndExistsAccountValidate;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class AccountFacadeTest {

    private UseCase<IProcessResponse<AccountDTO>> createNewAccountByCpfUseCase;

    private IValidations passwordAndExistsAccountValidate;

    @Mock
    private Repository<Boolean> findIsExistsPeerCPFRepository;

    @Mock
    private Repository<Account> saveRepository;

    @Mock
    private Repository<Optional<Account>> findAccountRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        createNewAccountByCpfUseCase = new CreateNewAccountByCpfUseCase(findIsExistsPeerCPFRepository, saveRepository);
        UseCase<IProcessResponse<? extends IGenericPort>> findAccountUseCase = new FindAccountUseCase(findAccountRepository);
        passwordAndExistsAccountValidate = new PasswordAndExistsAccountValidate(findAccountUseCase);
    }

    @Test
    void mustCreateNewAccountByCpf() throws GenericException {
        when(findIsExistsPeerCPFRepository.exec(Mockito.anyString())).thenReturn(false);
        when(saveRepository.exec(Mockito.any(Account.class))).thenReturn(buildMockAccount());
        when(findAccountRepository.exec(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.of(buildMockAccount()));
        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");

        IProcessResponse<AccountDTO> response = accountFacade.exec(createNewAccountByCpfDTO);
        AccountDTO accountDTO = (AccountDTO) response.build();

        assertNotNull(accountDTO);
    }

    private Account buildMockAccount() {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), true, "99988877766", null, Collections.emptyList());
    }
}