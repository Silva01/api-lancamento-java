package br.net.silva.business.usecase;

import br.net.silva.business.dto.FindAccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DeactivateAccountUseCaseTest {

    private DeactivateAccountUseCase deactivateAccountUseCase;

    @Mock
    private Repository<Account> deactivateAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deactivateAccountUseCase = new DeactivateAccountUseCase(deactivateAccountRepository);
    }

    @Test
    void mustDeactivateAccountWithSuccess() throws GenericException {
        when(deactivateAccountRepository.exec(Mockito.any(String.class))).thenReturn(buildMockAccount(false));

        var findAccountDTO = new FindAccountDTO("99988877766", 0, 0, null);
        var account = deactivateAccountUseCase.exec(findAccountDTO);

        assertNotNull(account);
        Mockito.verify(deactivateAccountRepository, Mockito.times(1)).exec(findAccountDTO.cpf());

        var accountDTO = account.build();
        assertFalse(accountDTO.active());
    }

    @Test
    void mustDeactivateAccountWithErrorAnyone() {
        when(deactivateAccountRepository.exec(Mockito.any(String.class))).thenReturn(buildMockAccount(false));
        assertThrows(GenericException.class, () -> deactivateAccountUseCase.exec(null));
    }

    private Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}