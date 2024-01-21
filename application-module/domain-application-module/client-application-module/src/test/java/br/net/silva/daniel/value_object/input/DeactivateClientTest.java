package br.net.silva.daniel.value_object.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeactivateClientTest {

    @Test
    public void testDeactivateClient() {
        // Create an instance of DeactivateClient
        DeactivateClient deactivateClient = new DeactivateClient("12345678901");

        // Test that the methods throw the expected exception
        assertThrows(RuntimeException.class, deactivateClient::id);
        assertThrows(RuntimeException.class, deactivateClient::name);
        assertThrows(RuntimeException.class, deactivateClient::telephone);
        assertThrows(RuntimeException.class, deactivateClient::active);
        assertThrows(RuntimeException.class, deactivateClient::agency);
        assertThrows(RuntimeException.class, deactivateClient::address);

        // Test the value returned by the cpf() method
        assertEquals("12345678901", deactivateClient.cpf());
    }

}