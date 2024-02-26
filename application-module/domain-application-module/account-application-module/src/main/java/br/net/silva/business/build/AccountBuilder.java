package br.net.silva.business.build;

import br.net.silva.business.factory.AccountDtoFactory;
import br.net.silva.business.factory.AccountOutputFactory;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;

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
                .withTransactions(TransactionBuilder.buildFullTransactionsOutput().createFrom(account.transactions()))
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
}
