package com.example.api_de_lancamentos.domain.transaction.policy;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.factory.AccountFactory;
import com.example.api_de_lancamentos.domain.shared.interfaces.TransactionPolicy;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.domain.transaction.enuns.TypeTransactionEnum;
import com.example.api_de_lancamentos.domain.transaction.exception.TransactionNotValidException;

import java.math.BigDecimal;

public class CreditTransactionPolicy extends TransactionPolicy<Account> {
    @Override
    protected void validateAmount() {
        if (account.getAccountCreditBalance().compareTo(amount) < 0) {
            throw new TransactionNotValidException("Saldo insuficiente");
        }
    }

    @Override
    public void addAccount(Account account) {
        super.addAccount(account, account.getTransactions().stream().filter(t -> t.getType().equals(TypeTransactionEnum.CREDIT)).map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Override
    protected Account updateBalance() {
        return AccountFactory.createAccount(account.getAccountNumber(), account.getAccountName(), account.getAccountBalance(), account.getAccountCreditBalance().min(amount), account.getTransactions());
    }
}
