package com.example.api_de_lancamentos.domain.transaction.enuns;

public enum TypeTransactionEnum {
    CREDIT("CREDIT"),
    DEBIT("DEBIT");

    private final String type;

    TypeTransactionEnum(String type) {
        this.type = type;
    }

    public TypeTransactionEnum getType(String type) {
        return TypeTransactionEnum.valueOf(type);
    }
}
