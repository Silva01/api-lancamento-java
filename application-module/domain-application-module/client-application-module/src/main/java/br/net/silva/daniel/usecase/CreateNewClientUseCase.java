package br.net.silva.daniel.usecase;

import br.net.silva.daniel.mapper.ClientDtoMapper;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.factory.CreateNewAddressFactory;
import br.net.silva.daniel.factory.CreateNewClientFactory;
import br.net.silva.daniel.factory.IFactoryAggregate;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.IMapper;
import br.net.silva.daniel.repository.Repository;

public class CreateNewClientUseCase implements UseCase<ClientRequestDTO, ClientDTO> {
    private final Repository<Client> saveRepository;
    private final IFactoryAggregate<Client, ClientDTO> createNewClientFactory;
    private final IMapper<ClientDTO, ClientRequestDTO> clientMapper;

    public CreateNewClientUseCase(Repository<Client> saveRepository) {
        this.saveRepository = saveRepository;
        this.createNewClientFactory = new CreateNewClientFactory(new CreateNewAddressFactory());
        this.clientMapper = new ClientDtoMapper();
    }

    @Override
    public ClientDTO exec(ClientRequestDTO request) throws ExistsClientRegistredException {
        try {
            return saveRepository.exec(buildClient(request)).build();
        } catch (Exception e) {
            throw new ExistsClientRegistredException(e.getMessage());
        }
    }
    private Client buildClient(ClientRequestDTO request) {
        return createNewClientFactory.create(clientMapper.map(request));
    }
}
