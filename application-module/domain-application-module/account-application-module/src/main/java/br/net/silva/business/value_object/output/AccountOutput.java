package br.net.silva.business.value_object.output;

import br.net.silva.daniel.shared.application.interfaces.Output;

import java.math.BigDecimal;
import java.util.List;

public record AccountOutput(
        Integer number,
        Integer agency,
        BigDecimal balance,
        String password,
        boolean active,
        String cpf,
        CreditCardOutput creditCard, List<TransactionOutput> transactions
) implements Output {
}
