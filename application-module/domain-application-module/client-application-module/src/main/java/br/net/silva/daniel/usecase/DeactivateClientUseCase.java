package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateClientByDtoFactory;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.FindApplicationBaseRepository;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class DeactivateClientUseCase implements UseCase<ClientOutput> {

    private final FindClientUseCase findClientUseCase;
    private final Repository<ClientOutput> saveRepository;
    private final GenericResponseMapper genericFactory;

    private final IFactoryAggregate<Client, ClientDTO> factory;

    public DeactivateClientUseCase(FindApplicationBaseRepository<ClientOutput> findClientRepository, Repository<ClientOutput> saveRepository, GenericResponseMapper genericFactory) {
        this.genericFactory = genericFactory;
        this.findClientUseCase = new FindClientUseCase(findClientRepository, genericFactory);
        this.saveRepository = saveRepository;
        this.factory = new CreateClientByDtoFactory();
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var clientDto = findClientUseCase.exec(param);
        var client = factory.create(ClientBuilder.buildFullClientDto().createFrom(clientDto));
        client.deactivate();

        var clientUpdated = saveRepository.exec(client);
        genericFactory.fillIn(ClientBuilder.buildFullClientDto().createFrom(clientUpdated), param.output());

        return clientUpdated;
    }
}
