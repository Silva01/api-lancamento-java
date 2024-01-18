package br.net.silva.daniel.value_object.input;

public record AddressRequestDTO(
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city,
        String zipCode
) {
}
