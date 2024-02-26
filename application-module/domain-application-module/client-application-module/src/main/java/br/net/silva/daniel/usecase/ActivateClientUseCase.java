package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IClientParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class ActivateClientUseCase implements UseCase<ClientOutput> {

    private final Repository<Client> activateClientRepository;

    public ActivateClientUseCase(Repository<Client> activateClientRepository) {
        this.activateClientRepository = activateClientRepository;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var dto = (IClientParam) param.input();
        return ClientBuilder.buildFullClientOutput().createFrom(activateClientRepository.exec(dto.cpf()).build());
    }
}
