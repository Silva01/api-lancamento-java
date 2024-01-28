package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AccountAlreadyExistsCreditCardValidationTest {

    private AccountAlreadyExistsCreditCardValidation validation;

    @Mock
    private Repository<Optional<Account>> findAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validation = new AccountAlreadyExistsCreditCardValidation(findAccountRepository);
    }

    @Test
    void shouldValidateWithSuccess() {
        when(findAccountRepository.exec(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(buildMockAccount(true, null)));
        var createCreditCardInput = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, createCreditCardInput);

        assertDoesNotThrow(() -> validation.validate(source));

        verify(findAccountRepository, times(1)).exec(createCreditCardInput.cpf(), createCreditCardInput.accountNumber(), createCreditCardInput.agency());
    }

    @Test
    void shouldValidateErrorCreditCardAlreadyExists() {
        when(findAccountRepository.exec(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(buildMockAccount(true, buildMockCreditCard())));
        var createCreditCardInput = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, createCreditCardInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> validation.validate(source));
        assertEquals("This account already have a credit card", exceptionResponse.getMessage());

        verify(findAccountRepository, times(1)).exec(createCreditCardInput.cpf(), createCreditCardInput.accountNumber(), createCreditCardInput.agency());
    }

    @Test
    void shouldValidateErrorCreditCardAlreadyExistsWithDeactivated() {
        var creditCard = buildMockCreditCard();
        creditCard.deactivate();
        when(findAccountRepository.exec(anyString(), anyInt(), anyInt())).thenReturn(Optional.of(buildMockAccount(true, creditCard)));
        var createCreditCardInput = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, createCreditCardInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> validation.validate(source));
        assertEquals("This account already have a credit card", exceptionResponse.getMessage());

        verify(findAccountRepository, times(1)).exec(createCreditCardInput.cpf(), createCreditCardInput.accountNumber(), createCreditCardInput.agency());
    }

    private Account buildMockAccount(boolean active, CreditCard creditCard) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, Collections.emptyList());
    }

    private CreditCard buildMockCreditCard() {
        return new CreditCard("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.of(2027, 1, 1), true);
    }

}