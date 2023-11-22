package br.net.silva.daniel.dto;

import br.net.silva.daniel.enuns.TransactionTypeEnum;

import java.math.BigDecimal;

public record TransactionDTO(
        Long id,
        String description,
        BigDecimal price,
        Integer quantity,
        TransactionTypeEnum type,
        Integer originAccountNumber,
        Integer destinationAccountNumber,
        Long idempotencyId,
        String creditCardNumber,
        Integer creditCardCvv
) {
}
