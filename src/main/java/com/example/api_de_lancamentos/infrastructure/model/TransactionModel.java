package com.example.api_de_lancamentos.infrastructure.model;

import com.example.api_de_lancamentos.domain.transaction.enuns.TypeTransactionEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public final class TransactionModel {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    private TypeTransactionEnum type;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "date")
    private LocalDateTime date;

    public TransactionModel(Long id, String description, TypeTransactionEnum type, BigDecimal value, LocalDateTime date) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.value = value;
        this.date = date;
    }

    public TransactionModel() {
        this(0L, "Objeto vazio", null, null, null);
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

    public BigDecimal getValue() {
        return value;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
