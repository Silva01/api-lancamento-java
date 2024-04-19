package br.net.silva.business.usecase;

import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeactivateAccountUseCaseTest {

    private DeactivateAccountUseCase deactivateAccountUseCase;

    @Mock
    private ApplicationBaseGateway<AccountOutput> deactivateAccountRepository;

    @BeforeEach
    void setUp() {
        deactivateAccountUseCase = new DeactivateAccountUseCase(deactivateAccountRepository);
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
        verify(deactivateAccountRepository, times(1)).save(any(AccountOutput.class));
        verify(deactivateAccountRepository, times(1)).findById(any(ParamGateway.class));
        assertFalse(account.active());
    }

    @Test
    void deactivateAccount_WithAccountNotExists_ThrowsAccountNotExistsException() {
        when(deactivateAccountRepository.findById(any(ParamGateway.class))).thenReturn(Optional.empty());

        var deactivateAccount = new DeactivateAccount("99988877766", null, null);
        var source = new Source(EmptyOutput.INSTANCE, deactivateAccount);

        assertThatThrownBy(() -> deactivateAccountUseCase.exec(source))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage("Account not found");

        verify(deactivateAccountRepository, never()).save(any(AccountOutput.class));
        verify(deactivateAccountRepository, times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void deactivateAccount_WithAccountDeactivated_ThrowsAccountDeactivatedException() {
        when(deactivateAccountRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(false)));

        var deactivateAccount = new DeactivateAccount("99988877766", null, null);
        var source = new Source(EmptyOutput.INSTANCE, deactivateAccount);

        assertThatThrownBy(() -> deactivateAccountUseCase.exec(source))
                .isInstanceOf(AccountDeactivatedException.class)
                .hasMessage("Account is Deactivated");

        verify(deactivateAccountRepository, never()).save(any(AccountOutput.class));
        verify(deactivateAccountRepository, times(1)).findById(any(ParamGateway.class));
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}