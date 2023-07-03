package com.example.api_de_lancamentos.infrastructure.component;

import com.example.api_de_lancamentos.domain.shared.interfaces.CreateRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.domain.transaction.use_case.RegisterPurchaseLaunchUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TransactionComponent {

    private final CreateRepository<List<Transaction>> repository;

    public TransactionComponent(CreateRepository<List<Transaction>> repository) {
        this.repository = repository;
    }

    @Bean
    public UseCase<List<Transaction>, List<Transaction>> registerPurchaseLaunchUseCase () {
        return new RegisterPurchaseLaunchUseCase(repository);
    }
}
