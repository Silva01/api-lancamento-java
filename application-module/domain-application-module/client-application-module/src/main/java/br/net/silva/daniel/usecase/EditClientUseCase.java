package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
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
    private final Repository<Optional<ClientOutput>> findRepository;
    private final Repository<ClientOutput> saveRepository;
    private final GenericResponseMapper mapper;

    public EditClientUseCase(Repository<Optional<ClientOutput>> findRepository, Repository<ClientOutput> saveRepository, GenericResponseMapper mapper) {
        this.findRepository = findRepository;
        this.saveRepository = saveRepository;
        this.mapper = mapper;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        try {
            var input = (EditClientInput) param.input();
            var clientOutput = findRepository.exec(input.cpf()).orElseThrow(() -> new ClientNotExistsException("Client not exists"));

            var client = ClientBuilder.buildAggregate().createFrom(clientOutput);
            client.editName(input.name());
            client.editTelephone(input.telephone());

            var response = saveRepository.exec(client);

            mapper.fillIn(ClientBuilder.buildFullClientDto().createFrom(response), param.output());
            return response;
        } catch (GenericException ge) {
            throw ge;
        } catch (Exception e) {
            throw new GenericException("Generic error");
        }
    }
}
