package silva.daniel.project.app.domain.client;

import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Component;
import silva.daniel.project.app.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@Component
public class ClientGatewayImpl implements ApplicationBaseGateway<ClientOutput> {

    private final ClientRepository repository;
    private final Mapper<ClientOutput, Client> mapper;

    public ClientGatewayImpl(ClientRepository repository, Mapper<ClientOutput, Client> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public boolean deleteById(ParamGateway param) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public Optional<ClientOutput> findById(ParamGateway param) {
        var cpf = (ICpfParam) param;
        return repository
                .findByCpf(cpf.cpf()).map(client -> new ClientOutput(client.getAggregateId(), client.getCpf(), client.getName(), client.getTelephone(), client.isActive(), new AddressOutput(
                        client.getAddress().getStreet(), client.getAddress().getNumber(), client.getAddress().getComplement(), client.getAddress().getNeighborhood(), client.getAddress().getState(), client.getAddress().getCity(), client.getAddress().getZipCode())
                ));
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
