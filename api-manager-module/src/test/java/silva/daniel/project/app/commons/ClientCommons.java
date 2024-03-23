package silva.daniel.project.app.commons;

import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import silva.daniel.project.app.domain.client.*;

import java.util.UUID;

public interface ClientCommons {

    static Client entityMock() {
        return new Client(
                "12345678901",
                UUID.randomUUID().toString(),
                "Test",
                "12345678901",
                true,
                addressEntityMock());
    }

    static Address addressEntityMock() {
        return new Address(
                1L,
                "rua 1",
                "123",
                "test",
                "Bairro 1",
                "DF",
                "Brasilia",
                "12345678"
        );
    }

    static ClientOutput outputMock() {
        return new ClientOutput(
                UUID.randomUUID().toString(),
                "12345678901",
                "Test",
                "12345678901",
                true,
                addressOutputMock());
    }

    static AddressOutput addressOutputMock() {
        return new AddressOutput(
                "rua 1",
                "123",
                "test",
                "Bairro 1",
                "DF",
                "Brasilia",
                "12345678"
        );
    }

    static ClientRequest requestValidMock() {
        return requestMock(
                "123",
                "12345678901",
                123,
                addressRequestDTOMock());
    }

    static ClientRequest requestInvalidAddressMock() {
        return requestMock(
                "123",
                "12345678901",
                123,
                null);
    }

    static ClientRequest requestCpfEmptyMock() {
        return requestMock(
                "123",
                "",
                123,
                addressRequestDTOMock());
    }

    static ClientRequest requestNullCpfMock() {
        return requestMock(
                "123",
                null,
                123,
                addressRequestDTOMock());
    }

    static ClientRequest requestInvalidAgencyMock() {
        return requestMock(
                "123",
                "12345678901",
                null,
                addressRequestDTOMock());
    }

    static ClientRequest requestInvalidNegativeAgencyMock() {
        return requestMock(
                "123",
                "12345678901",
                -1,
                addressRequestDTOMock());
    }

    static ClientRequest requestInvalidZeroAgencyMock() {
        return requestMock(
                "123",
                "12345678901",
                0,
                addressRequestDTOMock());
    }
    static ClientRequest requestMock(String id, String cpf, Integer agency, AddressRequestDTO address) {
        return new ClientRequest(
                id,
                cpf,
                "Test",
                "12345678901",
                true,
                agency,
                address);
    }

    static AddressRequestDTO addressRequestDTOMock() {
        return new AddressRequestDTO(
                "rua 1",
                "123",
                "test",
                "Bairro 1",
                "DF",
                "Brasilia",
                "12345678"
        );
    }

    static EditClientRequest editClientRequestMock() {
        return new EditClientRequest(
                "12345678901",
                "Test",
                "12345678901");
    }

    static EditStatusClientRequest editStatusClientRequestMock() {
        return new EditStatusClientRequest("12345678901");
    }

    static ActivateClient activateClientMock() {
        return new ActivateClient("123456789");
    }
}
