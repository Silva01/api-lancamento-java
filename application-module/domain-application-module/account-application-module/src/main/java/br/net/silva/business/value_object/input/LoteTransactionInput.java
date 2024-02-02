package br.net.silva.business.value_object.input;

import br.net.silva.daniel.interfaces.Input;

import java.util.List;

public record LoteTransactionInput(
        List<RegisterTransactionInput> batchTransaction
) implements Input {
}
