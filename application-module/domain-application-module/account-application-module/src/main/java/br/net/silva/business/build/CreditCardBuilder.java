package br.net.silva.business.build;

import br.net.silva.business.factory.CreditCardDtoFactory;
import br.net.silva.business.factory.CreditCardOutputFactory;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.interfaces.IGenericBuilder;

import java.util.Objects;

public final class CreditCardBuilder {

    private CreditCardBuilder() {
    }

    public static IGenericBuilder<CreditCardOutput, CreditCardDTO> buildFullCreditCardOutput() {
        return creditCard -> {
            if (Objects.isNull(creditCard)) {
                return null;
            }

            return CreditCardOutputFactory
                    .createOutput()
                    .withNumber(creditCard.number())
                    .withCvv(creditCard.cvv())
                    .withCreditCardBrand(creditCard.flag())
                    .withBalance(creditCard.balance())
                    .withExpirationDate(creditCard.expirationDate())
                    .andWithFlagActive(creditCard.active())
                    .build();
        };
    }

    public static IGenericBuilder<CreditCardDTO, CreditCardOutput> buildFullCreditCardDto() {
        return creditCard -> {
            if (Objects.isNull(creditCard)) {
                return null;
            }

            return CreditCardDtoFactory
                    .createDto()
                    .withNumber(creditCard.number())
                    .withCvv(creditCard.cvv())
                    .withCreditCardBrand(creditCard.flag())
                    .withBalance(creditCard.balance())
                    .withExpirationDate(creditCard.expirationDate())
                    .andWithFlagActive(creditCard.active())
                    .build();
        };
    }

    public static IGenericBuilder<CreditCard, CreditCardOutput> buildAggregate() {
        return output -> {
            if (Objects.isNull(output)) {
                return null;
            }

            return new CreditCard(
                    output.number(),
                    output.cvv(),
                    output.flag(),
                    output.balance(),
                    output.expirationDate(),
                    output.active()
            );
        };
    }
}
