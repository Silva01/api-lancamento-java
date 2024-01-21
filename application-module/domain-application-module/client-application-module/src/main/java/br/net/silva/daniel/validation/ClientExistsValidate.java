package br.net.silva.daniel.validation;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.value_object.Source;

public class ClientExistsValidate implements IValidations {
    private final UseCase<ClientDTO> findClientUseCase;

    public ClientExistsValidate(UseCase<ClientDTO> findClientUseCase) {
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
