package br.net.silva.daniel.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreditCardDTO(
        String number,
        Integer secretKey,
        String flag,
        BigDecimal balance,
        LocalDate expirationDate,
        boolean active
) {
}
