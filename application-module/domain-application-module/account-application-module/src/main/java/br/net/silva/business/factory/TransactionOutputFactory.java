package br.net.silva.business.factory;

import br.net.silva.business.interfaces.TransactionFactorySpec;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.enuns.TransactionTypeEnum;

import java.math.BigDecimal;

public class TransactionOutputFactory implements TransactionFactorySpec.BuildSpec<TransactionOutput> {

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

    public static TransactionFactorySpec.IdSpec<TransactionOutput> createOutput() {
        return new TransactionOutputFactory();
    }

    @Override
    public TransactionFactorySpec.DescriptionSpec<TransactionOutput> withId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public TransactionFactorySpec.PriceSpec<TransactionOutput> withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public TransactionFactorySpec.QuantitySpec<TransactionOutput> withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public TransactionFactorySpec.TypeSpec<TransactionOutput> withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public TransactionFactorySpec.OriginAccountNumberSpec<TransactionOutput> withType(TransactionTypeEnum type) {
        this.type = type;
        return this;
    }

    @Override
    public TransactionFactorySpec.DestinationAccountNumberSpec<TransactionOutput> withOriginAccountNumber(Integer originAccountNumber) {
        this.originAccountNumber = originAccountNumber;
        return this;
    }

    @Override
    public TransactionFactorySpec.IdempotencyIdSpec<TransactionOutput> withDestinationAccountNumber(Integer destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
        return this;
    }

    @Override
    public TransactionFactorySpec.CreditCardNumberSpec<TransactionOutput> withIdempotencyId(Long idempotencyId) {
        this.idempotencyId = idempotencyId;
        return this;
    }

    @Override
    public TransactionFactorySpec.BuildSpec<TransactionOutput> andWithIdempotencyId(Long idempotencyId) {
        withIdempotencyId(idempotencyId);
        return this;
    }

    @Override
    public TransactionFactorySpec.CreditCardCvvSpec<TransactionOutput> withCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    @Override
    public TransactionFactorySpec.BuildSpec<TransactionOutput> andWithCreditCardCvv(Integer creditCardCvv) {
        this.creditCardCvv = creditCardCvv;
        return this;
    }

    @Override
    public TransactionOutput build() {
        return new TransactionOutput(id, description, price, quantity, type, originAccountNumber, destinationAccountNumber, idempotencyId, creditCardNumber, creditCardCvv);
    }
}
