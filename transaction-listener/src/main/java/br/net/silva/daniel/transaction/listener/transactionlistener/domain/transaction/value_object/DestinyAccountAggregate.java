package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;

import java.math.BigDecimal;

public class DestinyAccountAggregate extends BaseAccountAggregate {

    @Override
    public BigDecimal calculateBalance() {
        return accountConfigValidation().balance().add(totalTransaction());
    }

    public DestinyAccountAggregate(AccountInput accountInput, BigDecimal totalTransaction, AccountConfigValidationParam accountConfigValidation) {
        super(accountInput, totalTransaction, accountConfigValidation);
    }

    @Override
    public boolean validateIfBalanceIsSufficient() {
        return false;
    }

    @Override
    public boolean validateIfTransactionIsDuplicated() {
        return false;
    }
}
