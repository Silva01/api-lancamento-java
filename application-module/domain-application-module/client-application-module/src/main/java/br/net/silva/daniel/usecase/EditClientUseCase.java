package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.EditClientInput;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class EditClientUseCase implements UseCase<ClientOutput> {
    private final Repository<Optional<Client>> findRepository;
    private final Repository<Client> saveRepository;
    private final GenericResponseMapper mapper;

    public EditClientUseCase(Repository<Optional<Client>> findRepository, Repository<Client> saveRepository, GenericResponseMapper mapper) {
        this.findRepository = findRepository;
        this.saveRepository = saveRepository;
        this.mapper = mapper;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        try {
            var input = (EditClientInput) param.input();
            var client = findRepository.exec(input.cpf()).orElseThrow(() -> new ClientNotExistsException("Client not exists"));
            client.editName(input.name());
            client.editTelephone(input.telephone());

            var response = saveRepository.exec(client).build();

            mapper.fillIn(response, param.output());
            return ClientBuilder.buildFullClientOutput().createFrom(response);
        } catch (GenericException ge) {
            throw ge;
        } catch (Exception e) {
            throw new GenericException("Generic error");
        }
    }
}
