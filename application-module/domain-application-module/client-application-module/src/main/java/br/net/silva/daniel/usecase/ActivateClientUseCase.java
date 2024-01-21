package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IClientParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class ActivateClientUseCase implements UseCase<ClientDTO> {

    private final Repository<Client> activateClientRepository;

    public ActivateClientUseCase(Repository<Client> activateClientRepository) {
        this.activateClientRepository = activateClientRepository;
    }

    @Override
    public ClientDTO exec(Source param) throws GenericException {
        var dto = (IClientParam) param.input();
        return activateClientRepository.exec(dto.cpf()).build();
    }
}
