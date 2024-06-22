package br.net.silva.daniel.usecase;

import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IAddressParam;
import br.net.silva.daniel.shared.application.interfaces.IClientParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.validation.ClientNotExistsValidate;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.UUID;


@ValidateStrategyOn(validations = {ClientNotExistsValidate.class})
public final class CreateNewClientUseCase implements UseCase<ClientOutput> {
    private final SaveApplicationBaseGateway<ClientOutput> saveRepository;
    private final FindApplicationBaseGateway<ClientOutput> findGateway;
    private final GenericResponseMapper factory;

    public CreateNewClientUseCase(ApplicationBaseGateway<ClientOutput> baseGateway, GenericResponseMapper factory) {
        this.saveRepository = baseGateway;
        this.findGateway = baseGateway;
        this.factory = factory;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var clientRequest = (IClientParam) param.input();
        var clientOpt = findGateway.findById(clientRequest);

        execValidate(clientOpt);

        var newClient = saveRepository.save(createNewClient(clientRequest.address(), clientRequest));
        buildResponse(param, newClient);
        return newClient;
    }

    private void buildResponse(Source param, ClientOutput clientOutput) {
        factory.fillIn(clientOutput, param.output());
    }

    private static ClientOutput createNewClient(IAddressParam addressRequestDto, IClientParam clientRequest) {
        var address = new AddressOutput(addressRequestDto.street(), addressRequestDto.number(), addressRequestDto.complement(), addressRequestDto.neighborhood(), addressRequestDto.state(), addressRequestDto.city(), addressRequestDto.zipCode());
        return new ClientOutput(UUID.randomUUID().toString(), clientRequest.cpf(), clientRequest.name(), clientRequest.telephone(), true, address);
    }
}
