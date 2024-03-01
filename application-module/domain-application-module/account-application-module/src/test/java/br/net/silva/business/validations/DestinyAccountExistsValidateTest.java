package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class DestinyAccountExistsValidateTest extends AbstractAccountBuilder {

    private DestinyAccountExistsValidate validate;

    @Mock
    private Repository<Optional<AccountOutput>> findDetinyAccountRepository;

    @BeforeEach
    void serUp() {
        MockitoAnnotations.openMocks(this);
        validate = new DestinyAccountExistsValidate(findDetinyAccountRepository);
    }

    @Test
    void shouldThrowAccountNotExistsExceptionWhenDestinyAccountNotExists() {
        var destinyAccount = new AccountInput(123, 123, "12345678901");
        var input = new BatchTransactionInput(null, destinyAccount, Collections.emptyList());
        var source = new Source(input);
        when(findDetinyAccountRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(Optional.empty());

        var exceptionResponse = assertThrows(AccountNotExistsException.class, () -> validate.validate(source));
        assertEquals("Destiny account not found", exceptionResponse.getMessage());

        verify(findDetinyAccountRepository, times(1)).exec(input.destinyAccount().accountNumber(), input.destinyAccount().agency(), input.destinyAccount().cpf());
    }
}