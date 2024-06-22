package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.factory.CreateClientByDtoFactory;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IClientParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.validation.ClientExistsAndActivedValidate;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

@ValidateStrategyOn(validations = {ClientExistsAndActivedValidate.class})
public final class DeactivateClientUseCase implements UseCase<ClientOutput> {

    private final FindApplicationBaseGateway<ClientOutput> findApplicationBaseGateway;
    private final SaveApplicationBaseGateway<ClientOutput> saveRepository;
    private final GenericResponseMapper genericFactory;

    private final IFactoryAggregate<Client, ClientDTO> factory;

    public DeactivateClientUseCase(ApplicationBaseGateway<ClientOutput> baseRepository, GenericResponseMapper genericFactory) {
        this.genericFactory = genericFactory;
        this.findApplicationBaseGateway = baseRepository;
        this.saveRepository = baseRepository;
        this.factory = new CreateClientByDtoFactory();
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var clientOutput = execValidate(getClient(param)).extract();

        var client = factory.create(ClientBuilder.buildFullClientDto().createFrom(clientOutput));
        client.deactivate();

        var clientUpdated = saveRepository.save(ClientBuilder.buildFullClientOutput().createFrom(client.build()));
        genericFactory.fillIn(client.build(), param.output());

        return clientUpdated;
    }

    private Optional<ClientOutput> getClient(Source param) {
        return findApplicationBaseGateway.findById(((IClientParam) param.input()));
    }
}
