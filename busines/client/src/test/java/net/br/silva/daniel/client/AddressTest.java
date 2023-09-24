package net.br.silva.daniel.client;

import junit.framework.TestCase;

public class AddressTest extends TestCase {

    public void testCreateAddress() {
        Address address = new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
    }

    public void testEditStreet() {
        Address address = new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
        address.editStreet("Rua 2");
        assertTrue(address.toString().contains("street='Rua 2', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
    }

    public void testEditNumber() {
        Address address = new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
        address.editNumber("456");
        assertTrue(address.toString().contains("street='Rua 1', number='456', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
    }

    public void testEditComplement() {
        Address address = new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
        address.editComplement("Apto 2");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 2', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
    }

    public void testEditNeighborhood() {
        Address address = new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
        address.editNeighborhood("Bairro 2");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 2', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
    }

    public void testEditCity() {
        Address address = new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
        address.editCity("Cidade 2");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 2', state='Estado 1', zipCode='12345678'"));
    }

    public void testEditState() {
        Address address = new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
        address.editState("Estado 2");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 2', zipCode='12345678'"));
    }

    public void testEditZipCode() {
        Address address = new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'"));
        address.editZipCode("87654321");
        assertTrue(address.toString().contains("street='Rua 1', number='123', complement='Apto 1', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='87654321'"));
    }

    public void testValidateEditStreetEmptyAndNull() {
        try {
            new Address("", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("Street is required", eae.getMessage());
        }

        try {
            new Address(null, "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("Street is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editStreet("");
        } catch (IllegalArgumentException eae) {
            assertEquals("Street is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editStreet(null);
        } catch (IllegalArgumentException eae) {
            assertEquals("Street is required", eae.getMessage());
        }
    }

    public void testValidateEditNumberEmptyAndNull() {
        try {
            new Address("Rua 1", "", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("Number is required", eae.getMessage());
        }

        try {
            new Address("Rua 1", null, "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("Number is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editNumber("");
        } catch (IllegalArgumentException eae) {
            assertEquals("Number is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editNumber(null);
        } catch (IllegalArgumentException eae) {
            assertEquals("Number is required", eae.getMessage());
        }
    }

    public void testValidateEditNeighborhoodEmptyAndNull() {
        try {
            new Address("Rua 1", "123", "Apto 1", "", "Cidade 1", "Estado 1", "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("Neighborhood is required", eae.getMessage());
        }

        try {
            new Address("Rua 1", "123", "Apto 1", null, "Cidade 1", "Estado 1", "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("Neighborhood is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editNeighborhood("");
        } catch (IllegalArgumentException eae) {
            assertEquals("Neighborhood is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editNeighborhood(null);
        } catch (IllegalArgumentException eae) {
            assertEquals("Neighborhood is required", eae.getMessage());
        }
    }

    public void testValidateEditCityEmptyAndNull() {
        try {
            new Address("Rua 1", "123", "Apto 1", "Bairro 1", "", "Estado 1", "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("City is required", eae.getMessage());
        }

        try {
            new Address("Rua 1", "123", "Apto 1", "Bairro 1", null, "Estado 1", "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("City is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editCity("");
        } catch (IllegalArgumentException eae) {
            assertEquals("City is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editCity(null);
        } catch (IllegalArgumentException eae) {
            assertEquals("City is required", eae.getMessage());
        }
    }

    public void testValidateEditStateEmptyAndNull() {
        try {
            new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "", "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("State is required", eae.getMessage());
        }

        try {
            new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", null, "12345678");
        } catch (IllegalArgumentException eae) {
            assertEquals("State is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editState("");
        } catch (IllegalArgumentException eae) {
            assertEquals("State is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editState(null);
        } catch (IllegalArgumentException eae) {
            assertEquals("State is required", eae.getMessage());
        }
    }

    public void testValidateEditZipCodeEmptyAndNull() {
        try {
            new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "");
        } catch (IllegalArgumentException eae) {
            assertEquals("ZipCode is required", eae.getMessage());
        }

        try {
            new Address("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", null);
        } catch (IllegalArgumentException eae) {
            assertEquals("ZipCode is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editZipCode("");
        } catch (IllegalArgumentException eae) {
            assertEquals("ZipCode is required", eae.getMessage());
        }

        try {
            var address = new Address("teste", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
            address.editZipCode(null);
        } catch (IllegalArgumentException eae) {
            assertEquals("ZipCode is required", eae.getMessage());
        }
    }
}