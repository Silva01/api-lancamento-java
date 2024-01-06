package br.net.silva.daniel.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;

import java.math.BigDecimal;
import java.util.List;

public class AccountDTO implements IGenericPort {

    private Integer number;
    private Integer bankAgencyNumber;
    private BigDecimal balance;
    private String password;
    private boolean active;
    private String cpf;
    private List<TransactionDTO>transactions;
    private CreditCardDTO creditCard;

    public AccountDTO(Integer number, Integer bankAgencyNumber, BigDecimal balance, String password, boolean active, String cpf, List<TransactionDTO> transactions, CreditCardDTO creditCard) {
        this.number = number;
        this.bankAgencyNumber = bankAgencyNumber;
        this.balance = balance;
        this.password = password;
        this.active = active;
        this.cpf = cpf;
        this.transactions = transactions;
        this.creditCard = creditCard;
    }

    public AccountDTO() {
        this(null, 0, BigDecimal.valueOf(0), null, false, null, null, null);
    }

    public Integer number() {
        return number;
    }

    public Integer bankAgencyNumber() {
        return bankAgencyNumber;
    }

    public BigDecimal balance() {
        return balance;
    }

    public String password() {
        return password;
    }

    public boolean active() {
        return active;
    }

    public String cpf() {
        return cpf;
    }

    public List<TransactionDTO> transactions() {
        return transactions;
    }

    public CreditCardDTO creditCard() {
        return creditCard;
    }

    @Override
    public void accept(Class<?> clazz) {
        if (!clazz.isInstance(AccountDTO.class))
            throw new IllegalArgumentException("Class must be assignable from IAccountPort");
    }

    @Override
    public AccountDTO get() {
        return this;
    }
}
