package br.net.silva.daniel.usecase;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.ToClientMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.utils.ConverterUtils;
import br.net.silva.daniel.value_object.Source;

public class ActivateClientUseCase implements UseCase {

    private final Repository<Client> activateClientRepository;
    private final ToClientMapper mapper;

    public ActivateClientUseCase(Repository<Client> activateClientRepository) {
        this.activateClientRepository = activateClientRepository;
        this.mapper = ToClientMapper.INSTANCE;
    }

    @Override
    public void exec(Source param) throws GenericException {
        var dto = mapper.toClientRequestDTO(param);
        var client = activateClientRepository.exec(dto.cpf());
        param.map().put(TypeClientMapperEnum.CLIENT.name(), client.build());
    }
}
