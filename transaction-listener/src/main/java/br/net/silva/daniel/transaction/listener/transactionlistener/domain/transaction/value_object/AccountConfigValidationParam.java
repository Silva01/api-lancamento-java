package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object;

import java.math.BigDecimal;

public record AccountConfigValidationParam(
        boolean accountExists,
        BigDecimal balance,
        boolean isActive
) {
    public boolean accountNotExists() {
        return !accountExists;
    }

    public boolean accountIsInactive() {
        return !isActive;
    }
}
