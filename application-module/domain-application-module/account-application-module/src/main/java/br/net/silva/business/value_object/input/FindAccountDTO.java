package br.net.silva.business.value_object.input;

import br.net.silva.daniel.interfaces.Input;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public record FindAccountDTO(
        String cpf,
        Integer agency,
        Integer account,
        String password
) implements IGenericPort, Input {

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, FindAccountDTO.class, "Type of class is incompatible with FindAccountDTO");
    }

    @Override
    public Object get() {
        return this;
    }
}
