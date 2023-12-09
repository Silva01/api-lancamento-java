package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.value_object.Address;
import junit.framework.TestCase;

public class CreateNewAddressFactoryTest extends TestCase {

    public void testCreateNewAddress() {
        // Arrange
        AddressDTO mockAddressDTO = new AddressDTO("street", "1234", "complement", "neighborhood", "state", "city", "zipCode");
        CreateNewAddressFactory factory = new CreateNewAddressFactory();

        // Act
        Address address = factory.create(mockAddressDTO);

        // Assert
        assertEquals(mockAddressDTO.street(), address.street());
        assertEquals(mockAddressDTO.number(), address.number());
        assertEquals(mockAddressDTO.complement(), address.complement());
        assertEquals(mockAddressDTO.neighborhood(), address.neighborhood());
        assertEquals(mockAddressDTO.city(), address.city());
        assertEquals(mockAddressDTO.state(), address.state());
        assertEquals(mockAddressDTO.zipCode(), address.zipCode());
    }

}