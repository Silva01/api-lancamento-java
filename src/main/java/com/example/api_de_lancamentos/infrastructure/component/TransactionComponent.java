package com.example.api_de_lancamentos.infrastructure.component;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.shared.interfaces.CreateRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.FindRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.UpdateRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.domain.transaction.dto.TransactionRequestDTO;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.domain.transaction.use_case.RegisterPurchaseLaunchUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TransactionComponent {

    private final CreateRepository<List<Transaction>> repository;
    private final FindRepository<Account, Long> accountFindRepository;
    private final UpdateRepository<Account> accountUpdateRepository;

    public TransactionComponent(CreateRepository<List<Transaction>> repository, FindRepository<Account, Long> accountFindRepository, UpdateRepository<Account> accountUpdateRepository) {
        this.repository = repository;
        this.accountFindRepository = accountFindRepository;
        this.accountUpdateRepository = accountUpdateRepository;
    }

    @Bean
    public UseCase<List<Transaction>, TransactionRequestDTO> registerPurchaseLaunchUseCase () {
        return new RegisterPurchaseLaunchUseCase(repository, accountFindRepository, accountUpdateRepository);
    }
}
