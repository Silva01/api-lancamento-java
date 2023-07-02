package com.example.api_de_lancamentos.domain.account.aggregate;

import com.example.api_de_lancamentos.domain.account.command.GetAccountCommand;
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

    public Account () {
        this(new BigDecimal(0), new BigDecimal(0), 0, "");
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public BalanceResponseDTO solicitationBalanceCommand(final SolicitationBalanceCommand solicitationBalanceCommand) {
        Account account = getAccountCommand(new GetAccountCommand(solicitationBalanceCommand.getAccountNumber()));
        return new BalanceResponseDTO(account.getCreditBalance(), account.getBalance());
    }

    public Account getAccountCommand(final GetAccountCommand getAccountCommand) {
        return new Account(new BigDecimal("3000.00"), new BigDecimal("5000.00"), getAccountCommand.getAccountNumber(), "Daniel");
    }
}
