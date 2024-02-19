package br.net.silva.business.usecase;

import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.enuns.FlagEnum;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeactivateCreditCardUseCaseTest {

    private DeactivateCreditCardUseCase useCase;

    @Mock
    private Repository<Account> findAccountRepository;

    @Mock
    private Repository<Account> saveAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountRepository.exec(any(), any(), any())).thenReturn(buildMockAccount(true, buildMockCreditCard(true)));
        doAnswer(invocation -> invocation.getArguments()[0]).when(saveAccountRepository).exec(any(Account.class));

        useCase = new DeactivateCreditCardUseCase(findAccountRepository, saveAccountRepository);
    }

    @Test
    void shouldDeactivateCreditCardWithSuccess() {
        var input = new DeactivateCreditCardInput("1234", 1, 1234, "99988877766");
        var source = new Source(EmptyOutput.INSTANCE, input);

        assertDoesNotThrow(() -> useCase.exec(source));

        verify(findAccountRepository, times(1)).exec(input.accountNumber(), input.agency(), input.cpf());
        verify(saveAccountRepository, times(1)).exec(any(Account.class));
    }

    @Test
    void shouldErrorAtDeactivateCreditCardNumberIsDifferent() {
        var input = new DeactivateCreditCardInput("1234", 1, 1234, "1234456653");
        var source = new Source(EmptyOutput.INSTANCE, input);

        var responseException = assertThrows(CreditCardNumberDifferentException.class, () -> useCase.exec(source));
        assertEquals("Credit card number is different the credit card informed for the user", responseException.getMessage());

        verify(findAccountRepository, times(1)).exec(input.accountNumber(), input.agency(), input.cpf());
        verify(saveAccountRepository, never()).exec(any(Account.class));
    }

    private Account buildMockAccount(boolean active, CreditCard creditCard) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, Collections.emptyList());
    }

    private CreditCard buildMockCreditCard(boolean activate) {
        return new CreditCard("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.of(2027, 1, 1), activate);
    }

}