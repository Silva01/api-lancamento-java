package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.factory.CreateNewAddressFactory;
import br.net.silva.daniel.factory.CreateNewClientFactory;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.interfaces.IClientParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.value_object.Source;

public class CreateNewClientUseCase implements UseCase<ClientDTO> {
    private final Repository<Client> saveRepository;
    private final IFactoryAggregate<Client, ClientDTO> createNewClientFactory;
    private final GenericResponseMapper factory;

    public CreateNewClientUseCase(Repository<Client> saveRepository, GenericResponseMapper factory) {
        this.saveRepository = saveRepository;
        this.factory = factory;
        this.createNewClientFactory = new CreateNewClientFactory(new CreateNewAddressFactory());
    }

    //TODO: This method need refactor urgent, it's are very ugly
    @Override
    public ClientDTO exec(Source param) throws ExistsClientRegistredException {
        try {
            var clientRequestDto = (IClientParam) param.input();
            var addressRequestDto = clientRequestDto.address();

            var address = new AddressDTO(addressRequestDto.street(), addressRequestDto.number(), addressRequestDto.complement(), addressRequestDto.neighborhood(), addressRequestDto.state(), addressRequestDto.city(), addressRequestDto.zipCode());
            var clientDto = new ClientDTO(clientRequestDto.id(), clientRequestDto.cpf(), clientRequestDto.name(), clientRequestDto.telephone(), clientRequestDto.active(), address);

            var clientAggregate = saveRepository.exec(buildClient(clientDto));
            factory.fillIn(clientAggregate.build(), param.output());
            return clientAggregate.build();
        } catch (Exception e) {
            throw new ExistsClientRegistredException(e.getMessage());
        }
    }
    private Client buildClient(ClientDTO request) {
        return createNewClientFactory.create(request);
    }
}
