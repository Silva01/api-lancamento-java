package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.interfaces.AddressFactorySpec;

public final class AddressDtoFactory implements AddressFactorySpec.BuildSpec<AddressDTO> {

    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String state;
    private String city;
    private String zipCode;

    public static AddressFactorySpec.StreetSpec<AddressDTO> createDto() {
        return new AddressDtoFactory();
    }

    @Override
    public AddressFactorySpec.NumberSpec<AddressDTO> withStreet(String street) {
        this.street = street;
        return this;
    }

    @Override
    public AddressFactorySpec.NeighborhoodSpec<AddressDTO> withNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public AddressFactorySpec.StateSpec<AddressDTO> withNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    @Override
    public AddressFactorySpec.CitySpec<AddressDTO> withState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public AddressFactorySpec.ZipCodeSpec<AddressDTO> withCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public AddressFactorySpec.ComplementSpec<AddressDTO> withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    @Override
    public AddressFactorySpec.BuildSpec<AddressDTO> andWithComplement(String complement) {
        this.complement = complement;
        return this;
    }

    @Override
    public AddressDTO build() {
        return new AddressDTO(street, number, complement, neighborhood, state, city, zipCode);
    }
}
