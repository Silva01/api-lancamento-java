package com.example.api_de_lancamentos.domain.account.dto;

public final class CreatedAccountNumberDTO {
    private final long accountNumber;

    public CreatedAccountNumberDTO(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getAccountNumber() {
        return accountNumber;
    }
}
