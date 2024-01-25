package br.net.silva.daniel.value_object.input;

import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.Input;

public record EditAddressInput(
        String cpf,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode
) implements Input, ICpfParam {
}
