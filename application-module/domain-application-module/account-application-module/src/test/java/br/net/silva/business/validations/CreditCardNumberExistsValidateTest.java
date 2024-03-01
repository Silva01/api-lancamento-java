package br.net.silva.business.validations;

import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditCardNumberExistsValidateTest extends AbstractAccountBuilder {

    private CreditCardNumberExistsValidate validate;

    @Mock
    private Repository<AccountOutput> findAccountByCpfAndAccountNumberAndAgencyRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(true, buildMockCreditCard(true)));
        validate = new CreditCardNumberExistsValidate(findAccountByCpfAndAccountNumberAndAgencyRepository);
    }

    @Test
    void shouldValidateCreditCardNumberExistsWithSuccess() {
        var input = new DeactivateCreditCardInput("99988877766", 1, 45678, "99988877766");
        var source = new Source(input);

        assertDoesNotThrow(() -> validate.validate(source));

        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(input.accountNumber(), input.agency(), input.cpf());
    }

    @Test
    void shouldErrorAtValidateCreditCardNumberNotExistsInTheAccount() {
        when(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(true, null));
        var input = new DeactivateCreditCardInput("99988877766", 1, 45678, "99988877766");
        var source = new Source(input);

        var responseException = assertThrows(CreditCardNotExistsException.class, () -> validate.validate(source));
        assertEquals("Credit card not exists in the account", responseException.getMessage());

        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(input.accountNumber(), input.agency(), input.cpf());
    }

    @Test
    void shouldErrorAtValidateCreditCardNumberDifferentWithAccountHave() {
        var input = new DeactivateCreditCardInput("99988877766", 1, 45678, "123456");
        var source = new Source(input);

        var responseException = assertThrows(CreditCardNumberDifferentException.class, () -> validate.validate(source));
        assertEquals("Credit Card number is different at register in account", responseException.getMessage());

        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(input.accountNumber(), input.agency(), input.cpf());
    }

    @Test
    void shouldErrorAtValidateCreditCardNumberDeactivatedInTheAccount() {
        when(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(true, buildMockCreditCard(false)));
        var input = new DeactivateCreditCardInput("99988877766", 1, 45678, "99988877766");
        var source = new Source(input);

        var responseException = assertThrows(CreditCardNotExistsException.class, () -> validate.validate(source));
        assertEquals("Credit card deactivated in the account", responseException.getMessage());

        verify(findAccountByCpfAndAccountNumberAndAgencyRepository, times(1)).exec(input.accountNumber(), input.agency(), input.cpf());
    }

}