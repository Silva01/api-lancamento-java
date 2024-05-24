package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.ITransactionParam;
import br.net.silva.daniel.shared.application.interfaces.Input;

public record ReversalTransactionInput(
        String cpf,
        Long id,
        Long idempotencyId
) implements Input, ICpfParam, ITransactionParam {}
