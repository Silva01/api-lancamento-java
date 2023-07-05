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

    @Column(name = "account_number")
    private Long accountNumber;

    public TransactionModel(Long id, String description, TypeTransactionEnum type, BigDecimal value, LocalDateTime date, Long accountNumber) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.value = value;
        this.date = date;
        this.accountNumber = accountNumber;
    }

    public TransactionModel() {
        this(0L, "Objeto vazio", null, null, null, 0L);
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

    public Long getAccountNumber() {
        return accountNumber;
    }
}
