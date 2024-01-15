package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateClientByDtoFactory;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.ToClientMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.business.value_object.Source;

import java.util.Optional;

public class DeactivateClientUseCase implements UseCase {

    private final FindClientUseCase findClientUseCase;
    private final Repository<Client> saveRepository;
    private final ToClientMapper mapper;

    private final IFactoryAggregate<Client, ClientDTO> factory;

    public DeactivateClientUseCase(Repository<Optional<Client>> findClientRepository, Repository<Client> saveRepository) {
        this.findClientUseCase = new FindClientUseCase(findClientRepository);
        this.saveRepository = saveRepository;
        this.mapper = ToClientMapper.INSTANCE;
        this.factory = new CreateClientByDtoFactory();
    }

    @Override
    public void exec(Source param) throws GenericException {
        findClientUseCase.exec(param);
        var client = factory.create((ClientDTO) param.map().get(TypeClientMapperEnum.CLIENT.name()));
        client.deactivate();

        var clientUpdated = saveRepository.exec(client);
        param.map().put(TypeClientMapperEnum.CLIENT.name(), clientUpdated.build());
    }
}
