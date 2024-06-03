package br.net.silva.daniel.transaction.listener.transactionlistener.domain.validation;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(MockitoExtension.class)
class AccountValidationTest {

    private Validation<Optional<AccountOutput>> accountValidation;

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
        final Optional<AccountOutput> optionalAccount = Optional.empty();
        Assertions.assertThatThrownBy(() -> accountValidation.validate(optionalAccount))
                .isInstanceOf(AccountNotExistsException.class)
                .hasMessage("Account not found");
    }

    private static @NotNull AccountOutput createAccountOutputMock() {
        return new AccountOutput(
                1,
                123,
                BigDecimal.ONE,
                "ttt",
                true,
                "12333344455",
                null,
                null);
    }
}