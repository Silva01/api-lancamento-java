package br.net.silva.business.dto;

import br.net.silva.daniel.interfaces.ICreateAccountPord;

public record CreateNewAccontDTO(
        String cpf,
        Integer agency,
        String password,
        boolean isWithCreditCard
) implements ICreateAccountPord {
    @Override
    public Integer bankAgencyNumber() {
        return agency;
    }
}
