package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class FindClientUseCase implements UseCase<ClientOutput> {

    private final Repository<Optional<Client>> findClientRepository;
    private final GenericResponseMapper factory;

    public FindClientUseCase(Repository<Optional<Client>> findClientRepository, GenericResponseMapper factory) {
        this.findClientRepository = findClientRepository;
        this.factory = factory;
    }

    @Override
    public ClientOutput exec(Source param) throws ClientNotExistsException {
        var clientRequestDTO = (ICpfParam) param.input();
        var optionalClient = findClientRepository.exec(clientRequestDTO.cpf());
        var client = optionalClient.orElseThrow(() -> new ClientNotExistsException("Client not exists in database"));
        factory.fillIn(client.build(), param.output());
        return ClientBuilder.buildFullClientOutput().createFrom(client.build());
    }
}
