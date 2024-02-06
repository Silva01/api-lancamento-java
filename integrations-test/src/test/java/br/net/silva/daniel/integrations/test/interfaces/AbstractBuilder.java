package br.net.silva.daniel.integrations.test.interfaces;

import br.net.silva.business.mapper.CreateResponseToFindAccountsByCpfFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountByClientFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountFactory;
import br.net.silva.business.mapper.GetInformationMapper;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.CreditCardUtils;
import br.net.silva.daniel.value_object.Address;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractBuilder extends GeneratorRandomValues {

    protected Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, new ArrayList<>());
    }

    protected Account buildMockAccountWithCreditCard(boolean active, boolean activeCreditCard) {
        var creditCard = new CreditCard("5400639532571841", 121, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.of(2025, 12, 31), activeCreditCard);
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, new ArrayList<>());
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
        var account1 = new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), true, "99988877766", null, new ArrayList<>());
        var account2 = new Account(2, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), false, "99988877766", null, new ArrayList<>());
        var account3 = new Account(3, 45680, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), false, "99988877766", null, new ArrayList<>());
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

    protected List<TransactionInput> generateTransactionOnlyDebit(int quantity) {
        return generateTransactionMixedInput(quantity, false, TransactionTypeEnum.DEBIT);
    }

    protected List<TransactionInput> generateTransactionOnlyCredit(int quantity, boolean isFixedCreditCardNumber) {
        return generateTransactionMixedInput(quantity, isFixedCreditCardNumber, TransactionTypeEnum.CREDIT);
    }

    protected List<TransactionInput> generateTransactionOnlyRandomType(int quantity) {
        return generateTransactionMixedInput(quantity, false, null);
    }

    protected List<TransactionInput> generateTransactionMixedInput(int quantity, boolean isFixedCreditCardNumber, TransactionTypeEnum type) {
        List<TransactionInput> transactions = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            var transaction = new TransactionInput(
                    generateRandomNumberLong(999) + 1,
                    "Compra de teste",
                    BigDecimal.valueOf(1000),
                    generateRandomNumber(9) + 1,
                    Objects.isNull(type) ? generateRandomNumberLogic().equals(0) ? TransactionTypeEnum.DEBIT : TransactionTypeEnum.CREDIT : type,
                    generateIdempotencyId(),
                    isFixedCreditCardNumber ? "5400639532571841" : CreditCardUtils.generateCreditCardNumber(),
                    CreditCardUtils.generateCvv());
            transactions.add(transaction);
        }

        return transactions;
    }
}
