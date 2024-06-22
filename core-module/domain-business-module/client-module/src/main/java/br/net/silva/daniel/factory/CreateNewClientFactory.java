package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;

public class CreateNewClientFactory implements IFactoryAggregate<Client, ClientDTO> {

    private final CreateNewAddressFactory createNewAddressFactory;

    public CreateNewClientFactory(CreateNewAddressFactory createNewAddressFactory) {
        this.createNewAddressFactory = createNewAddressFactory;
    }

    @Override
    public Client create(ClientDTO clientDTO) {
        return new Client(clientDTO.cpf(), clientDTO.name(), clientDTO.telephone(), createNewAddressFactory.create(clientDTO.address()));
    }
}
