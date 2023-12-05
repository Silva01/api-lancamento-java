package br.net.silva.daniel.dto;

import java.math.BigDecimal;
import java.util.List;

public record AccountDTO(
        Integer number,
        Integer bankAgencyNumber,
        BigDecimal balance,
        String password,
        boolean active,
        String cpf,
        List<TransactionDTO>transactions,
        CreditCardDTO creditCard
) {
}
