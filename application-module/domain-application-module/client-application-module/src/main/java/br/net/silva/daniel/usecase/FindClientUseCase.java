package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;

import java.util.Optional;

public class FindClientUseCase implements UseCase<String, ClientDTO> {

    private final Repository<Optional<Client>> repository;

    public FindClientUseCase(Repository<Optional<Client>> repository) {
        this.repository = repository;
    }

    @Override
    public ClientDTO exec(String cpf) throws ClientNotExistsException {
        var optionalClient = repository.exec(cpf);
        var client = optionalClient.orElseThrow(() -> new ClientNotExistsException("Client not exists in database"));
        return client.create();
    }
}
