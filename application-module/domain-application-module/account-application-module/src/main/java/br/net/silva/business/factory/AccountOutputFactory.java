package br.net.silva.business.factory;

import br.net.silva.business.interfaces.AccountFactorySpec;
import br.net.silva.business.interfaces.CreditCardFactorySpec;
import br.net.silva.business.interfaces.TransactionFactorySpec;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

public class AccountOutputFactory implements AccountFactorySpec.BuildSpec<AccountOutput> {

    private Integer number;
    private Integer agency;
    private BigDecimal balance;
    private String password;
    private boolean active;
    private String cpf;
    private List<TransactionOutput> transactions;
    private CreditCardOutput creditCard;

    public static AccountFactorySpec.NumberSpec<AccountOutput> createOutput() {
        return new AccountOutputFactory();
    }


    @Override
    public AccountFactorySpec.AgencySpec<AccountOutput> withNumber(Integer number) {
        this.number = number;
        return this;
    }

    @Override
    public AccountFactorySpec.BalanceSpec<AccountOutput> withAgency(Integer agency) {
        this.agency = agency;
        return this;
    }

    @Override
    public AccountFactorySpec.PasswordSpec<AccountOutput> withBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    @Override
    public AccountFactorySpec.ActiveSpec<AccountOutput> withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public AccountFactorySpec.CpfSpec<AccountOutput> withFlagActive(Boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public AccountFactorySpec.TransactionsSpec<AccountOutput> withCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public AccountFactorySpec.BuildSpec<AccountOutput> andWithCpf(String cpf) {
        withCpf(cpf);
        return this;
    }

    @Override
    public AccountFactorySpec.CreditCardSpec<AccountOutput> withTransactions(List<TransactionOutput> transactions) {
        this.transactions = transactions;
        return this;
    }

    @Override
    public AccountFactorySpec.BuildSpec<AccountOutput> andWithTransactions(List<TransactionOutput> transactions) {
        withTransactions(transactions);
        return null;
    }

    @Override
    public AccountFactorySpec.BuildSpec<AccountOutput> andWithCreditCard(CreditCardOutput creditCard) {
        this.creditCard = creditCard;
        return this;
    }

    @Override
    public AccountOutput build() {
        return new AccountOutput(number, agency, balance, password, active, cpf, transactions, creditCard);
    }
}
