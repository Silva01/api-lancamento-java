package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;

import java.math.BigDecimal;

public class SourceAccountAggregate extends BaseAccountAggregate {

    @Override
    public BigDecimal calculateBalance() {
        return accountConfigValidation().balance().subtract(totalTransaction());
    }

    public SourceAccountAggregate(AccountInput accountInput, BigDecimal totalTransaction, AccountConfigValidationParam accountConfigValidation) {
        super(accountInput, totalTransaction, accountConfigValidation);
    }
}
