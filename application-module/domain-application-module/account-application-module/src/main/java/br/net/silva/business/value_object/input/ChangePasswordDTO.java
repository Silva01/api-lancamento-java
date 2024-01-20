package br.net.silva.business.value_object.input;

import br.net.silva.daniel.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

import java.math.BigDecimal;

public record ChangePasswordDTO(
        String cpf,
        Integer agency,
        Integer account,
        String password,
        String newPassword
) implements IGenericPort, IAccountParam {

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, ChangePasswordDTO.class, "Type of class is incompatible with ChangePasswordDTO");
    }

    @Override
    public ChangePasswordDTO get() {
        return this;
    }

    @Override
    public Integer accountNumber() {
        return account;
    }

    @Override
    public BigDecimal balance() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }

    @Override
    public boolean active() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }
}
