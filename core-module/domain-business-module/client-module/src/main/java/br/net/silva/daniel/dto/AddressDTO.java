package br.net.silva.daniel.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;

public record AddressDTO(
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city,
        String zipCode
) implements IGenericOutput {
}
