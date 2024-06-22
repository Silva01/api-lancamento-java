package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces;

public interface TransactionValidation {

    default boolean validateIfAccountExists() {
        return true;
    }

    default boolean validateIfAccountIsActive() {
        return true;
    }

    default boolean validateIfBalanceIsSufficient() {
        return true;
    }

    default boolean validateIfTransactionIsDuplicated() {
        return true;
    }
}
