package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DeactivateAccountUseCaseTest {

    private DeactivateAccountUseCase deactivateAccountUseCase;

    @Mock
    private ApplicationBaseGateway<AccountOutput> deactivateAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deactivateAccountUseCase = new DeactivateAccountUseCase(deactivateAccountRepository, new GenericResponseMapper(Collections.emptyList()));
    }

    @Test
    void mustDeactivateAccountWithSuccess() throws GenericException {
        when(deactivateAccountRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true)));
        when(deactivateAccountRepository.save(any(AccountOutput.class))).thenReturn(buildMockAccount(false));

        var deactivateAccount = new DeactivateAccount("99988877766", null, null);
        var source = new Source(EmptyOutput.INSTANCE, deactivateAccount);
        var account = deactivateAccountUseCase.exec(source);
        assertNotNull(account);

        var emptyResponse = source.output();
        assertNotNull(emptyResponse);
        Mockito.verify(deactivateAccountRepository, Mockito.times(1)).save(any(AccountOutput.class));
        Mockito.verify(deactivateAccountRepository, Mockito.times(1)).findById(any(ParamGateway.class));
        assertFalse(account.active());
    }

    @Test
    void mustDeactivateAccountWithErrorAnyone() {
        when(deactivateAccountRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true)));
        when(deactivateAccountRepository.save(any(AccountOutput.class))).thenReturn(buildMockAccount(false));
        Source source = null;
        assertThrows(GenericException.class, () -> deactivateAccountUseCase.exec(source));
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}