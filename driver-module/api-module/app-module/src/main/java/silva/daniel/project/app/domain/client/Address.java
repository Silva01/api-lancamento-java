package silva.daniel.project.app.domain.client;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String state;
    private String city;
    private String zipCode;

    public Address(Long id, String street, String number, String complement, String neighborhood, String state, String city, String zipCode) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
    }

    public Address() {
    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String street() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String number() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String complement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String neighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String state() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String city() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String zipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
