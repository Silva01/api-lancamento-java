package com.example.api_de_lancamentos.domain.shared.interfaces;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class TransactionPolicy<R> {
    protected BigDecimal amount;
    protected Account account;

    protected TransactionPolicy(Account account, BigDecimal amount) {
        this.amount = amount;
        this.account = account;
    }

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

    protected abstract void validateAmount();
    protected abstract R updateBalance();
}
