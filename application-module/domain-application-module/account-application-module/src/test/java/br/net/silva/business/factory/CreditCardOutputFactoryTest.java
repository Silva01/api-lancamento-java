package br.net.silva.business.factory;

import br.net.silva.business.build.CreditCardOutputBuilder;
import br.net.silva.business.interfaces.AbstractAccountBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardOutputFactoryTest extends AbstractAccountBuilder {

    @Test
    void shouldCreateOutputForCreditCardWithSuccess() {
        var creditCardMock = buildMockCreditCard(true).build();
        var creditCardOutput = CreditCardOutputBuilder.buildFullCreditCardOutput().createFrom(creditCardMock);

        assertNotNull(creditCardOutput);
        assertEquals(creditCardMock.number(), creditCardOutput.number());
        assertEquals(creditCardMock.cvv(), creditCardOutput.cvv());
        assertEquals(creditCardMock.flag(), creditCardOutput.flag());
        assertEquals(creditCardMock.balance(), creditCardOutput.balance());
        assertEquals(creditCardMock.expirationDate(), creditCardOutput.expirationDate());
        assertEquals(creditCardMock.active(), creditCardOutput.active());
    }

    @Test
    void shouldCreateNullObjectWhenCreditCardDTOWasNull() {
        var creditCardOutput = CreditCardOutputBuilder.buildFullCreditCardOutput().createFrom(null);
        assertNull(creditCardOutput);
    }
}