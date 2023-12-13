package br.net.silva.daniel;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.AddressRequestDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.mapper.IMapper;

public class ClientDtoMapper implements IMapper<ClientDTO, ClientRequestDTO> {
    @Override
    public ClientDTO map(ClientRequestDTO param) {
        return buildClientDTO(param);
    }

    private AddressDTO buldAddressDTO(AddressRequestDTO address) {
        return new AddressDTO(address.street(), address.number(), address.complement(), address.neighborhood(), address.state(), address.city(), address.zipCode());
    }

    private ClientDTO buildClientDTO(ClientRequestDTO request) {
        return new ClientDTO(request.id(), request.cpf(), request.name(), request.telephone(), request.active(), buldAddressDTO(request.addressRequestDTO()));
    }

}
