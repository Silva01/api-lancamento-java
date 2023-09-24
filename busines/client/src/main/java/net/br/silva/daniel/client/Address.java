package net.br.silva.daniel.client;

import java.util.UUID;

public class Address {

    private final String id;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;

    public Address(String street, String number, String complement, String neighborhood, String city, String state, String zipCode) {
        this(UUID.randomUUID().toString(), street, number, complement, neighborhood, city, state, zipCode);
    }

    public Address(String id, String street, String number, String complement, String neighborhood, String city, String state, String zipCode) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        validate();
    }

    public void editStreet(String street) {
        this.street = street;
        validate();
    }

    public void editNumber(String number) {
        this.number = number;
        validate();
    }

    public void editComplement(String complement) {
        this.complement = complement;
        validate();
    }

    public void editNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
        validate();
    }

    public void editCity(String city) {
        this.city = city;
        validate();
    }

    public void editState(String state) {
        this.state = state;
        validate();
    }

    public void editZipCode(String zipCode) {
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
                "id='" + id + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", complement='" + complement + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
