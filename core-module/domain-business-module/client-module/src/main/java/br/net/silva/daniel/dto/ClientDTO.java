package br.net.silva.daniel.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;

public record ClientDTO(
        String id,
        String cpf,
        String name,
        String telephone,
        boolean active,
        AddressDTO address
) implements IGenericPort, IGenericOutput {

    @Override
    public void accept(Class<?> clazz) {
        if (!clazz.isInstance(ClientDTO.class))
            throw new IllegalArgumentException("Class must be assignable from IAccountPort");
    }

    @Override
    public ClientDTO get() {
        return this;
    }
}
