package br.net.silva.daniel.value_object.output;

import br.net.silva.daniel.shared.application.interfaces.Output;

public record ClientOutput(
        String id,
        String cpf,
        String name,
        String telephone,
        boolean active,
        AddressOutput address
) implements Output {
}
