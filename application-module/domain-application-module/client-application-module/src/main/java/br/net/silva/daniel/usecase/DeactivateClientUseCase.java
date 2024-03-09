package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateClientByDtoFactory;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.ApplicationBaseRepository;
import br.net.silva.daniel.repository.SaveApplicationBaseRepository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class DeactivateClientUseCase implements UseCase<ClientOutput> {

    private final FindClientUseCase findClientUseCase;
    private final SaveApplicationBaseRepository<ClientOutput> saveRepository;
    private final GenericResponseMapper genericFactory;

    private final IFactoryAggregate<Client, ClientDTO> factory;

    public DeactivateClientUseCase(ApplicationBaseRepository<ClientOutput> baseRepository, GenericResponseMapper genericFactory) {
        this.genericFactory = genericFactory;
        this.findClientUseCase = new FindClientUseCase(baseRepository, genericFactory);
        this.saveRepository = baseRepository;
        this.factory = new CreateClientByDtoFactory();
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var clientDto = findClientUseCase.exec(param);
        var client = factory.create(ClientBuilder.buildFullClientDto().createFrom(clientDto));
        client.deactivate();

        var clientUpdated = saveRepository.save(ClientBuilder.buildFullClientOutput().createFrom(client.build()));
        genericFactory.fillIn(ClientBuilder.buildFullClientDto().createFrom(clientUpdated), param.output());

        return clientUpdated;
    }
}
