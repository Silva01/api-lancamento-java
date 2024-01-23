package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class FindClientUseCase implements UseCase<ClientDTO> {

    private final Repository<Optional<Client>> findClientRepository;
    private final GenericResponseMapper factory;

    public FindClientUseCase(Repository<Optional<Client>> findClientRepository, GenericResponseMapper factory) {
        this.findClientRepository = findClientRepository;
        this.factory = factory;
    }

    @Override
    public ClientDTO exec(Source param) throws ClientNotExistsException {
        var clientRequestDTO = (ICpfParam) param.input();
        var optionalClient = findClientRepository.exec(clientRequestDTO.cpf());
        var client = optionalClient.orElseThrow(() -> new ClientNotExistsException("Client not exists in database"));
        factory.fillIn(client.build(), param.output());
        return client.build();
    }
}
