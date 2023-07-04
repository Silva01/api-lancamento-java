package com.example.api_de_lancamentos.domain.account.use_case;

import com.example.api_de_lancamentos.domain.account.dto.BalanceDTO;
import com.example.api_de_lancamentos.domain.account.dto.CreatedAccountNumberDTO;
import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.shared.interfaces.FindRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.infrastructure.ApiDeLancamentosApplication;
import com.example.api_de_lancamentos.infrastructure.repository.impl.AccountModelRepositoryIMPL;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(classes = {ApiDeLancamentosApplication.class})
class UpdateBalanceUseCaseTest {

    private final UseCase<Boolean, Account> updateBalanceUseCase;

    private final UseCase<CreatedAccountNumberDTO, Account> createAccountUseCase;

    private final UseCase<BalanceDTO, Long> getBalanceUseCase;

    private final FindRepository<Account, Long> accountFindRepository;

    @Autowired
    UpdateBalanceUseCaseTest(AccountModelRepositoryIMPL accountRepository) {
        this.updateBalanceUseCase = new UpdateBalanceUseCase(accountRepository);
        this.createAccountUseCase = new CreateAccountUseCase(accountRepository);
        this.getBalanceUseCase = new GetBalanceAccountUseCase(accountRepository);
        this.accountFindRepository = accountRepository;
    }

    @Test
    void deve_atualizar_saldo_de_credito() {

        CreatedAccountNumberDTO accountNumber = createAccountUseCase.execute(new Account("Teste"));

        Account account = accountFindRepository.findBy(accountNumber.getAccountNumber());

        assertEquals(new BigDecimal("3000.00"), account.getAccountBalance());
        assertEquals(new BigDecimal("5000.00"), account.getAccountCreditBalance());
        assertEquals("Teste", account.getAccountName());

        updateBalanceUseCase.execute(new Account(account.getAccountNumber(), account.getAccountName(), account.getAccountBalance(), new BigDecimal("100")));

        BalanceDTO balance = getBalanceUseCase.execute(accountNumber.getAccountNumber());

        assertEquals(new BigDecimal("3000.00"), balance.getAccountBalance());
        assertEquals(new BigDecimal("100.00"), balance.getAccountCreditBalance());

    }

    @Test
    void deve_atualizar_saldo_de_debito() {

        CreatedAccountNumberDTO accountNumber = createAccountUseCase.execute(new Account("Teste"));

        Account account = accountFindRepository.findBy(accountNumber.getAccountNumber());

        assertEquals(new BigDecimal("3000.00"), account.getAccountBalance());
        assertEquals(new BigDecimal("5000.00"), account.getAccountCreditBalance());
        assertEquals("Teste", account.getAccountName());

        updateBalanceUseCase.execute(new Account(account.getAccountNumber(), account.getAccountName(), new BigDecimal("100"), account.getAccountCreditBalance()));

        BalanceDTO balance = getBalanceUseCase.execute(accountNumber.getAccountNumber());

        assertEquals(new BigDecimal("100.00"), balance.getAccountBalance());
        assertEquals(new BigDecimal("5000.00"), balance.getAccountCreditBalance());

    }
}