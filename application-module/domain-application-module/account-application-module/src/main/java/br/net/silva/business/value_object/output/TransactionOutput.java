package br.net.silva.business.value_object.output;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.application.interfaces.Output;

import java.math.BigDecimal;

public record TransactionOutput(
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
) implements Output {
}
