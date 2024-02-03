package br.net.silva.business.value_object.input;

import br.net.silva.daniel.interfaces.Input;

import java.util.List;

public record BatchTransactionInput(
        AccountInput sourceAccount,
        AccountInput destinyAccount,
        List<TransactionInput> batchTransaction
) implements Input {
}
