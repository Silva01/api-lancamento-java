package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.math.BigDecimal;

public record GetInformationAccountInput(
        String cpf
) implements IAccountParam {
    @Override
    public Integer accountNumber() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
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
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }
}
