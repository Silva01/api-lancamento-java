package br.net.silva.business.validations;

import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AccountAlreadyExistsCreditCardValidationTest extends AbstractAccountBuilder {

    private AccountAlreadyExistsCreditCardValidation validation;

    @Mock
    private Repository<Optional<AccountOutput>> findAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validation = new AccountAlreadyExistsCreditCardValidation(findAccountRepository);
    }

    @Test
    void shouldValidateWithSuccess() {
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true, null)));
        var createCreditCardInput = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, createCreditCardInput);

        assertDoesNotThrow(() -> validation.validate(source));

        verify(findAccountRepository, times(1)).exec(createCreditCardInput.accountNumber(), createCreditCardInput.agency(), createCreditCardInput.cpf());
    }

    @Test
    void shouldValidateErrorCreditCardAlreadyExists() {
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(true))));
        var createCreditCardInput = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, createCreditCardInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> validation.validate(source));
        assertEquals("This account already have a credit card", exceptionResponse.getMessage());

        verify(findAccountRepository, times(1)).exec(createCreditCardInput.accountNumber(), createCreditCardInput.agency(), createCreditCardInput.cpf());
    }

    @Test
    void shouldValidateErrorCreditCardAlreadyExistsWithDeactivated() {
        when(findAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard(false))));
        var createCreditCardInput = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, createCreditCardInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> validation.validate(source));
        assertEquals("This account already have a credit card", exceptionResponse.getMessage());

        verify(findAccountRepository, times(1)).exec(createCreditCardInput.accountNumber(), createCreditCardInput.agency(), createCreditCardInput.cpf());
    }

}