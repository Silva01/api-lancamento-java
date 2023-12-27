package br.net.silva.daniel.dto;

import br.net.silva.daniel.interfaces.IGenericPort;

public record ChangePasswordDTO(
        String cpf,
        Integer agency,
        Integer account,
        String password,
        String oldPassword
) implements IGenericPort {

    @Override
    public void accept(Class<?> clazz) {
        if (!clazz.isInstance(ChangePasswordDTO.class))
            throw new IllegalArgumentException("Class must be assignable from IAccountPort");
    }

    @Override
    public ChangePasswordDTO get() {
        return this;
    }
}
