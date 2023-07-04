package com.example.api_de_lancamentos.domain.shared.interfaces;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class TransactionPolicy<R> {
    protected BigDecimal amount;
    protected Account account;

    public final R executeTransaction() {
        validateAccount();
        validateAmount();
        return updateBalance();
    }

    private void validateAccount() {
        if (Objects.isNull(account)) {
            throw new AccountNotExistsException("Conta não foi encontrada");
        }
    }

    protected void addAccount(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    };

    public abstract void addAccount(Account account);

    protected abstract void validateAmount();
    protected abstract R updateBalance();
}
