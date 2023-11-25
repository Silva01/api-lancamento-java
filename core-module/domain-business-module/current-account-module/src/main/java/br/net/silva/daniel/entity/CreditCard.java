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

    public CreditCard() {
        this(null, null, null, null, null, true);
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
        validateAttributeNotNullAndNotEmpty(number, "Number of credit card cannot be null or empty");
        validateAttributeNonNull(secretKey, "Key secret of credit card cannot be null");
        validateAttributeEqualsZero(BigDecimal.valueOf(secretKey), "Key secret of credit card cannot be zero");
        validateAttributeLessThanZero(BigDecimal.valueOf(secretKey), "Key secret of credit card cannot be less than zero");
        validateAttributeNotNullAndNotEmpty(flag, "This flag of credit card cannot be null or empty");
        validateAttributeNonNull(balance, "This balance of credit card cannot be null");
        validateAttributeNonNull(expirationDate, "Date of expiration of credit card cannot be null");
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public String getNumber() {
        return this.number;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public CreditCardDTO create() {
        return new CreditCardDTO(number, secretKey, flag, balance, expirationDate, active);
    }

    private void validateBalance(BigDecimal value) {
        validateBalance(balance, value);
    }

    private String generateCardNumber() {
        //TODO: Here is where generating the card number
        return "";
    }

    private Integer generateSecretKey() {
        //TODO: Here is where generating the secret key
        return 0;
    }
}
