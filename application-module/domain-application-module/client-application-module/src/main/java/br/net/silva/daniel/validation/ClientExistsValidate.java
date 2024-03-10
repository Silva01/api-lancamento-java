package br.net.silva.daniel.validation;

import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class ClientExistsValidate implements IValidations {
    private final UseCase<ClientOutput> findClientUseCase;

    public ClientExistsValidate(UseCase<ClientOutput> findClientUseCase) {
        this.findClientUseCase = findClientUseCase;
    }

    @Override
    public void validate(Source input) throws GenericException {
        try {
            findClientUseCase.exec(input);
        } catch (GenericException e) {
            throw new GenericException("Client not exists in database");
        }
    }
}
