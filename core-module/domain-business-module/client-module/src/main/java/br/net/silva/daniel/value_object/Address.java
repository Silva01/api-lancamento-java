package br.net.silva.daniel.value_object;

public record Address (
        String street,
        String number,
        String complement,
        String neighborhood,
        String state,
        String city,
        String zipCode
) {
    public void validate() {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street is required");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Number is required");
        }
        if (neighborhood == null || neighborhood.isBlank()) {
            throw new IllegalArgumentException("Neighborhood is required");
        }
        if (state == null || state.isBlank()) {
            throw new IllegalArgumentException("State is required");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City is required");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("ZipCode is required");
        }
    }
}
