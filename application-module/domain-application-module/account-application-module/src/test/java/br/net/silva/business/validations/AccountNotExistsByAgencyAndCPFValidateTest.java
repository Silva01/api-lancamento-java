package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.FindAccountDTO;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.gateway.Repository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AccountNotExistsByAgencyAndCPFValidateTest {

    private AccountNotExistsByAgencyAndCPFValidate validate;
    @Mock
    private Repository<Optional<AccountOutput>> findAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findAccountRepository.exec(anyInt(), anyString())).thenReturn(Optional.empty());

        validate = new AccountNotExistsByAgencyAndCPFValidate(findAccountRepository);
    }

    @Test
    void mustValidateAccountWithSuccess() {
        var findAccount = new FindAccountDTO("99988877766", 1, null, null);
        var source = new Source(EmptyOutput.INSTANCE, findAccount);
        assertDoesNotThrow(() -> validate.validate(source));
    }

    @Test
    void mustValidateAccountWithError() {
        var findAccount = new FindAccountDTO("99988877766", 1, null, null);
        var source = new Source(EmptyOutput.INSTANCE, findAccount);
        when(findAccountRepository.exec(anyInt(), anyString())).thenReturn(Optional.of(buildMockAccount(true)));
        var exceptionResponse = assertThrows(GenericException.class, () -> validate.validate(source));
        assertEquals("Account already active", exceptionResponse.getMessage());
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}