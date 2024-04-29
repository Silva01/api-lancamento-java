package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public record CreateNewAccountByCpfDTO(
        String cpf,
        Integer agency,
        String password
) implements IGenericPort, ICpfParam {

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, CreateNewAccountByCpfDTO.class, "Type of class is not CreateNewAccountByCpfDTO");
    }

    @Override
    public Object get() {
        return this;
    }
}
