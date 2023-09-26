package net.br.silva.daniel.client.domain;


public final class Address {

    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;
    private final String zipCode;

    public Address(String street, String number, String complement, String neighborhood, String city, String state, String zipCode) {
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        validate();
    }
    private void validate() {
        if (this.street == null || this.street.isEmpty()) {
            throw new IllegalArgumentException("Street is required");
        }
        if (this.number == null || this.number.isEmpty()) {
            throw new IllegalArgumentException("Number is required");
        }
        if (this.neighborhood == null || this.neighborhood.isEmpty()) {
            throw new IllegalArgumentException("Neighborhood is required");
        }
        if (this.city == null || this.city.isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        if (this.state == null || this.state.isEmpty()) {
            throw new IllegalArgumentException("State is required");
        }
        if (this.zipCode == null || this.zipCode.isEmpty()) {
            throw new IllegalArgumentException("ZipCode is required");
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", complement='" + complement + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
