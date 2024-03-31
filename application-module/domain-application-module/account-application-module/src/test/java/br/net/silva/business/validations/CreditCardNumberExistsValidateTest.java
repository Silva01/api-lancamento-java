package br.net.silva.business.validations;

import br.net.silva.business.exception.CreditCardDeactivatedException;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreditCardNumberExistsValidateTest extends AbstractAccountBuilder {

    private CreditCardNumberExistsValidate validate;

    @Mock
    private FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(findAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(true))));
        validate = new CreditCardNumberExistsValidate(findAccountGateway);
    }

    @Test
    void shouldValidateCreditCardNumberExistsWithSuccess() {
        var input = new DeactivateCreditCardInput("99988877766", 1, 45678, "99988877766");
        var source = new Source(input);

        assertDoesNotThrow(() -> validate.validate(source));

        verify(findAccountGateway, times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void shouldErrorAtValidateCreditCardNumberNotExistsInTheAccount() {
        when(findAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, null)));
        var input = new DeactivateCreditCardInput("99988877766", 1, 45678, "99988877766");
        var source = new Source(input);

        var responseException = assertThrows(CreditCardNotExistsException.class, () -> validate.validate(source));
        assertEquals("Credit card not exists in the account", responseException.getMessage());

        verify(findAccountGateway, times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void shouldErrorAtValidateCreditCardNumberDifferentWithAccountHave() {
        var input = new DeactivateCreditCardInput("99988877766", 1, 45678, "123456");
        var source = new Source(input);

        var responseException = assertThrows(CreditCardNumberDifferentException.class, () -> validate.validate(source));
        assertEquals("Credit Card number is different at register in account", responseException.getMessage());

        verify(findAccountGateway, times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void shouldErrorAtValidateCreditCardNumberDeactivatedInTheAccount() {
        when(findAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(false))));
        var input = new DeactivateCreditCardInput("99988877766", 1, 45678, "99988877766");
        var source = new Source(input);

        var responseException = assertThrows(CreditCardDeactivatedException.class, () -> validate.validate(source));
        assertEquals("Credit card deactivated in the account", responseException.getMessage());

        verify(findAccountGateway, times(1)).findById(any(ParamGateway.class));
    }

}