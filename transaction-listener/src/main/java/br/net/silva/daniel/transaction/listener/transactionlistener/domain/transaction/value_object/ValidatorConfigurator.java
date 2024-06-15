package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.enums.AccountType;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.TransactionValidation;

import java.math.BigDecimal;

public record ValidatorConfigurator(
        AccountInput accountInput,
        AccountType type,
        BigDecimal totalTransaction,
        AccountConfigValidationParam accountConfigValidation
) implements TransactionValidation {

    @Override
    public boolean validateIfBalanceIsSufficient() {
        return AccountType.SOURCE.equals(type);
    }
}
