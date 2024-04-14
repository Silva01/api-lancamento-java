package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.IClientParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public final class ActivateClientUseCase implements UseCase<ClientOutput> {

    private final ApplicationBaseGateway<ClientOutput> baseRepository;

    public ActivateClientUseCase(ApplicationBaseGateway<ClientOutput> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var dto = (IClientParam) param.input();
        var clientOutput = baseRepository.findById(dto).orElseThrow(() -> new ClientNotExistsException("Client not Found"));
        var aggregateClient = ClientBuilder.buildAggregate().createFrom(clientOutput);
        aggregateClient.activate();

        return baseRepository.save(ClientBuilder.buildFullClientOutput().createFrom(aggregateClient.build()));
    }
}
