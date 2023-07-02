package com.example.api_de_lancamentos.domain.account.object_value;

import java.math.BigDecimal;

public final class BalanceResponseDTO {
    private final BigDecimal creditBalance;
    private final BigDecimal balance;

    public BalanceResponseDTO(BigDecimal creditBalance, BigDecimal balance) {
        this.creditBalance = creditBalance;
        this.balance = balance;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
