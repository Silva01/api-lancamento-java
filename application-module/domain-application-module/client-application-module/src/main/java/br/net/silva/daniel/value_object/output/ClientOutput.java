package br.net.silva.daniel.value_object.output;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.interfaces.Output;

public record ClientOutput(
        String id,
        String cpf,
        String name,
        String telephone,
        boolean active,
        AddressDTO address
) implements Output {
}
