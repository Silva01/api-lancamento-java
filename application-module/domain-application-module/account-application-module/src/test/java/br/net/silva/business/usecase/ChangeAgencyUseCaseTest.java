package br.net.silva.business.usecase;

import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeAgencyUseCaseTest {

    private ChangeAgencyUseCase useCase;

    @Mock
    private ApplicationBaseGateway<AccountOutput> baseGateway;

    @BeforeEach
    void setUp() {
        useCase = new ChangeAgencyUseCase(baseGateway);
    }

    @Test
    void shouldChangeAgencyOfAccountWithSuccess() {
        when(baseGateway.findById(any(ParamGateway.class)))
                .thenReturn(Optional.of(buildMockAccount(true)))
                .thenReturn(Optional.empty());
        doAnswer(invocation -> invocation.getArguments()[0]).when(baseGateway).save(any(AccountOutput.class));

        final var input = buildInputBase();
        final var source = new Source(EmptyOutput.INSTANCE, input);

        assertDoesNotThrow(() -> useCase.exec(source));

        verify(baseGateway, times(2)).save(any(AccountOutput.class));
        verify(baseGateway, times(2)).findById(any(ParamGateway.class));
    }

    @Test
    void changeAgency_WithAccountNotExists_ThrowsAccountNotExistsException() {
        when(baseGateway.findById(any(ParamGateway.class)))
                .thenReturn(Optional.empty());

        final var input = buildInputBase();
        final var source = new Source(EmptyOutput.INSTANCE, input);

        assertThatThrownBy(() -> useCase.exec(source))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage("Account not exists");

        verify(baseGateway, never()).save(any(AccountOutput.class));
        verify(baseGateway, times(2)).findById(any(ParamGateway.class));
    }

    @Test
    void changeAgency_WithAccountExistsToNewAgency_ThrowsAccountAlreadyExistsForNewAgencyException() {
        when(baseGateway.findById(any(ParamGateway.class)))
                .thenReturn(Optional.of(buildMockAccount(true)))
                .thenReturn(Optional.of(buildMockAccount(true)));

        final var input = buildInputBase();
        final var source = new Source(EmptyOutput.INSTANCE, input);

        assertThatThrownBy(() -> useCase.exec(source))
                .isInstanceOf(AccountAlreadyExistsForNewAgencyException.class)
                .hasMessage("Account With new agency already exists");

        verify(baseGateway, never()).save(any(AccountOutput.class));
        verify(baseGateway, times(2)).findById(any(ParamGateway.class));
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

    private static ChangeAgencyInput buildInputBase() {
        return new ChangeAgencyInput("99988877766", 45678, 1234, 4321);
    }
}