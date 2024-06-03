package br.net.silva.daniel.transaction.listener.transactionlistener.domain.validation;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.validation.AccountValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.validation.Validation;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.AccountKey;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(MockitoExtension.class)
class AccountValidationTest {

    private Validation<Optional<Account>> accountValidation;

    @BeforeEach
    void setUp() {
        accountValidation = new AccountValidation();
    }

    @Test
    void validateAccount_WithAccountIsPresent_NotThrowsException() {
        final var optionalAccount = Optional.of(createAccountOutputMock());
        assertThatCode(() -> accountValidation.validate(optionalAccount))
                .doesNotThrowAnyException();
    }

    @Test
    void validateAccount_WithAccountEmpty_ThrowsException() {
        final Optional<Account> optionalAccount = Optional.empty();
        Assertions.assertThatThrownBy(() -> accountValidation.validate(optionalAccount))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage("Account not found");
    }

    private static @NotNull Account createAccountOutputMock() {
        return new Account(
                new AccountKey(1, 123),
                BigDecimal.ONE,
                "ttt",
                true,
                "12344455566",
                null,
                null,
                LocalDateTime.now()
        );
    }
}