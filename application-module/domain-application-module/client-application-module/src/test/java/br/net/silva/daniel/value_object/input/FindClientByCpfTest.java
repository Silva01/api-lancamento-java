package br.net.silva.daniel.value_object.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindClientByCpfTest {

    @Test
    public void testFindClientByCpf() {
        // Create an instance of FindClientByCpf
        FindClientByCpf findClientByCpf = new FindClientByCpf("12345678901");

        // Test that the methods throw the expected exception
        assertThrows(RuntimeException.class, findClientByCpf::id);
        assertThrows(RuntimeException.class, findClientByCpf::name);
        assertThrows(RuntimeException.class, findClientByCpf::telephone);
        assertThrows(RuntimeException.class, findClientByCpf::active);
        assertThrows(RuntimeException.class, findClientByCpf::agency);
        assertThrows(RuntimeException.class, findClientByCpf::address);

        // Test the value returned by the cpf() method
        assertEquals("12345678901", findClientByCpf.cpf());
    }

}