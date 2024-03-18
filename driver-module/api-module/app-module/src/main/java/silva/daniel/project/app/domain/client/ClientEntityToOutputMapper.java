package silva.daniel.project.app.domain.client;

import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Component;
import silva.daniel.project.app.mapper.Mapper;

@Component
public class ClientEntityToOutputMapper implements Mapper<ClientOutput, Client>{
    @Override
    public ClientOutput mapTo(Client entity) {
        var addressOutput = new AddressOutput(
                entity.getAddress().getStreet(),
                entity.getAddress().getNumber(),
                entity.getAddress().getComplement(),
                entity.getAddress().getNeighborhood(),
                entity.getAddress().getState(),
                entity.getAddress().getCity(),
                entity.getAddress().getZipCode());

        return new ClientOutput(
                entity.getAggregateId(),
                entity.getCpf(),
                entity.getName(),
                entity.getTelephone(),
                entity.isActive(),
                addressOutput);
    }

    @Override
    public Client mapTo(ClientOutput output) {
        var address = new Address(
                null,
                output.address().street(),
                output.address().number(),
                output.address().complement(),
                output.address().neighborhood(),
                output.address().state(),
                output.address().city(),
                output.address().zipCode());

        return new Client(
                output.cpf(),
                output.id(),
                output.name(),
                output.telephone(),
                output.active(),
                address);
    }
}
