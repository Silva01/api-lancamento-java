package br.net.silva.daniel.enuns;

public enum TransactionTypeEnum {
    DEBIT("DEBIT"), CREDIT("CREDIT");

    private final String value;

    TransactionTypeEnum(String value) {
        this.value = value;
    }
}
