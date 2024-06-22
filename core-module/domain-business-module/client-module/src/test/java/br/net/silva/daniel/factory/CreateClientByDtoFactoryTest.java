package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.ClientDTO;
import junit.framework.TestCase;

public class CreateClientByDtoFactoryTest extends TestCase {

    public void testShouldCreateClientByDtoFactory() {
        var addressDto = new AddressDTO("Street Test", "123", "Complement Test", "Neighborhood Test", "State Test", "City Test", "ZipCode Test");
        var clientDTO = new ClientDTO("123", "00099988877", "test", "76544443333", true, addressDto);

        var client = new CreateClientByDtoFactory().create(clientDTO);
        assertNotNull(client);

        var clientDtoBuild = client.build();
        assertEquals(clientDTO.id(), clientDtoBuild.id());
        assertEquals(clientDTO.cpf(), clientDtoBuild.cpf());
        assertEquals(clientDTO.name(), clientDtoBuild.name());
        assertEquals(clientDTO.telephone(), clientDtoBuild.telephone());
        assertEquals(clientDTO.active(), clientDtoBuild.active());
        assertEquals(clientDTO.address().street(), clientDtoBuild.address().street());
        assertEquals(clientDTO.address().number(), clientDtoBuild.address().number());
        assertEquals(clientDTO.address().complement(), clientDtoBuild.address().complement());
        assertEquals(clientDTO.address().neighborhood(), clientDtoBuild.address().neighborhood());
        assertEquals(clientDTO.address().state(), clientDtoBuild.address().state());
        assertEquals(clientDTO.address().city(), clientDtoBuild.address().city());
        assertEquals(clientDTO.address().zipCode(), clientDtoBuild.address().zipCode());
    }

}