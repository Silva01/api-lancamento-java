package br.net.silva.daniel.entity;

import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.interfaces.Aggregate;
import br.net.silva.daniel.interfaces.IFactory;
import br.net.silva.daniel.validation.Validation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCard extends Validation implements Aggregate, IFactory<CreditCardDTO> {

    private final String number;
    private final Integer secretKey;
    private final String flag;
    private final BigDecimal balance;
    private final LocalDate expirationDate;
    private boolean active;

    public CreditCard(String number, Integer secretKey, String flag, BigDecimal balance, LocalDate expirationDate, boolean active) {
        this.number = number;
        this.secretKey = secretKey;
        this.flag = flag;
        this.balance = balance;
        this.expirationDate = expirationDate;
        this.active = active;
    }

    public CreditCard(String number, Integer secretKey, String flag, BigDecimal balance, LocalDate expirationDate) {
        this(number, secretKey, flag, balance, expirationDate, true);
    }

    public CreditCard(String flag) {
        //TODO: Deve gerar um cartão de forma automatica, implementar formas de gerar os atributos que estão nulos
        this(null, null, flag, null, null, true);
    }

    @Override
    public void validate() {

    }

    @Override
    public CreditCardDTO create() {
        return null;
    }
}
