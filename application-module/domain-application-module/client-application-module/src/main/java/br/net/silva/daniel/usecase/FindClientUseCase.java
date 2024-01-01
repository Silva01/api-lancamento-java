package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.interfaces.IGenericPort;
import br.net.silva.daniel.interfaces.IMapper;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericMapper;
import br.net.silva.daniel.repository.Repository;

import java.util.Optional;

public class FindClientUseCase implements UseCase<ClientDTO> {

    private final Repository<Optional<Client>> findClientRepository;
    private final IMapper<ClientDTO, IGenericPort> genericMapper;

    public FindClientUseCase(Repository<Optional<Client>> findClientRepository) {
        this.findClientRepository = findClientRepository;
        this.genericMapper = new GenericMapper<>(ClientDTO.class);
    }

    @Override
    public ClientDTO exec(IGenericPort param) throws ClientNotExistsException {
        var clientDto = genericMapper.map(param);
        var optionalClient = findClientRepository.exec(clientDto.cpf());
        var client = optionalClient.orElseThrow(() -> new ClientNotExistsException("Client not exists in database"));
        return client.build();
    }
}
