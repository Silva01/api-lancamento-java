package br.net.silva.daniel.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

import java.math.BigDecimal;
import java.util.List;

public record AccountDTO(
        Integer number,
        Integer agency,
        BigDecimal balance,
        String password,
        boolean active,
        String cpf,
        List<TransactionDTO> transactions,
        CreditCardDTO creditCard
) implements IGenericPort, IGenericOutput {

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, AccountDTO.class, "Class must be assignable from AccountDTO");
    }

    @Override
    public AccountDTO get() {
        return this;
    }
}
