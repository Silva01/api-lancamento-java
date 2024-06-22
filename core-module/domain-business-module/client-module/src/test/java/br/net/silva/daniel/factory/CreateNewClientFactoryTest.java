package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import junit.framework.TestCase;

public class CreateNewClientFactoryTest extends TestCase {

    public void testCreateNewCLient() {
        // Arrange
        AddressDTO mockAddressDTO = new AddressDTO("street", "number", "complement", "neighborhood", "city", "state", "zipCode");
        ClientDTO mockClientDTO = new ClientDTO("", "cpf", "name", "telephone", true, mockAddressDTO);
        CreateNewAddressFactory addressFactory = new CreateNewAddressFactory();
        CreateNewClientFactory clientFactory = new CreateNewClientFactory(addressFactory);

        // Act
        Client client = clientFactory.create(mockClientDTO);
        var clientResponse = client.build();

        // Assert
        assertEquals(clientResponse.cpf(), mockClientDTO.cpf());
        assertEquals(clientResponse.name(), mockClientDTO.name());
        assertEquals(clientResponse.telephone(), mockClientDTO.telephone());
        assertEquals(clientResponse.address().street(), mockAddressDTO.street());
        assertEquals(clientResponse.address().number(), mockAddressDTO.number());
        assertEquals(clientResponse.address().complement(), mockAddressDTO.complement());
        assertEquals(clientResponse.address().neighborhood(), mockAddressDTO.neighborhood());
        assertEquals(clientResponse.address().city(), mockAddressDTO.city());
        assertEquals(clientResponse.address().state(), mockAddressDTO.state());
        assertEquals(clientResponse.address().zipCode(), mockAddressDTO.zipCode());
    }

}