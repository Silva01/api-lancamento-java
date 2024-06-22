package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object;

public record AccountConfiguration(
        boolean isValidateBalance
) {

    public static AccountConfiguration sourceAccountConfiguration() {
        return new AccountConfiguration(true);
    }

    public static AccountConfiguration destinyAccountConfiguration() {
        return new AccountConfiguration(false);
    }
}
