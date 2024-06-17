package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object;

import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.IExtractor;
import java.math.BigDecimal;

public record AccountConfigValidationParam(
        IExtractor accountExtractor,
        boolean accountExists,
        BigDecimal balance,
        boolean isActive,
        boolean hasTransactionDuplicated
) {
    public boolean accountNotExists() {
        return !accountExists;
    }

    public boolean accountIsInactive() {
        return !isActive;
    }

    public boolean transactionIsDuplicated() {
        return hasTransactionDuplicated;
    }
}
