package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountWithNewAgencyAlreadyExistsValidateTest {

    private AccountWithNewAgencyAlreadyExistsValidate accountWithNewAgencyAlreadyExistsValidate;

    @Mock
    private FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.empty());

        accountWithNewAgencyAlreadyExistsValidate = new AccountWithNewAgencyAlreadyExistsValidate(findAccountGateway);
    }

    @Test
    void shouldGiveSuccessInTheValidation() {
        var input = new ChangeAgencyInput("99988877766", 45678, 1234, 4321);
        var source = new Source(EmptyOutput.INSTANCE, input);

        assertDoesNotThrow(() -> accountWithNewAgencyAlreadyExistsValidate.validate(source));
        verify(findAccountGateway, times(1)).findById(any(ParamGateway.class));
    }

    @Test
    void shouldGiveErrorWhenAccountWithNewAgencyAlreadyExists() {
        var input = new ChangeAgencyInput("99988877766", 45678, 1234, 4321);
        var source = new Source(EmptyOutput.INSTANCE, input);

        when(findAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true)));

        var exceptionResponse = assertThrows(GenericException.class, () -> accountWithNewAgencyAlreadyExistsValidate.validate(source));
        assertEquals("Account With new agency already exists", exceptionResponse.getMessage());
        verify(findAccountGateway, times(1)).findById(any(ParamGateway.class));
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}