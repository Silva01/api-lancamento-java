package br.net.silva.business.value_object.input;

import br.net.silva.daniel.interfaces.Input;

public record ActivateAccount(
        Integer agency,
        Integer accountNumber,
        String cpf
) implements Input {
}
