package com.example.api_de_lancamentos.domain.account.command;

import com.example.api_de_lancamentos.domain.account.aggregate.Account;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GetAccountCommandTest {

    @Test
    public void deve_trazer_uma_conta_valida() {
        GetAccountCommand getAccountCommand = new GetAccountCommand(1234);
        Account account = new Account();
        account = account.getAccountCommand(getAccountCommand);

        assertEquals(new BigDecimal("3000"), account.getBalance());
        assertEquals(new BigDecimal("5000"), account.getCreditBalance());
    }
}