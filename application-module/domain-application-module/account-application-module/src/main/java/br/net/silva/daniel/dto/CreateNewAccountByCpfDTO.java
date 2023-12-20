package br.net.silva.daniel.dto;

import br.net.silva.daniel.interfaces.ICreateAccountPord;

public record CreateNewAccountByCpfDTO(
        String cpf,
        Integer agency
) implements ICreateAccountPord {
    @Override
    public Integer bankAgencyNumber() {
        return agency;
    }

    @Override
    public String password() {
        return "123456";
    }
}
