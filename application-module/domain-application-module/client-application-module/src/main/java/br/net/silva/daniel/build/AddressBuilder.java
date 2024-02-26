package br.net.silva.daniel.build;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.factory.AddressOutputFactory;
import br.net.silva.daniel.interfaces.IGenericBuilder;
import br.net.silva.daniel.value_object.output.AddressOutput;

public final class AddressBuilder {

    private AddressBuilder() {
    }

    public static IGenericBuilder<AddressOutput, AddressDTO> buildFullAddressOutput() {
        return address -> AddressOutputFactory.createOutput()
                .withStreet(address.street())
                .withNumber(address.number())
                .withNeighborhood(address.neighborhood())
                .withState(address.state())
                .withCity(address.city())
                .withZipCode(address.zipCode())
                .andWithComplement(address.complement())
                .build();
    }
}
