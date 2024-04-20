package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
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
import br.net.silva.daniel.value_object.output.ClientOutput;


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

    //TODO: This method need refactor urgent, it's are very ugly
    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var clientRequest = (IClientParam) param.input();
        var clientOpt = findGateway.findById(clientRequest);

        execValidate(clientOpt);

        var addressRequestDto = clientRequest.address();

        final var clientDto = createNewClient(addressRequestDto, clientRequest);

        var clientOutput = saveRepository.save(ClientBuilder.buildFullClientOutput().createFrom(clientDto));
        factory.fillIn(clientOutput, param.output());
        return clientOutput;
    }

    private static ClientDTO createNewClient(IAddressParam addressRequestDto, IClientParam clientRequest) {
        var address = new AddressDTO(addressRequestDto.street(), addressRequestDto.number(), addressRequestDto.complement(), addressRequestDto.neighborhood(), addressRequestDto.state(), addressRequestDto.city(), addressRequestDto.zipCode());
        return new ClientDTO(clientRequest.cpf(), clientRequest.name(), clientRequest.telephone(), true, address);
    }
}
