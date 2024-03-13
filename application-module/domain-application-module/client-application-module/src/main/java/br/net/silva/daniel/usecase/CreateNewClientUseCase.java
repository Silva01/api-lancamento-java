package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.shared.application.interfaces.IClientParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class CreateNewClientUseCase implements UseCase<ClientOutput> {
    private final SaveApplicationBaseGateway<ClientOutput> saveRepository;
    private final GenericResponseMapper factory;

    public CreateNewClientUseCase(SaveApplicationBaseGateway<ClientOutput> saveRepository, GenericResponseMapper factory) {
        this.saveRepository = saveRepository;
        this.factory = factory;
    }

    //TODO: This method need refactor urgent, it's are very ugly
    @Override
    public ClientOutput exec(Source param) throws ExistsClientRegistredException {
        try {
            var clientRequest = (IClientParam) param.input();
            var addressRequestDto = clientRequest.address();

            var address = new AddressDTO(addressRequestDto.street(), addressRequestDto.number(), addressRequestDto.complement(), addressRequestDto.neighborhood(), addressRequestDto.state(), addressRequestDto.city(), addressRequestDto.zipCode());
            var clientDto = new ClientDTO(clientRequest.cpf(), clientRequest.name(), clientRequest.telephone(), clientRequest.active(), address);

            var clientOutput = saveRepository.save(ClientBuilder.buildFullClientOutput().createFrom(clientDto));
            factory.fillIn(clientOutput, param.output());
            return clientOutput;
        } catch (Exception e) {
            throw new ExistsClientRegistredException(e.getMessage());
        }
    }
}
