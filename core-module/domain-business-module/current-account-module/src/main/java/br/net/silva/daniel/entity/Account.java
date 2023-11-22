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
        validateAttributeNotNullAndNotEmpty(cpf, "CPF is required");
        validateAttributeNotNullAndNotEmpty(password, "Password is required");
        validateAttributeNonNull(bankAgencyNumber, "Bank agency number is required");
        validateAttributeNonNull(number, "Account number is required");
        validateAttributeNonNull(balance, "Balance is required");
        validateAttributeLessThanZero(BigDecimal.valueOf(bankAgencyNumber), "Bank agency number must be greater than zero");
        validateAttributeEqualsZero(BigDecimal.valueOf(bankAgencyNumber), "Bank agency number must be greater than zero");
        validateAttributeLessThanZero(BigDecimal.valueOf(number), "Account number must be greater than zero");
        validateAttributeEqualsZero(BigDecimal.valueOf(number), "Account number must be greater than zero");
        validateAttributeLessThanZero(balance, "Balance must be greater than zero");

    }

    private void validateBalance() {
        var sumTransactionPrice = transactions.stream().map(Transaction::create).reduce(BigDecimal.ZERO, (acc, transaction) -> acc.add(transaction.price()), BigDecimal::add);
        validateBalance(balance, sumTransactionPrice);
    }
}
