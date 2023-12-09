package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.value_object.Address;

public class CreateNewAddressFactory implements IFactoryAggregate<Address, AddressDTO>{
    @Override
    public Address create(AddressDTO addressDTO) {
        return new Address(
                addressDTO.street(),
                addressDTO.number(),
                addressDTO.complement(),
                addressDTO.neighborhood(),
                addressDTO.state(),
                addressDTO.city(),
                addressDTO.zipCode());
    }
}
