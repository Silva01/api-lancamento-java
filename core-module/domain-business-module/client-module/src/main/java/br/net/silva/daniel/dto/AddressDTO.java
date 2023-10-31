package br.net.silva.daniel.dto;

public record AddressDTO(
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city,
        String zipCode
) {
}
