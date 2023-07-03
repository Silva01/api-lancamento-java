package com.example.api_de_lancamentos.domain.transaction.dto;

import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;

import java.util.List;

public final class TransactionRequestDTO {
    private final List<Transaction> transactions;

    public TransactionRequestDTO(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
