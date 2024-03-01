package br.net.silva.daniel.usecase;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IClientParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class ActivateClientUseCase implements UseCase<ClientOutput> {

    private final Repository<ClientOutput> activateClientRepository;

    public ActivateClientUseCase(Repository<ClientOutput> activateClientRepository) {
        this.activateClientRepository = activateClientRepository;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        var dto = (IClientParam) param.input();
        return activateClientRepository.exec(dto.cpf());
    }
}
