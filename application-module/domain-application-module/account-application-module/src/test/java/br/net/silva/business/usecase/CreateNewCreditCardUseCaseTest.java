package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.enuns.FlagEnum;
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
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CreateNewCreditCardUseCaseTest {

    private CreateNewCreditCardUseCase useCase;

    @Mock
    private Repository<AccountOutput> findAccountByCpfAndAgencyAndAccountNumberRepository;

    @Mock
    private Repository<AccountOutput> saveAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(findAccountByCpfAndAgencyAndAccountNumberRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(buildMockAccount(true, null));
        doAnswer(invocation -> invocation.getArgument(0)).when(saveAccountRepository).exec(any(AccountOutput.class));

        useCase = new CreateNewCreditCardUseCase(findAccountByCpfAndAgencyAndAccountNumberRepository, saveAccountRepository);
    }

    @Test
    void shouldCreateNewCreditCardWithSuccess() {
        var input = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, input);

        var accountDTO = assertDoesNotThrow(() -> useCase.exec(source));
        assertNotNull(accountDTO);
        assertNotNull(accountDTO.creditCard());
        assertTrue(accountDTO.creditCard().active());
        assertEquals(BigDecimal.valueOf(2000), accountDTO.creditCard().balance());

        verify(findAccountByCpfAndAgencyAndAccountNumberRepository, times(1)).exec(input.agency(), input.accountNumber(), input.cpf());
        verify(saveAccountRepository, times(1)).exec(any(AccountOutput.class));
    }

    @Test
    void shouldGenericErrorWhenTryCreateNewCreditCard() {
        when(findAccountByCpfAndAgencyAndAccountNumberRepository.exec(anyInt(), anyInt(), anyString())).thenReturn(null);
        var input = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, input);

        var response = assertThrows(GenericException.class, () -> useCase.exec(source));
        assertEquals("Generic error", response.getMessage());

        verify(findAccountByCpfAndAgencyAndAccountNumberRepository, times(1)).exec(input.agency(), input.accountNumber(), input.cpf());
        verify(saveAccountRepository, never()).exec(any(Account.class));
    }

    private AccountOutput buildMockAccount(boolean active, CreditCardOutput creditCard) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", Collections.emptyList(), creditCard);
    }

    private CreditCardOutput buildMockCreditCard() {
        return new CreditCardOutput("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.of(2027, 1, 1), true);
    }

}