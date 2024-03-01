package br.net.silva.daniel.interfaces;

public interface AddressFactorySpec {

    interface StreetSpec<R> {
        NumberSpec<R> withStreet(String street);
    }

    interface NumberSpec<R> {
        NeighborhoodSpec<R> withNumber(String number);
    }

    interface NeighborhoodSpec<R> {
        StateSpec<R> withNeighborhood(String neighborhood);
    }

    interface StateSpec<R> {
        CitySpec<R> withState(String state);
    }

    interface CitySpec<R> {
        ZipCodeSpec<R> withCity(String city);
    }

    interface ZipCodeSpec<R> {
        ComplementSpec<R> withZipCode(String zipCode);
    }

    interface ComplementSpec<R> {
        BuildSpec<R> andWithComplement(String complement);
    }

    interface BuildSpec<R> extends StreetSpec<R>, NumberSpec<R>, NeighborhoodSpec<R>, StateSpec<R>, CitySpec<R>, ZipCodeSpec<R>, ComplementSpec<R> {
        R build();
    }
}
