package br.net.silva.business.build;

import br.net.silva.business.factory.AccountOutputFactory;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;

public final class AccountOutputBuilder {

    private AccountOutputBuilder() {
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
                .withTransactions(TransactionOutputBuilder.buildFullTransactionsOutput().createFrom(account.transactions()))
                .andWithCreditCard(CreditCardOutputBuilder.buildFullCreditCardOutput().createFrom(account.creditCard()))
                .build();
    }
}
