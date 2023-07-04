package com.example.api_de_lancamentos.domain.transaction.dto;

import java.util.List;

public final class TransactionRequestDTO {

    private final long accountNumber;
    private final List<TransactionDTO> transactions;

    public TransactionRequestDTO(long accountNumber, List<TransactionDTO> transactions) {
        this.accountNumber = accountNumber;
        this.transactions = transactions;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }
    public long getAccountNumber() {
        return accountNumber;
    }
}
