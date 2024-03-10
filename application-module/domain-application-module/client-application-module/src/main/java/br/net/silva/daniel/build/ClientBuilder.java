package br.net.silva.daniel.build;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.factory.ClientDtoFactory;
import br.net.silva.daniel.factory.ClientOutputFactory;
import br.net.silva.daniel.shared.application.interfaces.IGenericBuilder;
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

    public static IGenericBuilder<ClientDTO, ClientOutput> buildFullClientDto() {
        return client -> ClientDtoFactory.createDto()
                .withCpf(client.cpf())
                .withName(client.name())
                .withActive(client.active())
                .withAddress(client.address())
                .withTelephone(client.telephone())
                .andWithId(client.id())
                .build();
    }

    public static IGenericBuilder<Client, ClientOutput> buildAggregate() {
        return clientOutput -> new Client(
                clientOutput.id(),
                clientOutput.cpf(),
                clientOutput.name(),
                clientOutput.telephone(),
                clientOutput.active(),
                AddressBuilder.buildAggregate().createFrom(clientOutput.address())
        );
    }
}
