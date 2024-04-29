package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IClientParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.validation.ClientExistsAndDeactivatedValidate;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

@ValidateStrategyOn(validations = {ClientExistsAndDeactivatedValidate.class})
public final class ActivateClientUseCase implements UseCase<ClientOutput> {

    private final ApplicationBaseGateway<ClientOutput> baseRepository;

    public ActivateClientUseCase(ApplicationBaseGateway<ClientOutput> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        final var clientOpt = getClient(param);

        final var clientOutput = execValidate(clientOpt).extract();

        var aggregateClient = ClientBuilder.buildAggregate().createFrom(clientOutput);
        aggregateClient.activate();

        return baseRepository.save(ClientBuilder.buildFullClientOutput().createFrom(aggregateClient.build()));
    }

    private Optional<ClientOutput> getClient(Source param) {
        return baseRepository.findById((IClientParam) param.input());
    }
}
