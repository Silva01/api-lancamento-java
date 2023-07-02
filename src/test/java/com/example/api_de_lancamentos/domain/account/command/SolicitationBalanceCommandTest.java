package com.example.api_de_lancamentos.domain.account.command;

import com.example.api_de_lancamentos.domain.account.aggregate.Account;
import com.example.api_de_lancamentos.domain.account.object_value.BalanceResponseDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@SpringBootTest(classes = {SolicitationBalanceCommand.class, Account.class})
public class SolicitationBalanceCommandTest {
    @Test
    public void deve_retornar_saldo_da_conta_corrente() {

        boolean isCredit = false;
        SolicitationBalanceCommand solicitationBalanceCommand = new SolicitationBalanceCommand(4321111L);

        Account account = new Account(new BigDecimal("3000.00"), new BigDecimal("5000.00"), 4321111L, "Daniel");
        BalanceResponseDTO balance = account.solicitationBalanceCommand(solicitationBalanceCommand);

        assertEquals(new BigDecimal("3000.00"), balance.getBalance());
    }

    @Test
    void deve_retornar_saldo_da_conta_credito() {
        boolean isCredit = true;
        SolicitationBalanceCommand solicitationBalanceCommand = new SolicitationBalanceCommand(4321111L);

        Account account = new Account(new BigDecimal("3000.00"), new BigDecimal("5000.00"), 4321111L, "Daniel");
        BalanceResponseDTO balance = account.solicitationBalanceCommand(solicitationBalanceCommand);

        assertEquals(new BigDecimal("5000.00"), balance.getCreditBalance());
    }
}