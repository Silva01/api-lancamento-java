package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.FindApplicationBaseRepository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class FindClientUseCase implements UseCase<ClientOutput> {

    private final FindApplicationBaseRepository<ClientOutput> findClientRepository;
    private final GenericResponseMapper factory;

    public FindClientUseCase(FindApplicationBaseRepository<ClientOutput> findClientRepository, GenericResponseMapper factory) {
        this.findClientRepository = findClientRepository;
        this.factory = factory;
    }

    @Override
    public ClientOutput exec(Source param) throws ClientNotExistsException {
        var clientRequestDTO = (ICpfParam) param.input();
        var optionalClient = findClientRepository.findById(clientRequestDTO);
        var client = optionalClient.orElseThrow(() -> new ClientNotExistsException("Client not exists in database"));
        factory.fillIn(ClientBuilder.buildFullClientDto().createFrom(client), param.output());
        return client;
    }
}
