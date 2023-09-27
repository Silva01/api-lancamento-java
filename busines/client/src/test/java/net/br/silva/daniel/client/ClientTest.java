package net.br.silva.daniel.client;

import junit.framework.TestCase;
import net.br.silva.daniel.client.value_object.Address;
import net.br.silva.daniel.client.domain.Client;
import net.br.silva.daniel.client.value_object.ClientDTO;

import java.util.Optional;

public class ClientTest extends TestCase {

    public void testShouldCreateANewClientActive() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        ClientDTO clientDTO = client.createDTO();

        assertEquals(Optional.of(1L).get(), clientDTO.id());
        assertEquals("12345678901", clientDTO.cpf());
        assertEquals("Daniel", clientDTO.name());
        assertEquals("999999999", clientDTO.telephone());
        assertEquals("Rua 1", clientDTO.address().street());
        assertEquals("123", clientDTO.address().number());
        assertEquals("Casa", clientDTO.address().complement());
        assertEquals("Bairro 1", clientDTO.address().neighborhood());
        assertEquals("Cidade 1", clientDTO.address().city());
        assertEquals("Estado 1", clientDTO.address().state());
        assertEquals("12345678", clientDTO.address().zipCode());
        assertTrue(clientDTO.isActive());
    }

    public void testShouldDesactivateClient() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        client.desactivateClient();
        ClientDTO clientDTO = client.createDTO();

        assertEquals(Optional.of(1L).get(), clientDTO.id());
        assertEquals("12345678901", clientDTO.cpf());
        assertEquals("Daniel", clientDTO.name());
        assertEquals("999999999", clientDTO.telephone());
        assertEquals("Rua 1", clientDTO.address().street());
        assertEquals("123", clientDTO.address().number());
        assertEquals("Casa", clientDTO.address().complement());
        assertEquals("Bairro 1", clientDTO.address().neighborhood());
        assertEquals("Cidade 1", clientDTO.address().city());
        assertEquals("Estado 1", clientDTO.address().state());
        assertEquals("12345678", clientDTO.address().zipCode());
        assertFalse(clientDTO.isActive());
    }

    public void testShouldEditName() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        client.editName("Daniel Silva");
        ClientDTO clientDTO = client.createDTO();

        assertEquals(Optional.of(1L).get(), clientDTO.id());
        assertEquals("12345678901", clientDTO.cpf());
        assertEquals("Daniel Silva", clientDTO.name());
        assertEquals("999999999", clientDTO.telephone());
        assertEquals("Rua 1", clientDTO.address().street());
        assertEquals("123", clientDTO.address().number());
        assertEquals("Casa", clientDTO.address().complement());
        assertEquals("Bairro 1", clientDTO.address().neighborhood());
        assertEquals("Cidade 1", clientDTO.address().city());
        assertEquals("Estado 1", clientDTO.address().state());
        assertEquals("12345678", clientDTO.address().zipCode());
        assertTrue(clientDTO.isActive());
    }

    public void testShouldEditTelephone() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        client.editTelephone("888888888");

        ClientDTO clientDTO = client.createDTO();
        assertEquals(Optional.of(1L).get(), clientDTO.id());
        assertEquals("12345678901", clientDTO.cpf());
        assertEquals("Daniel", clientDTO.name());
        assertEquals("888888888", clientDTO.telephone());
        assertEquals("Rua 1", clientDTO.address().street());
        assertEquals("123", clientDTO.address().number());
        assertEquals("Casa", clientDTO.address().complement());
        assertEquals("Bairro 1", clientDTO.address().neighborhood());
        assertEquals("Cidade 1", clientDTO.address().city());
        assertEquals("Estado 1", clientDTO.address().state());
        assertEquals("12345678", clientDTO.address().zipCode());
        assertTrue(clientDTO.isActive());
    }

    public void testShouldNotEditNameWhenClientIsDesactivated() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        client.desactivateClient();
        try {
            client.editName("Daniel Silva");
            fail("Should not edit name when client is desactivated");
        } catch (IllegalArgumentException e) {
            assertEquals("Client edit not allowed", e.getMessage());
        }
    }

    public void testShouldNotEditTelephoneWhenClientIsDesactivated() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        client.desactivateClient();
        try {
            client.editTelephone("888888888");
            fail("Should not edit telephone when client is desactivated");
        } catch (IllegalArgumentException e) {
            assertEquals("Client edit not allowed", e.getMessage());
        }
    }

    public void testShouldNotCreateClientWhenNameIsNullAndEmpty() {
        try {
            new Client(1L, "12345678901", null, "999999999", createAddress());
            fail("Should not create client when name is null");
        } catch (NullPointerException e) {
            assertEquals("Name is not null", e.getMessage());
        }

        try {
            new Client(1L, "12345678901", "", "999999999", createAddress());
            fail("Should not create client when name is empty");
        } catch (IllegalArgumentException e) {
            assertEquals("Name is not empty", e.getMessage());
        }
    }

    public void testShouldNotCreateClientWhenTelephoneIsNullAndEmpty() {
        try {
            new Client(1L, "12345678901", "Daniel", null, createAddress());
            fail("Should not create client when telephone is null");
        } catch (NullPointerException e) {
            assertEquals("Telephone is not null", e.getMessage());
        }

        try {
            new Client(1L, "12345678901", "Daniel", "", createAddress());
            fail("Should not create client when telephone is empty");
        } catch (IllegalArgumentException e) {
            assertEquals("Telephone is not empty", e.getMessage());
        }
    }

    public void testShouldRegisterAddressByClient() {
        Address address1 = new Address("Rua 1", "123", "Casa", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", address1);
        ClientDTO clientDTO = client.createDTO();

        assertEquals(Optional.of(1L).get(), clientDTO.id());
        assertEquals("12345678901", clientDTO.cpf());
        assertEquals("Daniel", clientDTO.name());
        assertEquals("999999999", clientDTO.telephone());
        assertEquals("Rua 1", clientDTO.address().street());
        assertEquals("123", clientDTO.address().number());
        assertEquals("Casa", clientDTO.address().complement());
        assertEquals("Bairro 1", clientDTO.address().neighborhood());
        assertEquals("Cidade 1", clientDTO.address().city());
        assertEquals("Estado 1", clientDTO.address().state());
        assertEquals("12345678", clientDTO.address().zipCode());
        assertTrue(clientDTO.isActive());


        Address address2 = new Address("Rua 2", "456", "Casa", "Bairro 2", "Cidade 2", "Estado 2", "12345678");
        client.registerAddress(address2);
        ClientDTO clientDTO2 = client.createDTO();

        assertEquals(Optional.of(1L).get(), clientDTO2.id());
        assertEquals("12345678901", clientDTO2.cpf());
        assertEquals("Daniel", clientDTO2.name());
        assertEquals("999999999", clientDTO2.telephone());
        assertEquals("Rua 2", clientDTO2.address().street());
        assertEquals("456", clientDTO2.address().number());
        assertEquals("Casa", clientDTO2.address().complement());
        assertEquals("Bairro 2", clientDTO2.address().neighborhood());
        assertEquals("Cidade 2", clientDTO2.address().city());
        assertEquals("Estado 2", clientDTO2.address().state());
        assertEquals("12345678", clientDTO2.address().zipCode());
        assertTrue(clientDTO2.isActive());

    }

    private Address createAddress() {
        return new Address("Rua 1", "123", "Casa", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
    }

}