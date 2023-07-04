package com.example.api_de_lancamentos.domain.transaction.policy;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.factory.AccountFactory;
import com.example.api_de_lancamentos.domain.shared.interfaces.TransactionPolicy;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.domain.transaction.enuns.TypeTransactionEnum;
import com.example.api_de_lancamentos.domain.transaction.exception.TransactionNotValidException;

import java.math.BigDecimal;
import java.util.List;

public class DebitTransactionPolicy extends TransactionPolicy<Account> {
    public DebitTransactionPolicy(Account account) {
        super(account, account.getTransactions().stream().filter(t -> t.getType().equals(TypeTransactionEnum.DEBIT)).map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @Override
    protected void validateAmount() {
        if (account.getAccountBalance().compareTo(amount) < 0) {
            throw new TransactionNotValidException("Saldo insuficiente");
        }
    }

    @Override
    protected Account updateBalance() {
        return AccountFactory.createAccount(account.getAccountNumber(), account.getAccountName(), account.getAccountBalance().min(amount), account.getAccountCreditBalance(), account.getTransactions());
    }
}
