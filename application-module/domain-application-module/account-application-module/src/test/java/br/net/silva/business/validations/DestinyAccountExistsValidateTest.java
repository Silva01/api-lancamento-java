package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.RegisterTransactionInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class DestinyAccountExistsValidateTest extends AbstractAccountBuilder {

    private DestinyAccountExistsValidate validate;

    @Mock
    private Repository<Optional<Account>> findDetinyAccountRepository;

    @BeforeEach
    void serUp() {
        MockitoAnnotations.openMocks(this);
        validate = new DestinyAccountExistsValidate(findDetinyAccountRepository);
    }

    @Test
    void shouldThrowAccountNotExistsExceptionWhenDestinyAccountNotExists() {
        var input = new RegisterTransactionInput("id", "description", null, null, null, "cpf", 1, 1, 2, 2, 1L);
        var source = new Source(input);
        when(findDetinyAccountRepository.exec(anyInt(), anyInt())).thenReturn(Optional.empty());

        var exceptionResponse = assertThrows(AccountNotExistsException.class, () -> validate.validate(source));
        assertEquals("Destiny account not found", exceptionResponse.getMessage());

        verify(findDetinyAccountRepository, times(1)).exec(input.destinyAccountNumber(), input.destinyAgency());
    }
}