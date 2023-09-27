package net.br.silva.daniel.client.value_object;

public record ClientDTO (
        Long id,
        String cpf,
        String name,
        String telephone,
        Address address,
        boolean isActive) {}
