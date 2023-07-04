package com.example.api_de_lancamentos.domain.account.factory;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class AccountFactory {

    public static Account createAccount(long accountNumber, String accountName, BigDecimal accountBalance, BigDecimal accountCreditBalance) {
        return new Account(accountNumber, accountName, accountBalance, accountCreditBalance);
    }

    public static Account createAccount(String accountName) {
        return new Account(accountName);
    }

    public static Account createAccount(long accountNumber, String accountName, BigDecimal accountBalance, BigDecimal accountCreditBalance, List<Transaction> transactions) {
        return new Account(accountNumber, accountName, accountBalance, accountCreditBalance, transactions);
    }
}
