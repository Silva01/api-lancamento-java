package br.net.silva.business.value_object.input;

import br.net.silva.daniel.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.math.BigDecimal;

public record DeactivateAccount(
        String cpf
) implements IAccountParam {
    @Override
    public Integer accountNumber() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }

    @Override
    public Integer agency() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }

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
