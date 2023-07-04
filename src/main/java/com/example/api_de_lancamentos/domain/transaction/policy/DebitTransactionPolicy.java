package com.example.api_de_lancamentos.domain.transaction.policy;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.factory.AccountFactory;
import com.example.api_de_lancamentos.domain.shared.interfaces.TransactionPolicy;
import com.example.api_de_lancamentos.domain.transaction.exception.TransactionNotValidException;

import java.math.BigDecimal;

public class DebitTransactionPolicy extends TransactionPolicy {
    public DebitTransactionPolicy(Account account, BigDecimal amount) {
        super(account, amount);
    }

    @Override
    protected void validateAmount() {
        if (account.getAccountBalance().compareTo(amount) < 0) {
            throw new TransactionNotValidException("Saldo insuficiente");
        }
    }

    @Override
    protected Account updateBalance() {
        return AccountFactory.createAccount(account.getAccountNumber(), account.getAccountName(), account.getAccountBalance().min(amount), account.getAccountCreditBalance());
    }
}
