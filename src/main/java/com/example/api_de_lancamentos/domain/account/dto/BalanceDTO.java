package com.example.api_de_lancamentos.domain.account.dto;

import java.math.BigDecimal;

public final class BalanceDTO {
    private final BigDecimal accountBalance;
    private final BigDecimal accountCreditBalance;

    public BalanceDTO(BigDecimal accountBalance, BigDecimal accountCreditBalance) {
        this.accountBalance = accountBalance;
        this.accountCreditBalance = accountCreditBalance;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public BigDecimal getAccountCreditBalance() {
        return accountCreditBalance;
    }
}
