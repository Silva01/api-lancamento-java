package br.net.silva.daniel.dto;

import br.net.silva.daniel.interfaces.IGenericPort;

import java.math.BigDecimal;
import java.util.List;

public record AccountDTO(
        Integer number,
        Integer bankAgencyNumber,
        BigDecimal balance,
        String password,
        boolean active,
        String cpf,
        List<TransactionDTO>transactions,
        CreditCardDTO creditCard
) implements IGenericPort {
    @Override
    public void accept(Class<?> clazz) {
        if (!clazz.isInstance(AccountDTO.class))
            throw new IllegalArgumentException("Class must be assignable from IAccountPort");
    }

    @Override
    public AccountDTO get() {
        return this;
    }
}
