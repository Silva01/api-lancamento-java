package br.net.silva.daniel.dto;

import br.net.silva.daniel.enuns.FlagEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreditCardDTO(
        String number,
        Integer cvv,
        FlagEnum flag,
        BigDecimal balance,
        LocalDate expirationDate,
        boolean active
) {
}
