package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AccountWithNewAgencyAlreadyExistsValidateTest {

    private AccountWithNewAgencyAlreadyExistsValidate accountWithNewAgencyAlreadyExistsValidate;

    @Mock
    private Repository<Optional<AccountOutput>> findAccountByNewAgencyNumberAndAccountNumberRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountByNewAgencyNumberAndAccountNumberRepository.exec(anyInt(), anyInt())).thenReturn(Optional.empty());

        accountWithNewAgencyAlreadyExistsValidate = new AccountWithNewAgencyAlreadyExistsValidate(findAccountByNewAgencyNumberAndAccountNumberRepository);
    }

    @Test
    void shouldGiveSuccessInTheValidation() {
        var input = new ChangeAgencyInput("99988877766", 45678, 1234, 4321);
        var source = new Source(EmptyOutput.INSTANCE, input);

        assertDoesNotThrow(() -> accountWithNewAgencyAlreadyExistsValidate.validate(source));
        verify(findAccountByNewAgencyNumberAndAccountNumberRepository, times(1)).exec(45678, 4321);
    }

    @Test
    void shouldGiveErrorWhenAccountWithNewAgencyAlreadyExists() {
        var input = new ChangeAgencyInput("99988877766", 45678, 1234, 4321);
        var source = new Source(EmptyOutput.INSTANCE, input);

        when(findAccountByNewAgencyNumberAndAccountNumberRepository.exec(anyInt(), anyInt())).thenReturn(Optional.of(buildMockAccount(true)));

        var exceptionResponse = assertThrows(GenericException.class, () -> accountWithNewAgencyAlreadyExistsValidate.validate(source));
        assertEquals("Account with new agency already exists", exceptionResponse.getMessage());
        verify(findAccountByNewAgencyNumberAndAccountNumberRepository, times(1)).exec(45678, 4321);
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}