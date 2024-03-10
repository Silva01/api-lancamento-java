package silva.daniel.project.app.domain.client;

import br.net.silva.daniel.shared.application.repository.ApplicationBaseRepository;
import br.net.silva.daniel.shared.application.repository.ParamRepository;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Component;
import silva.daniel.project.app.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@Component
public class CreateClientRepositoryImpl implements ApplicationBaseRepository<ClientOutput> {

    private final ClientRepository repository;
    private final Mapper<ClientOutput, Client> mapper;

    public CreateClientRepositoryImpl(ClientRepository repository, Mapper<ClientOutput, Client> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public boolean deleteById(ParamRepository param) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public Optional<ClientOutput> findById(ParamRepository param) {
        return Optional.empty();
    }

    @Override
    public List<ClientOutput> findAll() {
        return null;
    }

    @Override
    public ClientOutput save(ClientOutput output) {
        var entity = mapper.mapTo(output);
        return mapper.mapTo(repository.save(entity));
    }

    @Override
    public void saveAll(List<ClientOutput> paramList) {

    }
}
