package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;

public class ActivateClientUseCase implements UseCase<IProcessResponse<ClientDTO>> {

    private final Repository<Client> activateClientRepository;
    private final GenericMapper<ClientRequestDTO> mapper;

    public ActivateClientUseCase(Repository<Client> activateClientRepository) {
        this.activateClientRepository = activateClientRepository;
        this.mapper = new GenericMapper<>(ClientRequestDTO.class);
    }

    @Override
    public IProcessResponse<ClientDTO> exec(IGenericPort param) throws GenericException {
        var dto = mapper.map(param);
        return activateClientRepository.exec(dto.cpf());
    }
}
