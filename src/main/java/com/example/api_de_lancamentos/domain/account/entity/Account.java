package com.example.api_de_lancamentos.domain.account.entity;

import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Account {

    private final long accountNumber;
    private final String accountName;
    private final BigDecimal accountBalance;
    private final BigDecimal accountCreditBalance;
    private List<Transaction> transactions;


    public Account(long accountNumber, String accountName, BigDecimal accountBalance, BigDecimal accountCreditBalance, List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.accountCreditBalance = accountCreditBalance;
        this.transactions = transactions;
    }

    public Account(long accountNumber, String accountName, BigDecimal accountBalance, BigDecimal accountCreditBalance) {
        this(accountNumber, accountName, accountBalance, accountCreditBalance, new ArrayList<>());
    }

    public Account(String accountName) {
        this(0, accountName, new BigDecimal("3000.00"), new BigDecimal("5000.00"), new ArrayList<>());
    }

    public void addTransactions(List<Transaction> transactions) {
        this.transactions.addAll(transactions);
    }

    public List<Transaction> getTransactions() {
        return transactions;
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
