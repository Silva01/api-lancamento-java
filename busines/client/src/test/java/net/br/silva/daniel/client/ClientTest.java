package net.br.silva.daniel.client;

import junit.framework.TestCase;

public class ClientTest extends TestCase {

    public void testShouldCreateANewClientActive() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        assertEquals("Client{id=1, cpf='12345678901', name='Daniel', telephone='999999999', address=Address{id='abc', street='Rua 1', number='123', complement='Casa', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'}, isActive=true}", client.toString());
    }

    public void testShouldDesactivateClient() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        client.desactivateClient();
        assertEquals("Client{id=1, cpf='12345678901', name='Daniel', telephone='999999999', address=Address{id='abc', street='Rua 1', number='123', complement='Casa', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'}, isActive=false}", client.toString());
    }

    public void testShouldEditName() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        client.editName("Daniel Silva");
        assertEquals("Client{id=1, cpf='12345678901', name='Daniel Silva', telephone='999999999', address=Address{id='abc', street='Rua 1', number='123', complement='Casa', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'}, isActive=true}", client.toString());
    }

    public void testShouldEditTelephone() {
        Client client = new Client(1L, "12345678901", "Daniel", "999999999", createAddress());
        client.editTelephone("888888888");
        assertEquals("Client{id=1, cpf='12345678901', name='Daniel', telephone='888888888', address=Address{id='abc', street='Rua 1', number='123', complement='Casa', neighborhood='Bairro 1', city='Cidade 1', state='Estado 1', zipCode='12345678'}, isActive=true}", client.toString());
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

    private Address createAddress() {
        return new Address("abc","Rua 1", "123", "Casa", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
    }

}