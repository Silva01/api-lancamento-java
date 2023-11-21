package br.net.silva.daniel.entity;

import br.net.silva.daniel.interfaces.Aggregate;
import br.net.silva.daniel.validation.Validation;

import java.math.BigDecimal;

public class Transaction extends Validation implements Aggregate {

    private final Long id;
    private final String description;
    private final BigDecimal price;
    private final Integer quantity;
    private final String type;
    private final Integer originAccountNumber;
    private final Integer destinationAccountNumber;
    private final Long idempotencyId;
    private String creditCardNumber;
    private Integer creditCardCvv;

    public Transaction(Long id, String description, BigDecimal price, Integer quantity, String type, Integer originAccountNumber, Integer destinationAccountNumber, Long idempotencyId, String creditCardNumber, Integer creditCardCvv) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.originAccountNumber = originAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        this.idempotencyId = idempotencyId;
        this.creditCardNumber = creditCardNumber;
        this.creditCardCvv = creditCardCvv;
        validate();
    }

    @Override
    public void validate() {
        validateAttributeNotNullAndNotEmpty(description, "Description is null or empty");
        validateAttributeNonNull(price, "Price is null");
        validateAttributeLessThanZero(price, "Price is less than zero");
        validateAttributeNonNull(quantity, "Quantity is null");
        validateAttributeLessThanZero(BigDecimal.valueOf(quantity), "Quantity is less than zero");
        validateAttributeNotNullAndNotEmpty(type, "Type is null or empty");
        validateAttributeNonNull(originAccountNumber, "Origin account number is null");
        validateAttributeLessThanZero(BigDecimal.valueOf(originAccountNumber), "Origin account number is less than zero");
        validateAttributeNonNull(destinationAccountNumber, "Destination account number is null");
        validateAttributeLessThanZero(BigDecimal.valueOf(destinationAccountNumber), "Destination account number is less than zero");
        validateAttributeNonNull(idempotencyId, "Idempotency id is null");

        if (type.equals("CREDIT")) {
            validateAttributeNotNullAndNotEmpty(creditCardNumber, "Credit card number is null or empty");
            validateAttributeNonNull(creditCardCvv, "Credit card cvv is null");
            validateAttributeEqualsZero(BigDecimal.valueOf(creditCardCvv), "Credit card cvv is equals zero");

        }
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public Integer getOriginAccountNumber() {
        return originAccountNumber;
    }

    public Integer getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public Long getIdempotencyId() {
        return idempotencyId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public Integer getCreditCardCvv() {
        return creditCardCvv;
    }
}
