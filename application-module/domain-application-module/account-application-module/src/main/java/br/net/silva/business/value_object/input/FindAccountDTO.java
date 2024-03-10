package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

import java.math.BigDecimal;

public record FindAccountDTO(
        String cpf,
        Integer agency,
        Integer account,
        String password
) implements IGenericPort, IAccountParam {

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, FindAccountDTO.class, "Type of class is incompatible with FindAccountDTO");
    }

    @Override
    public Object get() {
        return this;
    }

    @Override
    public Integer accountNumber() {
        return account;
    }

    @Override
    public BigDecimal balance() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public boolean active() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }
}
