package br.net.silva.business.value_object.input;

import br.net.silva.daniel.interfaces.Input;

public record DeactivateAccount(
        String cpf
) implements Input {
}
