package br.net.silva.daniel.dto;

import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
public class CreditCardDTO implements IGenericPort {

    private String number;
    private Integer cvv;
    private FlagEnum flag;
    private BigDecimal balance;
    private LocalDate expirationDate;
    private boolean active;

    public CreditCardDTO(String number, Integer cvv, FlagEnum flag, BigDecimal balance, LocalDate expirationDate, boolean active) {
        this.number = number;
        this.cvv = cvv;
        this.flag = flag;
        this.balance = balance;
        this.expirationDate = expirationDate;
        this.active = active;
    }

    public CreditCardDTO() {
        this(null, null, null, null, null, false);
    }

    public String number() {
        return number;
    }

    public Integer cvv() {
        return cvv;
    }

    public FlagEnum flag() {
        return flag;
    }

    public BigDecimal balance() {
        return balance;
    }

    public LocalDate expirationDate() {
        return expirationDate;
    }

    public boolean active() {
        return active;
    }

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, CreditCardDTO.class, "Class must be assignable from CreditCardDTO");
    }

    @Override
    public CreditCardDTO get() {
        return this;
    }
}
