package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.math.BigDecimal;

public record ChangeAgencyInput(
        String cpf,
        Integer accountNumber,
        Integer oldAgencyNumber,
        Integer newAgencyNumber
) implements IAccountParam {
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
        return oldAgencyNumber;
    }
}
