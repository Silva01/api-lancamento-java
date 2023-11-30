package br.net.silva.daniel.entity;

import br.net.silva.daniel.utils.CreditCardUtils;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.interfaces.Aggregate;
import br.net.silva.daniel.interfaces.IFactory;
import br.net.silva.daniel.validation.Validation;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCard extends Validation implements Aggregate, IFactory<CreditCardDTO> {

    private final String number;
    private final Integer cvv;
    private final FlagEnum flag;
    private final BigDecimal balance;
    private final LocalDate expirationDate;
    private boolean active;

    public CreditCard(String number, Integer cvv, FlagEnum flag, BigDecimal balance, LocalDate expirationDate, boolean active) {
        this.number = number;
        this.cvv = cvv;
        this.flag = flag;
        this.balance = balance;
        this.expirationDate = expirationDate;
        this.active = active;
        validate();
    }

    public CreditCard() {
        this(CreditCardUtils.generateCreditCardNumber(), CreditCardUtils.generateCvv(), FlagEnum.MASTER_CARD, BigDecimal.valueOf(2000), CreditCardUtils.generateExpirationDate(), true);
    }

    public CreditCard(String number, Integer cvv, FlagEnum flag, BigDecimal balance, LocalDate expirationDate) {
        this(number, cvv, flag, balance, expirationDate, true);
    }

    public CreditCard(FlagEnum flag) {
        this(CreditCardUtils.generateCreditCardNumber(), CreditCardUtils.generateCvv(), flag, BigDecimal.valueOf(2000), CreditCardUtils.generateExpirationDate(), true);
    }

    @Override
    public void validate() {
        validateAttributeNotNullAndNotEmpty(number, "Number of credit card cannot be null or empty");
        validateAttributeNonNull(cvv, "Key secret of credit card cannot be null");
        validateAttributeEqualsZero(cvv, "Key secret of credit card cannot be zero");
        validateAttributeLessThanZero(cvv, "Key secret of credit card cannot be less than zero");
        validateAttributeNonNull(flag, "This flag of credit card cannot be null");
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
        return new CreditCardDTO(number, cvv, flag, balance, expirationDate, active);
    }

    public void validateBalance(BigDecimal value) {
        validateBalance(balance, value);
    }
}
