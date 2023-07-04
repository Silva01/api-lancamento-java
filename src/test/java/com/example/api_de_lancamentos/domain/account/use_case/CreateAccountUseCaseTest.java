package com.example.api_de_lancamentos.domain.account.use_case;

import com.example.api_de_lancamentos.domain.account.dto.CreatedAccountNumberDTO;
import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.factory.AccountFactory;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.infrastructure.ApiDeLancamentosApplication;
import com.example.api_de_lancamentos.infrastructure.repository.impl.AccountModelRepositoryIMPL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ApiDeLancamentosApplication.class})
class CreateAccountUseCaseTest {

    private final AccountModelRepositoryIMPL accountRepository;

    @Autowired
    CreateAccountUseCaseTest(AccountModelRepositoryIMPL accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Test
    void deve_criar_uma_conta() {
        UseCase<CreatedAccountNumberDTO, Account> createAccountUseCase = new CreateAccountUseCase(accountRepository);
        CreatedAccountNumberDTO accountCreated = createAccountUseCase.execute(AccountFactory.createAccount("Teste"));

        assertNotNull(accountCreated.getAccountNumber());

        CreatedAccountNumberDTO accountCreated2 = createAccountUseCase.execute(AccountFactory.createAccount("Teste2"));

        assertNotNull(accountCreated2.getAccountNumber());
    }
}