package br.net.silva.daniel.validation;

import br.net.silva.business.dto.ChangePasswordDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;

public class ClientExistsValidate implements IValidations {

    private final GenericMapper<ChangePasswordDTO> mapper;
    private final UseCase<ClientDTO> findClientUseCase;

    public ClientExistsValidate(UseCase<ClientDTO> findClientUseCase) {
        this.findClientUseCase = findClientUseCase;
        this.mapper = new GenericMapper<>(ChangePasswordDTO.class);
    }

    @Override
    public void validate(IGenericPort param) throws GenericException {
        findClientUseCase.exec(param);
    }
}
