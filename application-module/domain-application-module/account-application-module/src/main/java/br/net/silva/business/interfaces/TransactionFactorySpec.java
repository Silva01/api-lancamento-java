package br.net.silva.business.interfaces;

import br.net.silva.daniel.enuns.TransactionTypeEnum;

import java.math.BigDecimal;

public interface TransactionFactorySpec {

    interface IdSpec<R> {
        DescriptionSpec<R> withId(Long id);
    }

    interface DescriptionSpec<R> {
        PriceSpec<R> withDescription(String description);
    }

    interface PriceSpec<R> {
        QuantitySpec<R> withPrice(BigDecimal price);
    }

    interface QuantitySpec<R> {
        TypeSpec<R> withQuantity(Integer quantity);
    }

    interface TypeSpec<R> {
        OriginAccountNumberSpec<R> withType(TransactionTypeEnum type);
    }

    interface OriginAccountNumberSpec<R> {
        DestinationAccountNumberSpec<R> withOriginAccountNumber(Integer originAccountNumber);
    }

    interface DestinationAccountNumberSpec<R> {
        IdempotencyIdSpec<R> withDestinationAccountNumber(Integer destinationAccountNumber);
    }

    interface IdempotencyIdSpec<R> {
        CreditCardNumberSpec<R> withIdempotencyId(Long idempotencyId);
        BuildSpec<R> andWithIdempotencyId(Long idempotencyId);
    }

    interface CreditCardNumberSpec<R> {
        CreditCardCvvSpec<R> withCreditCardNumber(String creditCardNumber);
    }

    interface CreditCardCvvSpec<R> {
        BuildSpec<R> andWithCreditCardCvv(Integer creditCardCvv);
    }

    interface BuildSpec<R> extends IdSpec<R>, DescriptionSpec<R>, PriceSpec<R>, QuantitySpec<R>, TypeSpec<R>, OriginAccountNumberSpec<R>, DestinationAccountNumberSpec<R>, IdempotencyIdSpec<R>, CreditCardNumberSpec<R>, CreditCardCvvSpec<R>{
        R build();
    }

}
