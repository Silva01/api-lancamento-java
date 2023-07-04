package com.example.api_de_lancamentos.infrastructure.component;

import com.example.api_de_lancamentos.domain.account.dto.BalanceDTO;
import com.example.api_de_lancamentos.domain.account.dto.CreatedAccountNumberDTO;
import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.use_case.CreateAccountUseCase;
import com.example.api_de_lancamentos.domain.account.use_case.GetBalanceAccountUseCase;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.infrastructure.repository.impl.AccountModelRepositoryIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountComponent {

    private final AccountModelRepositoryIMPL accountRepository;

    @Autowired
    public AccountComponent(AccountModelRepositoryIMPL accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public UseCase<BalanceDTO, Long> getBalanceAccountUseCase() {
        return new GetBalanceAccountUseCase(accountRepository);
    }

    @Bean
    public UseCase<CreatedAccountNumberDTO, Account> createAccountUseCase() {
        return new CreateAccountUseCase(accountRepository);
    }
}
