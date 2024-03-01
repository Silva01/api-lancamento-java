package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.factory.CreateNewAddressFactory;
import br.net.silva.daniel.factory.CreateNewClientFactory;
import br.net.silva.daniel.interfaces.IClientParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class CreateNewClientUseCase implements UseCase<ClientOutput> {
    private final Repository<ClientOutput> saveRepository;
    private final IFactoryAggregate<Client, ClientDTO> createNewClientFactory;
    private final GenericResponseMapper factory;

    public CreateNewClientUseCase(Repository<ClientOutput> saveRepository, GenericResponseMapper factory) {
        this.saveRepository = saveRepository;
        this.factory = factory;
        this.createNewClientFactory = new CreateNewClientFactory(new CreateNewAddressFactory());
    }

    //TODO: This method need refactor urgent, it's are very ugly
    @Override
    public ClientOutput exec(Source param) throws ExistsClientRegistredException {
        try {
            var clientRequest = (IClientParam) param.input();
            var addressRequestDto = clientRequest.address();

            var address = new AddressDTO(addressRequestDto.street(), addressRequestDto.number(), addressRequestDto.complement(), addressRequestDto.neighborhood(), addressRequestDto.state(), addressRequestDto.city(), addressRequestDto.zipCode());
            var clientDto = new ClientDTO(clientRequest.id(), clientRequest.cpf(), clientRequest.name(), clientRequest.telephone(), clientRequest.active(), address);

            var clientOutput = saveRepository.exec(ClientBuilder.buildFullClientOutput().createFrom(clientDto));
            factory.fillIn(clientOutput, param.output());
            return clientOutput;
        } catch (Exception e) {
            throw new ExistsClientRegistredException(e.getMessage());
        }
    }
    private Client buildClient(ClientDTO request) {
        return createNewClientFactory.create(request);
    }
}
