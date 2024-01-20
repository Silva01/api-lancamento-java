package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.value_object.Source;
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

        var deactivateAccount = new DeactivateAccount("99988877766", null, null);
        var source = new Source(EmptyOutput.INSTANCE, deactivateAccount);
        var account = deactivateAccountUseCase.exec(source);
        assertNotNull(account);

        var emptyResponse = source.output();
        assertNotNull(emptyResponse);
        Mockito.verify(deactivateAccountRepository, Mockito.times(1)).exec(deactivateAccount.cpf());
        assertFalse(account.active());
    }

    @Test
    void mustDeactivateAccountWithErrorAnyone() {
        when(deactivateAccountRepository.exec(Mockito.any(String.class))).thenReturn(buildMockAccount(false));
        Source source = null;
        assertThrows(GenericException.class, () -> deactivateAccountUseCase.exec(source));
    }

    private Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}