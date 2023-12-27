package br.net.silva.daniel.dto;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.interfaces.IGenericPort;

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
) implements IGenericPort {
    @Override
    public void accept(Class<?> clazz) {
        if (!clazz.isInstance(TransactionDTO.class))
            throw new IllegalArgumentException("Class must be assignable from IAccountPort");
    }

    @Override
    public TransactionDTO get() {
        return this;
    }
}
