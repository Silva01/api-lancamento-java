package br.net.silva.business.value_object.input;

import br.net.silva.business.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

import java.math.BigDecimal;

public record CreateNewAccountByCpfDTO(
        String cpf,
        Integer agency,
        String password
) implements IGenericPort, IAccountParam {

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, CreateNewAccountByCpfDTO.class, "Type of class is not CreateNewAccountByCpfDTO");
    }

    @Override
    public Object get() {
        return this;
    }

    @Override
    public Integer accountNumber() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
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
