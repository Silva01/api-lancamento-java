package br.net.silva.daniel.integrations.test.interfaces;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.value_object.Address;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractBuilder {

    protected Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

    protected Client buildMockClient() {
        var address = new Address("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new Client("abcd", "99988877766", "Daniel", "6122223333", true, address);
    }

    protected void assertionAccount(AccountDTO expected, AccountDTO actual) {
        assertNotNull(actual);
        assertEquals(expected.number(), actual.number());
        assertEquals(expected.agency(), actual.agency());
        assertEquals(expected.balance(), actual.balance());
        assertEquals(expected.active(), actual.active());
        assertEquals(expected.password(), actual.password());
        assertEquals(expected.cpf(), actual.cpf());
        assertEquals(expected.creditCard(), actual.creditCard());
        assertEquals(expected.transactions(), actual.transactions());
    }
}
