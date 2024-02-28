package br.net.silva.business.factory;

import br.net.silva.business.build.CreditCardBuilder;
import br.net.silva.business.build.TransactionBuilder;
import br.net.silva.business.interfaces.AccountFactorySpec;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

public class AccountDtoFactory implements AccountFactorySpec.BuildSpec<AccountDTO> {

    private Integer number;
    private Integer agency;
    private BigDecimal balance;
    private String password;
    private boolean active;
    private String cpf;
    private List<TransactionDTO> transactions;
    private CreditCardDTO creditCard;

    public static AccountFactorySpec.NumberSpec<AccountDTO> createDto() {
        return new AccountDtoFactory();
    }


    @Override
    public AccountFactorySpec.AgencySpec<AccountDTO> withNumber(Integer number) {
        this.number = number;
        return this;
    }

    @Override
    public AccountFactorySpec.BalanceSpec<AccountDTO> withAgency(Integer agency) {
        this.agency = agency;
        return this;
    }

    @Override
    public AccountFactorySpec.PasswordSpec<AccountDTO> withBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    @Override
    public AccountFactorySpec.ActiveSpec<AccountDTO> withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public AccountFactorySpec.CpfSpec<AccountDTO> withFlagActive(Boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public AccountFactorySpec.TransactionsSpec<AccountDTO> withCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public AccountFactorySpec.BuildSpec<AccountDTO> andWithCpf(String cpf) {
        withCpf(cpf);
        return this;
    }

    @Override
    public AccountFactorySpec.CreditCardSpec<AccountDTO> withTransactions(List<TransactionOutput> transactions) {
        this.transactions = TransactionBuilder.buildFullTransactionListDto().createFrom(transactions);
        return this;
    }

    @Override
    public AccountFactorySpec.BuildSpec<AccountDTO> andWithTransactions(List<TransactionOutput> transactions) {
        withTransactions(transactions);
        return null;
    }

    @Override
    public AccountFactorySpec.BuildSpec<AccountDTO> andWithCreditCard(CreditCardOutput creditCard) {
        this.creditCard = CreditCardBuilder.buildFullCreditCardDto().createFrom(creditCard);
        return this;
    }

    @Override
    public AccountDTO build() {
        return new AccountDTO(number, agency, balance, password, active, cpf, transactions, creditCard);
    }
}
