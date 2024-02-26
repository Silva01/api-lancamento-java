package br.net.silva.business.value_object.output;

import br.net.silva.daniel.interfaces.Output;

import java.math.BigDecimal;
import java.util.List;

public record AccountOutput(
        Integer number,
        Integer agency,
        BigDecimal balance,
        String password,
        boolean active,
        String cpf,
        List<TransactionOutput> transactions,
        CreditCardOutput creditCard
) implements Output {
}
