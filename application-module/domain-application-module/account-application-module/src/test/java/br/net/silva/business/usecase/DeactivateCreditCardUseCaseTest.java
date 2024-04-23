package br.net.silva.business.usecase;

import br.net.silva.business.exception.CreditCardDeactivatedException;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeactivateCreditCardUseCaseTest {

    private DeactivateCreditCardUseCase useCase;

    @Mock
    private ApplicationBaseGateway<AccountOutput> baseAccountGateway;

    @BeforeEach
    void setUp() {
        useCase = new DeactivateCreditCardUseCase(baseAccountGateway);
    }

    @Test
    void shouldDeactivateCreditCardWithSuccess() {
        when(baseAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(true))));
        doAnswer(invocation -> invocation.getArguments()[0]).when(baseAccountGateway).save(any(AccountOutput.class));

        var input = new DeactivateCreditCardInput("1234", 1, 1234, "99988877766");
        var source = new Source(EmptyOutput.INSTANCE, input);

        assertDoesNotThrow(() -> useCase.exec(source));

        verify(baseAccountGateway, times(1)).findById(any(ParamGateway.class));
        verify(baseAccountGateway, times(1)).save(any(AccountOutput.class));
    }

    @Test
    void shouldErrorAtDeactivateCreditCardNumberIsDifferent() {
        when(baseAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(true))));

        var input = new DeactivateCreditCardInput("1234", 1, 1234, "1234456653");
        var source = new Source(EmptyOutput.INSTANCE, input);

        var responseException = assertThrows(CreditCardNumberDifferentException.class, () -> useCase.exec(source));
        assertEquals("Credit Card number is different at register in account", responseException.getMessage());

        verify(baseAccountGateway, times(1)).findById(any(ParamGateway.class));
        verify(baseAccountGateway, never()).save(any(AccountOutput.class));
    }

    @Test
    void deactivateCreditCard_WithCreditCardNull_ThrowsCreditCardNotExistsException() {
        when(baseAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, null)));
        var input = new DeactivateCreditCardInput("1234", 1, 1234, "1234456653");
        var source = new Source(EmptyOutput.INSTANCE, input);
        assertThatThrownBy(() -> useCase.exec(source))
                .isInstanceOf(CreditCardNotExistsException.class)
                .hasMessage("Credit card not exists in the account");
    }

    @Test
    void deactivateCreditCard_WithCreditCardDeactivated_ThrowsCreditCardDeactivatedException() {
        when(baseAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(false))));
        var input = new DeactivateCreditCardInput("1234", 1, 1234, "1234456653");
        var source = new Source(EmptyOutput.INSTANCE, input);

        assertThatThrownBy(() -> useCase.exec(source))
            .isInstanceOf(CreditCardDeactivatedException.class)
            .hasMessage("Credit card deactivated in the account");
    }

    private AccountOutput buildMockAccount(boolean active, CreditCardOutput creditCard) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, Collections.emptyList());
    }

    private CreditCardOutput buildMockCreditCard(boolean activate) {
        return new CreditCardOutput("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.of(2027, 1, 1), activate);
    }

}