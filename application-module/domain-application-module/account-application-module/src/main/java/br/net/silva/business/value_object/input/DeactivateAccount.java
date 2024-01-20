package br.net.silva.business.value_object.input;

import br.net.silva.daniel.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.math.BigDecimal;

public record DeactivateAccount(
        String cpf,
        Integer agency,
        Integer accountNumber
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
