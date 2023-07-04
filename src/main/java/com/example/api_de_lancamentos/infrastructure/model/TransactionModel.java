package com.example.api_de_lancamentos.infrastructure.model;

import com.example.api_de_lancamentos.domain.transaction.enuns.TypeTransactionEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public final class TransactionModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private TypeTransactionEnum type;

    @Column(name = "transaction_value")
    private BigDecimal value;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "account_number")
    private AccountModel account;

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
