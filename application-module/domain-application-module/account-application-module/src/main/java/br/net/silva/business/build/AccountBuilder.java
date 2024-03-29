package br.net.silva.business.build;

import br.net.silva.business.factory.AccountDtoFactory;
import br.net.silva.business.factory.AccountOutputFactory;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.application.interfaces.IGenericBuilder;

import java.util.List;

public final class AccountBuilder {

    private AccountBuilder() {
    }

    public static IGenericBuilder<AccountOutput, AccountDTO> buildFullAccountOutput() {
        return account -> AccountOutputFactory
                .createOutput()
                .withNumber(account.number())
                .withAgency(account.agency())
                .withBalance(account.balance())
                .withPassword(account.password())
                .withFlagActive(account.active())
                .withCpf(account.cpf())
                .withTransactions(TransactionBuilder.buildFullTransactionListOutput().createFrom(account.transactions()))
                .andWithCreditCard(CreditCardBuilder.buildFullCreditCardOutput().createFrom(account.creditCard()))
                .build();
    }

    public static IGenericBuilder<AccountDTO, AccountOutput> buildFullAccountDto() {
        return account -> AccountDtoFactory
                .createDto()
                .withNumber(account.number())
                .withAgency(account.agency())
                .withBalance(account.balance())
                .withPassword(account.password())
                .withFlagActive(account.active())
                .withCpf(account.cpf())
                .withTransactions(account.transactions())
                .andWithCreditCard(account.creditCard())
                .build();
    }

    public static IGenericBuilder<Account, AccountOutput> buildAggregate() {
        return output -> new Account(
                output.number(),
                output.agency(),
                output.balance(),
                output.password(),
                output.active(),
                output.cpf(),
                CreditCardBuilder.buildAggregate().createFrom(output.creditCard()),
                TransactionBuilder.buildAggregateList().createFrom(output.transactions()));
    }

    public static AccountOutput buildNewAccountFrom(AccountOutput account, List<TransactionOutput> transactions, CreditCardOutput creditCard) {
        return AccountOutputFactory
                .createOutput()
                .withNumber(account.number())
                .withAgency(account.agency())
                .withBalance(account.balance())
                .withPassword(account.password())
                .withFlagActive(account.active())
                .withCpf(account.cpf())
                .withTransactions(transactions)
                .andWithCreditCard(creditCard)
                .build();
    }
}
