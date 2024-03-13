package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.application.exception.GenericException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ChangeAgencyUseCaseTest {

    private ChangeAgencyUseCase useCase;

    @Mock
    private Repository<AccountOutput> findAccountByCpfAndAccountNumberRepository;

    @Mock
    private Repository<AccountOutput> saveAccountRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(findAccountByCpfAndAccountNumberRepository.exec(anyString(), anyInt(), anyInt())).thenReturn(buildMockAccount(true));
        doAnswer(invocation -> invocation.getArguments()[0]).when(saveAccountRepository).exec(any(Account.class));

        useCase = new ChangeAgencyUseCase(findAccountByCpfAndAccountNumberRepository, saveAccountRepository);
    }

    @Test
    void shouldChangeAgencyOfAccountWithSuccess() {
        var input = new ChangeAgencyInput("99988877766", 45678, 1234, 4321);
        var source = new Source(EmptyOutput.INSTANCE, input);

        var mockAccount = buildMockAccount(true);
        assertDoesNotThrow(() -> useCase.exec(source));

        verify(saveAccountRepository, times(2)).exec(any(AccountOutput.class));
    }

    @Test
    void shouldTryChangeAgencyOfAccountButGiveError() throws Exception {
        when(findAccountByCpfAndAccountNumberRepository.exec(anyString(), anyInt(), anyInt())).thenReturn(null);
        var input = new ChangeAgencyInput("99988877766", 45678, 1234, 4321);
        var source = new Source(EmptyOutput.INSTANCE, input);

        var mockAccount = buildMockAccount(true);
        var exceptionResponse = assertThrows(GenericException.class, () -> useCase.exec(source));
        assertEquals("Generic error", exceptionResponse.getMessage());
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}