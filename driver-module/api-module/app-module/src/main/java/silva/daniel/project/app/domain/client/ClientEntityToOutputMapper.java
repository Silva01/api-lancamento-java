package silva.daniel.project.app.domain.client;

import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Component;
import silva.daniel.project.app.mapper.Mapper;

@Component
public class ClientEntityToOutputMapper implements Mapper<ClientOutput, Client>{
    @Override
    public boolean accept(Class<ClientOutput> output, Class<Client> entity) {
        return output.isInstance(ClientOutput.class) && entity.isInstance(Client.class);
    }

    @Override
    public ClientOutput mapTo(Client entity) {
        var addressOutput = new AddressOutput(
                entity.address().street(),
                entity.address().number(),
                entity.address().complement(),
                entity.address().neighborhood(),
                entity.address().city(),
                entity.address().state(),
                entity.address().zipCode());

        return new ClientOutput(
                entity.aggregateId(),
                entity.cpf(),
                entity.name(),
                entity.telephone(),
                entity.active(),
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
                output.address().city(),
                output.address().state(),
                output.address().zipCode());

        return new Client(
                null,
                output.id(),
                output.cpf(),
                output.name(),
                output.telephone(),
                output.active(),
                address);
    }
}
