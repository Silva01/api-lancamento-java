package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.daniel.entity.Account;
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
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AccountExistsAndActiveByCpfValidateTest {

    private AccountExistsAndActiveByCpfValidate validate;

    @Mock
    private Repository<Optional<Account>> repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(repository.exec(anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        validate = new AccountExistsAndActiveByCpfValidate(repository);
    }

    @Test
    void shouldGetInformationAccountWithSuccess() throws GenericException {
        var getInformationAccountInput = new GetInformationAccountInput("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, getInformationAccountInput);

        assertDoesNotThrow(() -> validate.validate(source));
        verify(repository, times(1)).exec(anyString());
    }

    @Test
    void shouldGetInformationAccountWithErrorAccountNotExists() {
        when(repository.exec(anyString())).thenReturn(Optional.empty());
        var getInformationAccountInput = new GetInformationAccountInput("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, getInformationAccountInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> validate.validate(source));
        assertEquals("Account not exists", exceptionResponse.getMessage());
        verify(repository, times(1)).exec(anyString());
    }

    @Test
    void shouldGetInformationAccountWithErrorAccountDeactivate() {
        when(repository.exec(anyString())).thenReturn(Optional.of(buildMockAccount(false)));
        var getInformationAccountInput = new GetInformationAccountInput("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, getInformationAccountInput);

        var exceptionResponse = assertThrows(GenericException.class, () -> validate.validate(source));
        assertEquals("Account is not active", exceptionResponse.getMessage());
        verify(repository, times(1)).exec(anyString());
    }

    private Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}