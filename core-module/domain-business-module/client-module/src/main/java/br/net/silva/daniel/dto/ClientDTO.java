package br.net.silva.daniel.dto;

public record ClientDTO(
        String id,
        String cpf,
        String name,
        String telephone,
        boolean active,
        AddressDTO address
) {
}
