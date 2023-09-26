package br.net.silva.daniel.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCard {
    private String cardNumber;
    private Integer secretKey;
    private final String cardFlag;
    private BigDecimal balance;
    private boolean active;
    private final LocalDate validateDate;

    public CreditCard(String cardNumber, Integer secretKey, String cardFlag, BigDecimal balance, boolean active, LocalDate validateDate) {
        this.cardNumber = cardNumber;
        this.secretKey = secretKey;
        this.cardFlag = cardFlag;
        this.balance = balance;
        this.active = active;
        this.validateDate = validateDate;
    }

    public String getNumber() {
        return cardNumber;
    }
}
