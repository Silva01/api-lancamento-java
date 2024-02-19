package br.net.silva.business.value_object.input;

import br.net.silva.daniel.interfaces.IBasicAccountParam;
import br.net.silva.daniel.interfaces.Input;

public record AccountInput(
        Integer accountNumber,
        Integer agency,
        String cpf
) implements Input, IBasicAccountParam {
}
