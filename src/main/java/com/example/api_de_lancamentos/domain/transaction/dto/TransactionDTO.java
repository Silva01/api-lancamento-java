package com.example.api_de_lancamentos.domain.transaction.dto;

import com.example.api_de_lancamentos.domain.transaction.enuns.TypeTransactionEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class TransactionDTO {
    private final Long id;
    private final String description;
    private final TypeTransactionEnum type;
    private final LocalDateTime date;
    private final BigDecimal value;


    public TransactionDTO(Long id, String description, TypeTransactionEnum type, LocalDateTime date, BigDecimal value) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.date = date;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TypeTransactionEnum getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }
}
