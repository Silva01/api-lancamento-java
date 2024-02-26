package br.net.silva.daniel.factory;

import br.net.silva.daniel.interfaces.AddressFactorySpec;
import br.net.silva.daniel.value_object.output.AddressOutput;

public class AddessOutputFactory implements AddressFactorySpec.BuildSpec<AddressOutput> {

    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String state;
    private String city;
    private String zipCode;

    @Override
    public AddressFactorySpec.NumberSpec<AddressOutput> withStreet(String street) {
        this.street = street;
        return this;
    }

    @Override
    public AddressFactorySpec.NeighborhoodSpec<AddressOutput> withNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public AddressFactorySpec.StateSpec<AddressOutput> withNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        return this;
    }

    @Override
    public AddressFactorySpec.CitySpec<AddressOutput> withState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public AddressFactorySpec.ZipCodeSpec<AddressOutput> withCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public AddressFactorySpec.ComplementSpec<AddressOutput> withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    @Override
    public AddressFactorySpec.BuildSpec<AddressOutput> andWithComplement(String complement) {
        this.complement = complement;
        return this;
    }

    @Override
    public AddressOutput build() {
        return new AddressOutput(street, number, complement, neighborhood, state, city, zipCode);
    }
}
