package br.net.silva.daniel.entity;

import br.net.silva.daniel.value_object.Address;
import junit.framework.TestCase;

public class ClientTest extends TestCase {

    public void testShouldCreateClientWithSuccess() {
        var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
        var client = new Client("99988877766", "Daniel", "665555444222", address);

        var clientDTO = client.create();

        assertFalse(clientDTO.id().isEmpty());
        assertEquals("99988877766", clientDTO.cpf());
        assertEquals("Daniel", clientDTO.name());
        assertEquals("665555444222", clientDTO.telephone());
        assertEquals("Rua 1", clientDTO.address().street());
        assertEquals("test", clientDTO.address().neighborhood());
        assertEquals("test", clientDTO.address().complement());
        assertEquals("1234", clientDTO.address().number());
        assertEquals("Cidade 1", clientDTO.address().city());
        assertEquals("Estado 1", clientDTO.address().state());
        assertEquals("11111111", clientDTO.address().zipCode());
    }

    public void testShouldFailCreateClientUnnamed() {
        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("99988877766", "", "665555444222", address);
        } catch (Exception e) {
            assertEquals("Name is required", e.getMessage());
        }

        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("99988877766", null, "665555444222", address);
        } catch (Exception e) {
            assertEquals("Name is required", e.getMessage());
        }
    }

    public void testShouldFailCreateClientWithoutCPF() {
        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("99988877766", "Daniel", "", address);
        } catch (Exception e) {
            assertEquals("Telephone is required", e.getMessage());
        }

        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("99988877766", "Daniel", null, address);
        } catch (Exception e) {
            assertEquals("Telephone is required", e.getMessage());
        }
    }

    public void testShouldFailCreateClientWithoutTelephone() {
        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("99988877766", "Daniel", "", address);
        } catch (Exception e) {
            assertEquals("Telephone is required", e.getMessage());
        }

        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("99988877766", "Daniel", null, address);
        } catch (Exception e) {
            assertEquals("Telephone is required", e.getMessage());
        }
    }

    public void testShouldFailCreateClientWithoutAddress() {
        try {
            new Client("", "Daniel", "665555444222", null);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }
    }

    public void testShouldErrorCreateClientWithAddressInvalid() {
        // Validate Street blank and null
        try {
            var address = new Address("", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        try {
            var address = new Address(null, "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        // Validate Number blank and null
        try {
            var address = new Address("Rua 1", "", "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        try {
            var address = new Address("Rua 1", null, "test", "test", "Estado 1", "Cidade 1", "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        // Validate Neighborhood blank and null
        try {
            var address = new Address("Rua 1", "1234", "teste", "", "Estado 1", "Cidade 1", "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        try {
            var address = new Address("Rua 1", "1234", "test", null, "Estado 1", "Cidade 1", "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        // Validate State blank and null
        try {
            var address = new Address("Rua 1", "1234", "test", "test", "", "Cidade 1", "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        try {
            var address = new Address("Rua 1", "1234", "test", "test", null, "Cidade 1", "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        // Validate City blank and null
        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "", "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", null, "11111111");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        // Validate ZipCode blank and null
        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "");
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", null);
            new Client("", "Daniel", "665555444222", address);
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

    }

    public void testShouldDesactivateClientWithSuccess() {
        var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
        var client = new Client("99988877766", "Daniel", "665555444222", address);
        client.deactivate();

        var dto = client.create();

        assertFalse(dto.active());
    }

    public void testShouldActivateClientWithSuccess() {
        var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
        var client = new Client("99988877766", "Daniel", "665555444222", address);
        client.deactivate();
        var dto = client.create();
        assertFalse(dto.active());

        client.activate();
        dto = client.create();
        assertTrue(dto.active());
    }

    public void testShouldEditNameWithSuccess() {
        var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
        var client = new Client("99988877766", "Daniel", "665555444222", address);
        var dto = client.create();
        assertEquals("Daniel", dto.name());

        client.editName("Daniel Silva");
        dto = client.create();
        assertEquals("Daniel Silva", dto.name());
    }

    public void testShouldEditTelephoneWithSuccess() {
        var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
        var client = new Client("99988877766", "Daniel", "665555444222", address);
        var dto = client.create();
        assertEquals("665555444222", dto.telephone());

        client.editTelephone("665555444333");
        dto = client.create();
        assertEquals("665555444333", dto.telephone());
    }

    public void testShouldErrorEditName() {
        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            var client = new Client("99988877766", "Daniel", "665555444222", address);
            var dto = client.create();
            assertEquals("Daniel", dto.name());

            client.deactivate();
            client.editName("Daniel Silva");
        } catch (Exception e) {
            assertEquals("Client is not active", e.getMessage());
        }
    }

    public void testShouldErrorEditTelephone() {
        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            var client = new Client("99988877766", "Daniel", "665555444222", address);
            var dto = client.create();
            assertEquals("665555444222", dto.telephone());

            client.deactivate();
            client.editTelephone("665555444333");
        } catch (Exception e) {
            assertEquals("Client is not active", e.getMessage());
        }
    }

    public void testShouldEditAddressWithSuccess() {
        var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
        var client = new Client("99988877766", "Daniel", "665555444222", address);
        var dto = client.create();
        assertEquals("Rua 1", dto.address().street());
        assertEquals("1234", dto.address().number());
        assertEquals("test", dto.address().neighborhood());
        assertEquals("test", dto.address().complement());
        assertEquals("Estado 1", dto.address().state());
        assertEquals("Cidade 1", dto.address().city());
        assertEquals("11111111", dto.address().zipCode());

        var newAddress = new Address("Rua 2", "4321", "test2", "test2", "Estado 2", "Cidade 2", "22222222");
        client.registerAddress(newAddress);
        dto = client.create();
        assertEquals("Rua 2", dto.address().street());
        assertEquals("4321", dto.address().number());
        assertEquals("test2", dto.address().neighborhood());
        assertEquals("test2", dto.address().complement());
        assertEquals("Estado 2", dto.address().state());
        assertEquals("Cidade 2", dto.address().city());
        assertEquals("22222222", dto.address().zipCode());
    }

    public void testShouldErrorEditAddress() {
        try {
            var address = new Address("Rua 1", "1234", "test", "test", "Estado 1", "Cidade 1", "11111111");
            var client = new Client("99988877766", "Daniel", "665555444222", address);
            var dto = client.create();
            assertEquals("Rua 1", dto.address().street());
            assertEquals("1234", dto.address().number());
            assertEquals("test", dto.address().neighborhood());
            assertEquals("test", dto.address().complement());
            assertEquals("Estado 1", dto.address().state());
            assertEquals("Cidade 1", dto.address().city());
            assertEquals("11111111", dto.address().zipCode());

            client.deactivate();
            var newAddress = new Address("Rua 2", "4321", "test2", "test2", "Estado 2", "Cidade 2", "22222222");
            client.registerAddress(newAddress);
        } catch (Exception e) {
            assertEquals("Client is not active", e.getMessage());
        }
    }

}