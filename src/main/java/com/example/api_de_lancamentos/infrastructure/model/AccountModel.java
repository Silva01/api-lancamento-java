package com.example.api_de_lancamentos.infrastructure.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class AccountModel {

    @Id
    @Column(name = "account_number")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "credit_balance")
    private BigDecimal creditBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }
}
