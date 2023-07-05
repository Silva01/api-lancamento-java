package com.example.api_de_lancamentos.domain.transaction.factory;

import com.example.api_de_lancamentos.domain.transaction.dto.TransactionDTO;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionFactory {

    public static List<Transaction> createTransactions(List<TransactionDTO> transactions, long accountNumber) {
        return transactions
                .stream()
                .map(t -> new Transaction(t.getDescription(), t.getType(), t.getDate(), t.getValue(), accountNumber))
                .collect(Collectors.toList());
    }
}
