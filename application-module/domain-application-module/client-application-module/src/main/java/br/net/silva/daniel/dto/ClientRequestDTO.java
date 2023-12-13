package br.net.silva.daniel.dto;

public record ClientRequestDTO(
        String id,
        String cpf,
        String name,
        String telephone,
        boolean active,
        Integer agencyNumber,
        AddressRequestDTO addressRequestDTO
) {
}
