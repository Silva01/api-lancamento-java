package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.ArrayList;
import java.util.List;

public class ClientNotExistsValidate implements IValidations {
    private final UseCase<ClientOutput> findClientUseCase;

    public ClientNotExistsValidate(UseCase<ClientOutput> findClientUseCase) {
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
