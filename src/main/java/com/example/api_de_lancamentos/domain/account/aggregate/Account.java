package com.example.api_de_lancamentos.domain.account.aggregate;

import com.example.api_de_lancamentos.domain.account.command.SolicitationBalanceCommand;
import com.example.api_de_lancamentos.domain.account.object_value.BalanceResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

public final class Account {

    private final BigDecimal balance;
    private final BigDecimal creditBalance;
    private final long accountNumber;
    private final String name;
    private final UUID id;

    public Account(BigDecimal balance, BigDecimal creditBalance, long accountNumber, String name) {
        this.balance = balance;
        this.creditBalance = creditBalance;
        this.accountNumber = accountNumber;
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public BalanceResponseDTO solicitationBalanceCommand(SolicitationBalanceCommand solicitationBalanceCommand) {
        return new BalanceResponseDTO(this.creditBalance, this.balance);
    }
}
