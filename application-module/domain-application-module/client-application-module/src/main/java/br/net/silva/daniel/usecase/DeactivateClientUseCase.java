package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;

import java.util.Optional;

public class DeactivateClientUseCase implements UseCase<Client> {

    private final FindClientUseCase findClientUseCase;
    private final Repository<Client> saveRepository;

    private final GenericMapper<ClientRequestDTO> mapper;

    public DeactivateClientUseCase(Repository<Optional<Client>> findClientRepository, Repository<Client> saveRepository) {
        this.findClientUseCase = new FindClientUseCase(findClientRepository);
        this.saveRepository = saveRepository;
        this.mapper = new GenericMapper<>(ClientRequestDTO.class);
    }

    @Override
    public Client exec(IGenericPort dto) throws GenericException {
        var request = mapper.map(dto);
        var client = findClientUseCase.exec(request);
        client.deactivate();

        return saveRepository.exec(client);
    }
}
