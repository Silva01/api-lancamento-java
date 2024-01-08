package br.net.silva.daniel.validation;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;

public class ClientExistsValidate implements IValidations {
    private final UseCase<Client> findClientUseCase;

    public ClientExistsValidate(UseCase<Client> findClientUseCase) {
        this.findClientUseCase = findClientUseCase;
    }

    @Override
    public void validate(IGenericPort param) throws GenericException {
        try {
            findClientUseCase.exec(param);
        } catch (GenericException e) {
            throw new GenericException("Client not exists in database");
        }
    }
}
