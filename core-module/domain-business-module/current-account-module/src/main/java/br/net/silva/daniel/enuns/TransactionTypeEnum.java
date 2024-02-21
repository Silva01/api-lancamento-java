package br.net.silva.daniel.enuns;

public enum TransactionTypeEnum {
    DEBIT("DEBIT"), CREDIT("CREDIT"), REVERSAL("REVERSAL");

    private final String value;

    TransactionTypeEnum(String value) {
        this.value = value;
    }
}
