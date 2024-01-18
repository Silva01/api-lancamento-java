package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public record ChangePasswordDTO(
        String cpf,
        Integer agency,
        Integer account,
        String password,
        String newPassword
) implements IGenericPort {

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, ChangePasswordDTO.class, "Type of class is incompatible with ChangePasswordDTO");
    }

    @Override
    public ChangePasswordDTO get() {
        return this;
    }
}
