package silva.daniel.project.app.domain.client;

import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Component;
import silva.daniel.project.app.mapper.Mapper;

@Component
public class CreateClientRepositoryImpl implements Repository<ClientOutput> {

    private final ClientRepository repository;
    private final Mapper<ClientOutput, Client> mapper;

    public CreateClientRepositoryImpl(ClientRepository repository, Mapper<ClientOutput, Client> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ClientOutput exec(Object... args) {
        var output = (ClientOutput) args[0];
        var clientEntity = mapper.mapTo(output);
        var newClient = repository.save(clientEntity);
        return mapper.mapTo(newClient);
    }
}
