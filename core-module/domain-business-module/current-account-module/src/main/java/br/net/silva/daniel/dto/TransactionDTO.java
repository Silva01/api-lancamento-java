package br.net.silva.daniel.dto;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

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
        ValidateUtils.isTypeOf(clazz, TransactionDTO.class, "Class must be assignable from TransactionDTO");
    }

    @Override
    public TransactionDTO get() {
        return this;
    }
}
