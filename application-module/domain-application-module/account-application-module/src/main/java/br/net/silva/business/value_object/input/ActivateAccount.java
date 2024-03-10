package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.math.BigDecimal;

public record ActivateAccount(
        Integer agency,
        Integer accountNumber,
        String cpf
) implements IAccountParam {
    @Override
    public BigDecimal balance() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }

    @Override
    public String password() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }

    @Override
    public boolean active() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }
}
