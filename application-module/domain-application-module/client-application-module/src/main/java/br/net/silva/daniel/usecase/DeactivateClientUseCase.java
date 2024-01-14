package br.net.silva.daniel.usecase;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.ToClientMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.utils.ConverterUtils;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class DeactivateClientUseCase implements UseCase {

    private final FindClientUseCase findClientUseCase;
    private final Repository<Client> saveRepository;
    private final ToClientMapper mapper;

    public DeactivateClientUseCase(Repository<Optional<Client>> findClientRepository, Repository<Client> saveRepository) {
        this.findClientUseCase = new FindClientUseCase(findClientRepository);
        this.saveRepository = saveRepository;
        this.mapper = ToClientMapper.INSTANCE;
    }

    @Override
    public void exec(Source param) throws GenericException {
        var request = mapper.toClientRequestDTO(param);
        var client = findClientUseCase.exec(request);
        client.deactivate();

        var clientUpdated = saveRepository.exec(client);
        param.map().put(TypeClientMapperEnum.CLIENT.name(), ConverterUtils.convertJsonToMap(ConverterUtils.convertObjectToJson(clientUpdated.build())));
    }
}
