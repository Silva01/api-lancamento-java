package br.net.silva.business.build;

import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountBuilderTest extends AbstractAccountBuilder {

    @Test
    void shouldCreateOutputWithSuccess() {
        var accountDto = AccountBuilder.buildFullAccountDto().createFrom(buildMockAccount(true, buildMockCreditCard(true), List.of(buildMockTransaction(TransactionTypeEnum.DEBIT))));
        var accountOutput = AccountBuilder.buildFullAccountOutput().createFrom(accountDto);

        assertNotNull(accountOutput);
        assertEquals(accountDto.number(), accountOutput.number());
        assertEquals(accountDto.agency(), accountOutput.agency());
        assertEquals(accountDto.balance(), accountOutput.balance());
        assertEquals(accountDto.password(), accountOutput.password());
        assertEquals(accountDto.active(), accountOutput.active());
        assertEquals(accountDto.cpf(), accountOutput.cpf());
        assertEquals(accountDto.transactions().size(), accountOutput.transactions().size());
        assertEquals(accountDto.creditCard().number(), accountOutput.creditCard().number());
        assertEquals(accountDto.creditCard().cvv(), accountOutput.creditCard().cvv());
        assertEquals(accountDto.creditCard().flag(), accountOutput.creditCard().flag());
        assertEquals(accountDto.creditCard().balance(), accountOutput.creditCard().balance());
        assertEquals(accountDto.creditCard().expirationDate(), accountOutput.creditCard().expirationDate());
        assertEquals(accountDto.creditCard().active(), accountOutput.creditCard().active());
    }

    @Test
    void shouldCreateDtoWithSuccess() {
        var accountDto = AccountBuilder.buildFullAccountDto().createFrom(buildMockAccount(true, buildMockCreditCard(true), List.of(buildMockTransaction(TransactionTypeEnum.DEBIT))));
        var accountOutput = AccountBuilder.buildFullAccountOutput().createFrom(accountDto);

        var newAccountDto = AccountBuilder.buildFullAccountDto().createFrom(accountOutput);

        assertNotNull(newAccountDto);
        assertEquals(accountDto.number(), newAccountDto.number());
        assertEquals(accountDto.agency(), newAccountDto.agency());
        assertEquals(accountDto.balance(), newAccountDto.balance());
        assertEquals(accountDto.password(), newAccountDto.password());
        assertEquals(accountDto.active(), newAccountDto.active());
        assertEquals(accountDto.cpf(), newAccountDto.cpf());
        assertEquals(accountDto.transactions().size(), newAccountDto.transactions().size());
        assertEquals(accountDto.creditCard().number(), newAccountDto.creditCard().number());
        assertEquals(accountDto.creditCard().cvv(), newAccountDto.creditCard().cvv());
        assertEquals(accountDto.creditCard().flag(), newAccountDto.creditCard().flag());
        assertEquals(accountDto.creditCard().balance(), newAccountDto.creditCard().balance());
        assertEquals(accountDto.creditCard().expirationDate(), newAccountDto.creditCard().expirationDate());
        assertEquals(accountDto.creditCard().active(), newAccountDto.creditCard().active());
    }

}