package br.net.silva.daniel.dto;

import br.net.silva.daniel.value_object.Address;

public record ClientDTO(
        String id,
        String cpf,
        String name,
        String telephone,
        boolean active,
        AddressDTO address
) {
}
