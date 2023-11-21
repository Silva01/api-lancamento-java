package br.net.silva.daniel.entity;

import br.net.silva.daniel.interfaces.AggregateRoot;
import br.net.silva.daniel.validation.Validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account extends Validation implements AggregateRoot {

    private final Integer number;
    private final Integer bankAgencyNumber;
    private final BigDecimal balance;
    private final String password;
    private boolean active;
    private final String cpf;
    private final List<Transaction> transactions;

    public Account(Integer number, Integer bankAgencyNumber, BigDecimal balance, String password, boolean active, String cpf, List<Transaction> transactions) {
        this.number = number;
        this.bankAgencyNumber = bankAgencyNumber;
        this.balance = balance;
        this.password = password;
        this.active = active;
        this.cpf = cpf;
        this.transactions = transactions;
        validate();
    }

    public Account(Integer number, Integer bankAgencyNumber, String password, String cpf) {
        this(number, bankAgencyNumber, BigDecimal.ZERO, password, true, cpf, new ArrayList<>());
    }

    @Override
    public void validate() {
        // criar método de validação para numeros
    }

    private void validateBalance() {
        var sumTransactionPrice = transactions.stream().reduce(BigDecimal.ZERO, (acc, transaction) -> acc.add(transaction.getPrice()), BigDecimal::add);
        validateBalance(balance, sumTransactionPrice);
    }
}
