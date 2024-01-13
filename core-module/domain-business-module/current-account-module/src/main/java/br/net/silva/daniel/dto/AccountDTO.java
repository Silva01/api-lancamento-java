package br.net.silva.daniel.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
public class AccountDTO implements IGenericPort {

    private Integer number;
    private Integer agency;
    private BigDecimal balance;
    private String password;
    private boolean active;
    private String cpf;
    private List<TransactionDTO> transactions;
    private CreditCardDTO creditCard;

    public AccountDTO(Integer number, Integer agency, BigDecimal balance, String password, boolean active, String cpf, List<TransactionDTO> transactions, CreditCardDTO creditCard) {
        this.number = number;
        this.agency = agency;
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
        return agency;
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
        ValidateUtils.isTypeOf(clazz, AccountDTO.class, "Class must be assignable from AccountDTO");
    }

    @Override
    public AccountDTO get() {
        return this;
    }
}
