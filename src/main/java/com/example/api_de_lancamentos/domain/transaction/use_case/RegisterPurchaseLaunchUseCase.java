package com.example.api_de_lancamentos.domain.transaction.use_case;

import com.example.api_de_lancamentos.domain.shared.interfaces.CreateRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;

import java.util.List;

public class RegisterPurchaseLaunchUseCase implements UseCase<List<Transaction>, List<Transaction>> {

    private final CreateRepository<List<Transaction>> repository;

    public RegisterPurchaseLaunchUseCase(CreateRepository<List<Transaction>> repository) {
        this.repository = repository;
    }

    @Override
    public List<Transaction> execute(List<Transaction> entity) {
        return repository.create(entity);
    }
}
