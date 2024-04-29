package br.net.silva.daniel.usecase;

import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.validation.ClientActivedValidation;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

@ValidateStrategyOn(validations = ClientActivedValidation.class)
public class FindActiveClientUseCase implements UseCase<ClientOutput> {

    private final FindClientUseCase findClientUseCase;

    public FindActiveClientUseCase(FindApplicationBaseGateway<ClientOutput> findClientRepository, GenericResponseMapper factory) {
        this.findClientUseCase = new FindClientUseCase(findClientRepository, factory);
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var clientOutput = findClientUseCase.exec(param);
        return execValidate(Optional.of(clientOutput)).extract();
    }
}
