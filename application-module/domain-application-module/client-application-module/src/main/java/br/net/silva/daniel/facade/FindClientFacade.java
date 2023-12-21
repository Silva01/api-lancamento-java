package br.net.silva.daniel.facade;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IFacade;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.FindClientUseCase;

import java.util.Optional;

public class FindClientFacade implements IFacade<String, ClientDTO> {
    private final UseCase<String, ClientDTO> useCase;

    public FindClientFacade(Repository<Optional<Client>> findClientRepository) {
        this.useCase = new FindClientUseCase(findClientRepository);
    }

    @Override
    public ClientDTO execute(String cpf) throws GenericException {
        return useCase.exec(cpf);
    }
}
