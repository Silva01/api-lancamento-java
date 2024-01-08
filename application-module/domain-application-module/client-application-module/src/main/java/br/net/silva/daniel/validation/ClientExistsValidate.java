package br.net.silva.daniel.validation;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;

public class ClientExistsValidate implements IValidations {
    private final UseCase<IProcessResponse<ClientDTO>> findClientUseCase;

    public ClientExistsValidate(UseCase<IProcessResponse<ClientDTO>> findClientUseCase) {
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
