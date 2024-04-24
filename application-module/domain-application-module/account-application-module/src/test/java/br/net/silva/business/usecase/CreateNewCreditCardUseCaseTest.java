package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateNewCreditCardUseCaseTest {

    private CreateNewCreditCardUseCase useCase;

    @Mock
    private ApplicationBaseGateway<AccountOutput> baseGateway;

    @BeforeEach
    void setUp() {
        when(baseGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount(true, null)));
        when(baseGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount(true, buildMockCreditCard()));
        useCase = new CreateNewCreditCardUseCase(baseGateway);
    }

    @Test
    void shouldCreateNewCreditCardWithSuccess() {
        var input = new CreateCreditCardInput("99988877766", 45678, 1234);
        var source = new Source(EmptyOutput.INSTANCE, input);

        var accountDTO = assertDoesNotThrow(() -> useCase.exec(source));
        assertNotNull(accountDTO);
        assertNotNull(accountDTO.creditCard());
        assertTrue(accountDTO.creditCard().active());
        assertEquals(BigDecimal.valueOf(1000), accountDTO.creditCard().balance());

        verify(baseGateway, times(1)).findById(any(ParamGateway.class));
        verify(baseGateway, times(1)).save(any(AccountOutput.class));
    }



    private AccountOutput buildMockAccount(boolean active, CreditCardOutput creditCard) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, Collections.emptyList());
    }

    private CreditCardOutput buildMockCreditCard() {
        return new CreditCardOutput("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.of(2027, 1, 1), true);
    }

}