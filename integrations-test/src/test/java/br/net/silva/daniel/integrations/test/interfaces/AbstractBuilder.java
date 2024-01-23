package br.net.silva.daniel.integrations.test.interfaces;

import br.net.silva.business.mapper.CreateResponseToFindAccountsByCpfFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountByClientFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountFactory;
import br.net.silva.business.mapper.GetInformationMapper;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.value_object.Address;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractBuilder {

    protected Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

    @Deprecated(since = "2.0.0", forRemoval = true)
    protected Client buildMockClient() {
        var address = new Address("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new Client("abcd", "99988877766", "Daniel", "6122223333", true, address);
    }

    protected Client buildMockClient(boolean active) {
        var address = new Address("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new Client("abcd", "99988877766", "Daniel", "6122223333", active, address);
    }

    protected List<Account> buildMockListAccount() {
        var account1 = new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), true, "99988877766", null, Collections.emptyList());
        var account2 = new Account(2, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), false, "99988877766", null, Collections.emptyList());
        var account3 = new Account(3, 45680, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), false, "99988877766", null, Collections.emptyList());
        return List.of(account1, account2, account3);
    }

    protected List<Transaction> buildListTransaction() {
        return List.of(
                new Transaction(1L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(2L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(3L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(4L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(5L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(6L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(7L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(8L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(9L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(10L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321)
        );
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

    protected void assertionClient(ClientDTO expected, ClientDTO actual) {
        assertNotNull(actual);
        assertEquals(expected.id(), actual.id());
        assertEquals(expected.cpf(), actual.cpf());
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.telephone(), actual.telephone());
        assertEquals(expected.active(), actual.active());
        assertEquals(expected.address(), actual.address());
    }

    protected GenericResponseMapper buildFactoryResponse() {
        return new GenericResponseMapper(List.of(
                new CreateResponseToNewAccountByClientFactory(),
                new CreateResponseToFindAccountsByCpfFactory(),
                new CreateResponseToNewAccountFactory(),
                new GetInformationMapper()
        ));
    }
}
