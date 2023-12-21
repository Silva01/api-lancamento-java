package br.net.silva.daniel.dto;

import br.net.silva.daniel.interfaces.ICreateAccountPord;

public record CreateNewAccountByCpfDTO(
        String cpf,
        Integer agency,
        String password
) implements ICreateAccountPord {

    public CreateNewAccountByCpfDTO(String cpf, Integer agency) {
        this(cpf, agency, "123456");
    }

    @Override
    public Integer bankAgencyNumber() {
        return agency;
    }
}
