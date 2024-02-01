package br.net.silva.business.value_object.input;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.math.BigDecimal;

public record RegisterTransactionInput(
        String id,
        String description,
        BigDecimal price,
        Integer quantity,
        TransactionTypeEnum type,
        String cpf,
        Integer originAccountNumber,
        Integer originAgency,
        Integer destinyAccountNumber,
        Integer destinyAgency,
        Long idempotencyId
) implements IAccountParam {
    @Override
    public Integer accountNumber() {
        return originAccountNumber();
    }

    @Override
    public BigDecimal balance() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public String password() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public boolean active() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public Integer agency() {
        return originAgency();
    }
}
