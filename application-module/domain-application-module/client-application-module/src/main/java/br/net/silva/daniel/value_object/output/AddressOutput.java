package br.net.silva.daniel.value_object.output;

import br.net.silva.daniel.interfaces.Output;

public record AddressOutput(
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city,
        String zipCode
) implements Output {
}
