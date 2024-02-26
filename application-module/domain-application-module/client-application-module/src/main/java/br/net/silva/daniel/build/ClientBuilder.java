package br.net.silva.daniel.build;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.factory.ClientOutputFactory;
import br.net.silva.daniel.interfaces.IGenericBuilder;
import br.net.silva.daniel.value_object.output.ClientOutput;

public final class ClientBuilder {

    private ClientBuilder() {
    }

    public static IGenericBuilder<ClientOutput, ClientDTO> buildFullClientOutput() {
        return client -> ClientOutputFactory.createOutput()
                .withCpf(client.cpf())
                .withName(client.name())
                .withActive(client.active())
                .withAddress(AddressBuilder.buildFullAddressOutput().createFrom(client.address()))
                .withTelephone(client.telephone())
                .andWithId(client.id())
                .build();
    }
}
