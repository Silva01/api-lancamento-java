package com.example.api_de_lancamentos.infrastructure.repository.impl;

import com.example.api_de_lancamentos.domain.shared.interfaces.CreateRepository;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.infrastructure.model.TransactionModel;
import com.example.api_de_lancamentos.infrastructure.repository.TransactionModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionModelRepositoryIMPL implements CreateRepository<List<Transaction>> {

    private final TransactionModelRepository repository;

    @Autowired
    public TransactionModelRepositoryIMPL(TransactionModelRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Transaction> create(List<Transaction> entList) {
        List<TransactionModel> models = entList.stream().map(transaction -> new TransactionModel(transaction.getId(), transaction.getDescription(), transaction.getType(), transaction.getValue(), transaction.getDate(), transaction.getAccountNumber())).collect(Collectors.toList());
        repository.saveAll(models);
        return entList;
    }
}
