package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.factory.CreateNewAddressFactory;
import br.net.silva.daniel.factory.CreateNewClientFactory;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.ToClientMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.utils.ConverterUtils;
import br.net.silva.daniel.value_object.Source;

public class CreateNewClientUseCase implements UseCase {
    private final Repository<Client> saveRepository;
    private final IFactoryAggregate<Client, ClientDTO> createNewClientFactory;
    private final ToClientMapper mapper;

    public CreateNewClientUseCase(Repository<Client> saveRepository) {
        this.saveRepository = saveRepository;
        this.createNewClientFactory = new CreateNewClientFactory(new CreateNewAddressFactory());
        this.mapper = ToClientMapper.INSTANCE;
    }

    @Override
    public void exec(Source param) throws ExistsClientRegistredException {
        try {
            var clientDto = mapper.toClientDTO(param);
            var clientAggregate = saveRepository.exec(buildClient(clientDto));
            param.map().put(TypeClientMapperEnum.CLIENT.name(), clientAggregate.build());
        } catch (Exception e) {
            throw new ExistsClientRegistredException(e.getMessage());
        }
    }
    private Client buildClient(ClientDTO request) {
        return createNewClientFactory.create(request);
    }
}
