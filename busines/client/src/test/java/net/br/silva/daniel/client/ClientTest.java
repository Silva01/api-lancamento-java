package net.br.silva.daniel.client;

import junit.framework.TestCase;

public class ClientTest extends TestCase {

    public void testShouldCreateANewClientActive() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999");
        assertEquals("Client{id=1, cpf='12345678901', name='Daniel', telephone='999999999', isActive=true}", client.toString());
    }

    public void testShouldDesactivateClient() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999");
        client.desactivateClient();
        assertEquals("Client{id=1, cpf='12345678901', name='Daniel', telephone='999999999', isActive=false}", client.toString());
    }

    public void testShouldEditName() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999");
        client.editName("Daniel Silva");
        assertEquals("Client{id=1, cpf='12345678901', name='Daniel Silva', telephone='999999999', isActive=true}", client.toString());
    }

    public void testShouldEditTelephone() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999");
        client.editTelephone("888888888");
        assertEquals("Client{id=1, cpf='12345678901', name='Daniel', telephone='888888888', isActive=true}", client.toString());
    }

    public void testShouldNotEditNameWhenClientIsDesactivated() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999");
        client.desactivateClient();
        try {
            client.editName("Daniel Silva");
            fail("Should not edit name when client is desactivated");
        } catch (IllegalArgumentException e) {
            assertEquals("Client edit not allowed", e.getMessage());
        }
    }

    public void testShouldNotEditTelephoneWhenClientIsDesactivated() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999");
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
            new Client(1L, "12345678901", null, "999999999");
            fail("Should not create client when name is null");
        } catch (NullPointerException e) {
            assertEquals("Name is not null", e.getMessage());
        }

        try {
            new Client(1L, "12345678901", "", "999999999");
            fail("Should not create client when name is empty");
        } catch (IllegalArgumentException e) {
            assertEquals("Name is not empty", e.getMessage());
        }
    }

    public void testShouldNotCreateClientWhenTelephoneIsNullAndEmpty() {
        try {
            new Client(1L, "12345678901", "Daniel", null);
            fail("Should not create client when telephone is null");
        } catch (NullPointerException e) {
            assertEquals("Telephone is not null", e.getMessage());
        }

        try {
            new Client(1L, "12345678901", "Daniel", "");
            fail("Should not create client when telephone is empty");
        } catch (IllegalArgumentException e) {
            assertEquals("Telephone is not empty", e.getMessage());
        }
    }

}