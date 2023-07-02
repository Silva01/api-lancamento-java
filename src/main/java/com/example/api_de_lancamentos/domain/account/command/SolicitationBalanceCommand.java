package com.example.api_de_lancamentos.domain.account.command;

public final class SolicitationBalanceCommand {
    private final long accountNumber;

    public SolicitationBalanceCommand(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }
}
