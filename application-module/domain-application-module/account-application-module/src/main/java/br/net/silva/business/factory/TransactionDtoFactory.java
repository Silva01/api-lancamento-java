package br.net.silva.business.factory;

import br.net.silva.business.interfaces.TransactionFactorySpec;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.enuns.TransactionTypeEnum;

import java.math.BigDecimal;

public class TransactionDtoFactory implements TransactionFactorySpec.BuildSpec<TransactionDTO> {

    private Long id;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private TransactionTypeEnum type;
    private Integer originAccountNumber;
    private Integer destinationAccountNumber;
    private Long idempotencyId;
    private String creditCardNumber;
    private Integer creditCardCvv;

    public static TransactionFactorySpec.IdSpec<TransactionDTO> createDto() {
        return new TransactionDtoFactory();
    }

    @Override
    public TransactionFactorySpec.DescriptionSpec<TransactionDTO> withId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public TransactionFactorySpec.PriceSpec<TransactionDTO> withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public TransactionFactorySpec.QuantitySpec<TransactionDTO> withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public TransactionFactorySpec.TypeSpec<TransactionDTO> withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public TransactionFactorySpec.OriginAccountNumberSpec<TransactionDTO> withType(TransactionTypeEnum type) {
        this.type = type;
        return this;
    }

    @Override
    public TransactionFactorySpec.DestinationAccountNumberSpec<TransactionDTO> withOriginAccountNumber(Integer originAccountNumber) {
        this.originAccountNumber = originAccountNumber;
        return this;
    }

    @Override
    public TransactionFactorySpec.IdempotencyIdSpec<TransactionDTO> withDestinationAccountNumber(Integer destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
        return this;
    }

    @Override
    public TransactionFactorySpec.CreditCardNumberSpec<TransactionDTO> withIdempotencyId(Long idempotencyId) {
        this.idempotencyId = idempotencyId;
        return this;
    }

    @Override
    public TransactionFactorySpec.BuildSpec<TransactionDTO> andWithIdempotencyId(Long idempotencyId) {
        withIdempotencyId(idempotencyId);
        return this;
    }

    @Override
    public TransactionFactorySpec.CreditCardCvvSpec<TransactionDTO> withCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    @Override
    public TransactionFactorySpec.BuildSpec<TransactionDTO> andWithCreditCardCvv(Integer creditCardCvv) {
        this.creditCardCvv = creditCardCvv;
        return this;
    }

    @Override
    public TransactionDTO build() {
        return new TransactionDTO(id, description, price, quantity, type, originAccountNumber, destinationAccountNumber, idempotencyId, creditCardNumber, creditCardCvv);
    }
}
