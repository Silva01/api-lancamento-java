package br.net.silva.business.value_object.input;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.application.interfaces.Input;

import java.math.BigDecimal;

public record TransactionInput(
        Long id,
        String description,
        BigDecimal price,
        Integer quantity,
        TransactionTypeEnum type,
        Long idempotencyId,
        String creditCardNumber,
        Integer creditCardCvv
) implements Input {}
