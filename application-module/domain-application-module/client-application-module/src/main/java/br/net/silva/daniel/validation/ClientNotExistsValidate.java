package br.net.silva.daniel.validation;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;

import java.util.ArrayList;
import java.util.List;

public class ClientNotExistsValidate implements IValidations {
    private final UseCase<Client> findClientUseCase;

    public ClientNotExistsValidate(UseCase<Client> findClientUseCase) {
        this.findClientUseCase = findClientUseCase;
    }

    @Override
    public void validate(IGenericPort param) throws GenericException {
        List<GenericException> errors = new ArrayList<>();

        try {
            findClientUseCase.exec(param);
        } catch (GenericException e) {
           errors.add(e);
        }

        if (errors.isEmpty()) {
            throw new GenericException("Client exists in database");
        }
    }
}
