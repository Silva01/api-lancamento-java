package br.net.silva.daniel.dto;

import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;


public record CreditCardDTO(
        String number,
        Integer cvv,
        FlagEnum flag,
        BigDecimal balance,
        LocalDate expirationDate,
        boolean active
) implements IGenericPort, IGenericOutput {

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, CreditCardDTO.class, "Class must be assignable from CreditCardDTO");
    }

    @Override
    public CreditCardDTO get() {
        return this;
    }
}
