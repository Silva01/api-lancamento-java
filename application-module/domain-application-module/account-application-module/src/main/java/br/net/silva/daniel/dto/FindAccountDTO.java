package br.net.silva.daniel.dto;

public record FindAccountDTO(
        String cpf,
        Integer agency,
        Integer accountNumber
) {
}
