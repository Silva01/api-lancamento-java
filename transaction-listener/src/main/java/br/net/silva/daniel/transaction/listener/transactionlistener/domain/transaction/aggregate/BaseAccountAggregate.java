package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.TransactionValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.AccountConfigValidationParam;

import java.math.BigDecimal;

public abstract class BaseAccountAggregate implements TransactionValidation {

    private final AccountInput accountInput;
    private final BigDecimal totalTransaction;
    private final AccountConfigValidationParam accountConfigValidation;

    protected BaseAccountAggregate(AccountInput accountInput, BigDecimal totalTransaction, AccountConfigValidationParam accountConfigValidation) {
        this.accountInput = accountInput;
        this.totalTransaction = totalTransaction;
        this.accountConfigValidation = accountConfigValidation;
    }

    public abstract BigDecimal calculateBalance();

    public AccountConfigValidationParam accountConfigValidation() {
        return accountConfigValidation;
    }

    public AccountInput accountInput() {
        return accountInput;
    }

    public BigDecimal totalTransaction() {
        return totalTransaction;
    }
}
