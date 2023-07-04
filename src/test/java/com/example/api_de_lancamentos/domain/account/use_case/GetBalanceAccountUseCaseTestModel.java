package com.example.api_de_lancamentos.domain.account.use_case;

import com.example.api_de_lancamentos.domain.account.dto.BalanceDTO;
import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.infrastructure.ApiDeLancamentosApplication;
import com.example.api_de_lancamentos.infrastructure.model.AccountModel;
import com.example.api_de_lancamentos.infrastructure.repository.AccountModelRepository;
import com.example.api_de_lancamentos.infrastructure.repository.impl.AccountModelRepositoryIMPL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {ApiDeLancamentosApplication.class})
class GetBalanceAccountUseCaseTestModel {

    private final AccountModelRepositoryIMPL accountRepository;

    private final AccountModelRepository repository;

    @Autowired
    GetBalanceAccountUseCaseTestModel(AccountModelRepositoryIMPL accountRepository, AccountModelRepository repository) {
        this.accountRepository = accountRepository;
        this.repository = repository;
    }

    @BeforeEach
    void loadContext() {
        AccountModel accountModel = new AccountModel();
        accountModel.setAccountNumber(1L);
        accountModel.setName("Teste");
        accountModel.setBalance(new BigDecimal("3000.00"));
        accountModel.setCreditBalance(new BigDecimal("5000.00"));
        repository.save(accountModel);
    }

    @Test
    public void deve_trazer_saldo_conta_valida() throws AccountNotExistsException {
        UseCase<BalanceDTO, Long> obterSaldoUseCase = new GetBalanceAccountUseCase(accountRepository);
        BalanceDTO responseBalance = obterSaldoUseCase.execute(1L);

        assertEquals(responseBalance.getAccountBalance().toString(), "3000.00");
        assertEquals(responseBalance.getAccountCreditBalance().toString(), "5000.00");
    }

    @Test
    public void deve_dar_erro_ao_nao_encontrar_saldo() throws AccountNotExistsException {
        UseCase<BalanceDTO, Long> obterSaldoUseCase = new GetBalanceAccountUseCase(accountRepository);
        AccountNotExistsException exception = assertThrows(AccountNotExistsException.class, () -> obterSaldoUseCase.execute(9000000L));

        assertEquals(exception.getMessage(), "Conta não encontrada");
    }

}