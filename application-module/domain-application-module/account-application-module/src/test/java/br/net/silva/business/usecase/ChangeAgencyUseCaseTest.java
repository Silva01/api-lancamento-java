package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ChangeAgencyUseCaseTest {

    private ChangeAgencyUseCase useCase;

    @Mock
    private Repository<Account> findAccountByCpfAndAccountNumberRepository;

    @Mock
    private Repository<Account> saveAccountRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(findAccountByCpfAndAccountNumberRepository.exec(anyString(), anyInt(), anyInt())).thenReturn(buildMockAccount(true));
        when(saveAccountRepository.exec(any())).thenReturn(buildMockAccount(true));

        useCase = new ChangeAgencyUseCase(findAccountByCpfAndAccountNumberRepository, saveAccountRepository);
    }

    @Test
    void shouldChangeAgencyOfAccountWithSuccess() {
        var input = new ChangeAgencyInput("99988877766", 45678, 1234, 4321);
        var source = new Source(EmptyOutput.INSTANCE, input);

        var mockAccount = buildMockAccount(true).build();
        var response = assertDoesNotThrow(() -> useCase.exec(source));

        assertEquals(4321, response.agency());
        assertEquals(mockAccount.number(), response.number());
        assertEquals(mockAccount.balance(), response.balance());
        assertEquals(mockAccount.password(), response.password());
        assertTrue(response.active());
        assertEquals(mockAccount.cpf(), response.cpf());
        assertTrue(response.transactions().isEmpty());
        assertEquals(mockAccount.creditCard(), response.creditCard());
    }

    @Test
    void shouldTryChangeAgencyOfAccountButGiveError() throws Exception {
        when(findAccountByCpfAndAccountNumberRepository.exec(anyString(), anyInt(), anyInt())).thenReturn(null);
        var input = new ChangeAgencyInput("99988877766", 45678, 1234, 4321);
        var source = new Source(EmptyOutput.INSTANCE, input);

        var mockAccount = buildMockAccount(true).build();
        var exceptionResponse = assertThrows(GenericException.class, () -> useCase.exec(source));
        assertEquals("Generic error", exceptionResponse.getMessage());
    }

    private Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}