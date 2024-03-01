package br.net.silva.daniel.build;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.factory.AddressDtoFactory;
import br.net.silva.daniel.factory.AddressOutputFactory;
import br.net.silva.daniel.interfaces.IGenericBuilder;
import br.net.silva.daniel.value_object.Address;
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

    public static IGenericBuilder<AddressDTO, AddressOutput> buildFullAddressDto() {
        return address -> AddressDtoFactory.createDto()
                .withStreet(address.street())
                .withNumber(address.number())
                .withNeighborhood(address.neighborhood())
                .withState(address.state())
                .withCity(address.city())
                .withZipCode(address.zipCode())
                .andWithComplement(address.complement())
                .build();
    }

    public static IGenericBuilder<Address, AddressOutput> buildAggregate() {
        return addressOutput -> new Address(
                addressOutput.street(),
                addressOutput.number(),
                addressOutput.complement(),
                addressOutput.neighborhood(),
                addressOutput.state(),
                addressOutput.city(),
                addressOutput.zipCode()
        );
    }
}
