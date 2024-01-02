package br.net.silva.daniel.dto;

import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreditCardDTO(
        String number,
        Integer cvv,
        FlagEnum flag,
        BigDecimal balance,
        LocalDate expirationDate,
        boolean active
) implements IGenericPort {
    @Override
    public void accept(Class<?> clazz) {
        if (!clazz.isInstance(CreditCardDTO.class))
            throw new IllegalArgumentException("Class must be assignable from IAccountPort");
    }

    @Override
    public CreditCardDTO get() {
        return this;
    }
}
