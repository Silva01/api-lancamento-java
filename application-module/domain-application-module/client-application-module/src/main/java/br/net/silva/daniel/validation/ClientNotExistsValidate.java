package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.value_object.Source;

import java.util.ArrayList;
import java.util.List;

public class ClientNotExistsValidate implements IValidations {
    private final UseCase findClientUseCase;

    public ClientNotExistsValidate(UseCase findClientUseCase) {
        this.findClientUseCase = findClientUseCase;
    }

    @Override
    public void validate(Source input) throws GenericException {
        List<GenericException> errors = new ArrayList<>();

        try {
            findClientUseCase.exec(input);
        } catch (GenericException e) {
           errors.add(e);
        }

        if (errors.isEmpty()) {
            throw new GenericException("Client exists in database");
        }
    }
}
