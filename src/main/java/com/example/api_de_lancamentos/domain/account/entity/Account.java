package com.example.api_de_lancamentos.domain.account.entity;

import java.math.BigDecimal;

public final class Account {

    private final long accountNumber;
    private final String accountName;
    private final BigDecimal accountBalance;
    private final BigDecimal accountCreditBalance;

    public Account(long accountNumber, String accountName, BigDecimal accountBalance, BigDecimal accountCreditBalance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.accountCreditBalance = accountCreditBalance;
    }

    public Account(long accountNumber, String accountName) {
        this(accountNumber, accountName, new BigDecimal("3000.00"), new BigDecimal("5000.00"));
    }

    public Account(String accountName) {
        this(0, accountName, new BigDecimal("3000.00"), new BigDecimal("5000.00"));
    }

    public void validadeAccountBalance(BigDecimal value) {
        if (this.accountBalance.compareTo(value) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }

    public void validadeAccountCreditBalance(BigDecimal value) {
        if (this.accountCreditBalance.compareTo(value) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public BigDecimal getAccountCreditBalance() {
        return accountCreditBalance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }
}
