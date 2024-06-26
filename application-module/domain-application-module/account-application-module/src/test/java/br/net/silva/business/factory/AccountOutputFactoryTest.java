package br.net.silva.business.factory;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.build.CreditCardBuilder;
import br.net.silva.business.build.TransactionBuilder;
import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountOutputFactoryTest extends AbstractAccountBuilder {

    @Test
    void createOutputWithSuccess() {
        var accountDtoMock = AccountBuilder.buildFullAccountDto().createFrom(buildMockAccount(true, buildMockCreditCard(true), List.of(buildMockTransaction(TransactionTypeEnum.DEBIT))));
        var accountOutput = AccountOutputFactory.createOutput()
                .withNumber(accountDtoMock.number())
                .withAgency(accountDtoMock.agency())
                .withBalance(accountDtoMock.balance())
                .withPassword(accountDtoMock.password())
                .withFlagActive(accountDtoMock.active())
                .withCpf(accountDtoMock.cpf())
                .withTransactions(TransactionBuilder.buildFullTransactionListOutput().createFrom(accountDtoMock.transactions()))
                .andWithCreditCard(CreditCardBuilder.buildFullCreditCardOutput().createFrom(accountDtoMock.creditCard()))
                .build();

        assertNotNull(accountOutput);
        assertEquals(accountDtoMock.number(), accountOutput.number());
        assertEquals(accountDtoMock.agency(), accountOutput.agency());
        assertEquals(accountDtoMock.balance(), accountOutput.balance());
        assertEquals(accountDtoMock.password(), accountOutput.password());
        assertEquals(accountDtoMock.active(), accountOutput.active());
        assertEquals(accountDtoMock.cpf(), accountOutput.cpf());
        assertEquals(accountDtoMock.transactions().size(), accountOutput.transactions().size());
        assertEquals(accountDtoMock.creditCard().number(), accountOutput.creditCard().number());
        assertEquals(accountDtoMock.creditCard().cvv(), accountOutput.creditCard().cvv());
        assertEquals(accountDtoMock.creditCard().flag(), accountOutput.creditCard().flag());
        assertEquals(accountDtoMock.creditCard().balance(), accountOutput.creditCard().balance());
        assertEquals(accountDtoMock.creditCard().expirationDate(), accountOutput.creditCard().expirationDate());
        assertEquals(accountDtoMock.creditCard().active(), accountOutput.creditCard().active());

    }

    @Test
    void createOutputWithSuccessAndCreditCardNull() {
        var accountDtoMock = AccountBuilder.buildFullAccountDto().createFrom(buildMockAccount(true, null, List.of(buildMockTransaction(TransactionTypeEnum.DEBIT))));
        var accountOutput = AccountOutputFactory.createOutput()
                .withNumber(accountDtoMock.number())
                .withAgency(accountDtoMock.agency())
                .withBalance(accountDtoMock.balance())
                .withPassword(accountDtoMock.password())
                .withFlagActive(accountDtoMock.active())
                .withCpf(accountDtoMock.cpf())
                .withTransactions(TransactionBuilder.buildFullTransactionListOutput().createFrom(accountDtoMock.transactions()))
                .andWithCreditCard(CreditCardBuilder.buildFullCreditCardOutput().createFrom(accountDtoMock.creditCard()))
                .build();

        assertNotNull(accountOutput);
        assertEquals(accountDtoMock.number(), accountOutput.number());
        assertEquals(accountDtoMock.agency(), accountOutput.agency());
        assertEquals(accountDtoMock.balance(), accountOutput.balance());
        assertEquals(accountDtoMock.password(), accountOutput.password());
        assertEquals(accountDtoMock.active(), accountOutput.active());
        assertEquals(accountDtoMock.cpf(), accountOutput.cpf());
        assertEquals(accountDtoMock.transactions().size(), accountOutput.transactions().size());
        assertNull(accountOutput.creditCard());

    }
}