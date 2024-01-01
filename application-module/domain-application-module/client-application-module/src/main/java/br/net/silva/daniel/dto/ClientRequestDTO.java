package br.net.silva.daniel.dto;

import br.net.silva.daniel.interfaces.IGenericPort;
import br.net.silva.daniel.utils.ValidateUtils;

public record ClientRequestDTO(
        String id,
        String cpf,
        String name,
        String telephone,
        boolean active,
        Integer agencyNumber,
        AddressRequestDTO addressRequestDTO
) implements IGenericPort {
    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, ClientRequestDTO.class, "Type of class is not ClientRequestDTO");
    }

    @Override
    public Object get() {
        return this;
    }
}
