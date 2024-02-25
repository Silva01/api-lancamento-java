package br.net.silva.business.build;

import br.net.silva.business.factory.CreditCardOutputFactory;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.daniel.dto.CreditCardDTO;

import java.util.Objects;

public final class CreditCardOutputBuilder {

    private CreditCardOutputBuilder() {
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
}
