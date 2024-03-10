package br.net.silva.business.value_object.output;

import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.shared.application.interfaces.Output;

import java.math.BigDecimal;
import java.time.LocalDate;


public record CreditCardOutput(
        String number,
        Integer cvv,
        FlagEnum flag,
        BigDecimal balance,
        LocalDate expirationDate,
        boolean active
) implements Output {
}
