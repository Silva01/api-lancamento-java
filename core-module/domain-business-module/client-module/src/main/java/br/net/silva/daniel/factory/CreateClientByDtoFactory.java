package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.value_object.Address;

public class CreateClientByDtoFactory implements IFactoryAggregate<Client, ClientDTO> {
    @Override
    public Client create(ClientDTO clientDTO) {
        var address = new Address(
                clientDTO.address().street(),
                clientDTO.address().number(),
                clientDTO.address().complement(),
                clientDTO.address().neighborhood(),
                clientDTO.address().state(),
                clientDTO.address().city(),
                clientDTO.address().zipCode());

        return new Client(clientDTO.id(), clientDTO.cpf(), clientDTO.name(), clientDTO.telephone(), clientDTO.active(), address);
    }
}
