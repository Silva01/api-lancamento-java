package com.example.api_de_lancamentos.domain.account.command;

public final class GetAccountCommand {
    private final long accountNumber;

    public GetAccountCommand(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }
}
