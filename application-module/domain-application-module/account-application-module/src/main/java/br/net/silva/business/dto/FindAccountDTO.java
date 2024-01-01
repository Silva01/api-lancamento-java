package br.net.silva.business.dto;

import br.net.silva.daniel.interfaces.IGenericPort;
import br.net.silva.daniel.utils.ValidateUtils;

public record FindAccountDTO(
        String cpf,
        Integer agency,
        Integer accountNumber,
        String password
) implements IGenericPort {
    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, FindAccountDTO.class, "Type of class is incompatible with FindAccountDTO");
    }

    @Override
    public Object get() {
        return this;
    }
}
