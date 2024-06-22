package br.net.silva.daniel.value_object.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivateClientTest {

    @Test
    public void testActivateClient() {
        // Create an instance of ActivateClient
        ActivateClient activateClient = new ActivateClient("12345678901");

        // Test that the methods throw the expected exception
        assertThrows(RuntimeException.class, activateClient::id);
        assertThrows(RuntimeException.class, activateClient::name);
        assertThrows(RuntimeException.class, activateClient::telephone);
        assertThrows(RuntimeException.class, activateClient::active);
        assertThrows(RuntimeException.class, activateClient::agency);
        assertThrows(RuntimeException.class, activateClient::address);

        // Test the value returned by the cpf() method
        assertEquals("12345678901", activateClient.cpf());
    }

}