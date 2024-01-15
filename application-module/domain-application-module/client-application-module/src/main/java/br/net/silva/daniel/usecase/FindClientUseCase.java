package br.net.silva.daniel.usecase;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.ToClientMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.utils.ConverterUtils;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class FindClientUseCase implements UseCase {

    private final Repository<Optional<Client>> findClientRepository;
    private final ToClientMapper mapper;

    public FindClientUseCase(Repository<Optional<Client>> findClientRepository) {
        this.findClientRepository = findClientRepository;
        this.mapper = ToClientMapper.INSTANCE;
    }

    @Override
    public void exec(Source param) throws ClientNotExistsException {
        var clientRequestDTO = mapper.toClientRequestDTO(param.input());
        var optionalClient = findClientRepository.exec(clientRequestDTO.cpf());
        var client = optionalClient.orElseThrow(() -> new ClientNotExistsException("Client not exists in database"));
        param.map().put(TypeClientMapperEnum.CLIENT.name(), client.build());
    }
}
