package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.ApplicationBaseRepository;
import br.net.silva.daniel.repository.FindApplicationBaseRepository;
import br.net.silva.daniel.repository.SaveApplicationBaseRepository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.EditClientInput;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class EditClientUseCase implements UseCase<ClientOutput> {
    private final FindApplicationBaseRepository<ClientOutput> findRepository;
    private final SaveApplicationBaseRepository<ClientOutput> saveRepository;
    private final GenericResponseMapper mapper;

    public EditClientUseCase(ApplicationBaseRepository<ClientOutput> baseRepository, GenericResponseMapper mapper) {
        this.findRepository = baseRepository;
        this.saveRepository = baseRepository;
        this.mapper = mapper;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        try {
            var input = (EditClientInput) param.input();
            var clientOutput = findRepository.findById(input).orElseThrow(() -> new ClientNotExistsException("Client not exists"));

            var client = ClientBuilder.buildAggregate().createFrom(clientOutput);
            client.editName(input.name());
            client.editTelephone(input.telephone());

            var response = saveRepository.save(ClientBuilder.buildFullClientOutput().createFrom(client.build()));

            mapper.fillIn(ClientBuilder.buildFullClientDto().createFrom(response), param.output());
            return response;
        } catch (GenericException ge) {
            throw ge;
        } catch (Exception e) {
            throw new GenericException("Generic error");
        }
    }
}
