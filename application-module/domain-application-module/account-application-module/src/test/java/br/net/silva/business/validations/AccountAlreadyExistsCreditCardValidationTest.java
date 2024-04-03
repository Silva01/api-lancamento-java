package br.net.silva.business.validations;

import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
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

class AccountAlreadyExistsCreditCardValidationTest extends AbstractAccountBuilder {

    private AccountAlreadyExistsCreditCardValidation validation;

    @Mock
    private FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validation = new AccountAlreadyExistsCreditCardValidation(findAccountGateway);
    }

    @Test
    void shouldValidateWithSuccess() {
        when(findAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, null)));
        var createCreditCardInput = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, createCreditCardInput);

        assertDoesNotThrow(() -> validation.validate(source));

        verify(findAccountGateway, times(1)).findById(createCreditCardInput);
    }

    @Test
    void shouldValidateErrorCreditCardAlreadyExists() {
        when(findAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(true))));
        var createCreditCardInput = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, createCreditCardInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> validation.validate(source));
        assertEquals("Credit card already exists", exceptionResponse.getMessage());

        verify(findAccountGateway, times(1)).findById(createCreditCardInput);
    }

    @Test
    void shouldValidateErrorCreditCardAlreadyExistsWithDeactivated() {
        when(findAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(false))));
        var createCreditCardInput = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, createCreditCardInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> validation.validate(source));
        assertEquals("Credit card already exists", exceptionResponse.getMessage());

        verify(findAccountGateway, times(1)).findById(createCreditCardInput);
    }

}