package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.shared.application.annotations.ValidateOn;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.value_object.output.ClientOutput;

@ValidateOn(validations = ClientExistsValidate.class)
public class FindClientUseCase implements UseCase<ClientOutput> {

    private final FindApplicationBaseGateway<ClientOutput> findClientRepository;
    private final GenericResponseMapper factory;

    public FindClientUseCase(FindApplicationBaseGateway<ClientOutput> findClientRepository, GenericResponseMapper factory) {
        this.findClientRepository = findClientRepository;
        this.factory = factory;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var clientRequestDTO = (ICpfParam) param.input();
        var optionalClient = findClientRepository.findById(clientRequestDTO);

        var client = execValidate(optionalClient).extract();

        factory.fillIn(ClientBuilder.buildFullClientDto().createFrom(client), param.output());
        return client;
    }
}
