package com.example.api_de_lancamentos.domain.account.factory;

import com.example.api_de_lancamentos.domain.account.entity.Account;

import java.math.BigDecimal;

public class AccountFactory {

    public static Account createAccount(long accountNumber, String accountName, BigDecimal accountBalance, BigDecimal accountCreditBalance) {
        return new Account(accountNumber, accountName, accountBalance, accountCreditBalance);
    }

    public static Account createAccount(String accountName) {
        return new Account(accountName);
    }
}
