package br.net.silva.daniel.facade;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IFacade;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.FindClientUseCase;

import java.util.Objects;
import java.util.Optional;

public class ValidateExistsClientFacade implements IFacade<String, Boolean> {

    private final UseCase<String, ClientDTO> useCase;
    private static final boolean CLIENT_NOT_EXISTS = false;

    public ValidateExistsClientFacade(Repository<Optional<Client>> findClientRepository) {
        this.useCase = new FindClientUseCase(findClientRepository);
    }

    @Override
    public Boolean execute(String cpf) {
        try {
            var client = useCase.exec(cpf);
            return Objects.nonNull(client);
        } catch (GenericException e) {
            return CLIENT_NOT_EXISTS;
        }
    }
}
