package br.net.silva.daniel.entity;

import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.business.interfaces.Aggregate;
import br.net.silva.daniel.shared.business.factory.IFactoryDto;
import br.net.silva.daniel.shared.business.validation.Validation;

import java.math.BigDecimal;

public class Transaction extends Validation implements Aggregate, IFactoryDto<TransactionDTO> {

    private final Long id;
    private final String description;
    private final BigDecimal price;
    private final Integer quantity;
    private final TransactionTypeEnum type;
    private final Integer originAccountNumber;
    private final Integer destinationAccountNumber;
    private final Long idempotencyId;
    private String creditCardNumber;
    private Integer creditCardCvv;

    public Transaction(Long id, String description, BigDecimal price, Integer quantity, TransactionTypeEnum type, Integer originAccountNumber, Integer destinationAccountNumber, Long idempotencyId, String creditCardNumber, Integer creditCardCvv) {
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
        validateAttributeLessThanZero(quantity, "Quantity is less than zero");
        validateAttributeNonNull(type, "Type is required");
        validateAttributeNonNull(originAccountNumber, "Origin account number is null");
        validateAttributeLessThanZero(originAccountNumber, "Origin account number is less than zero");
        validateAttributeNonNull(destinationAccountNumber, "Destination account number is null");
        validateAttributeLessThanZero(destinationAccountNumber, "Destination account number is less than zero");
        validateAttributeNonNull(idempotencyId, "Idempotency id is null");

        if (type.equals(TransactionTypeEnum.CREDIT)) {
            validateAttributeNotNullAndNotEmpty(creditCardNumber, "Credit card number is null or empty");
            validateAttributeNonNull(creditCardCvv, "Credit card cvv is null");
            validateAttributeEqualsZero(creditCardCvv, "Credit card cvv is equals zero");
        }
    }

    public boolean isCreditTransaction() {
        return type.equals(TransactionTypeEnum.CREDIT);
    }

    @Override
    public TransactionDTO build() {
        return new TransactionDTO(
                id,
                description,
                price,
                quantity,
                type,
                originAccountNumber,
                destinationAccountNumber,
                idempotencyId,
                creditCardNumber,
                creditCardCvv);
    }
}
