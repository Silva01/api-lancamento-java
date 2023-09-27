package br.net.silva.daniel.domain;

import br.net.silva.daniel.enuns.TransactionType;
import br.net.silva.daniel.interfaces.TransactionComponent;

import java.math.BigDecimal;

public class Transaction implements TransactionComponent {
    private final Long id;
    private final String description;
    private final BigDecimal price;
    private final TransactionType type;
    private final Integer originAccount;
    private final Integer destinationAccount;
    private final Long idempotencyId;
    private final String creditCardNumber;
    private final Integer secretKey;

    public Transaction(Long id, String description, BigDecimal price, TransactionType type, Integer originAccount, Integer destinationAccount, Long idempotencyId, String creditCardNumber, Integer secretKey) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.type = type;
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.idempotencyId = idempotencyId;
        this.creditCardNumber = creditCardNumber;
        this.secretKey = secretKey;
    }


    @Override
    public BigDecimal sumPricePeerType(TransactionType type) {
        return type.equals(this.type) ? price : BigDecimal.ZERO;
    }

    @Override
    public Transaction get() {
        return this;
    }

    public TransactionType getType() {
        return type;
    }

}
