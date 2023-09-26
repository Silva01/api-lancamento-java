package br.net.silva.daniel.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private final Integer accountNumber;
    private final Integer bankAgencyNumber;
    private final BigDecimal balance;
    private final String password;
    private CreditCard creditCard;
    private final List<Transaction> transactions;
    private boolean active;

    public Account(Integer bankAgencyNumber, BigDecimal balance, String password) {
        this(null, bankAgencyNumber, balance, password, new ArrayList<>(), true);
    }
    public Account(Integer accountNumber, Integer bankAgencyNumber, BigDecimal balance, String password, List<Transaction> transactions, boolean active) {
        this.accountNumber = accountNumber == null ? generateAccountNumber() : accountNumber;
        this.bankAgencyNumber = bankAgencyNumber;
        this.balance = balance;
        this.password = password;
        this.transactions = transactions;
        this.active = active;
        validate();
    }

    public void registerTransactions(List<Transaction> transactions) {
        //TODO: Found implementation
    }

    public void activate() {
        this.active = true;
    }

    public void desactivate() {
        this.active = false;
    }

    public String getCreditCard() {
        return creditCard.getNumber();
    }

    private void validate() {
        if (this.bankAgencyNumber == null) {
            throw new IllegalArgumentException("Bank agency number is required");
        }
        if (this.balance == null || this.balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance is required");
        }
        if (this.password == null || this.password.isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (accountNumber == null || accountNumber <= 0) {
            throw new IllegalArgumentException("Account number is required");
        }
    }

    private void validateBalance(BigDecimal value) {
        if (balance.compareTo(value) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

    private Integer generateAccountNumber() {
        return 1;
    }
}
