package br.net.silva.daniel.dto;

import br.net.silva.daniel.interfaces.IAccountPord;
import br.net.silva.daniel.utils.GenericErrorUtils;

import java.math.BigDecimal;

public record CreateNewAccountForCpfDTO(
        String cpf,
        Integer agency
) implements IAccountPord {

    private static final String MSG_NOT_PERMITTED = "Not Permitted";
    @Override
    public Integer number() {
        throw GenericErrorUtils.executeException(MSG_NOT_PERMITTED);
    }

    @Override
    public Integer bankAgencyNumber() {
        return agency;
    }

    @Override
    public BigDecimal balance() {
        throw GenericErrorUtils.executeException(MSG_NOT_PERMITTED);
    }

    @Override
    public String password() {
        throw GenericErrorUtils.executeException(MSG_NOT_PERMITTED);
    }
}
